package byow.Core;

import byow.TileEngine.TETile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import byow.TileEngine.Tileset;

import java.util.TreeMap;


public class HallWays {

    static TreeMap<Room, Boolean> isConnect = new TreeMap<>();


    public static void hallwayGenerator(Random random, TETile[][] world) {
        List<Integer> roomId = new ArrayList<>();
        for (int i = 0; i < Room.roomList.size(); i++) {
            roomId.add(i);
        }
        for (int i = 0; i < Room.roomList.size() - 1; i++) {
            int id = roomId.get(random.nextInt(roomId.size()));
            Room curRoom = Room.roomList.get(id);
            roomId.remove(roomId.indexOf(id));
            int id2 = roomId.get(random.nextInt(roomId.size()));
            Room secRoom = Room.roomList.get(id2);
            connectTwoRoom(random, world, curRoom, secRoom);
        }
    }

    public static void connectTwoRoom(Random random1, TETile[][] world, Room room1, Room room2) {
        int x1 = random1.nextInt(room1.x + 1, room1.x + room1.weith - 1);
        int y1 = random1.nextInt(room1.y + 1, room1.y + room1.height - 1);
        int x2 = random1.nextInt(room2.x + 1, room2.x + room2.weith - 1);
        int y2 = random1.nextInt(room2.y + 1, room2.y + room2.height - 1);
        int newX = 0;
        newX = (x2 >= x1) ? x2 : x1;
        if (x2 - x1 >= 0) { //x2在右边
            for (int i = 0; i <= x2 - x1; i++) {
                if (world[x1 + i][y1] != Tileset.FLOOR) {
                    world[x1 + i][y1] = Tileset.FLOOR;
                }
                if (world[x1 + i][y1 + 1] == Tileset.NOTHING) {
                    world[x1 + i][y1 + 1] = Tileset.WALL;
                }
                if (world[x1 + i][y1 - 1] == Tileset.NOTHING) {
                    world[x1 + i][y1 - 1] = Tileset.WALL;
                }
                updateWall(world, newX, y1);
            }
        } else {
            for (int i = 0; i <= x1 - x2; i++) {
                if (world[x2 + i][y2] != Tileset.FLOOR) {
                    world[x2 + i][y2] = Tileset.FLOOR;
                }
                if (world[x2 + i][y2 + 1] == Tileset.NOTHING) {
                    world[x2 + i][y2 + 1] = Tileset.WALL;
                }
                if (world[x2 + i][y2 - 1] == Tileset.NOTHING) {
                    world[x2 + i][y2 - 1] = Tileset.WALL;
                }
                if (world[newX + 1][y2 - 1] == Tileset.NOTHING) {
                    world[newX + 1][y2 - 1] = Tileset.WALL;
                }
                if (world[newX - 1][y2 + 1] == Tileset.NOTHING) {
                    world[newX - 1][y2 + 1] = Tileset.WALL;
                }
                if (world[newX + 1][y2 + 1] == Tileset.NOTHING) {
                    world[newX + 1][y2 + 1] = Tileset.WALL;
                }
                if (world[newX - 1][y2 - 1] == Tileset.NOTHING) {
                    world[newX - 1][y2 - 1] = Tileset.WALL;
                }
            }
        }
        if (y2 - y1 >= 0) {
            for (int j = 0; j <= y2 - y1; j++) {
                if (y1 + j < world[0].length && world[newX][y1 + j] != Tileset.FLOOR) {
                    world[newX][y1 + j] = Tileset.FLOOR;
                }
                if (world[newX - 1][y1 + j] == Tileset.NOTHING) {
                    world[newX - 1][y1 + j] = Tileset.WALL;
                }
                if (world[newX + 1][y1 + j] == Tileset.NOTHING) {
                    world[newX + 1][y1 + j] = Tileset.WALL;
                }
            }
        } else {
            for (int j = 0; j <= y1 - y2; j++) {
                if (world[newX][y1 - j] != Tileset.FLOOR) {
                    world[newX][y1 - j] = Tileset.FLOOR;
                }
                if (world[newX + 1][y1 - j] == Tileset.NOTHING) {
                    world[newX + 1][y1 - j] = Tileset.WALL;
                }
                if (world[newX - 1][y1 - j] == Tileset.NOTHING) {
                    world[newX - 1][y1 - j] = Tileset.WALL;
                }
            }
        }
    }

    private static void updateWall(TETile[][] world, int newX, int y1) {
        if (world[newX + 1][y1 - 1] == Tileset.NOTHING) {
            world[newX + 1][y1 - 1] = Tileset.WALL;
        }
        if (world[newX - 1][y1 + 1] == Tileset.NOTHING) {
            world[newX - 1][y1 + 1] = Tileset.WALL;
        }
        if (world[newX + 1][y1 + 1] == Tileset.NOTHING) {
            world[newX + 1][y1 + 1] = Tileset.WALL;
        }
        if (world[newX - 1][y1 - 1] == Tileset.NOTHING) {
            world[newX - 1][y1 - 1] = Tileset.WALL;
        }
    }

}
