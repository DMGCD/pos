package TM;

public class employetable {

    private String itemname;

    public employetable() {
    }

    public employetable(String itemname, int price, int available, String id) {
        this.itemname = itemname;
        this.price = price;
        this.available = available;
        this.id = id;
    }

    private int price;
    private int available;
    private String id;

    public String getItemname() {

        return itemname;
    }

    public void setItemname(String itemname) {

        this.itemname = itemname;
    }

    public int getAvailable() {

        return available;
    }

    public void setAvailable(int available) {

        this.available = available;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
