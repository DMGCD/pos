package TM;

public class tblHost {

    private  String itemId;
    private String itemName;
    private  int Quantity;

    public tblHost() {
    }

    public tblHost(String itemId, String itemName, int quantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        Quantity = quantity;
    }



    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
