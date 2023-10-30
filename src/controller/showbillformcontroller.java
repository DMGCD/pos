package controller;

import TM.tblshowTM;
import db.DB;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class showbillformcontroller {
    public TableView<tblshowTM> tblshow;
    public Label lblbilidshowbill;

    public void initialize(){
        columntbl();
        loadshow();
        tblshow.refresh();
        lblbilidshowbill.setText(workformcontroller.bid);



    }
    public void columntbl(){

        tblshow.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblshow.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("quantity"));
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
                int quantity =Integer.parseInt(resultSet.getString(7));
                ObservableList<tblshowTM> items = tblshow.getItems();
                items.add(new tblshowTM(name,quantity));
                tblshow.refresh();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
