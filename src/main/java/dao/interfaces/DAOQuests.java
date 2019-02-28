package dao.interfaces;

import model.Quest;
import model.user.Codecooler;

import java.util.List;

public interface DAOQuests {

    List<Quest> getBasicQuests();
    List<Quest> getExtraQuests();
    //    Quest getQuests();
    void addQuest(Quest quest);
    void updateQuest(Quest quest);
    List<Quest> getCodecoolerQuests(Codecooler codecooler);
    void addCompleteQuest(int userId, int questId);
    }
