package main;

import cards.Monstercard;

import java.sql.*;
import java.util.Optional;

public class TestConnection {
    public static Optional<Monstercard> getSample() {

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/swe1user", "swe1user", "swe1pw");
             PreparedStatement statement = connection.prepareStatement("""
                             SELECT *
                             FROM cards   
                     """)) {

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                System.out.print("Monster:"+resultSet.getString("monster")+"|Element:"+resultSet.getString("element"));
                System.out.println();
                /*return Optional.of(new Data(
                ));*/
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public static void executeSqlStmt(String name, String element) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/swe1user", "swe1user", "swe1pw");
             PreparedStatement statement = connection.prepareStatement("""
                         INSERT INTO cards
                         (monster,element)
                         VALUES (?,?);   
                     """)) {

            statement.setString(1, name);
            statement.setString(2, element);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void createUser(String user, String pass) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/swe1user", "swe1user", "swe1pw");
             PreparedStatement statement = connection.prepareStatement("""
                         INSERT INTO cards
                         (monster,element)
                         VALUES (?,?);   
                     """)) {

            statement.setString(1, user);
            statement.setString(2, pass);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}