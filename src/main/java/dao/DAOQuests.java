package dao;

import model.Quest;

public interface DAOQuests {

    Quest getBasicQuest();
    Quest getExtraQuest();
    //    Quest getQuest();
    void addQuest();
    void addBasicQuest();
    void addExtraQuest();
    void updateQuest();
}
