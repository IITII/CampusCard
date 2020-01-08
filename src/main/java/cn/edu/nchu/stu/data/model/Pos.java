package cn.edu.nchu.stu.data.model;

public class Pos {

    int id;

    String name;
    int did;
    String dName;
    String address;

    public Pos(int id, String name, int did, String dName, String address) {
        this.id = id;
        this.name = name;
        this.did = did;
        this.address = address;
        this.dName = dName;
    }

    public String getdName() {
        return dName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDid() {
        return did;
    }

    public String getAddress() {
        return address;
    }
}
