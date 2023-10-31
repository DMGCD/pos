package TM;

public class tblshowTM {

   private String ItemID;
   private String name;

    public tblshowTM() {
    }

    private int  quntity;

    public tblshowTM(String itemID, String name, int quntity) {
        ItemID = itemID;
        this.name = name;
        this.quntity = quntity;
    }



    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuntity() {
        return quntity;
    }

    public void setQuntity(int quntity) {
        this.quntity = quntity;
    }
}
