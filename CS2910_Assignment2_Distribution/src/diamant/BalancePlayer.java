package diamant;

import java.util.Random;

public class BalancePlayer extends diamant.Player {

    public BalancePlayer(diamant.Pawn playerColour, int playerNumber)
    {
        super(playerColour, playerNumber);
    }

    /** A balanced player will stay in the mine until 2 trap cards have been revealed and then have a 50/50 chance of leaving.
     * @param numTraps - number of traps revealed
     * @param numRubies - number of rubies in the mine
     * @param numPlayers - number of players in the mine
     * @param cardsRemain - number of cards left ot be revealed in the deck
     * @return return true if leaving the mine, false otherwise.
     */
    public boolean leaveMine(int numTraps, int numRubies, int numPlayers, int cardsRemain)
    {
        Random rand = new Random();

        if(numTraps >=2)
        {
            int chance = rand.nextInt(100);
            if(chance >= 51) {
                this.leave = true;
                this.inMine = false;
                return true;
            }
        }
        this.leave = false;
        this.inMine = true;

        return false;
    }

    public String reportStrategy()
    {
        return "Balance";
    }

}
