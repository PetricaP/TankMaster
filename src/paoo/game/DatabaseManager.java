package paoo.game;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DatabaseManager {
    public static String returnCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static void insertScore(ScoreInfo scoreInfo, int levelNumber) {
        Connection c;
        Statement stmt;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:tiobe.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();

            String sql = "INSERT INTO Level" + levelNumber + " (Name, Score, Data)" +
                         " VALUES (" + "'" + scoreInfo.getName() + "'," + scoreInfo.getScore()
                         + ",'" + scoreInfo.getDate() + "');";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    public static ArrayList<ScoreInfo> getScores(int levelNumber) {
        Connection c;
        Statement stmt;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:tiobe.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();

            String sql = "SELECT * FROM Level" + levelNumber + " ORDER BY Score ASC;";
            ResultSet resultSet = stmt.executeQuery(sql);

            ArrayList<ScoreInfo> results = new ArrayList<>();
            while(resultSet.next()) {
                results.add(new ScoreInfo(resultSet.getString("Name"),
                                          resultSet.getInt("Score"),
                                          resultSet.getString("Data")));
            }
            c.commit();
            stmt.close();
            c.close();

            return results;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return null;
    }
}
