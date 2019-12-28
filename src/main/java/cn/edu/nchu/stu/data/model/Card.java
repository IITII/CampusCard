package cn.edu.nchu.stu.data.model;

public class Card {

    private long id, userId;

    private int type;

    private String typeName;

    private boolean enabled;

    private float balance, dailyLimit;

    public Card(long id, long userId, int type, String typeName, boolean enabled, float balance, float dailyLimit) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.typeName = typeName;
        this.enabled = enabled;
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

    public boolean isEnabled() {
        return enabled;
    }

    public float getBalance() {
        return balance;
    }

    public float getDailyLimit() {
        return dailyLimit;
    }
}
