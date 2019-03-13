package dao.interfaces;

public interface LoginDAO {
    boolean isActiveSession(String sessionid);
    int getUserId(String sessionid);
    void activateSessionId(String sessionid, int id);
    void removeSessionid(String sessionid);
}
