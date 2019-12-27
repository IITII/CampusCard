package cn.edu.nchu.stu.data.model;

public class User {

    private long id;

    private String username, password, typeName;

    private int type;

    public User(long id, String username, String password, int type, String typeName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
        this.typeName = typeName;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }
}
