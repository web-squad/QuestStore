package dao;

import model.Quest;

import java.util.List;

public interface DAOQuests {

    List<Quest> getBasicQuests();
    List<Quest> getExtraQuests();
    //    Quest getQuests();
    void addQuest(Quest quest);
    void addBasicQuest(Quest quest);
    void addExtraQuest(Quest quest);
    void updateQuest(Quest quest);
}
