package cn.edu.nchu.stu.data.model;

import java.sql.Timestamp;

public class Transaction {

    private long id, cardId;

    private float amount;

    private Timestamp createAt;

    public Transaction(long id, long cardId, float amount, Timestamp createAt) {
        this.id = id;
        this.cardId = cardId;
        this.amount = amount;
        this.createAt = createAt;
    }

    public long getId() {
        return id;
    }

    public long getCardId() {
        return cardId;
    }

    public float getAmount() {
        return amount;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }
}
