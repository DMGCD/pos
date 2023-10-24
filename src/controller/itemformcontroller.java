package controller;

import db.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
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

    public void initialize(){
        subrootaddnew.setDisable(true);
        subrootremove.setDisable(true);
        btncanceladd.setDisable(true);
        btncanselremove.setDisable(true);


    }

    public void btngoback(ActionEvent actionEvent) throws IOException {

        Parent parent = FXMLLoader.load(this.getClass().getResource("../veiw/hostform.fxml"));
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

    public void txtquantityonAction(ActionEvent actionEvent) {
        if(!txtiname.getText().isEmpty() && !txtprice.getText().isEmpty() && !txtquantity.getText().isEmpty()){
            getitem();
        }
        else{
            subrootaddnew.setDisable(true);
            btncanceladd.setDisable(true);
        }
    }

    public void btnsaveOnAction(ActionEvent actionEvent) {
        if(!txtiname.getText().isEmpty() && !txtprice.getText().isEmpty() && !txtquantity.getText().isEmpty()){
            getitem();
        }
        else{
            subrootaddnew.setDisable(true);
            btncanceladd.setDisable(true);
        }

    }

    public void btnAddnewOnAction(ActionEvent actionEvent) {
        subrootaddnew.setDisable(false);
        btncanceladd.setDisable(false);


    }
    //remove button

    public void btnremoveOnAction(ActionEvent actionEvent) {
        subrootremove.setDisable(false);
        btncanselremove.setDisable(false);
        txtremoveitemid.requestFocus();

    }

    public void txtremoveitemidOnAction(ActionEvent actionEvent) {
        if(!txtremoveitemid.getText().isEmpty()){
            removemethod();
        }
        else{
            subrootremove.setDisable(true);
            btncanselremove.setDisable(true);
        }


    }

    public void btnremoveinpaneOnAction(ActionEvent actionEvent) {
        if(!txtremoveitemid.getText().isEmpty()){
            removemethod();
        }
        else{
            subrootremove.setDisable(true);
            btncanselremove.setDisable(true);
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

    }

    public void btncancelremveOnAction(ActionEvent actionEvent) {
        txtremoveitemid.clear();
        subrootremove.setDisable(true);
        btncanselremove.setVisible(true);
    }
}
