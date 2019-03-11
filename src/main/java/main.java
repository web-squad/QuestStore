import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import dao.RoomsDaoImpl;
import dao.connectionPool.JDBCConnectionPool;
import dao.*;
import dao.interfaces.DAOQuests;
import dao.interfaces.RoomsDAO;
import model.Quest;
import model.Room;
import model.user.Codecooler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {

        JDBCConnectionPool pool = new JDBCConnectionPool("jdbc:postgresql://localhost:5432/QuestStore",
                "admin", "123");

        UserDaoImpl userDao = new UserDaoImpl(pool);
        Codecooler codecooler = new Codecooler("user20", "123", "codecooler", "Gi", "Kowalska");
        userDao.addUser(codecooler);

        DAOQuests questsDao = new QuestsDaoImpl(pool);
        List<Quest> basicQuests = new ArrayList<Quest>();
        basicQuests = questsDao.getBasicQuests();

        for (Quest bQuest: basicQuests) {
            System.out.println(bQuest.toString());
            System.out.println();
        }

//        System.out.println("${bold}Quest id:");
//        int questId = getUserInt();
//        System.out.println("Please enter ${bold}name${normal} of quest:");
//        String questName = main.getUserInput();
//        System.out.println("${bold}Quest description:");
//        String questDesc = getUserInput();
//        System.out.println("${bold}coins:");
//        int questCoins = getUserInt();
//        System.out.println("${bold}Quest type:");
//        String quest_type = getUserInput();

        Quest quest1 = new Quest(4,"Quest 4", "desc 44", 444, "extra");

//        questsDao.addQuest(quest1);

        List<Quest> extraQuests = new ArrayList<Quest>();
        extraQuests = questsDao.getExtraQuests();

        for (Quest eQuest: extraQuests) {
            System.out.println(eQuest.toString());
            System.out.println();
        }
//        Quest quest2 = new Quest(8, "Quest 8", "desc 88", 888, "extra");
//        questsDao.updateQuest(quest2);

        int userId = 1;
        int questId = 3;

        questsDao.addCompleteQuest(userId,questId);

        Codecooler student1 = new Codecooler(1,"jan123","123","student","jan","kowalski");

        List<Quest> cQuests = new ArrayList<Quest>();
        cQuests = questsDao.getCodecoolerQuests(student1);

        for (Quest cQuest: cQuests) {
            System.out.println(cQuest.getId()+" | "+cQuest.getName()+" | "+ cQuest.getQuestType()+" | "+cQuest.getCoins());
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
