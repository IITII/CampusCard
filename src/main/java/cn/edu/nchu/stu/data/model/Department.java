package cn.edu.nchu.stu.data.model;

public class Department {

    int id;

    String name;

    String DLink;
    int DTypeID;
    String DTypeName;

    public String getDTypeName() {
        return DTypeName;
    }

    public Department(int id, String name, String DLink, int DTypeID, String DTypeName) {
        this.id = id;
        this.DTypeName = DTypeName;
        this.name = name;
        this.DLink = DLink;
        this.DTypeID=DTypeID;
    }

    public String getDLink() {
        return DLink;
    }

    public int getDTypeID() {
        return DTypeID;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
