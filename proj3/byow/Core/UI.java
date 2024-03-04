package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.Random;

public class UI {
    private static int width;
    private static int height;
    public static TERenderer renderer;
    public static String fileName = "save.txt";

    public static void main(String[] args) {
        UI menu = new UI(80, 50);
        menu.start();
    }

    public UI(int width, int height) {
        this.width = width;
        this.height = height;
        renderer = new TERenderer();
    }

    public static void initialMenu() {

        Font fontSmall = new Font("Monaco", Font.BOLD, 30);
        Font fontBig = new Font("Monaco", Font.BOLD, 60);
        renderer.initialize(width, height);
        renderer.renderText(40, 44, "CS61B: The Game", fontBig);
        renderer.renderText(40, 30, "New Game (N)", fontSmall);
        renderer.renderText(40, 28, "Load Game (L)", fontSmall);
        renderer.renderText(40, 26, "Quit (Q)", fontSmall);
    }

    public static void start() {
        boolean gameOver = false;
        initialMenu();
        long seed;
        while (!gameOver) {
            if (StdDraw.hasNextKeyTyped()) {
                char keyTyped = StdDraw.nextKeyTyped();
                System.out.println(keyTyped);

                if (keyTyped == 'q') {
                    gameOver = true;
                    System.exit(1);
                } else if (keyTyped == 'n') {
                    Font fontBig = new Font("Monaco", Font.BOLD, 40);
                    renderer.renderText(40, 10, "Please Enter a seed:", fontBig);
                    String result = "";
                    while (true) {
                        if (StdDraw.hasNextKeyTyped()) {
                            keyTyped = StdDraw.nextKeyTyped();
                            System.out.println(keyTyped);
                            if (keyTyped == 'S'||keyTyped == 's') {
                                drawSeed(result + "S");
                                break;
                            } else if (keyTyped == 'Q'||keyTyped =='q')
                                System.exit(1);
                            else
                                result += Character.toString(keyTyped);
                            drawSeed(result);
                        }
                    }
                    seed = Long.parseLong(result);
                    Random random = new Random(seed);
                    TETile[][] finalWorldFrame = world.worldInitialize();
                    world.worldGenerator(random, finalWorldFrame);
                    Player player = new Player(finalWorldFrame, seed, renderer, -1, -1,random);
                    player.initialAvatar();
                    renderer.renderFrame(finalWorldFrame);
                    if (!player.move())
                        gameOver = true;
                }
                else if (keyTyped == 'l') {
                    if(!fileName.isEmpty()) {
                        In in = new In(fileName);
                        if (in.hasNextLine()) {
                            seed = Long.parseLong(in.readLine());
                            int x = Integer.parseInt(in.readLine());
                            int y = Integer.parseInt(in.readLine());
                            boolean light = Boolean.parseBoolean(in.readLine());
                            boolean skin = Boolean.parseBoolean(in.readLine());
                            System.out.println(seed);
                            Random random = new Random(seed);
                            Font font = new Font("Monaco", Font.BOLD, 20 );
                            StdDraw.setFont(font);
                            TETile[][] finalWorldFrame = world.worldInitialize();
                            world.worldGenerator(random, finalWorldFrame);
                            Player player = new Player(finalWorldFrame, seed, renderer, x, y,random);
                            player.light = light;
                            player.skin = skin;
                            player.initialAvatar();

                            if (!player.move())
                                gameOver = true;
                        }
                    }
                }

            }
        }
    }

    public static void drawSeed(String entered) {
        StdDraw.clear(Color.BLACK);
        Font fontBig = new Font("Monaco", Font.BOLD, 60);
        Font fontSmall = new Font("Monaco", Font.BOLD, 30);
        renderer.renderText(40, 44, "CS61B: The Game", fontBig);
        renderer.renderText(40, 30, "New Game (N)", fontSmall);
        renderer.renderText(40, 28, "Load Game (L)", fontSmall);
        renderer.renderText(40, 26, "Quit (Q)", fontSmall);

        Font bigFont = new Font("Serif", Font.BOLD, 50);
        Font smallFont = new Font("Monaco", Font.BOLD, 25);
        renderer.renderText(40, 16, "Enter Seed (until press S):", fontSmall);
        renderer.renderText(40, 13, entered, fontSmall);
    }
    public static void winMenu() {
        StdDraw.clear(Color.BLACK);
        Font bigFont = new Font("Serif", Font.BOLD, 60);
        renderer.renderText(40, 24, "Congratulation! You Win!!!", bigFont);
    }




}
