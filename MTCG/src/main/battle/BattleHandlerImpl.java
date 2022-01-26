package battle;

import db.DBconnectionImpl;
import db.getDBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BattleHandlerImpl implements BattleHandler, getDBConnection {

    public BattleHandlerImpl() {
    }

    //----Check first for an Opponent to fight with
    public String checkforOpponent(String user) throws SQLException, IOException {
        PreparedStatement statement = null;
        Connection con = DBconnectionImpl.getInstance().getConnection();
        statement = con.prepareStatement("""
                    SELECT bio,username
                    from users
                    WHERE bio=? AND username!=?
                    LIMIT 1;
                """);
        statement.setString(1, "ready for Battle");
        statement.setString(2, user);
        ResultSet rs = statement.executeQuery();
        String opponent;
        if (rs.next()) {//if found start battle and set status to battle
            opponent = rs.getString("username");
            inBattle(user);
            inBattle(opponent);
            rs.close();
            statement.close();
            con.close();
            return "Battle Found\n\n" + battle.battle(user, opponent);
        } else {//if not wait till battle is found
            rs.close();
            statement.close();
            con.close();
            return "No battle found\n waiting for battle....";
        }

    }

    public boolean setrdy(String user) throws SQLException {//set status to ready for player
        PreparedStatement statement = null;
        Connection con = DBconnectionImpl.getInstance().getConnection();
        statement = con.prepareStatement("""
                UPDATE users
                SET bio=?
                WHERE username=?
                """);
        statement.setString(1, "ready for Battle");
        statement.setString(2, user);
        int up = statement.executeUpdate();
        statement.close();
        DBconnectionImpl.getInstance().getConnection().close();
        return up > 0;
    }

    public void inBattle(String user) throws SQLException {
        Connection con = DBconnectionImpl.getInstance().getConnection();
        PreparedStatement inBattle = con.prepareStatement("""
                UPDATE users
                    SET bio=?
                    WHERE username=?
                """);
        inBattle.setString(1, "in Battle");
        inBattle.setString(2, user);
        inBattle.execute();
        inBattle.close();
        con.close();
    }

    public void resetBio(String user) throws SQLException {//reset bio after battle
        Connection con = DBconnectionImpl.getInstance().getConnection();
        PreparedStatement inBattle = con.prepareStatement("""
                    UPDATE users
                    SET bio=?
                    WHERE username=?
                """);
        inBattle.setString(1, "...");
        inBattle.setString(2, user);
        inBattle.execute();
        inBattle.close();
        con.close();
    }

}
