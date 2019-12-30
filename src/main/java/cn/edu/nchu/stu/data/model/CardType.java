package cn.edu.nchu.stu.data.model;

public class CardType {

    private Integer id;

    private String name;

    public CardType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
