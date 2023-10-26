package controller;

import TM.employemsgTM;
import db.DB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Optional;

public class hostformcontroller {


    public AnchorPane root;
    public Pane subroot;
    public TextField txtusernameupdate;
    public TextField txtpasswordupdate;
    public Label lblemployeemsg;
    public ListView<employemsgTM> lstmsg;
    public ImageView imgclose;
    public Button btnlistdelete;
    public Button btncancel;
    employemsgTM getitem;

    public void initialize(){
        getmessage();
        subroot.setVisible(false);
        lstmsg.setVisible(true);
        imgclose.setVisible(false);
        loadlist();
        btnlistdelete.setVisible(false);
        btncancel.setVisible(false);

        lstmsg.getSelectionModel().clearSelection();
        // Anonymouse inner class
        lstmsg.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<employemsgTM>() {
            @Override
            public void changed(ObservableValue<? extends employemsgTM> observable, employemsgTM oldValue, employemsgTM newValue) {

                 getitem = lstmsg.getSelectionModel().getSelectedItem();

                if(getitem==null){
                    return;
                }
                btnlistdelete.setVisible(true);
                btncancel.setVisible(true);



            }
        });

    }
    public void btnupdateOnAction(ActionEvent actionEvent) {
        btnupdate();
        txtusernameupdate.clear();
        txtpasswordupdate.clear();

    }

    public void btncancelOnAction(ActionEvent actionEvent) {

        closeimage(false);
        txtusernameupdate.clear();
        txtpasswordupdate.clear();
    }

    public void btnprofileOnMouseClicked(MouseEvent mouseEvent) {

        closeimage(true);
        txtusernameupdate.requestFocus();
    }



    public void BackOnMouselicked(MouseEvent mouseEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You Logout From Host", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get().equals(ButtonType.YES)){
            Parent parent = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("../veiw/loginform.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        }



    }


    public void closeimgeOnmouseclicked(MouseEvent mouseEvent) {

        closeimage(false);
    }


    public void btnItemimageMouseOnAction(MouseEvent mouseEvent) throws IOException {


        Parent parent =FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("../veiw/itemvform.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }
// Create method

    public void closeimage(boolean x){
        imgclose.setVisible(x);
        subroot.setVisible(x);
    }

    public void btnupdate(){
        Connection connection = DB.getInstance().getConnection();
        try {
            if(!txtpasswordupdate.getText().isEmpty() && !txtusernameupdate.getText().isEmpty()){
                PreparedStatement preparedStatement = connection.prepareStatement("update host set username =?,password=? where hid=2 ");
                preparedStatement.setObject(1,txtusernameupdate.getText());
                preparedStatement.setObject(2,txtpasswordupdate.getText());
                preparedStatement.executeUpdate();

            }
            else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You want Update User name And Password for Given Employee", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if(buttonType.equals(ButtonType.YES)){

                     txtpasswordupdate.clear();
                     txtusernameupdate.clear();
                     txtusernameupdate.requestFocus();

                }
                else{
                    closeimage(false);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeimage(false);

    }

    public void loadlist(){

        ObservableList<employemsgTM> items = lstmsg.getItems();
        items.clear();
        Connection connection = DB.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select *from note");
            while(resultSet.next()){

                String desc = resultSet.getString(3);
                String empid = resultSet.getString(2);
                int nid = resultSet.getInt(1);
                items.add(new employemsgTM(desc,empid,nid));

            }
            lstmsg.refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    public void btndeletelistOnAction(ActionEvent actionEvent) {
        btncancel.setVisible(false);
        Connection connection = DB.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from note where nid =?");
            preparedStatement.setObject(1,getitem.getNid());
            preparedStatement.executeUpdate();
            loadlist();
            btnlistdelete.setVisible(false);
            lstmsg.getSelectionModel().clearSelection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        lstmsg.refresh();

    }

    public void getmessage(){
        ObservableList<employemsgTM> items = lstmsg.getItems();
        if(!items.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.show();

        }

    }

    public void btncancelOnaction(ActionEvent actionEvent) {
        btncancel.setVisible(false);
        btnlistdelete.setVisible(false);
        lstmsg.getSelectionModel().clearSelection();
        lstmsg.refresh();
    }
}
