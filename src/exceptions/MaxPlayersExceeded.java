package exceptions;

public class MaxPlayersExceeded extends Exception {
    public MaxPlayersExceeded(String message) {
        super(message);
    }

    public static void check(int numberOfPlayers, int maxPlayers) throws MaxPlayersExceeded {
        if(numberOfPlayers>maxPlayers)
            throw new MaxPlayersExceeded("maximum number of players allowed -> "+maxPlayers);
    }
}
