package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class world {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;


    public world() {

    }

    public static TETile[][] worldGenerator(Random RANDOM, TETile[][] world) {
        Room.createRoom(RANDOM, world);
        HallWays.hallwayGenerator(RANDOM, world);
        return world;
    }

    public static TETile[][] worldInitialize() {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        return world;
    }
    public static TETile[][] changeSkin(TETile[][] world){
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if(world[x][y] == Tileset.WALL){
                    world[x][y] = Tileset.WALL2;
                }
                if(world[x][y] == Tileset.FLOOR){
                    world[x][y] = Tileset.FLOOR2;
                }
            }
        }
        return world;
    }
    public static TETile[][] changeSkin2(TETile[][] world){
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if(world[x][y] == Tileset.WALL2){
                    world[x][y] = Tileset.WALL;
                }
                if(world[x][y] == Tileset.FLOOR2){
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
        return world;
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();

        ter.initialize(WIDTH, HEIGHT);
//        long seed = 2838278388919144292L;//7742738610918953084L
//        Random RANDOM = new Random(seed);
//        TETile[][]  finalWorldFrame = world.worldInitialize();
//        world.worldGenerator(RANDOM,finalWorldFrame);
//        Player player = new Player(finalWorldFrame, seed, ter, -1, -1,RANDOM);
//        player.initialAvatar();
//        player.inputString("aaaas");
        Engine e1 = new Engine();
        TETile[][] finalWorldFrame = e1.interactWithInputString("n2838278388919144292swwwdd:q");
        TETile[][] result2 = e1.interactWithInputString("l");
        ter.renderFrame(result2);

    }
}
