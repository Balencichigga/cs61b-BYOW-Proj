package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Room {
    public int x;
    public int y;
    public int height;
    public int weith;
    private static int howManyRooms;
    public static List<Room> roomList;



    public Room(int x, int y, int weith, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.weith = weith;
    }

    public static void createRoom(Random RANDOM, TETile[][] world) {
        roomList = new ArrayList<>();
        howManyRooms = RANDOM.nextInt(6, 15);

        for (int i = 0; i < howManyRooms; i++) {
            roomList.add(randomRoom(RANDOM, world));
        }

        Iterator<Room> iterator = roomList.iterator();
        while(iterator.hasNext()) {
            Room room = iterator.next();
            if (room.x + room.weith >= world.length - 1) {
                iterator.remove();
                continue;
            }
            if (room.y + room.height >= world[0].length - 1) {
                iterator.remove();
                continue;
            }
            boolean remove = false;
            for(int i =0;i <room.height;i++){
                if(world[room.x][room.y + i] == Tileset.FLOOR){
                    //iterator.remove();
                    remove = true;

                }
            }
            for(int i =0;i <room.weith;i++){
                if(world[room.x + i][room.y ] == Tileset.FLOOR ){
                    //iterator.remove();
                    remove = true;
                }
            }
            if(remove){
                iterator.remove();
                continue;
            }

            for (int i = 0; i <= room.height; i++) {
                world[room.x][room.y + i] = Tileset.WALL;
                world[room.x + room.weith ][room.y + i] = Tileset.WALL;
            }
            for (int i = 0; i <= room.weith; i++) {
                world[room.x + i][room.y] = Tileset.WALL;
                world[room.x + i][room.y + room.height ] = Tileset.WALL;
            }
            for (int i = 1; i <= room.weith - 1; i++) {
                for (int j = 1; j <= room.height - 1; j++) {
                    world[room.x+i][room.y+j] = Tileset.FLOOR;
                }
            }
        }

    }

    public static Room randomRoom(Random RANDOM, TETile[][] world) {
        int x = RANDOM.nextInt(world.length - 5);
        int y = RANDOM.nextInt(world[0].length - 4);
        int height = RANDOM.nextInt(4, 13);
        int weith = RANDOM.nextInt(5, 13);

        return new Room(x, y, weith, height);
    }


}
