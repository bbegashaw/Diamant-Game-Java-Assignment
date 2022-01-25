import diamant.*;

public class DiamantGame {

    // the list of players in the game of various types
    private Player[] players;

    // the tile deck associated with the game
    private TileDeck deck;

    // number of players currently in the mine
    private int playersIn = 0;

    // current status of the mine; true if collapsed, false otherwise.
    private boolean collapse = false;

    // the cumulative pile of treasure over an expedition
    private int treasurePile;

    // number of expeditions as a constant
    private final int NUM_EXPEDITIONS = 5;

    // some tracking variables for each expedition
    private int numLava = 0;
    private int numPoison = 0;
    private int numSnakes = 0;
    private int numSpiders = 0;
    private int numSpikes = 0;


    /** Create a game with an arbitrary number of players based on the types of players.
     * @param risky - number of risky players
     * @param balanced - number of balanced players
     * @param timid - number of timid players
     */
    public DiamantGame(int risky, int balanced, int timid)
    {
        int totalPlayers = risky+balanced+timid;

        players = new Player[totalPlayers];

        int pawnCount = 0;

        for(int i = 0; i<risky; i++)
        {
            players[i] = new diamant.RiskyPlayer(diamant.Pawn.values()[pawnCount], i);
            pawnCount = pawnCount+1;
            pawnCount = pawnCount% diamant.Pawn.values().length;
        }

        for(int j = risky; j<risky+balanced; j++)
        {
            players[j] = new diamant.BalancePlayer(diamant.Pawn.values()[pawnCount% diamant.Pawn.values().length], j);
            pawnCount = pawnCount+1;
            pawnCount = pawnCount% diamant.Pawn.values().length;
        }

        for(int k = risky+balanced; k<risky+balanced+timid; k++)
        {
            players[k] = new diamant.TimidPlayer(diamant.Pawn.values()[pawnCount% diamant.Pawn.values().length], k);
            pawnCount = pawnCount+1;
            pawnCount = pawnCount% diamant.Pawn.values().length;
        }

        // create a new deck to use for the game
        deck = new diamant.TileDeck();
    }

    /** Reporting the current standings in the game, returned as a formatted string for display.
     *
     * @return String for display describing the current game state.
     */
    public String toString()
    {
        String outStr="";

        for(int i = 0; i< players.length; i++)
        {
            outStr = outStr + players[i].toString() + "\n";
        }

        return outStr;
    }

    /** Players choose to stay or leave the mine based on their own strategy.
     *
     * @return the number of players leaving the mine.
     */
    public int playersReveal()
    {
        int playersLeaving = 0;

        for(int j = 0; j<players.length; j++)
        {
            if(players[j].isInMine()) {
                // if the player is not leaving the mine according to their strategy
                if (players[j].leaveMine(deck.countRevealedTraps(), treasurePile, playersIn, deck.remainingFaceDownTiles())) {
                    playersLeaving++;
                }
            }
        }
        return playersLeaving;
    }

    /** Safe method to calculate the number of rubies in the treasure pile based on a number of players leaving.
     *
     * @param playersLeaving the number of players leaving the mine
     * @return the number of rubies remaining the treasure pile.
     */
    public int calculateRemaining (int playersLeaving)
    {
        if (playersLeaving != 0)
            return treasurePile%playersLeaving;
        else
            // no players leaving - all remains
            return treasurePile;
    }

    /** Safe method to calculate the number of rubies taken by each leaving player.
     *
     * @param playersLeaving the number of players leaving the mine
     * @return the number of rubies to be given to each player
     */
    public int calculateWinnings (int playersLeaving)
    {
        if (playersLeaving != 0)
            return treasurePile/playersLeaving;
        else
            // no players leaving - no winnings
            return 0;
    }

    /** Unsafe method to execute the end of round by calculating the winnings and the amount of treasure remaining. Updates the
     * treasure pile and number of players in the mine at the end of the round.
     * @param playersLeaving number of players leaving the mine
     * @return the remaining treasure in the mine
     */
    public int endOfRound(int playersLeaving)
    {
        int playersWinnings = calculateWinnings(playersLeaving);
        int remainingTreasure = calculateRemaining(playersLeaving);

        // for each player leaving - take the treasure pile and update their personal treasure chest
        for(int k = 0; k < players.length; k++)
        {
            if(players[k].isLeaving())
            {
                players[k].setTreasureChest(players[k].getTreasureChest()+playersWinnings);
            }
        }

        // update the pile
        treasurePile = remainingTreasure;
        playersIn = playersIn - playersLeaving;

        return remainingTreasure;
    }

    // returns number of players in the mine

    /** Unsafe method to set up the beginning of an expedition by putting all players in the mine.
     *
     * @return number of players who entered the mine.
     */
    public int beginExpedition()
    {
        // put all the players in the mine
        for(int i=0; i<players.length; i++)
        {
            players[i].enterMine();
        }

        // set the mine to not be collapsed
        collapse = false;

        // all players are in the mine now
        playersIn = players.length;

        // treasure pile is initialized to 0
        treasurePile = 0;

        // return the number of players in the mine to the calling routine just to confirm it was done right
        return playersIn;
    }


    // returns the number of cards remaining in the deck

    /** Unsafe method to execute the steps for ending an expedition based on conditions in the rules.
     *
     * @return the number of cards remaining in the deck from the expedition.
     */
    public int endExpedition()
    {
        // when we have reached 0 players, the expedition ends and the winnings will already have been distributed
        // if the expedition ends with 2 traps, the trap to be removed will be in the last face up slot

        if((numLava >= 2)||(numPoison >= 2)||(numSnakes >= 2)||(numSpiders >= 2)||(numSpikes >= 2))
        {
            TrapTile lastCardUp = (TrapTile)deck.getLastRevealed();
            // check for the weird situation where a trap wasn't found which will silently fail
            if(lastCardUp != null)
                deck.removeTrapCard(lastCardUp.getTrapType());
        }

        //reset the counters for traps
        numLava = 0;
        numSpikes = 0;
        numPoison = 0;
        numSnakes = 0;
        numSpiders = 0;

        // reset the new deck
        deck.flipDeckFaceDown();
        deck.shuffle();
        return deck.getCurrentDeckSize();
    }

    /** A method that plays a full game of Diamant according to rules specified in the game. The game runs 5 consequtive expeditions
     * with several rounds of tile reveals until either there are no players in the mine or the mine collapses on the reveal of
     * two traps of the same type.
     * @param verbose a variable to turn on and off the outputs for testing purposes.
     */
    public void playGame(boolean verbose)
    {
        // some tracking variables for each round of play
        int playersStaying = 0, playersLeaving = 0, playersWinnings = 0, remainingTreasure = 0;

        // for the 5 expeditions


        for(int i = 1; i <= NUM_EXPEDITIONS; i++)
        {
            // everyone in the mine
            playersIn = beginExpedition();

            // announce start of the expedition
            if(verbose) {
                System.out.println("----------------------------------------------");
                System.out.println("Beginning Expedition " + i + " with deck of " + deck.getCurrentDeckSize() + " tiles...");
            }
            //shuffle the deck
            deck.shuffle();

            // this while loop is a round that continues until no players are in or until we encounter 2 trap cards
            do
            {
                // flip the next card
                //unusual situation where we have finished the deck - will only happen if there is a bug in the algorithm.
                if (!deck.revealNextTile())
                {
                    System.out.println("You have reached the end of the tile deck, the expedition ends.");
                    // deal with this anomaly by having all players divide evenly
                    endOfRound(playersIn);
                    // stop the expedition
                    collapse = true;
                }
                else
                {
                    diamant.MineTile reveal = deck.getLastRevealed();
                    // is the revealed tile a trap?
                    if(reveal.isTrap()) {
                        // if so we can use polymorphism to make it a trap
                        TrapTile itsATrap = (TrapTile) reveal;
                        // now we can check its trap type and increase a counter every time
                        if(itsATrap.getTrapType().equals(TrapTile.LAVA))
                            numLava++;
                        else if (itsATrap.getTrapType().equals(TrapTile.POISON))
                            numPoison++;
                        else if (itsATrap.getTrapType().equals(TrapTile.SNAKES))
                            numSnakes++;
                        else if (itsATrap.getTrapType().equals(TrapTile.SPIDERS))
                            numSpiders++;
                        else if(itsATrap.getTrapType().equals(TrapTile.SPIKES))
                            numSpikes++;
                    }
                    else
                    {
                        treasurePile = treasurePile+reveal.getTileValue();
                    }
                }

                // if any of these are 2 or more then the mine collapses and the expedition ends
                if((numLava >= 2)||(numPoison >= 2)||(numSnakes >= 2)||(numSpiders >= 2)||(numSpikes >= 2))
                    collapse = true;

                if(!collapse) {
                    // for each player decide if they will stay and reveal simultaneously
                    playersLeaving = playersReveal();

                    // calculate the stats for the current turn
                    playersStaying = playersIn - playersLeaving;
                    // treasure to each winner
                    playersWinnings = calculateWinnings(playersLeaving);
                    // treasure remaining in mine
                    remainingTreasure = endOfRound(playersLeaving);

                    // update the treasure pile

                    treasurePile = remainingTreasure;
                    // record the number staying in
                    playersIn = playersStaying;
                }
                if (verbose) {
                    System.out.println("Expedition " + i + ": " + "\nLeaving: " + playersLeaving + "; Staying: " + playersStaying + "; Reward: " + playersWinnings + " Remaining Treasure: " + remainingTreasure + "\nMine: " + deck.toString());
                }

            } while ((playersIn > 0)&&(!collapse));

            // here we have finished the expedition with a mine collapse or no one left in the mine, so we need to clean up
            if(verbose)
                if (playersIn == 0)
                    System.out.println("Expedition "+i+" has ended as all players have left. Treasure pile Left Behind: "+treasurePile+".");
                else
                    System.out.println("Mine Collapse! Expedition "+i+" has ended");

            endExpedition();

            if(verbose)
            {
                System.out.println("diamant.Player Standings after Expedition "+i+":");

                for(int stand = 0; stand<players.length; stand++)
                {
                    System.out.println(players[stand].toString());
                }
            }

        }

    }

    /** A sample test script for testing the DiamantGame */
    public static void main(String[] args)
    {
        DiamantGame game1 = new DiamantGame(2, 2, 2);

        System.out.println("Test: Game Init 6 Players MANUAL CHECK");
        System.out.println(game1.toString());

        game1.playGame(true);

    }
}
