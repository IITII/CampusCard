package cn.edu.nchu.stu.data;

import cn.edu.nchu.stu.data.model.*;

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
                        resultSet.getString("pos_name"),
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
                        resultSet.getString("type_name"),
                        resultSet.getString("gender"),
                        resultSet.getString("department"),
                        resultSet.getString("job_title"),
                        resultSet.getString("tel_number")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<CardType> parseCardTypes(ResultSet resultSet) {
        ArrayList<CardType> types = new ArrayList<>();
        try {
            while (resultSet.next()) {
                types.add(new CardType(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return types;
    }

    public List<Schedule> parseSchedules(ResultSet resultSet) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        try {
            while (resultSet.next()) {
                schedules.add(new Schedule(
                        resultSet.getInt("user_id"),
                        resultSet.getDate("date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    public List<Department> parseDepartments(ResultSet resultSet) {
        ArrayList<Department> departments = new ArrayList<>();
        try {
            while (resultSet.next()) {
                departments.add(new Department(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("DLink"),
                        resultSet.getInt("DTypeID"),
                        resultSet.getString("DType")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }


    public List<Pos> parsePoses(ResultSet resultSet) {
        ArrayList<Pos> poses = new ArrayList<>();
        try {
            while (resultSet.next()) {
                poses.add(new Pos(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("Did"),
                        resultSet.getString("DName"),
                        resultSet.getString("Address")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return poses;
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

    public User findUserById(long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from user_view where id = ?");
            statement.setLong(1, id);
            List<User> users = parseUsers(statement.executeQuery());
            if (users.size() == 0) {
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
            if (cards.size() != 0) {
                return cards.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Card> findCardsByUserId(long userId) {
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
            PreparedStatement statement = connection.prepareStatement("select * from transaction_view where from_card_id = ? or to_card_id = ? order by create_at desc limit ?, ?");
            statement.setLong(1, cardId);
            statement.setLong(2, cardId);
            statement.setInt(3, (pageNumber - 1) * pageSize);
            statement.setInt(4, pageSize);
            return parseTransactions(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Transaction> findTransactionsByCardId(long cardId) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from transaction_view where from_card_id = ? or to_card_id = ? order by create_at desc");
            statement.setLong(1, cardId);
            statement.setLong(2, cardId);
            return parseTransactions(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int countTransactionsByCardId(long cardId) {
        try {
            PreparedStatement statement = connection.prepareStatement("select count(*) from transaction where from_card_id = ? or to_card_id = ?");
            statement.setLong(1, cardId);
            statement.setLong(2, cardId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
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
            PreparedStatement statement = connection.prepareStatement("insert into card (type, user_id, enabled) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
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

    public void updatePasswordByUserId(long userId, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement("update user set user.password = ? where id = ?");
            statement.setString(1, password);
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertTransaction(long posId, long fromCardId, long toCardId, float amount, Timestamp createAt) {
        try {
            PreparedStatement statement;
            if (createAt == null) {
                statement = connection.prepareStatement("insert into transaction (from_card_id, to_card_id, amount, pos_id) values (?, ?, ?, ?)");
            }
            else {
                statement = connection.prepareStatement("insert into transaction (from_card_id, to_card_id, amount, pos_id, create_at) values (?, ?, ?, ?, ?)");
            }
            statement.setLong(1, fromCardId);
            statement.setLong(2, toCardId);
            statement.setFloat(3, amount);
            statement.setLong(4, posId);
            if (createAt != null) {
                statement.setTimestamp(5, createAt);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean transfer(long posId, long fromCardId, long toCardId, float amount) {
        if (findDailyTransferOutLessOrEqualThanDailyLimitByCardId(fromCardId) || fromCardId == 0) {
            try {
                connection.setAutoCommit(false);
                insertTransaction(posId, fromCardId, toCardId, amount, null);
                PreparedStatement statement;
                if (fromCardId != 0) {
                    statement = connection.prepareStatement("update card set balance = balance - ? where card.id = ?");
                    statement.setFloat(1, amount);
                    statement.setLong(2, fromCardId);
                    statement.executeUpdate();
                }
                if (toCardId != 0) {
                    statement = connection.prepareStatement("update card set balance = balance + ? where card.id = ?");
                    statement.setFloat(1, amount);
                    statement.setLong(2, toCardId);
                    statement.executeUpdate();
                }
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
        }
        return false;
    }


    public boolean findDailyTransferOutLessOrEqualThanDailyLimitByCardId(long cardId) {
        try {
            PreparedStatement statement = connection.prepareStatement("select sum(amount) <= (select daily_limit from card where id = ?) or sum(amount) is null from transaction_view where from_card_id = ? and create_at >= current_date()");
            statement.setLong(1, cardId);
            statement.setLong(2, cardId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.wasNull() || resultSet.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<CardType> findAllCardTypes() {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from card_type");
            return parseCardTypes(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Transaction> findAllTransactions() {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from transaction_view order by create_at desc");
            return parseTransactions(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Transaction> findAllTransactions(int pageNumber, int pageSize) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from transaction_view order by create_at desc limit ?, ?");
            statement.setInt(1, (pageNumber - 1) * pageSize);
            statement.setInt(2, pageSize);
            return parseTransactions(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int countTransactions() {
        try {
            PreparedStatement statement = connection.prepareStatement("select count(*) from transaction_view");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Schedule> findAllSchedules() {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from schedule");
            ResultSet resultSet = statement.executeQuery();
            return parseSchedules(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int countAllSchedules() {
        try {
            PreparedStatement statement = connection.prepareStatement("select count(*) from schedule");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Schedule> findSchedulesByUserId(long userId) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from schedule where user_id = ?");
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            return parseSchedules(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int countAllSchedulesByUserId(long userId) {
        try {
            PreparedStatement statement = connection.prepareStatement("select count(*) from schedule where user_id = ?");
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Schedule> findSchedulesByDate(Date date) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from schedule where date = ?");
            statement.setDate(1, date);
            ResultSet resultSet = statement.executeQuery();
            return parseSchedules(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertSchedule(Date date, long userId) {
        try {
            PreparedStatement statement = connection.prepareStatement("insert into schedule (date, user_id) values (?, ?)");
            statement.setDate(1, date);
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteScheduleByDateUserId(String date, long userId) {
        try {
            PreparedStatement statement = connection.prepareStatement("delete from schedule where date = ? and user_id = ?");
            statement.setString(1, date);
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStaffInfo(long userId, String department, String jobTitle, String gender, String telNumber) {
        try {
            PreparedStatement statement = connection.prepareStatement("update user set department_id = (select id from department where name = ?), job_title = ?, gender = ?, tel_number = ? where id = ?");
            statement.setString(1, department);
            statement.setString(2, jobTitle);
            statement.setString(3, gender);
            statement.setString(4, telNumber);
            statement.setLong(5, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Department> findAllDepartments() {
        try {
            PreparedStatement statement = connection.prepareStatement("select *,campus_card.DType.DType as DType from department,DType where DType.DTypeId = department.DTypeID");
            ResultSet resultSet = statement.executeQuery();
            return parseDepartments(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Pos> findAllPoses() {
        try {
            PreparedStatement statement = connection.prepareStatement("select *, campus_card.department.name as DName from pos,department where department.id = pos.Did and deleted = false ");
            ResultSet resultSet = statement.executeQuery();
            return parsePoses(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertPos(Long departmentId, String name, String position) {
        try {
            PreparedStatement statement = connection.prepareStatement("insert into pos (Did, Address, name) values (?, ?, ?)");
            statement.setLong(1, departmentId);
            statement.setString(2, position);
            statement.setString(3, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertDepartment(String name, String contact, int type) {
        try {
            PreparedStatement statement = connection.prepareStatement("insert into department (name, DLink, DTypeID) values (?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, contact);
            statement.setInt(3, type);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePos(long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("update pos set deleted = true where id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDepartment(long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("delete from department where id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePosById(long posId, long departmentId, String name, String position) {
        try {
            PreparedStatement statement = connection.prepareStatement("update pos set Did = ?, Address = ?, name = ? where id = ?");
            statement.setLong(1, departmentId);
            statement.setString(2, position);
            statement.setString(3, name);
            statement.setLong(4, posId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDepartmentById(long id, String name, String contact, int type) {
        try {
            PreparedStatement statement = connection.prepareStatement("update department set name = ?, DLink = ?, DTypeID = ? where id = ?");
            statement.setString(1, name);
            statement.setString(2, contact);
            statement.setInt(3, type);
            statement.setLong(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long findPosIdByName(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement("select id from pos where name = ? and deleted = false");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Pos findPosById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("select *,campus_card.department.name as dName from pos,department where department.id = pos.Did and pos.id = ? and deleted = false");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Pos(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("did"),
                        resultSet.getString("dName"),
                        resultSet.getString("address")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Transaction> findTransactionsByPosId(long posId) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from transaction_view where pos_id = ?");
            statement.setLong(1, posId);
            ResultSet resultSet = statement.executeQuery();
            return parseTransactions(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Transaction> findTransactionsByPosId(long posId, int pageNumber, int pageSize) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from transaction_view where pos_id = ? order by create_at desc limit ?, ?");
            statement.setLong(1, posId);
            statement.setInt(2, (pageNumber - 1) * pageSize);
            statement.setInt(3, pageSize);
            ResultSet resultSet = statement.executeQuery();
            return parseTransactions(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long countTransactionsByPosId(long posId) {
        try {
            PreparedStatement statement = connection.prepareStatement("select count(*) from transaction where pos_id = ?");
            statement.setLong(1, posId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Transaction> findTransactionsByPosesOfDepartmentOfUser(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from transaction_view left join pos on pos_id = pos.id left join department d on pos.did = d.id left join user u on d.id = u.department_id where u.username = ? order by create_at desc");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return parseTransactions(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Transaction> findTransactionsByPosesOfDepartmentOfUser(String username, int pageNumber, int pageSize) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from transaction_view left join pos on pos_id = pos.id left join department d on pos.did = d.id left join user u on d.id = u.department_id where u.username = ? order by create_at desc limit ?, ?");
            statement.setString(1, username);
            statement.setInt(2, (pageNumber - 1) * pageSize);
            statement.setInt(3, pageSize);
            ResultSet resultSet = statement.executeQuery();
            return parseTransactions(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long countTransactionsByPosesOfDepartmentOfUser(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("select count(*) from transaction_view left join pos on pos_id = pos.id left join department d on pos.did = d.id left join user u on d.id = u.department_id where u.username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int findDepartmentIdByUsername(String Dname){
        try {
            PreparedStatement statement = connection.prepareStatement("select department_id from user where username = ?");
            statement.setString(1, Dname);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<User> findAllStaffByDptID(int department_id){
        List<User> userList = new ArrayList<>();
        try {
            PreparedStatement statement = connection
                    .prepareStatement("select * from user_view where department_id = ?");
            statement.setLong(1, department_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                userList.add(new User(resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("type"),
                        resultSet.getString("type_name"),
                        resultSet.getString("gender"),
                        resultSet.getString("department"),
                        resultSet.getString("job_title"),
                        resultSet.getString("tel_number")
                ));
            }
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> findAllUser(){
        List<User> userList = new ArrayList<>();
        try {
            PreparedStatement statement = connection
                    .prepareStatement("select  * from user_view order by id");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                userList.add(new User(resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("type"),
                        resultSet.getString("type_name"),
                        resultSet.getString("gender"),
                        resultSet.getString("department"),
                        resultSet.getString("job_title"),
                        resultSet.getString("tel_number")
                ));
            }
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Card> findAllCards() {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from card_view");
            return parseCards(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Pos> findPosesOfDepartmentOfUserByUserId(long userId) {
        try {
            PreparedStatement statement = connection.prepareStatement("select *, department.name as Dname from pos left join user on user.department_id = pos.Did left join department on department_id = department.id where user.id = ?");
            statement.setLong(1, userId);
            return parsePoses(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
