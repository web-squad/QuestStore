package dao;

import model.Room;

public interface DAORooms {

    Room getRoomById(int id);
    Room getRoomByName(String name);
}