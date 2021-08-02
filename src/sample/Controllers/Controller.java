package sample.Controllers;

import sample.Main;
import java.sql.Connection;

public interface Controller {
    void setMainApp(Main main);
    void setConnection(Connection con);
    void updatePage();
    void setTab(int idTab);
}
