package hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.command;

/**
 * Abstract implementation of the {@link Command} interface. Capable of
 * returning the command name.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public abstract class AbstractCommand implements Command{
	
	/** The command name. */
	private String name;
	
	/**
	 * Instantiates a new abstract command.
	 *
	 * @param name the name
	 */
	protected AbstractCommand(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
}
