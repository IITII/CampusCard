package cn.edu.nchu.stu.data.model;

public class Card {

    private long id, userId;

    private int type;

    private String typeName;

    private boolean enable;

    private float balance, dailyLimit;

    public Card(long id, long userId, int type, String typeName, boolean enable, float balance, float dailyLimit) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.typeName = typeName;
        this.enable = enable;
        this.balance = balance;
        this.dailyLimit = dailyLimit;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public int getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    public boolean isEnable() {
        return enable;
    }

    public float getBalance() {
        return balance;
    }

    public float getDailyLimit() {
        return dailyLimit;
    }
}
