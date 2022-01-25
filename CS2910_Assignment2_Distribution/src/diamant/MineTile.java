package diamant;

public class MineTile {

    // indicator if a card is face up (true) or face down (false)
    private boolean up = false;

    // the value of the tile; positive integer for treasure, negative 1 for traps
    private int tileValue = 0;

    // indicator if a card is a trap card; false if not a trap, true if there is a
    // trap
    private boolean trap = false;

    /**
     * Create a mine tile with the value passed through the first parameter. Tiles
     * default to not being traps and being face down.
     *
     * @param value - the value of the card
     */
    public MineTile(int value) {

        //set up values for a default value of a mine in constructor
        this.tileValue = value;
        setTrap(false);
        isFaceDown();

    }

    /**
     * Retrieve the value of the tile.
     *
     * @return the value of the card
     */
    public int getTileValue() {

        return tileValue;
    }

    /**
     * Test if there is a trap on a tile.
     *
     * @return true if the tile is a trap, false if it is not a trap.
     */
    public boolean isTrap() {


        //if tile equals to 0, return true meaning it is a trap or else return false
        if (tileValue == 0) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Set a tile to be a trap or not.
     *
     * @param trap true if the tile is to have a trap, false otherwise.
     */
    public void setTrap(boolean trap) {



        if (tileValue == 0) {
            this.trap = trap;
        }

    }

    /**
     * Turn a tile face up if it is down, and down if it is up.
     *
     * @return resulting orientation of the tile.
     */
    public boolean flipTile() {


        if (isFaceDown()) {
            up = true;
        } else {
            up = false;
        }
        return up;

    }

    /**
     * Test to see if a tile is face up.
     *
     * @return true if face up, false otherwise.
     */
    public boolean isFaceUp() {


        if (up == true) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Test to see if a tile is face down.
     *
     * @return true if face down, false otherwise.
     */
    public boolean isFaceDown() {

        if (up == false) {
            return true;
        } else {
            return false;
        }

    }

    /** Turn a tile face down if it is up */
    public void turnFaceDown() {

        if (up == true) {
            flipTile();
        }

    }

    /** Turn a tile face up if it is down */
    public void turnFaceUp() {
        if (up == false) {
            flipTile();
        }

    }

    /**
     * Convert a tile to a string for printing.
     *
     * @return An asterisk * if face down, or its value if it is face up.
     */
    public String toString() {

        /* TODO */
        String value = Integer.toString(tileValue);
        if (isFaceDown()) {
            return "*";
        } else {
            return value;

        }

    }

    public static void main(String[] args) {
        MineTile three = new MineTile(3);

        three.turnFaceUp();

        if ((three.toString().equals("0")) && (three.isFaceUp())) {
            System.out.println("Test: MineTile turnFaceUp SUCCESS");
        } else {
            System.out.println("Test: MineTile turnFaceUp FAIL");
        }

        three.turnFaceDown();

        if ((three.toString().equals("*")) && (three.isFaceDown())) {
            System.out.println("Test: MineTile turnFaceDown SUCCESS");
        } else {
            System.out.println("Test: MineTile turnFaceDown FAIL");
        }

        three.flipTile();
        if ((three.toString().equals("3")) && (three.isFaceUp())) {
            System.out.println("Test: MineTile flipTile SUCCESS");
        } else {
            System.out.println("Test: MineTile flipTile FAIL");
        }

    }

}
