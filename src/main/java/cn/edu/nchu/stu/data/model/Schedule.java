package cn.edu.nchu.stu.data.model;

import java.sql.Date;

public class Schedule {

    private int userId;

    private Date date;

    public Schedule(int userId, Date date) {
        this.userId = userId;
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public Date getDate() {
        return date;
    }
}
