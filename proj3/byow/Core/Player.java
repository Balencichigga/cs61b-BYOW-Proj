package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Random;

public class Player {
    private TETile[][] newWorld;

    private Random randomSeed;

    private int x;
    private int y;

    private boolean win;
    private TERenderer renderer;
    private UI menu;
    public boolean light;
    private long seed;
    public boolean skin;
    private boolean instruction;


    public Player(TETile[][] world, long seed, TERenderer renderer, int x, int y, Random random) {
        instruction = false;
        skin = false;
        light = true;
        this.newWorld = world;
        this.seed = seed;
        randomSeed = random;
        this.renderer = renderer;
        Room firstRoom = Room.roomList.get(0);
        if (x == -1) {
            this.x = firstRoom.x + firstRoom.weith / 2;
        } else {
            this.x = x;
        }
        if (y == -1) {
            this.y = firstRoom.y + firstRoom.height / 2;
        } else {
            this.y = y;
        }
        menu = new UI(Engine.WIDTH, Engine.HEIGHT);
    }

    public void initialAvatar() {
        Room lastRoom = Room.roomList.get(Room.roomList.size() - 1);
        newWorld[x][y] = Tileset.AVATAR;
        win = false;
        generateDoor(lastRoom);
    }

    public void inputString(String move) {
        for (int i = 0; i < move.length(); i++) {
            char keyTyped = move.charAt(i);
            switch (keyTyped) {
                case 'w':
                    if (canMoveUp()) {
                        moveUp();
                    }
                    break;
                case 's':
                    if (canMoveDown()) {
                        moveDown();
                    }
                    break;
                case 'a':
                    if (canMoveLeft()) {
                        moveLeft();
                    }
                    break;
                case 'd':
                    if (canMoveRight()) {
                        moveRight();
                    }
                    break;
                case 'q':
                    break;
                case ':':
                    Out out = new Out(UI.fileName);
                    out.println(seed);
                    out.println(x);
                    out.println(y);
                    out.println(light);
                    out.println(skin);
                    System.out.println(light);
                    this.light = light;
//                        if (light)
//                            renderer.renderFrame(newWorld);
//                        else
//                            renderer.renderFrame(shadowedWorld());
                    break;


            }
            if (win) {
                UI.winMenu();
                return;
            }
        }
    }

    public void generateDoor(Room room) {
//        int direction = randomSeed.nextInt(4);
//        System.out.println(direction);
//        int x;
//        int y;
//
//        if (direction == 0) {//top wall
//            x = randomSeed.nextInt(room.x + 1, room.x + room.weith);
//            y = room.y + room.height;
////            newWorld[room.x + room.weith][room.y] = Tileset.AVATAR;
////            newWorld[room.x][room.y+room.height] = Tileset.AVATAR;
//            while (newWorld[x][y] != Tileset.WALL)
//                x = randomSeed.nextInt(room.x + 1, room.x + room.weith);
//            newWorld[x][y] = Tileset.UNLOCKED_DOOR;
//            System.out.println(x);
//            System.out.println(y);
//        }
//        else if (direction == 1) {//bottom
//            x = randomSeed.nextInt(room.x + 1, room.x + room.weith);
//            y = room.y;
//            while (newWorld[x][y] != Tileset.WALL)
//                x = randomSeed.nextInt(room.x + 1, room.x + room.weith);
//            newWorld[x][y] = Tileset.UNLOCKED_DOOR;
//        } else if (direction == 2) {//left
//            x = room.x;
//            y = randomSeed.nextInt(room.y + 1, room.y + room.height);
//            while (newWorld[x][y] != Tileset.WALL)
//                y = randomSeed.nextInt(room.y + 1, room.y + room.height);
//            newWorld[x][y] = Tileset.UNLOCKED_DOOR;
//        } else {//right
//            x = room.x + room.weith;
//            y = randomSeed.nextInt(room.y + 1, room.y + room.height);
//            while (newWorld[x][y] != Tileset.WALL)
//                y = randomSeed.nextInt(room.y + 1, room.y + room.height);
//            newWorld[x][y] = Tileset.UNLOCKED_DOOR;
//        }
        int direction = randomSeed.nextInt(4);
        int x1, y1;
        if (direction == 0) {//top wall
            x1 = randomSeed.nextInt(room.x + 1, room.x + room.weith);
            y1 = room.y + room.height;
        } else if (direction == 1) {//bottom
            x1 = randomSeed.nextInt(room.x + 1, room.x + room.weith);
            y1 = room.y;
        } else if (direction == 2) {//left
            x1 = room.x;
            y1 = randomSeed.nextInt(room.y + 1, room.y + room.height);
        } else {//right
            x1 = room.x + room.weith;
            y1 = randomSeed.nextInt(room.y + 1, room.y + room.height);
        }
        while (newWorld[x1][y1] != Tileset.WALL) {
            direction = randomSeed.nextInt(4);
            if (direction == 0) {//top wall
                x1 = randomSeed.nextInt(room.x + 1, room.x + room.weith);
                y1 = room.y + room.height;
            } else if (direction == 1) {//bottom
                x1 = randomSeed.nextInt(room.x + 1, room.x + room.weith);
                y1 = room.y;
            } else if (direction == 2) {//left
                x1 = room.x;
                y1 = randomSeed.nextInt(room.y + 1, room.y + room.height);
            } else {//right
                x1 = room.x + room.weith;
                y1 = randomSeed.nextInt(room.y + 1, room.y + room.height);
            }
        }
        newWorld[x1][y1] = Tileset.UNLOCKED_DOOR;
    }

    public boolean move() {
        if (skin) {
            newWorld = world.changeSkin(newWorld);
        }
        if (light)
            renderer.renderFrame(newWorld);
        else
            renderer.renderFrame(shadowedWorld());
        while (!win) {
            if (StdDraw.isMousePressed()) {
                int mouseX = (int) StdDraw.mouseX();
                int mouseY = (int) StdDraw.mouseY();
//                if(mouseY >= Engine.HEIGHT){
//
//                }
                if (newWorld[mouseX][mouseY] == Tileset.WALL || newWorld[mouseX][mouseY] == Tileset.WALL2) {

                    StdDraw.enableDoubleBuffering();
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.textRight(Engine.WIDTH, Engine.HEIGHT - 1, "Wall");
                }
                if (newWorld[mouseX][mouseY] == Tileset.NOTHING) {
                    StdDraw.enableDoubleBuffering();
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.textRight(Engine.WIDTH, Engine.HEIGHT - 1, "Nothing");
                }
                if (newWorld[mouseX][mouseY] == Tileset.FLOOR || newWorld[mouseX][mouseY] == Tileset.FLOOR2) {
                    StdDraw.enableDoubleBuffering();
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.textRight(Engine.WIDTH, Engine.HEIGHT - 1, "Floor");
                }
                if (newWorld[mouseX][mouseY] == Tileset.UNLOCKED_DOOR || newWorld[mouseX][mouseY] == Tileset.UNLOCKED_DOOR2) {
                    StdDraw.enableDoubleBuffering();
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.textRight(Engine.WIDTH, Engine.HEIGHT - 1, "Exit");
                }
                if (newWorld[mouseX][mouseY] == Tileset.AVATAR) {
                    StdDraw.enableDoubleBuffering();
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.textRight(Engine.WIDTH, Engine.HEIGHT - 1, "Avatar");
                }
                StdDraw.show();
                StdDraw.pause(20);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.filledRectangle(77, 47, 3, 3);

            }
            if (StdDraw.hasNextKeyTyped()) {
                char keyTyped = StdDraw.nextKeyTyped();
                System.out.println(keyTyped);
                switch (keyTyped) {
                    case 'w':
                        if (canMoveUp()) {
                            moveUp();
                        }
                        break;
                    case 's':
                        if (canMoveDown()) {
                            moveDown();
                        }
                        break;
                    case 'a':
                        if (canMoveLeft()) {
                            moveLeft();
                        }
                        break;
                    case 'd':
                        if (canMoveRight()) {
                            moveRight();
                        }
                        break;
                    case 'q':
                        UI.start();
                        return false;
                    case ':':
                        Out out = new Out(UI.fileName);
                        out.println(seed);
                        out.println(x);
                        out.println(y);
                        out.println(light);
                        out.println(skin);
                        System.out.println(light);
                        if (light)
                            renderer.renderFrame(newWorld);
                        else
                            renderer.renderFrame(shadowedWorld());
                        break;
                    case 'v':
                        light = !light;
                        if (light)
                            renderer.renderFrame(newWorld);
                        else
                            renderer.renderFrame(shadowedWorld());
                        break;
//                    case 'i':
//                        instruction = !instruction;
//                        if (!instruction)
//                            if (light)
//                                renderer.renderFrame(newWorld);
//                            else
//                                renderer.renderFrame(shadowedWorld());
//                        else
//                            menu.instructionMenu();
//                        break;
                    case 'g':
                        if (skin == false) {
                            newWorld = world.changeSkin(newWorld);
                            skin = true;
                        } else {
                            newWorld = world.changeSkin2(newWorld);
                            skin = false;
                        }

                        if (light)
                            renderer.renderFrame(newWorld);
                        else
                            renderer.renderFrame(shadowedWorld());
                        break;

                }
            }
        }

        UI.winMenu();
        return true;
    }

    public TETile[][] shadowedWorld() {
        TETile[][] shadowedWorld = world.worldInitialize();


        shadowedWorld[x][y + 1] = newWorld[x][y + 1];
        shadowedWorld[x + 1][y + 1] = newWorld[x + 1][y + 1];
        shadowedWorld[x - 1][y + 1] = newWorld[x - 1][y + 1];
        shadowedWorld[x][y] = newWorld[x][y];
        shadowedWorld[x + 1][y] = newWorld[x + 1][y];
        shadowedWorld[x - 1][y] = newWorld[x - 1][y];
        shadowedWorld[x][y - 1] = newWorld[x][y - 1];
        shadowedWorld[x + 1][y - 1] = newWorld[x + 1][y - 1];
        shadowedWorld[x - 1][y - 1] = newWorld[x - 1][y - 1];

        if (y + 2 < Engine.HEIGHT)
            shadowedWorld[x][y + 2] = newWorld[x][y + 2];
        if (y - 2 >= 0)
            shadowedWorld[x][y - 2] = newWorld[x][y - 2];
        if (x + 2 < Engine.WIDTH)
            shadowedWorld[x + 2][y] = newWorld[x + 2][y];
        if (x - 2 >= 0)
            shadowedWorld[x - 2][y] = newWorld[x - 2][y];

        return shadowedWorld;
    }

    public boolean canMoveUp() {
        return newWorld[x][y + 1] == Tileset.FLOOR || newWorld[x][y + 1] == Tileset.FLOOR2 || newWorld[x][y + 1] == Tileset.UNLOCKED_DOOR;
    }

    public boolean canMoveDown() {
        return newWorld[x][y - 1] == Tileset.FLOOR || newWorld[x][y - 1] == Tileset.FLOOR2 || newWorld[x][y - 1] == Tileset.UNLOCKED_DOOR;
    }

    public boolean canMoveLeft() {
        return newWorld[x - 1][y] == Tileset.FLOOR || newWorld[x - 1][y] == Tileset.FLOOR2 || newWorld[x - 1][y] == Tileset.UNLOCKED_DOOR;
    }

    public boolean canMoveRight() {
        return newWorld[x + 1][y] == Tileset.FLOOR || newWorld[x + 1][y] == Tileset.FLOOR2 || newWorld[x + 1][y] == Tileset.UNLOCKED_DOOR;
    }

    public void moveUp() {
        if (skin) {
            newWorld[x][y] = Tileset.FLOOR2;
        } else {
            newWorld[x][y] = Tileset.FLOOR;
        }
        if (newWorld[x][y + 1] == Tileset.UNLOCKED_DOOR)
            win = true;
        else {
            x = x;
            y = y + 1;
            newWorld[x][y] = Tileset.AVATAR;
        }
        if (light)
            renderer.renderFrame(newWorld);
        else
            renderer.renderFrame(shadowedWorld());
    }

    public void moveLeft() {
        if (skin) {
            newWorld[x][y] = Tileset.FLOOR2;

        } else {
            newWorld[x][y] = Tileset.FLOOR;
        }
        if (newWorld[x - 1][y] == Tileset.UNLOCKED_DOOR)
            win = true;
        else {
            x = x - 1;
            y = y;
            newWorld[x][y] = Tileset.AVATAR;
        }
        if (light)
            renderer.renderFrame(newWorld);
        else
            renderer.renderFrame(shadowedWorld());
    }

    public void moveRight() {
        if (skin) {
            newWorld[x][y] = Tileset.FLOOR2;

        } else {
            newWorld[x][y] = Tileset.FLOOR;
        }
        if (newWorld[x + 1][y] == Tileset.UNLOCKED_DOOR)
            win = true;
        else {
            x = x + 1;
            y = y;
            newWorld[x][y] = Tileset.AVATAR;
        }
        if (light)
            renderer.renderFrame(newWorld);
        else
            renderer.renderFrame(shadowedWorld());
    }

    public void moveDown() {
        if (skin) {
            newWorld[x][y] = Tileset.FLOOR2;

        } else {
            newWorld[x][y] = Tileset.FLOOR;
        }
        if (newWorld[x][y - 1] == Tileset.UNLOCKED_DOOR)
            win = true;
        else {
            x = x;
            y = y - 1;
            newWorld[x][y] = Tileset.AVATAR;
        }
        if (light)
            renderer.renderFrame(newWorld);
        else
            renderer.renderFrame(shadowedWorld());
    }
}
