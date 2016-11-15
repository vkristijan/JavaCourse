package hr.fer.zemris.java.tecaj.hw07.shell;

/**
 * Defines the status of the shell after executing a command. <br>
 * There are two supported statuses:
 * <ul>
 * <li><strong>CONTINUE</strong> if the shell should continue accepting and
 * executing other commands after this command.</li>
 * <li><strong>TERMINATE</strong> if the shell should be terminated after
 * executing the given command.</li>
 * </ul>
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public enum ShellStatus {
	/**
	 * A state where the shell should continue accepting and executing other
	 * commands after this command.
	 */
	CONTINUE,
	/**
	 * A state where the shell should be terminated after executing the given
	 * command.
	 */
	TERMINATE;

}
