package dao.interfaces;

import model.Room;

public interface RoomsDAO {
    Room getRoomById(int id);

    Room getRoomByName(String name);

    void addRoom(Room room);

    void updateRoom(Room room);
}
