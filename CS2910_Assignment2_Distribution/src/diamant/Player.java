package diamant;

import static diamant.Pawn.*;

public abstract class Player {

    // amount of treasure collected throughout expeditions
    protected int treasureChest = 0;
    // player pawn colour to distinguish using enum diamant.Pawn
    protected diamant.Pawn playerColour;

    // player number to uniquely identify a player
    protected int playerNumber = 0;
    // Stores last decision by a player; false if staying, true if leaving
    protected boolean leave = false;

    // Stores whether a player is currently in a mine, true if in the mine, false otherwise.
    protected boolean inMine = false;

    /** Constructor for a new Player
     *
     * @param playerColour the colour of pawn assigned from the player as determined by the Pawn enumeration.
     * @param playerNumber the number assigned to the player.
     */
    public Player(diamant.Pawn playerColour, int playerNumber) {
        this.playerColour = playerColour;
        this.playerNumber = playerNumber;
    }

    /** Identify if a player is in the mine or not.
     *
     * @return true if the player is in the mine, false otherwise.
     */
    public boolean isInMine() {
        return inMine;
    }

    /** Put a player in a mine, usually at the beginning of an expedition. */
    public void enterMine() {
        this.inMine = true;
    }

    /** Pull a player out of a mine. */
    public void exitMine() {
        this.inMine = false;
    }

    /** Retrieve the value of the player's personal treasure chest.
     *
     * @return the number of rubies currently stored in the player's chest.
     */
    public int getTreasureChest() {
        return treasureChest;
    }

    /** Update the value of the player's personal treasure chest.
     *
     * @param treasureChest the number of rubies to set the treasure chest value to.
     */
    public void setTreasureChest(int treasureChest) {
        this.treasureChest = treasureChest;
    }

    /** retrieve a Player's colour.
     *
     * @return the player's pawn colour as a value from the Pawn enumeration
     */
    public diamant.Pawn getPlayerColour() {
        return playerColour;
    }

    /** Update a Player's player colour.
     *
     * @param playerColour the colour of a player's pawn
     */
    public void setPlayerColour(diamant.Pawn playerColour) {
        this.playerColour = playerColour;
    }

    /** Retrieve a Player's player number
     *
     * @return the player's identifying number
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /** update a player's number.
     *
     * @param playerNumber the number assigned to a player in the game
     */
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }


    /** Determine if a player is leaving the mine based on their last made decision with leaveMine.
     * @return boolean - false if the player is staying in the mine, true if they are leaving the mine
     * */
    public boolean isLeaving(){
        return leave;
    }

    /** Determine if a player is staying in the mine based on their last made decision with leaveMine.
     *
     * @return boolean - false if the player is leaving the mine, true if they are staying in the mine.
     */
    public boolean isStaying(){
        return !leave;
    }

    /**
     * Reports the player attributes as a string.
     * @return Returns a string with the unique player number and the colour of their pawn as a string formatted for display.
     */
    public String toString()
    {
        String outStr = "Player Number: "+playerNumber+"; Pawn: ";


        if (playerColour== RED)
            outStr = outStr+"Red";
        else if (playerColour == BLUE)
            outStr = outStr+"Blue";
        else if (playerColour == BLACK)
            outStr = outStr+"Black";
        else if (playerColour == YELLOW)
            outStr = outStr+"Yellow";
        else if (playerColour == GREEN)
            outStr = outStr+"Green";
        else
            outStr = outStr+"Purple";

        outStr = outStr + "; Player Strategy: " + this.reportStrategy() + "; Player Treasure: "+treasureChest+".";

        return outStr;
    }

    /**
     * Report the strategy the player uses as a string.
     * @return Each player returns the strategy it is using based on the class type.
     */
    public abstract String reportStrategy();

    /** Given the total number of traps revealed, the number of rubies in the mine, the number of players remaining in the
     * mine and the number of cards that remain to be revealed, make a decision as to whether to return to the surface.
     * @param numTraps - number of traps revealed
     * @param numRubies - number of rubies in the mine
     * @param numPlayers - number of players in the mine
     * @param cardsRemain - number of cards left ot be revealed in the deck
     * @return true if return to surface and leave the mine, false if staying in the mine.
     **/
    public abstract boolean leaveMine(int numTraps, int numRubies, int numPlayers, int cardsRemain);

}
