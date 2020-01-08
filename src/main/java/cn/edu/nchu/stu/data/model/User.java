package cn.edu.nchu.stu.data.model;

public class User {

    public static final int STUDENT = 1;

    public static final int ADMINISTRATOR = 2;

    public static final int STAFF = 3;

    private long id;

    private String username, password, typeName, gender, department, jobTitle, telNumber;

    private int type;

    public User(long id, String username, String password, int type, String typeName, String gender, String department, String jobTitle, String telNumber) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
        this.typeName = typeName;
        this.gender = gender;
        this.department = department;
        this.jobTitle = jobTitle;
        this.telNumber = telNumber;
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

    public String getDepartment() {
        return department;
    }

    public String getGender() {
        return gender;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getTelNumber() {
        return telNumber;
    }
}
