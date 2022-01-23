package battle;
import Http.HttpStatus;
import db.DBconnectionImpl;
import db.getDBConnection;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BattleHandler implements BattleHandlerInteface, getDBConnection {

    public BattleHandler() {
    }

    public void checkforOpponent(String user){
        PreparedStatement statement = null;
        try {
            statement = DBconnectionImpl.getInstance().getConnection().prepareStatement("""
                        SELECT bio,username
                        from users
                        WHERE bio=? AND username!=?
                        LIMIT 1;
                    """);
            statement.setString(1,"ready for Battle");
            statement.setString(2,user);
            ResultSet rs=statement.executeQuery();
            String opponent;
            if(rs.next()){
                respond.writeHttpResponse(HttpStatus.OK,"found battle");
                opponent=rs.getString("username");
                inBattle(user);
                inBattle(opponent);
                battle.battle(user,opponent);
            }else{
                respond.writeHttpResponse(HttpStatus.OK,"No battle found\n waiting for battle....");
            }
            rs.close();
            statement.close();
            DBconnectionImpl.getInstance().getConnection().close();
        } catch (SQLException | IOException e) {
            try {
                respond.writeHttpResponse(HttpStatus.BAD_REQUEST,"");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }
    public void setrdy(String user){
        PreparedStatement statement = null;
        try {
            statement = DBconnectionImpl.getInstance().getConnection().prepareStatement("""
                    UPDATE users
                    SET bio=?
                    WHERE username=?
                    """);
            statement.setString(1,"ready for Battle");
            statement.setString(2,user);
            statement.execute();
            statement.close();
            DBconnectionImpl.getInstance().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void inBattle(String user){
        try {
            PreparedStatement inBattle = DBconnectionImpl.getInstance().getConnection().prepareStatement("""
                    UPDATE users
                        SET bio=?
                        WHERE username=?
                    """);
            inBattle.setString(1,"in Battle");
            inBattle.setString(2,user);
            inBattle.execute();
            inBattle.close();
            DBconnectionImpl.getInstance().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void resetBio(String user){
        try {
            PreparedStatement inBattle = DBconnectionImpl.getInstance().getConnection().prepareStatement("""
                        UPDATE users
                        SET bio=?
                        WHERE username=?
                    """);
            inBattle.setString(1,"...");
            inBattle.setString(2,user);
            inBattle.execute();
            inBattle.close();
            DBconnectionImpl.getInstance().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
