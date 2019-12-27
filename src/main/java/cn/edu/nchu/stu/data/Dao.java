package cn.edu.nchu.stu.data;

import cn.edu.nchu.stu.data.model.Card;
import cn.edu.nchu.stu.data.model.Transaction;
import cn.edu.nchu.stu.data.model.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Dao {

    private static Dao dao;

    public static Dao getInstance() {
        if (dao == null) {
            dao = new Dao();
        }
        return dao;
    }

    static {
        try {
            // TODO
            Class.forName("");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection connection;

    private Dao() {
        try {
            Properties properties = new Properties();
            properties.load(getClass().getResourceAsStream("/config.prop"));
            connection = DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.user"), properties.getProperty("db.password"));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Card> parseCards(ResultSet resultSet) {
        ArrayList<Card> cards = new ArrayList<>();
        try {
            while (resultSet.next()) {
                cards.add(new Card(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        resultSet.getInt("type"),
                        resultSet.getString("type_name"),
                        resultSet.getBoolean("enable"),
                        resultSet.getFloat("balance"),
                        resultSet.getFloat("daily_limit")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    private List<Transaction> parseTransactions(ResultSet resultSet) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            while (resultSet.next()) {
                transactions.add(new Transaction(
                        resultSet.getLong("id"),
                        resultSet.getLong("card_id"),
                        resultSet.getFloat("amount"),
                        Timestamp.valueOf(resultSet.getString("create_at"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private List<User> parseUsers(ResultSet resultSet) {
        ArrayList<User> users = new ArrayList<>();
        try {
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("type"),
                        resultSet.getString("type_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


}
