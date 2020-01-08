package cn.edu.nchu.stu.data.model;

import java.sql.Timestamp;

public class Transaction {

    private long id, fromCardId, toCardId;

    private String posName;

    private float amount;

    private Timestamp createAt;

    public Transaction(long id, Long fromCardId, Long toCardId, String posName, float amount, Timestamp createAt) {
        this.id = id;
        this.fromCardId = fromCardId;
        this.toCardId = toCardId;
        this.posName = posName;
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

    public String getPosName() {
        return posName;
    }

    public float getAmount() {
        return amount;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }
}
