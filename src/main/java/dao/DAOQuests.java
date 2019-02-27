package dao;

import model.Quest;

import java.util.List;

public interface DAOQuests {

    List<Quest> getBasicQuests();
    List<Quest> getExtraQuests();
    //    Quest getQuests();
    void addQuest();
    void addBasicQuest();
    void addExtraQuest();
    void updateQuest();
}
