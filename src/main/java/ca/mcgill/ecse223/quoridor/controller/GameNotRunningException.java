package ca.mcgill.ecse223.quoridor.controller;

public class GameNotRunningException extends Exception{
	private static final long serialVersionUID = -45678127364712894L;

    /**
     * Constructor.
     *
     * @param errorMessage Error message to be displayed
     */
    public GameNotRunningException(String errorMessage) {
        super(errorMessage);
    }
}
