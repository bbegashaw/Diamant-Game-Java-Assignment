package diamant;

public class RiskyPlayer extends Player{

    public RiskyPlayer(diamant.Pawn playerColour, int playerNumber)
    {
        super(playerColour, playerNumber);
    }

    /** A risky player will only return to the surface if they think they can be guaranteed more than 10 rubies on the way
     * out of the mine.
     * @param numTraps - number of traps revealed
     * @param numRubies - number of rubies in the mine
     * @param numPlayers - number of players in the mine at the start of the round
     * @param cardsRemain - number of cards left ot be revealed in the deck
     * @return true if player will be leaving the mine, false if they are remaining in the mine
     *
     */
    public boolean leaveMine(int numTraps, int numRubies, int numPlayers, int cardsRemain)
    {
       if(numRubies/numPlayers < 10)
        {
            this.leave = false;
            this.inMine = true;
            return false;
        }
        this.leave = true;
        this.inMine = false;

        return true;
    }

    public String reportStrategy()
    {
        return "Risky";
    }

}
