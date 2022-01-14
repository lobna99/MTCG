
package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnectionImpl implements DBconnection {//Singelton Pattern only one object of this class
    private static DBconnectionImpl OBJ =null;
    private static final String DBurl ="jdbc:postgresql://localhost:5432/mtcg";
    private static final String DBuser="swe1user";
    private static final String DBpass="swe1pw";

    private DBconnectionImpl(){
    }
    public static DBconnectionImpl getInstance() {
        if (OBJ == null)
            OBJ = new DBconnectionImpl();

        return OBJ;
    }
    public Connection getConnection(){
        try {
            return DriverManager.getConnection(DBurl,DBuser,DBpass);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
