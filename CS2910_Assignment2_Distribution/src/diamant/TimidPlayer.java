package diamant;

public class TimidPlayer extends diamant.Player {

    public TimidPlayer(diamant.Pawn playerColour, int playerNumber)
    {
        super(playerColour, playerNumber);
    }

    /** A timid player will leave a mine as soon as one trap appears.
     * @param numTraps - number of traps revealed
     * @param numRubies - number of rubies in the mine
     * @param numPlayers - number of players in the mine
     * @param cardsRemain - number of cards left ot be revealed in the deck
     * @return
     */
    public boolean leaveMine(int numTraps, int numRubies, int numPlayers, int cardsRemain)
    {
        if (numTraps>=1)
        {
            this.leave = true;
            this.inMine = false;
            return true;

        }
        this.leave = false;
        this.inMine = true;

        return false;
    }

    public String reportStrategy()
    {
        return "Timid";
    }
}
