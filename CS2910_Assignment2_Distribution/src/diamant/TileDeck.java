package diamant;

import java.util.Random;

public class TileDeck {

    private MineTile[] deck;
    private final int INIT_DECK_SIZE = 35;
    private int currDeckSize;
    private int currReveal;

    /**
     * Initialize the TileDeck object. Creates original deck of INIT_DECK_SIZE
     * tiles.
     */
    public TileDeck() {
        // constructor for a deck

        deck = new MineTile[INIT_DECK_SIZE];//initialize deck with 35 spaces
        currDeckSize = INIT_DECK_SIZE;// assign current deck to initial since it is the start
        initializeDeck();// call initialize method

    }

    /**
     * Retrieve a tile located at a particular point in the deck given by an index
     * originating from 0
     *
     * @param tileInd
     * @return the MineTile object or its null pointer located at the index.
     */
    public MineTile getTileAt(int tileInd) {
        return deck[tileInd];
    }

    /**
     * Initialize the deck with the standard 20 treasure cards and 15 trap cards.
     */
    private void initializeDeck() {
        /* TODO */

        //for loop to assign values form 2 - 21 for the treasure tile
        for (int i = 0; i < INIT_DECK_SIZE - 15; i++) {
            deck[i] = new MineTile(i + 2);
        }

        //assign the rest of the spaces in the array to each 5 types of traps
        deck[23] = new TrapTile("Poison");
        deck[24] = new TrapTile("Poison");
        deck[25] = new TrapTile("Poison");
        deck[26] = new TrapTile("Spikes");
        deck[27] = new TrapTile("Spikes");
        deck[28] = new TrapTile("Spikes");
        deck[20] = new TrapTile("Lava");
        deck[21] = new TrapTile("Lava");
        deck[22] = new TrapTile("Lava");
        deck[32] = new TrapTile("Spiders");
        deck[33] = new TrapTile("Spiders");
        deck[34] = new TrapTile("Spiders");
        deck[29] = new TrapTile("Snakes");
        deck[30] = new TrapTile("Snakes");
        deck[31] = new TrapTile("Snakes");

    }

    /**
     * Return the current deck size.
     *
     * @return Returns the current deck size, which changes throughout a game, and
     *         thus is distinct from the initial deck size.
     */
    public int getCurrentDeckSize() {
        /* TODO */

        return currDeckSize;
    }

    /**
     * Flip the next tile in the deck to be face up
     *
     * @return Return true if able to flip the next card
     */
    public boolean revealNextTile() {

        boolean reveal = false;// assign false at start

        //for loop to iterate through the deck and flip tile if tile is face down
        for (int i = 0; i < currDeckSize; i++) {
            if (getTileAt(i).isFaceDown()) {
                getTileAt(i).flipTile();
                reveal = true;// set reveal to true once flipped

            }
        }

        return reveal;//return final result
    }

    /**
     * Shuffle the current tile deck using a standard shuffling algorithm.
     *
     *
     * Shuffle Method from Class TextBook: Introduction to Programming Using Java by David E. Jack
     */
    public void shuffle() {

        //for loop that iterates by going backwards
        for (int l = deck.length - 1; l > 0; l--) {
            int random = (int) (Math.random() * (l + 1));
            MineTile temp = deck[random];
            deck[random] = deck[l];
            deck[l] = temp;
        }
    }

    /**
     * Find and remove the first trap tile of a particular type from the tile deck.
     * This should resize the data structure such that it does not have any empty
     * slots. The current deck size should be updated appropriately.
     *
     * @param trap - the type of trap being returned. In theory should be from the
     *             TrapTile class but could be any string.
     * @return the size of the newly reconfigured deck
     */
    public int removeTrapCard(String trap) {

        boolean removed = false;
        MineTile[] mineTiles = new MineTile[currDeckSize - 1];
        int trapCount = countTraps(trap);

        //for loop to store the trap into new array
        for (int i = 0; i < currDeckSize; i++)
        {
            if (trapCount > 1)
            {
                if (getTileAt(i).equals(trap))
                {
                    mineTiles[i] = getTileAt(i+1);
                }
            }



        }


        currDeckSize--;// reduce size of current deck size
        deck = mineTiles;//make these two arrays equal
        return currDeckSize;// return final value
    }

    /**
     * Take all the tiles in the tile deck and set them so they are face down.
     * Currently revealed cards in the deck is set to -1 to indicate all cards are
     * face down.
     */
    public void flipDeckFaceDown() {

        // for loop to iterate through deck and flipping face down if it is face up
        for (int i = 0; i < currDeckSize; i++) {
            if (getTileAt(i).isFaceUp()) {
                getTileAt(i).turnFaceDown();
            }
        }

    }

    /**
     * Take all the tiles in the tile deck and set them so they are face up.
     * Currently revealed cards in the deck is set to one less of the current size
     * of the deck to indicate all cards are face up.
     */
    public void flipDeckFaceUp() {


        // for loop to iterate through deck and flipping face down if it is face up
        for (int i = 0; i < currDeckSize; i++) {
            if (getTileAt(i).isFaceDown()) {
                getTileAt(i).turnFaceUp();
            }

        }

    }

    /**
     * Counting a trap of a particular type contained within the deck.
     *
     * @param trap type of trap drawn from the enum Trap
     * @return number of traps of that type
     */
    public int countTraps(String trap) {
        /* TODO */

        int counter = 0;//set counter to 0

        // for loop to iterate through deck and count if it encounters a trap
        for (int i = 0; i < currDeckSize; i++) {
            if (getTileAt(i).equals(trap)) {
                counter++;
            }
        }
        return counter;// return final counter value
    }

    /**
     * Returns the total number of traps revealed in the current mine
     *
     * @return the number of traps that have been revealed in the face up cards.
     */
    public int countRevealedTraps() {

        //for loop to iterate through deck to count all of total trap cards
        int totalCounter = 0;
        for (int i = 0; i < currDeckSize; i++) {
            if (getTileAt(i).isFaceUp() && getTileAt(i).isTrap()) {
                totalCounter++;

            }
        }
        return totalCounter;
    }

    /**
     * Calculate remaining face down tiles.
     *
     * @return the number of tiles that can still be revealed.
     */
    public int remainingFaceDownTiles() {
        // count all of the tiles with face down
        int faceDownCounter = 0;
        for (int i = 0; i < currDeckSize; i++) {

            if (getTileAt(i).isFaceDown()) {
                faceDownCounter++;
            }
        }
        return faceDownCounter;// return counter
    }

    /**
     * Retrieve the last mine tile that was revealed from the deck.
     *
     * @return Return the last face up tile assuming the tiles are revealed in order
     *         from the beginning of the deck.
     */
    public MineTile getLastRevealed() {
        /* TODO */

        MineTile lastTile = null;// assign last tile of type MineTile to null for the time being

        //for loop to find face up card
        for (int i = 0; i < currDeckSize; i++) {
            if (getTileAt(i).isFaceUp()) {
                lastTile = getTileAt(i);
                currReveal = i;
            }
        }
        return lastTile; //return tile object which is last

    }

    /**
     * Retrieve the index of the last mine tile that was revealed from the deck.
     *
     * @return an integer index of the last face up tile assuming tiles are revealed
     *         in order from the beginning of the deck.
     */
    public int getLastRevealedIndex() {

        return currReveal;
    }

    /**
     * Convert the deck into a string for printing. This should be a comma delimited
     * list, with tile values for face up cards and asterisks * for face down cards.
     * NB: This format is very important to implement precisely pass the tests in
     * the test suite for this class.
     *
     * @return the String object representing the deck.
     */
    public String toString() {
        String deckPrint = "";
        for (int i = 0; i < currDeckSize; i++) {
            if (i == currDeckSize-1)
                deckPrint += getTileAt(i).toString();
            else {
                deckPrint += getTileAt(i).toString() + ",";
            }
        }
        return deckPrint;
    }

    public static void main(String[] args) {
        TileDeck newDeck = new TileDeck();

        // Ensure the deck is initialized with cards down
        if (newDeck.toString().equals("*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*"))
            System.out.println("Test: TileDeck Init SUCCESS");
        else {
            System.out.println("Test: TileDeck Init FAIL");
            System.out.println("Expected: *,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*");
            System.out.println("Received: " + newDeck);
        }

        // Flip the deck up and ensure the initialization did actually work and
        // flipDeckFaceUp works
        newDeck.flipDeckFaceUp();

        if (newDeck.toString().equals(
                "2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,Lava,Lava,Lava,Poison,Poison,Poison,Spikes,Spikes,Spikes,Snakes,Snakes,Snakes,Spiders,Spiders,Spiders"))
            System.out.println("Test: TileDeck Flip Up SUCCESS");
        else {
            System.out.println("Test: TileDeck Flip Up FAIL");
            System.out.println(
                    "Expected: 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,Lava,Lava,Lava,Poison,Poison,Poison,Spikes,Spikes,Spikes,Snakes,Snakes,Snakes,Spiders,Spiders,Spiders");
            System.out.println("Received: " + newDeck);
        }

        // flip the deck down and ensure that works.
        newDeck.flipDeckFaceDown();
        if (newDeck.toString().equals("*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*"))
            System.out.println("Test: TileDeck Flip Down SUCCESS");
        else {
            System.out.println("Test: TileDeck Flip Down FAIL");
            System.out.println("Expected: *,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*");
            System.out.println("Received: " + newDeck);
        }

        // flip the deck up so error messages have meaning on failure
        newDeck.flipDeckFaceUp();

        // Remove 1 trap of one type in the first slot
        newDeck.removeTrapCard(TrapTile.LAVA);
        if (newDeck.toString().equals(
                "2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,Lava,Lava,Poison,Poison,Poison,Spikes,Spikes,Spikes,Snakes,Snakes,Snakes,Spiders,Spiders,Spiders"))
            System.out.println("Test: TileDeck removeTrapCard 1 SUCCESS");
        else {
            System.out.println("Test: TileDeck removeTrapCard 1 FAIL");
            System.out.println(
                    "Expected: 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,Lava,Lava,Poison,Poison,Poison,Spikes,Spikes,Spikes,Snakes,Snakes,Snakes,Spiders,Spiders,Spiders");
            System.out.println("Received: " + newDeck);
        }

        // Remove 1 trap of another type
        newDeck.removeTrapCard(TrapTile.POISON);
        if (newDeck.toString().equals(
                "2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,Lava,Lava,Poison,Poison,Spikes,Spikes,Spikes,Snakes,Snakes,Snakes,Spiders,Spiders,Spiders"))
            System.out.println("Test: TileDeck removeTrapCard 2 SUCCESS");
        else {
            System.out.println("Test: TileDeck removeTrapCard 2 FAIL");
            System.out.println(
                    "Expected: 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,Lava,Lava,Poison,Poison,Spikes,Spikes,Spikes,Snakes,Snakes,Snakes,Spiders,Spiders,Spiders");
            System.out.println("Received: " + newDeck);
        }

        // Remove 1 trap of the first type again
        newDeck.removeTrapCard(TrapTile.LAVA);
        if (newDeck.toString().equals(
                "2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,Lava,Poison,Poison,Spikes,Spikes,Spikes,Snakes,Snakes,Snakes,Spiders,Spiders,Spiders"))
            System.out.println("Test: TileDeck removeTrapCard 3 SUCCESS");
        else {
            System.out.println("Test: TileDeck removeTrapCard 3 FAIL");
            System.out.println(
                    "Expected: 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,Lava,Poison,Poison,Poison,Spikes,Spikes,Spikes,Snakes,Snakes,Snakes,Spiders,Spiders,Spiders");
            System.out.println("Received: " + newDeck);
        }

        // Remove 1 trap of a third type just to be sure.
        newDeck.removeTrapCard(TrapTile.SPIDERS);
        if (newDeck.toString().equals(
                "2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,Lava,Poison,Poison,Spikes,Spikes,Spikes,Snakes,Snakes,Snakes,Spiders,Spiders"))
            System.out.println("Test: TileDeck removeTrapCard 4 SUCCESS");
        else {
            System.out.println("Test: TileDeck removeTrapCard 4 FAIL");
            System.out.println(
                    "Expected: 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,Lava,Poison,Poison,Spikes,Spikes,Spikes,Snakes,Snakes,Snakes,Spiders,Spiders");
            System.out.println("Received: " + newDeck);
        }

        // Shuffle the deck
        newDeck.shuffle();
        System.out.println("Test: TileDeck Shuffle 1 MANUAL CHECK");
        System.out.println(newDeck);

        // Count the number of traps of one type
        int numSnakes = newDeck.countTraps(TrapTile.SNAKES);
        // Remove a trap of a from a shuffled deck of that type
        newDeck.removeTrapCard(TrapTile.SNAKES);

        // Check to make sure the number of snake traps is 1 fewer
        if (newDeck.countTraps(TrapTile.SNAKES) == numSnakes - 1)
            System.out.println("Test: TileDeck removeTrapCard 5 SUCCESS");
        else
            System.out.println("Test: TileDeck removeTrapCard 5 FAIL");

        newDeck.shuffle();
        System.out.println("Test: Deck Shuffle 2 MANUAL CHECK");
        System.out.println(newDeck);

        // Count the number of traps of one type
        int numPoison = newDeck.countTraps(TrapTile.POISON);
        // Remove a trap of a from a shuffled deck of that type
        newDeck.removeTrapCard(TrapTile.POISON);
        if (newDeck.countTraps(TrapTile.POISON) == numPoison - 1)
            System.out.println("Test: TileDeck removeTrapCard 6 SUCCESS");
        else
            System.out.println("Test: TileDeck removeTrapCard 6 FAIL");

        // reset the deck to 35 cards
        newDeck = new TileDeck();

        // flip the entire deck down, and then reveal the first card
        newDeck.flipDeckFaceUp();

        int slot = newDeck.getLastRevealedIndex();

        // Check correct reporting of all tiles revealed, corresponding to the size of
        // the current deck minus 1 (34)
        if (slot == newDeck.currDeckSize - 1)
            System.out.println("Test: TileDeck getLastRevealedIndex 1 SUCCESS");
        else
            System.out.println("Test: TileDeck getLastRevealedIndex 1 FAIL");

        newDeck.flipDeckFaceDown();

        slot = newDeck.getLastRevealedIndex();
        // Check correct reporting of no tiles have been revealed corresponding to -1.
        if (slot == -1)
            System.out.println("Test: TileDeck getLastRevealedIndex 2 SUCCESS");
        else
            System.out.println("Test: TileDeck getLastRevealedIndex 2 FAIL");

        newDeck.revealNextTile();
        System.out.println("Test: TileDeck revealNextTile 1 MANUAL CHECK Confirm Tile Index 0 revealed.");
        System.out.println(newDeck);

        // we expect tile 2 to be revealed in slot 0
        if (newDeck.getLastRevealed().getTileValue() == 2)
            System.out.println("Test: TileDeck getLastRevealed 1 SUCCESS");
        else {
            System.out.println("Test: TileDeck getLastRevealed 1 FAIL");
            System.out.println("Expected: 2");
            System.out.println("Received: " + newDeck.getLastRevealed().getTileValue());

        }

        // check to make sure the right slot is revealed and returned
        int slot3 = newDeck.getLastRevealedIndex();
        if (slot3 == 0)
            System.out.println("Test: TileDeck getLastRevealedIndex 3 SUCCESS");
        else {
            System.out.println("Test: TileDeck getLastRevealedIndex 3 FAIL");
            System.out.println("Expected: 3");
            System.out.println("Received: " + newDeck.getLastRevealedIndex());

        }
        // do it again a few of times and check it is working
        newDeck.revealNextTile();
        newDeck.revealNextTile();
        newDeck.revealNextTile();
        System.out.println("Test: TileDeck revealNextTile 2 MANUAL CHECK Confirm Tile Index 0-4 revealed");
        System.out.println(newDeck);

        // we expect tile 5 to be revealed in slot 3
        slot = newDeck.getLastRevealedIndex();
        if (slot == 3)
            System.out.println("Test: TileDeck getLastRevealedIndex 4 SUCCESS");
        else {
            System.out.println("Test: TileDeck getLastRevealedIndex 4 FAIL");
            System.out.println("Expected: 3");
            System.out.println("Received: " + newDeck.getLastRevealedIndex());

        }

        if (newDeck.getLastRevealed().getTileValue() == 5)
            System.out.println("Test: TileDeck getLastRevealed 2 SUCCESS");
        else {
            System.out.println("Test: TileDeck getLastRevealed 2 FAIL");
            System.out.println("Expected: 5");
            System.out.println("Received: " + newDeck.getLastRevealed().getTileValue());

        }
        // flip the deck down for a big test on trap functionality
        newDeck.flipDeckFaceDown();
        // first trap is located at slot 20, reveal deck up to that point and check each
        // one
        for (int reveal = 0; reveal < 21; reveal++) {
            newDeck.revealNextTile();
        }

        // we expect this to be a trap card, and thus value 0
        if (newDeck.getLastRevealed().getTileValue() == 0)
            System.out.println("Test: TileDeck getLastRevealed 3 SUCCESS");
        else
            System.out.println("Test: TileDeck getLastRevealed 3 FAIL");

        // we expect this to be a trap card, and so should be listed as such
        if (newDeck.getLastRevealed().isTrap()) {
            if (((TrapTile) newDeck.getLastRevealed()).getTrapType().equals(TrapTile.LAVA))
                System.out.println("Test: TileDeck getLastRevealed 4 SUCCESS");
            else {
                System.out.println("Test: TileDeck getLastRevealed 4 Trap Type FAIL");
                System.out.println("Expected: Lava");
                System.out.println("Received: " + ((TrapTile) newDeck.getLastRevealed()).getTrapType());
            }
        } else {
            System.out.println("Test: TileDeck getLastRevealed 5 Trap Detection FAIL");
            System.out.println("Expected: false");
            System.out.println("Received: " + newDeck.getLastRevealed().isTrap());
        }

        // to be on the safe side, we will check slot 23 just to be sure we have this
        // right with Poison
        newDeck.revealNextTile();
        newDeck.revealNextTile();
        newDeck.revealNextTile();
        if (newDeck.getLastRevealed().isTrap()) {
            if (((TrapTile) newDeck.getLastRevealed()).getTrapType().equals(TrapTile.POISON))
                System.out.println("Test: TileDeck getLastRevealed 5 SUCCESS");
            else {
                System.out.println("Test: TileDeck getLastRevealed 5 Trap Type FAIL");
                System.out.println("Expected: Poison");
                System.out.println("Received: " + ((TrapTile) newDeck.getLastRevealed()).getTrapType());
            }
        } else {
            System.out.println("Test: TileDeck getLastRevealed 5 Trap Detection FAIL");
            System.out.println("Expected: false");
            System.out.println("Received: " + newDeck.getLastRevealed().isTrap());
        }
    }

}