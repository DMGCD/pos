package controller;

import TM.tblshowTM;
import db.DB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.sql.*;

public class showbillformcontroller {
    public TableView<tblshowTM> tblshow;
    public Label lblbilidshowbill;
    public Pane subrootBillUpdate;
    public Button btnPrint;
    public TextField txtQuantityCange;
public String itemId;
public int priceNew;

    public Label lblpriceTotal;
    public int quantityBill;
    public int quantityItem;
int sum;
    public void initialize(){
        setTotalPrice();
        columntbl();
        loadshow();
        tblshow.refresh();
        subrootBillUpdate.setVisible(false);
        lblbilidshowbill.setText(workformcontroller.bid);

        tblshow.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<tblshowTM>() {
            @Override
            public void changed(ObservableValue<? extends tblshowTM> observable, tblshowTM oldValue, tblshowTM newValue) {
                tblshowTM selectedItem = tblshow.getSelectionModel().getSelectedItem();
                subrootBillUpdate.setVisible(true);

                if(selectedItem!=null){
                    txtQuantityCange.setText(Integer.toString(selectedItem.getQuntity()));
                    itemId= selectedItem.getItemID();

                    Connection connection = DB.getInstance().getConnection();
                    tblshow.setEditable(true);
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement("select *from item where itemid =?");
                        preparedStatement.setObject(1,itemId);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()){
                            String string = resultSet.getString(4);
                            quantityItem= resultSet.getInt(3);
                            priceNew =Integer.parseInt(string);

                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }


                }

            }
        });



    }
    public void columntbl(){

        tblshow.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ItemID"));
        tblshow.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblshow.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("quntity"));
        tblshow.refresh();
    }
    public void loadshow(){
        tblshow.getItems().clear();

        Connection connection = DB.getInstance().getConnection();
        try {
            String id =workformcontroller.bid;
            PreparedStatement preparedStatement = connection.prepareStatement("select *from bil where bid =?");
            preparedStatement.setObject(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                String name =resultSet.getString(5);
                String ItemID = resultSet.getString(3);
                int quantity =Integer.parseInt(resultSet.getString(7));
                ObservableList<tblshowTM> items = tblshow.getItems();
                items.add(new tblshowTM(ItemID,name,quantity));
                tblshow.refresh();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void btnChangeOnAction(ActionEvent actionEvent) {

        String text = txtQuantityCange.getText();
        int QuntyNew= Integer.parseInt(text);
        String bid = workformcontroller.bid;
        Connection connection = DB.getInstance().getConnection();
        int blance =QuntyNew* priceNew;
        if(QuntyNew<=workformcontroller.getQunatity){

            try {
                PreparedStatement preparedStatement = connection.prepareStatement("update bil set qunty =? ,blance=? where bid=? and itemid=?   ");
                preparedStatement.setObject(1,QuntyNew);
                preparedStatement.setObject(2,blance);
                preparedStatement.setObject(3,bid);
                preparedStatement.setObject(4,itemId);
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            updateItemTable();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Is Larger Than Store Quntity!");
            alert.showAndWait();
            txtQuantityCange.clear();
            tblshow.getSelectionModel().clearSelection();
        }
        setTotalPrice();
        loadshow();
        tblshow.refresh();

        txtQuantityCange.clear();
        subrootBillUpdate.setVisible(false);


    }

    public void btnCancleOnAction(ActionEvent actionEvent) {

        txtQuantityCange.clear();
        tblshow.getSelectionModel().clearSelection();
        tblshow.refresh();
        subrootBillUpdate.setVisible(false);
        setTotalPrice();

    }

    public void btnPrintOnAction(ActionEvent actionEvent) {
    }

    public void btnRemoveItemBIllOnAction(ActionEvent actionEvent) {
        Connection connection = DB.getInstance().getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from bil where itemid=? and bid=?");
            preparedStatement.setObject(1,itemId);
            preparedStatement.setObject(2,workformcontroller.bid);
            preparedStatement.executeUpdate();
            tblshow.getItems().clear();
            loadshow();
            tblshow.refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        updateItemTable();
        setTotalPrice();
        txtQuantityCange.clear();
        subrootBillUpdate.setVisible(false);

    }

    public void setTotalPrice(){
        sum=0;

        Connection connection = DB.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select *from bil where bid=?");
            preparedStatement.setObject(1,workformcontroller.bid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String string = resultSet.getString(4);
                quantityBill = resultSet.getInt(7);
                int getPriceBill = Integer.parseInt(string);
                sum+=getPriceBill;

            }
            lblpriceTotal.setText(Integer.toString(sum));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateItemTable(){

        Connection connection = DB.getInstance().getConnection();
        try {
            int x=quantityItem-quantityBill;
            PreparedStatement preparedStatement = connection.prepareStatement("update item set rqunty =? where itemid=?");
            preparedStatement.setObject(1,x);
            preparedStatement.setObject(2,itemId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
