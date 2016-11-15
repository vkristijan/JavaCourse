package hr.fer.zemris.java.tecaj_13;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * Initializes the database connection and checks if the required tables are
 * created or not. If the tables are not already created, they will be created.
 * If the polls table is empty, data from configuration files will be added.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebListener
public class Initialization implements ServletContextListener {
	/**
	 * The relative path to the file defining the database settings, used to
	 * create the connection string.
	 */
	private static String CONFIG_FILE = "/WEB-INF/dbsettings.properties";
	/**
	 * The relative path to the folder containing poll configuration files.
	 */
	private static String POLL_CONFIG_PATH = "/WEB-INF/pollConfig/";
	/**
	 * The relative path to the file defining the polls.
	 */
	private static String POLLS_PATH = POLL_CONFIG_PATH + "polls.txt";
	
	/**
	 * SQL command to create the polls table. Used to create the table while
	 * initializing the database.
	 */
	private static String CREATE_POLLS = 
			"CREATE TABLE Polls (" +
			"	id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
			"	title VARCHAR(150) NOT NULL," +
			"	message CLOB(2048) NOT NULL )";
	
	/**
	 * SQL command to create the poll options table. Used to create the table
	 * while initializing the database.
	 */
	private static String CREATE_POOL_OPTIONS = 
			"CREATE TABLE PollOptions (" +
			"	id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
			"	optionTitle VARCHAR(100) NOT NULL," +
			"	optionLink VARCHAR(150) NOT NULL," +
			"	pollID BIGINT," +
			"	votesCount BIGINT," +
			"	FOREIGN KEY (pollID) REFERENCES Polls(id) )";
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Properties dbProperties = new Properties();
		
		String filePath = sce.getServletContext().getRealPath(CONFIG_FILE);
		
		try (InputStream is = new FileInputStream(filePath);){
			dbProperties.load(is);
		} catch (FileNotFoundException e) {
			System.err.println("The given file has not been found!");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Unable to read from the given file!");
			System.exit(1);
		}
		
		String dbName = dbProperties.getProperty("name");
		String dbHost = dbProperties.getProperty("host");
		String dbPort = dbProperties.getProperty("port");
		String dbUser = dbProperties.getProperty("user");
		String dbPassword = dbProperties.getProperty("password");
		
		Objects.requireNonNull(dbName, "The database name was not given in the config file!");
		Objects.requireNonNull(dbHost, "The database host was not given in the config file!");
		Objects.requireNonNull(dbPort, "The database port was not given in the config file!");
		Objects.requireNonNull(dbUser, "The database user name was not given in the config file!");
		Objects.requireNonNull(dbPassword, "The database password was not given in the config file!");
		
		String connectionURL = "jdbc:derby://" + dbHost + ":" + dbPort + "/" + 
				dbName + ";user=" + dbUser + ";password=" + dbPassword;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		
		createTables(cpds, sce);
		populateTables(cpds, sce);
	}

	/**
	 * If there are no polls defined in the polls table, this method will read
	 * the poll configuration files and add the polls defined there into the
	 * polls table.
	 * 
	 * @param cpds
	 *            the {@link ComboPooledDataSource} used to get the connection.
	 * @param sce
	 *            the {@link ServletContextEvent}
	 */
	private void populateTables(ComboPooledDataSource cpds, ServletContextEvent sce) {
		Connection connection = null;
		try {
			connection = cpds.getConnection();
			
			String command = "SELECT COUNT(*) FROM Polls";
			PreparedStatement statement = connection.prepareStatement(command);
			ResultSet result= statement.executeQuery();
			result.next();
			
			if (!result.getString(1).equals("0")){
				return;
			}
			
			String filePath = sce.getServletContext().getRealPath(POLLS_PATH);
			Path pollsPath = Paths.get(filePath);
			if (!Files.exists(pollsPath)){
				System.err.println("The file defining the polls does not exist!");
			}
			
			List<String> polls = Files.readAllLines(pollsPath);
			for (String poll : polls){
				createPoll(poll, connection, sce);
			}
			
		} catch (SQLException e) {
			System.err.println("Unable to create required tables!");
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println("Unable to read polls definitions!");
		}
		
	}

	/**
	 * For the given file name of the polls properties file, reads the poll name
	 * and message, and adds it to the polls table. This method will also read
	 * all the poll options and add them to the appropriate table.
	 * 
	 * @param poll
	 *            the file name of the poll properties file.
	 * @param connection
	 *            the database connection that should be used to add the data
	 *            into tables.
	 * @param sce
	 *            the {@link ServletContextEvent}.
	 * @throws SQLException
	 *             if a database access error occurs; this method is called on a
	 *             closed PreparedStatement or an argument is supplied to this
	 *             method
	 * @throws IOException
	 *             if an error occurred when reading from the input stream.
	 */
	private void createPoll(String poll, Connection connection, ServletContextEvent sce) 
			throws SQLException, IOException {
		
		Properties pollProperties = new Properties();
		String pollProp = sce.getServletContext().getRealPath(POLL_CONFIG_PATH + poll + ".properties");
		
		try (InputStream is = new FileInputStream(pollProp);){
			pollProperties.load(is);
		} catch (FileNotFoundException e) {
			System.err.println("The given properties file has not been found!");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Unable to read from the given file!");
			System.exit(1);
		}
		
		String command = "INSERT INTO Polls (title, message) VALUES (?, ?)";
		PreparedStatement statement = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, pollProperties.getProperty("title"));
		statement.setString(2, pollProperties.getProperty("message"));
		
		statement.execute();
		ResultSet result = statement.getGeneratedKeys();
		if (result == null) return;
		result.next();
		long id = result.getLong(1);
		
		String options = pollProperties.getProperty("options");
		String optionsFilePath = sce.getServletContext().getRealPath(POLL_CONFIG_PATH + options);
		Path optionsPath = Paths.get(optionsFilePath);
		
		if (!Files.exists(optionsPath)){
			System.err.println("The file defining the poll options does not exist!");
		}
		
		command = "INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)";
		statement = connection.prepareStatement(command);
		statement.setString(3, String.valueOf(id));
		statement.setString(4, "0");
		
		List<String> optionLines = Files.readAllLines(optionsPath);
		for (String option : optionLines){
			String[] optionData = option.split("\t");
			if (optionData.length != 2){
				System.err.println("Invalid option format!");
			}
			
			statement.setString(1, optionData[0]);
			statement.setString(2, optionData[1]);
			
			statement.execute();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Checks if the polls table and the poll options table exist in the
	 * database, and creates them if necessary.
	 * 
	 * @param cpds
	 *            the {@link ComboPooledDataSource}.
	 * @param sce
	 *            the {@link ServletContextEvent}.
	 */
	private void createTables(ComboPooledDataSource cpds, ServletContextEvent sce) {
		Connection connection = null;
		try {
			connection = cpds.getConnection();
			
			if (!tableExists("Polls", connection)){
				PreparedStatement statement = connection.prepareStatement(CREATE_POLLS);
				statement.execute();
			}
			
			if (!tableExists("PollOptions", connection)){
				PreparedStatement statement = connection.prepareStatement(CREATE_POOL_OPTIONS);
				statement.execute();
			}
			
		} catch (SQLException e) {
			System.err.println("Unable to create required tables!");
			System.err.println(e.getMessage());
		}
	}

	
	
	/**
	 * Checks if the table with the given table name exists.
	 * 
	 * @param tableName
	 *            the name of the table that should be checked.
	 * @param con
	 *            the connection to the database.
	 * @return a boolean value, true if the table exists in the database, false
	 *         otherwise.
	 * @throws SQLException
	 *             if a database access error occurs.
	 */
	private boolean tableExists(String tableName, Connection con) throws SQLException {
		ResultSet res = con.getMetaData().getTables(null, null, tableName.toUpperCase(), null);
		if (res.next()) {
			return true;
		} else {
			return false;
		}
	}
	

}