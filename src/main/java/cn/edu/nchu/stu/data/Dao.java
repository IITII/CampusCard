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
            Class.forName("com.mysql.cj.jdbc.Driver");
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
                        resultSet.getBoolean("enabled"),
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
                        resultSet.getLong("from_card_id"),
                        resultSet.getLong("to_card_id"),
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

    public User findUserById(long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from user_view where id = ?");
            statement.setLong(1, id);
            List<User> users = parseUsers(statement.executeQuery());
            int size= users.size();
            assert size <= 1;
            if (size == 0) {
                return null;
            } else {
                return users.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findUserByUsername(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from user_view where username = ?");
            statement.setString(1, username);
            List<User> users = parseUsers(statement.executeQuery());
            int size= users.size();
            assert size <= 1;
            if (size == 0) {
                return null;
            } else {
                return users.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Card findCardById(long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from card_view where id = ?");
            statement.setLong(1, id);
            List<Card> cards = parseCards(statement.executeQuery());
            assert cards.size() == 1;
            return cards.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Card> findCardByUserId(long userId) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from card_view where user_id = ?");
            statement.setLong(1, userId);
            return parseCards(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* pageNumber 从 1 开始
     */
    public List<Transaction> findTransactionsByCardId(long cardId, int pageNumber, int pageSize) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from transaction where from_card_id = ? order by create_at desc limit ?, ?");
            statement.setLong(1, cardId);
            statement.setInt(2, (pageNumber - 1) * pageSize);
            statement.setInt(3, pageSize);
            return parseTransactions(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Transaction> findTransactionsByCardId(long cardId) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from transaction where from_card_id = ? order by create_at desc");
            statement.setLong(1, cardId);
            return parseTransactions(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long insertUser(String username, String password, int type) {
        try {
            PreparedStatement statement = connection.prepareStatement("insert into user(type, username, password) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, type);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            boolean success = resultSet.next();
            assert success;
            return resultSet.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long insertCard(long userId, int type, boolean enabled) {
        try {
            PreparedStatement statement = connection.prepareStatement("insert into card (type, user_id, enabled) values (?, ?, ?)");
            statement.setInt(1, type);
            statement.setLong(2, userId);
            statement.setBoolean(3, enabled);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            boolean success = resultSet.next();
            assert success;
            return resultSet.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateEnabledByCardId(long cardId, boolean enabled) {
        try {
            PreparedStatement statement = connection.prepareStatement("update card set enabled = ? where id = ?");
            statement.setBoolean(1, enabled);
            statement.setLong(2, cardId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDailyLimit(long cardId, float dailyLimit) {
        try {
            PreparedStatement statement = connection.prepareStatement("update card set daily_limit = ? where id = ?");
            statement.setFloat(1, dailyLimit);
            statement.setLong(2, cardId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean transfer(long fromCardId, long toCardId, float amount) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("insert into transaction (from_card_id, to_card_id, amount) values (?, ?, ?)");
            statement.setLong(1, fromCardId);
            statement.setLong(2, toCardId);
            statement.setFloat(3, amount);
            statement.executeUpdate();
            statement = connection.prepareStatement("update card set balance = balance - ? where card.id = ?");
            statement.setFloat(1, amount);
            statement.setLong(2, fromCardId);
            statement.executeUpdate();
            statement = connection.prepareStatement("update card set balance = balance + ? where card.id = ?");
            statement.setFloat(1, amount);
            statement.setLong(2, toCardId);
            statement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }


}
