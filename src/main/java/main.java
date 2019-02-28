import dao.RoomsDaoImpl;
import dao.connectionPool.JDBCConnectionPool;
import dao.*;
import dao.interfaces.DAOQuests;
import dao.interfaces.RoomsDAO;
import model.Quest;
import model.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {

        JDBCConnectionPool pool = new JDBCConnectionPool("jdbc:postgresql://localhost:5432/QuestStore",
                "admin", "123");

        RoomsDAO roomsDao = new RoomsDaoImpl(pool);
        Room cl = roomsDao.getRoomById(1);
        System.out.println(cl.getName());

        Room cl2 = roomsDao.getRoomByName("java");
        System.out.println(cl2.getId());

//        DAORooms roomsDao = new RoomsDaoImpl(pool);
//        Room cl = roomsDao.getRoomById(1);
//        System.out.println(cl.getName());

        DAOQuests questsDao = new QuestsDaoImpl(pool);
        List<Quest> basicQuests = new ArrayList<Quest>();
        basicQuests = questsDao.getBasicQuests();

        for (Quest bQuest: basicQuests) {
            System.out.println(bQuest.toString());
            System.out.println();
        }

        System.out.println("Please enter ${bold}name${normal} of quest:");
        String questName = main.getUserInput();
        System.out.println("${bold}Quest description");
        String questDesc = getUserInput();
        System.out.println("${bold}coins");
        int questCoins = getUserInt();

        Quest quest1 = new Quest(questName, questDesc, questCoins);

        questsDao.addQuest(quest1);

    }

    static String getUserInput() {
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        return userInput;
    }

    static int getUserInt() {
        Scanner sc = new Scanner(System.in);
        int userInt = sc.nextInt();
        return userInt;
    }
}
