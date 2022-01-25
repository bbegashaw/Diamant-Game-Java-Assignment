package diamant;

public class TrapTile extends MineTile {

    public static String LAVA = "Lava";
    public static String POISON = "Poison";
    public static String SNAKES = "Snakes";
    public static String SPIDERS = "Spiders";
    public static String SPIKES = "Spikes";

    private String trapType;

    /**
     * Create a tile with a trap on it
     *
     * @param newTrap - the type of trap, usually produced from static variables of
     *                this class.
     */
    public TrapTile(String newTrap) {
        /* TODO */
        super(0);//Set mine to take in 0 meaning object trap tile is becomes a trap
        this.trapType = newTrap;

    }



    /**
     * Return the trap type.
     *
     * @return returns the type of trap as a string.
     */
    public String getTrapType() {
        /* TODO */
        return trapType;
    }

    @Override
    /**
     * Convert a trap tile to a string for printing.
     *
     * @return string representing the card. Asterisk if face down, trap type if it
     *         is face up.
     */
    public String toString() {
        /* TODO */


        if (isFaceDown()) {
            return "*";

        } else {
            return getTrapType();

        }
    }

    public static void main(String[] args) {
        TrapTile lava = new TrapTile(TrapTile.LAVA);
        TrapTile spikes = new TrapTile(TrapTile.SPIKES);
        TrapTile snakes = new TrapTile(TrapTile.SNAKES);
        TrapTile spiders = new TrapTile(TrapTile.SPIDERS);
        TrapTile poison = new TrapTile(TrapTile.POISON);

        if ((lava.toString().equals("*")) && (spikes.toString().equals("*")) && (snakes.toString().equals("*"))
                && (spiders.toString().equals("*")) && (poison.toString().equals("*"))) {
            System.out.println("Test: TrapTile toString Face Down SUCCESS");
        } else {
            System.out.println("Test: TrapTile toString Face Down FAIL");
        }

        // flip all the tiles over to make sure they work face up as well
        lava.flipTile();
        spikes.flipTile();
        snakes.flipTile();
        spiders.flipTile();
        poison.flipTile();

        if ((lava.toString().equals("Lava")) && (spikes.toString().equals("Spikes"))
                && (snakes.toString().equals("Snakes")) && (spiders.toString().equals("Spiders"))
                && (poison.toString().equals("Poison"))) {
            System.out.println("Test: TrapTile toString Face Up SUCCESS");
        } else {
            System.out.println("Test: TrapTile toString Face Up FAIL");
        }

    }

}
