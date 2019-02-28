import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
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

//        RoomsDAO roomsDao = new RoomsDaoImpl(pool);
//        Room cl = roomsDao.getRoomById(1);
//        System.out.println(cl.getName());

//        Room cl2 = roomsDao.getRoomByName("java");
//        System.out.println(cl2.getId());

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

        System.out.println("${bold}Quest id:");
        int questId = getUserInt();;
        System.out.println("Please enter ${bold}name${normal} of quest:");
        String questName = main.getUserInput();
        System.out.println("${bold}Quest description:");
        String questDesc = getUserInput();
        System.out.println("${bold}coins:");
        int questCoins = getUserInt();
        System.out.println("${bold}Quest type:");
        String quest_type = getUserInput();

        Quest quest1 = new Quest(questId,questName, questDesc, questCoins, quest_type);

        questsDao.addQuest(quest1);

        List<Quest> extraQuests = new ArrayList<Quest>();
        extraQuests = questsDao.getExtraQuests();

        for (Quest eQuest: extraQuests) {
            System.out.println(eQuest.toString());
            System.out.println();
        }
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
