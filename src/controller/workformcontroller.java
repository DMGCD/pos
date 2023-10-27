package controller;

import TM.employetable;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class workformcontroller {
    public AnchorPane root;
    public TableView<employetable> tblitememp;
    public TextField txtitemid;
    public Label lblquntity;
    public Button btnaddbill;
    public TextField txtgetqunty;
    public String empid;
    public String price;
    public String nameItem;
    public Button btnnewBill;
    public Label lblitemid;
    int getqunty;
    String bId;



    public void initialize() {
        showQnadID(false);
        txtitemid.setDisable(true);
        addtabel();
        loadTalble();
        tblitememp.setDisable(true);

        tblitememp.getSelectionModel().clearSelection();
       tblitememp.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<employetable>() {
           @Override
           public void changed(ObservableValue<? extends employetable> observable, employetable oldValue, employetable newValue) {

               employetable select = tblitememp.getSelectionModel().getSelectedItem();
               if(select.getAvailable()!=0){
                    nameItem = select.getItemname();
                   price=Integer.toString(select.getPrice());
                   txtitemid.setText(select.getId());
                   lblquntity.setText(Integer.toString(select.getAvailable()));
                   txtgetqunty.requestFocus();
                   tblitememp.getSelectionModel().clearSelection();
                   tblitememp.refresh();
               }
               else{
                   Alert alert = new Alert(Alert.AlertType.WARNING, "Is Quantity Empty");
                   alert.show();


               }
               if(select == null){
                   return;
               }

           }


       });
    }
    public void btnimageonMouseClicked(MouseEvent mouseEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Go The Back ",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get().equals(ButtonType.YES)){

            Parent parent = FXMLLoader.load(this.getClass().getResource("../veiw/employeform.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) root.getScene().getWindow();
            stage.centerOnScreen();
            stage.setScene(scene);
        }
    }


    public void txtgetquantityOnAction(ActionEvent actionEvent) {
        onebilitem();

    }

    public void btnaddbillOnAction(ActionEvent actionEvent) {
        onebilitem();

    }

    public void btnShwobilOnAction(ActionEvent actionEvent) {
    }
    public void btnnewBillOnAction(ActionEvent actionEvent) {
        showQnadID(true);
        bId =bilid();
        tblitememp.setDisable(false);
    }
    public void btnFinishOnAction(ActionEvent actionEvent) {
        showQnadID(false);
        tblitememp.setDisable(true);
    }



    public void addtabel() {

        tblitememp.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblitememp.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("itemname"));
        tblitememp.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("available"));
        tblitememp.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("price"));
        tblitememp.refresh();

    }
    public void loadTalble(){

        ObservableList<employetable> item = tblitememp.getItems();
        item.clear();
        Connection connection = DB.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select *from item");
            while (resultSet.next()){
                String id=resultSet.getString(1);
                String iname =resultSet.getString(2);
                int qunty = Integer.parseInt(resultSet.getString(5));
                int price =Integer.parseInt(resultSet.getString(4));
                item.add(new employetable(iname,qunty,price,id));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void onebilitem(){

        empid=loginformcontroller.Empid;

        String itemID = txtitemid.getText();
        int priceI =Integer.parseInt(price);
        if(txtgetqunty.getText().isEmpty()){
            getqunty=1;
        }
        else{
            int i = Integer.parseInt(lblquntity.getText());
            int jget = Integer.parseInt(txtgetqunty.getText());
            if(i>=jget){
                getqunty = jget;
            }
            else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Not Availabel Quantity in Store , you needed");
                alert.show();
                txtgetqunty.requestFocus();
            }


        }
        int balance = priceI*getqunty;
        Connection connection = DB.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into bil(bid,empid,itemid,blance,iname,qunty) values(?,?,?,?,?,?)");
            preparedStatement.setObject(1,bId);
            preparedStatement.setObject(2,empid);
            preparedStatement.setObject(3,itemID);
            preparedStatement.setObject(4,balance);
            preparedStatement.setObject(5,nameItem);
            preparedStatement.setObject(6,getqunty);
            preparedStatement.executeUpdate();




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        txtgetqunty.clear();
        txtitemid.clear();


    }

    public String bilid(){
        String bilid;

        Connection connection = DB.getInstance().getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select bid from bil order by bid desc limit 1 ");
            boolean next = resultSet.next();
            if(next){
                bilid = resultSet.getString(1);
                bilid =bilid.substring(1,bilid.length());
                int empidI =Integer.parseInt(bilid);
                empidI++;
                if(empidI<10){
                    bilid ="B00"+empidI;
                }
                else if(empidI<100){
                    bilid ="B0"+empidI;
                }
                else if(empidI<1000){
                    bilid="B"+empidI;
                }
                return bilid;
            }
            else {
                bilid ="B001";
                return bilid;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void showQnadID( boolean x){
        txtitemid.setVisible(x);
        txtgetqunty.setVisible(x);
        lblquntity.setVisible(x);
        lblitemid.setVisible(x);
        btnaddbill.setVisible(x);


    }



}