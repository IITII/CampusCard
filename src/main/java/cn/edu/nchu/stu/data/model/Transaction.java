package cn.edu.nchu.stu.data.model;

import java.sql.Timestamp;

public class Transaction {

    private long id, fromCardId, toCardId;

    private float amount;

    private Timestamp createAt;

    public Transaction(long id, Long fromCardId, Long toCardId, float amount, Timestamp createAt) {
        this.id = id;
        this.fromCardId = fromCardId;
        this.toCardId = toCardId;
        this.amount = amount;
        this.createAt = createAt;
    }

    public long getId() {
        return id;
    }

    public long getFromCardId() {
        return fromCardId;
    }

    public long getToCardId() {
        return toCardId;
    }

    public float getAmount() {
        return amount;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }
}
