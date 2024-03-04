package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.In;


import java.util.Random;

public class Engine {
    private static TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;
    //private static TETile[][] finalWorldFrame;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        UI menu = new UI(WIDTH, HEIGHT);
        menu.start();
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, running both of these:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public static TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //n123q456s
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        //        char firstChar = input.charAt(0);
        //        char endChar = input.charAt(input.length() - 1);
        //
        //        if (firstChar == 'n' && endChar == 's') {
        //            String str = input.substring(1, input.length() - 1);
        //            long seed = Long.parseLong(str);
        //            Random random = new Random(seed);
        //
        //            finalWorldFrame = world.worldInitialize();
        //            world.worldGenerator(random, finalWorldFrame);
        //            ter.renderFrame(finalWorldFrame);
        //
        //        }
        input.toLowerCase();
        StringBuilder stringSeed = new StringBuilder();
        TETile[][] finalWorldFrame = new TETile[0][];
        long seed = 0;
        if (input.charAt(0) == 'N' || input.charAt(0) == 'n') {
            int index = 1;
            while (input.charAt(index) != 's' && input.charAt(index) != 'S') {
                stringSeed.append(input.charAt(index));
                index++;
            }
            if (!stringSeed.isEmpty()) {
                seed = Long.parseLong(stringSeed.toString());
            }
            finalWorldFrame = world.worldInitialize();
            Random random = new Random(seed);
            world.worldGenerator(random, finalWorldFrame);
            Player player = new Player(finalWorldFrame, seed, ter, -1, -1, random);
            player.initialAvatar();
            if (input.indexOf('s') != input.length() - 1) {
                String sub = input.substring(input.indexOf('s') + 1);
                player.inputString(sub);
            }
            return finalWorldFrame;

        } else if (input.charAt(0) == 'l') {
            if (!UI.fileName.isEmpty()) {
                In in = new In(UI.fileName);
                if (in.hasNextLine()) {
                    seed = Long.parseLong(in.readLine());
                    int x = Integer.parseInt(in.readLine());
                    int y = Integer.parseInt(in.readLine());
                    boolean light = Boolean.parseBoolean(in.readLine());
                    boolean skin = Boolean.parseBoolean(in.readLine());
                    System.out.println(seed);
                    Random random = new Random(seed);
                    finalWorldFrame = world.worldInitialize();
                    world.worldGenerator(random, finalWorldFrame);
                    Player player = new Player(finalWorldFrame, seed, UI.renderer, x, y, random);
                    player.light = light;
                    player.skin = skin;
                    player.initialAvatar();
                    if (input.length() > 1) {
                        player.inputString(input.substring(1));
                    }
                    return finalWorldFrame;
                }

            }

        }


        return finalWorldFrame;

    }
}
