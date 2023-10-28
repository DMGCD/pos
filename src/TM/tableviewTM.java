package TM;

public class tableviewTM {
private  String id;
private String name;

    public tableviewTM() {
    }

    private int price;

    public tableviewTM(String id, String name, int price, int qunty) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qunty = qunty;
    }

    private int qunty;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQunty() {
        return qunty;
    }

    public void setQunty(int qunty) {
        this.qunty = qunty;
    }
}
