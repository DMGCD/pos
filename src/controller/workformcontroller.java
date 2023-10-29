package controller;

import TM.tableviewTM;
import db.DB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class workformcontroller {


    public TableView<tableviewTM> tblview;
    public AnchorPane root;
    public Pane subroot;
    public TextField txtgetQunty;
    public TextField txtID;
    public String bid;
    public Label lblAvailble;
    public Label lbltotal;
    public Label lblpricelbl;
    int priceget;
    String iname;
    int available;
    int iget;
    String selectID;
    int balance;
int sum;
    public void initialize(){
        definecolumn();
        lbltotal.setText("0");
        loadTable();
        tblview.setDisable(true);
        txtID.setDisable(true);
        subroot.setVisible(false);
        lbltotlisvi(false);
        tblview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<tableviewTM>() {
            @Override
            public void changed(ObservableValue<? extends tableviewTM> observable, tableviewTM oldValue, tableviewTM newValue) {
                tableviewTM select = tblview.getSelectionModel().getSelectedItem();
                lblAvailble.setText(Integer.toString(select.getQunty()));
                txtgetQunty.requestFocus();
                priceget =select.getPrice();
                available=select.getQunty();
                iname =select.getName();
                txtID.setText(select.getId());
                selectID=select.getId();



                tblview.getSelectionModel().clearSelection();
                if(select==null){
                    return;
                }
                tblview.refresh();


            }
        });


}

// *****************************************************table function


public void definecolumn(){
    tblview.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
    tblview.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
    tblview.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("price"));
    tblview.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qunty"));
    tblview.refresh();

}
public void loadTable(){
    Connection connection = DB.getInstance().getConnection();
    try {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("Select *from item");
        while (resultSet.next()){
            String id =resultSet.getString(1);
            String name=resultSet.getString(2);
            int price =Integer.parseInt(resultSet.getString(4));
            int qunty =Integer.parseInt(resultSet.getString(5));
            ObservableList<tableviewTM> items = tblview.getItems();
            items.add(new tableviewTM(id,name,price,qunty));
            tblview.refresh();
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
// *************************************************************


    public void btnGoBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("../veiw/employeform.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();

    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        lbltotlisvi(true);
        bid =idgenarateBIl();
        subroot.setVisible(true);
        tblview.setDisable(false);

    }
// new one bill ad the bil table;
    public void btnaddONaction(ActionEvent actionEvent) {
        tblview.getItems().clear();
        adbilmethod();

        loadTable();
        tblview.refresh();

    }
    public void txtgetquntyOnAction(ActionEvent actionEvent) {
        tblview.getItems().clear();
        adbilmethod();

        loadTable();
        tblview.refresh();
    }
// insert to bill table data
    public void intobilltable(){
        int balnce = Integer.parseInt(txtgetQunty.getText()) *priceget;
        int qunty;
        Connection connection = DB.getInstance().getConnection();
        try {
            if(!txtgetQunty.getText().isEmpty() && Integer.parseInt(txtgetQunty.getText())!=0){
                qunty = Integer.parseInt(txtgetQunty.getText());
            }
            else {
                qunty=1;
            }

            PreparedStatement preparedStatement = connection.prepareStatement("insert into bil (bid,empid,itemid,blance,iname,qunty) values(?,?,?,?,?,?)");
            preparedStatement.setObject(1,bid);
            preparedStatement.setObject(2,loginformcontroller.Empid);
            preparedStatement.setObject(3,txtID.getText());
            preparedStatement.setObject(4,balnce);
            preparedStatement.setObject(5,iname);
            preparedStatement.setObject(6,qunty);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

// ID Genarate function for Create bil ID
    public String idgenarateBIl(){
        String BID =null;
        Connection connection = DB.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select *from bil order by bid desc limit 1");

            if(resultSet.next()){
                String bilid = resultSet.getString(1);
                bilid = bilid.substring(1,bilid.length());
                int id =Integer.parseInt(bilid);
                id++;
                if(id<10){
                    BID ="B0"+Integer.toString(id);
                }
                else {
                    BID ="B"+Integer.toString(id);
                }
                return  BID;

            }
            else {
                BID="B1";
                return  BID;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    //****************************
    public void adbilmethod(){
        iget= Integer.parseInt(txtgetQunty.getText());

        if(available>=iget && iget>0){
            intobilltable();
            txtID.clear();
            txtgetQunty.clear();
            lblAvailble.setText("0");
            tblview.requestFocus();
            updateItemTable();

            totalbalnce();
            sum+=balance;
            lbltotal.setText(Integer.toString(sum));



        }
        else{

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Quantity is Above limite in Store ");
            alert.showAndWait();
            txtgetQunty.clear();
            txtgetQunty.requestFocus();
        }
    }
public void updateItemTable(){

    Connection connection = DB.getInstance().getConnection();
    try {
        PreparedStatement preparedStatement = connection.prepareStatement("update item set rqunty=? where itemid=?");
        int rqunty =available-iget;
        preparedStatement.setObject(1,rqunty);
        preparedStatement.setObject(2,selectID);
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

public void totalbalnce(){

    Connection connection = DB.getInstance().getConnection();
    try {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select *from bil order by id desc limit 1");

        if(resultSet.next()){
            String balnceprice = resultSet.getString(4);
            balance =Integer.parseInt(balnceprice);

        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }


}
public void lbltotlisvi(Boolean x){
        lbltotal.setVisible(x);
        lblpricelbl.setVisible(x);
}

}
