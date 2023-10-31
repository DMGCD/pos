package controller;

import TM.itemtable;
import db.DB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Optional;

public class itemformcontroller {


    public AnchorPane root;
    public Pane subrootaddnew;
    public Pane subrootremove;
    public TextField txtiname;
    public TextField txtprice;
    public TextField txtquantity;
    public TextField txtremoveitemid;
    public Button btncanceladd;
    public Button btncanselremove;
    public TableView <itemtable>tblitem;
    public TableColumn<itemtable,String> tblitemidcolumn;
    public TableColumn<itemtable,String> tblnamecolomn;
    public TableColumn<itemtable,String> tblpricecolumn;
    public TableColumn<itemtable,String> tblquantitycolumn;
    public TableColumn<itemtable,String> tbltotalpricecolumn;
    public TextField txtnewQuantity;
    public TextField txtnewprice;
    public TextField txtNewname;
    public Pane subrootUpdate;
    String newItemID;


    public void initialize(){
        subrootUpdate.setDisable(true);
        subrootaddnew.setDisable(true);
        subrootremove.setDisable(true);
        btncanceladd.setDisable(true);
        btncanselremove.setDisable(true);
        setdetailsTable();
        tblitem.getSelectionModel().clearSelection();

        loadtableitem();

        tblitem.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<itemtable>() {
            @Override
            public void changed(ObservableValue<? extends itemtable> observable, itemtable oldValue, itemtable newValue) {

                itemtable selectedItem = tblitem.getSelectionModel().getSelectedItem();

                if(selectedItem!=null){
                    if(!subrootremove.isDisable()){
                        subrootUpdate.setDisable(true);
                        txtremoveitemid.setText(selectedItem.getId());
                        tblitem.refresh();

                    }
                    else if(!subrootUpdate.isDisable()){
                        subrootremove.setDisable(true);
                        

                        newItemID=selectedItem.getId();
                        txtNewname.setText(selectedItem.getName());
                        String price = selectedItem.getPrice();
                        txtnewprice.setText(price);
                         txtnewQuantity.setText(selectedItem.getQuantity());


                    }
                }
            }
        });
        


    }
    public void btnUpdateOnAction(ActionEvent actionEvent) {
        subrootUpdate.setDisable(false);



    }
    public void btnUpdataINSubrootONAction(ActionEvent actionEvent) {

        String itemNameNew = txtNewname.getText();
        String itemNewQuantity = txtnewQuantity.getText();
        String newPrice = txtnewprice.getText();
        Connection connection = DB.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update item set iname=?,price=?,qunty=?,totalPrice=? where itemid=? ");
            preparedStatement.setObject(1,itemNameNew);
            preparedStatement.setObject(2,Integer.parseInt(newPrice));
            preparedStatement.setObject(3,Integer.parseInt(itemNewQuantity));
            preparedStatement.setObject(4,Integer.parseInt(itemNewQuantity)*Integer.parseInt(newPrice));
            preparedStatement.setObject(5,newItemID);
            preparedStatement.executeUpdate();
            tblitem.refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        loadtableitem();
       UpdateClear();
        clr();


    }
public void UpdateClear(){
        txtNewname.clear();
        txtnewprice.clear();
        txtnewQuantity.clear();
    }
    public void btnCancelInSubrootOnAction(ActionEvent actionEvent) {
        subrootUpdate.setDisable(true);
        UpdateClear();
        loadtableitem();

    }

    public void btngoback(ActionEvent actionEvent) throws IOException {

        Parent parent = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("../veiw/hostform.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public void txtinameOnaction(ActionEvent actionEvent) {
        if(!txtiname.getText().isEmpty()){
            txtprice.requestFocus();

        }
    }

    public void txtpriceOnAction(ActionEvent actionEvent) {
        if(!txtprice.getText().isEmpty()){
            txtquantity.requestFocus();
        }
    }
// enter and save the new items

    public void txtquantityonAction(ActionEvent actionEvent) {
        if(!txtiname.getText().isEmpty() && !txtprice.getText().isEmpty() && !txtquantity.getText().isEmpty()){
            getitem();
            showthelastitems();
            tblitem.refresh();
            tblitem.getSelectionModel().clearSelection();

        }
        else{
            subrootaddnew.setDisable(true);
            tblitem.getSelectionModel().clearSelection();
            btncanceladd.setDisable(true);
        }
    }

    public void btnsaveOnAction(ActionEvent actionEvent) {
        if(!txtiname.getText().isEmpty() && !txtprice.getText().isEmpty() && !txtquantity.getText().isEmpty()){
            getitem();
            showthelastitems();
            tblitem.getSelectionModel().clearSelection();

        }
        else{
            subrootaddnew.setDisable(true);
            btncanceladd.setDisable(true);
            tblitem.getSelectionModel().clearSelection();
        }

    }

    public void btnAddnewOnAction(ActionEvent actionEvent) {
        subrootaddnew.setDisable(false);
        btncanceladd.setDisable(false);
        loadtableitem();
        tblitem.getSelectionModel().clearSelection();


    }
    //remove button

    public void btnremoveOnAction(ActionEvent actionEvent) {
        subrootremove.setDisable(false);
        btncanselremove.setDisable(false);
        tblitem.setDisable(false);
        txtremoveitemid.requestFocus();
        tblitem.refresh();
        clr();

    }
    public void clr(){

        tblitem.getSelectionModel().clearSelection();
    }
    public void txtremoveitemidOnAction(ActionEvent actionEvent) {
        if(!txtremoveitemid.getText().isEmpty()){
            removemethod();
            afterremovetablerefresh();
            tblitem.getSelectionModel().clearSelection();


        }
        else{
            subrootremove.setDisable(true);
            btncanselremove.setDisable(true);
            tblitem.getSelectionModel().clearSelection();

        }


    }

    public void btnremoveinpaneOnAction(ActionEvent actionEvent) {
        if(!txtremoveitemid.getText().isEmpty()){
            removemethod();
            tblitem.getSelectionModel().clearSelection();
            afterremovetablerefresh();




        }
        else{
            subrootremove.setDisable(true);
            tblitem.setDisable(true);
            btncanselremove.setDisable(true);
            tblitem.getSelectionModel().clearSelection();
        }

    }




// add new items to database
    public void getitem(){

        Connection connection = DB.getInstance().getConnection();
        try {
            if(!txtiname.getText().isEmpty() && !txtprice.getText().isEmpty() && !txtquantity.getText().isEmpty()){
                int y = Integer.parseInt(txtquantity.getText());
                PreparedStatement preparedStatement = connection.prepareStatement("insert into item (itemid,iname,qunty,price,rqunty,totalPrice) values(?,?,?,?,?,?)");
                preparedStatement.setObject(1,Itemid());
                preparedStatement.setObject(2,txtiname.getText());
                preparedStatement.setObject(3,y);
                preparedStatement.setObject(4,Integer.parseInt(txtprice.getText()));
                preparedStatement.setObject(5,remaining());
                preparedStatement.setObject(6,totlaprice());
                int i = preparedStatement.executeUpdate();
                if(i>=0){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"If You  want add another item !", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if(buttonType.get().equals(ButtonType.YES)){
                        txtiname.clear();
                        txtiname.requestFocus();
                        txtprice.clear();
                        txtquantity.clear();
                        btncanceladd.setDisable(false);
                    }
                    else{
                        txtiname.clear();
                        txtiname.requestFocus();
                        txtprice.clear();
                        txtquantity.clear();
                        subrootaddnew.setDisable(true);
                        btncanceladd.setDisable(true);

                    }
                }
            }
            else{
                subrootaddnew.setDisable(true);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public String Itemid(){
        String itemid;

        Connection connection = DB.getInstance().getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select itemid from item order by itemid desc limit 1 ");
            boolean next = resultSet.next();
            if(next){
                itemid = resultSet.getString(1);
                itemid =itemid.substring(1,itemid.length());
                int empidI =Integer.parseInt(itemid);
                empidI++;
                if(empidI<10){
                    itemid ="I00"+empidI;
                }
                else if(empidI<100){
                    itemid ="I0"+empidI;
                }
                else if(empidI<1000){
                    itemid="I"+empidI;
                }
                return itemid;
            }
            else {
                itemid ="I001";
                return itemid;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int remaining(){
        int i = Integer.parseInt(txtquantity.getText());
        return i;
    }
    public int totlaprice(){
        int j=0;
        int quantity = Integer.parseInt(txtquantity.getText());
        int price =Integer.parseInt(txtprice.getText());
        j=quantity*price;
        return j;
    }
    public void removemethod(){

        Connection connection = DB.getInstance().getConnection();
        if(!txtremoveitemid.getText().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Sure You Want Remove IT", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if(buttonType.get().equals(ButtonType.YES)){
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("delete from item where itemid=?");
                    preparedStatement.setObject(1,txtremoveitemid.getText());
                    int i = preparedStatement.executeUpdate();
                    if(i>=0){
                        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "Remove Success!");
                        alert1.showAndWait();
                        txtremoveitemid.clear();
                        subrootremove.setDisable(true);
                        btncanselremove.setDisable(true);

                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                txtremoveitemid.clear();
                btncanselremove.setDisable(false);
            }
        }
    }


    public void btncanceladdnewOnAction(ActionEvent actionEvent) {
        txtiname.clear();
        txtquantity.clear();
        txtprice.clear();
        subrootaddnew.setDisable(true);
        btncanceladd.setDisable(true);
        tblitem.getSelectionModel().clearSelection();

    }

    public void btncancelremveOnAction(ActionEvent actionEvent) {
        txtremoveitemid.clear();
        subrootremove.setDisable(true);
        btncanselremove.setVisible(true);
        tblitem.getSelectionModel().clearSelection();

    }

    // initialize for table to where colomn is its aurthorized
    
    public void setdetailsTable(){

       tblitemidcolumn.setCellValueFactory(new PropertyValueFactory<itemtable,String>("id"));
        tblnamecolomn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblpricecolumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        tblquantitycolumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tbltotalpricecolumn.setCellValueFactory(new PropertyValueFactory<>("total_price"));
    }
    
    public void loadtableitem(){
        int itemCount = 0,i=1;
        tblitem.getItems().clear();
        Connection connection = DB.getInstance().getConnection();

        try {


                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select *from item");
                Statement statement1 = connection.createStatement();
                ResultSet resultSet1 = statement1.executeQuery("SELECT COUNT(*) AS item_count FROM item");
                boolean next1 = resultSet1.next();
            int row = resultSet.getRow();


            if(next1){
                    itemCount = resultSet1.getInt("item_count");

                }
                i =itemCount;
                 resultSet.next();
            while(i>0) {



                           String id = resultSet.getString(1);
                           String name = resultSet.getString(2);
                           String quantity = resultSet.getString(3);
                           String itemprice = resultSet.getString(4);
                           String totalP = resultSet.getString(6);
                           ObservableList<itemtable> itemstore = tblitem.getItems();
                           itemstore.add(new itemtable(id, name, itemprice, quantity, totalP));
                resultSet.next();
                tblitem.refresh();


                   i--;


               }












            } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


    }

    public void showthelastitems(){
        Connection connection = DB.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select *from item order by itemid desc limit 1");
            boolean next = resultSet.next();
            if(next){

                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String quantity = resultSet.getString(3);
                String itemprice = resultSet.getString(4);
                String totalP = resultSet.getString(6);
                ObservableList<itemtable> itemstore = tblitem.getItems();
                itemstore.add(new itemtable(id, name, itemprice, quantity, totalP));
                tblitem.refresh();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ;
    }

    public void afterremovetablerefresh(){
        ObservableList<itemtable> items = tblitem.getItems();
        items.clear();
        loadtableitem();
        tblitem.refresh();
        tblitem.getSelectionModel().clearSelection();

    }



}

