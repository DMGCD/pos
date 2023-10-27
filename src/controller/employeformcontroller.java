package controller;

import db.DB;
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

public class employeformcontroller {
    public AnchorPane root;
 
    public Pane subrootnote;
    public Pane subrootchange;
    public PasswordField txtnewpassword;
    public PasswordField txtnewconfirmpassword;
    public TextField txtnewusername;
    public String Empid;
    public TextField txtnote;
    public Label lblusernamehostpermission;
    public Label lblpasswordhostpermission;
    public ImageView imgclose;

    public void initialize(){
        subrootchange.setDisable(true);
        subrootnote.setDisable(true);
        imgclose.setVisible(false);
        this.Empid =loginformcontroller.Empid;

        showhostpermission();



}
    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "confirm Logout from Employee ", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get().equals(ButtonType.YES)){

            Parent parent = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("../veiw/loginform.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setTitle("Login Form");
            stage.setScene(scene);
            stage.centerOnScreen();


        }
    }


    public void btnWorkOnMouseClicked(MouseEvent mouseEvent) throws IOException {

        Parent parent = FXMLLoader.load(this.getClass().getResource("../veiw/workform.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();


    }

    public void imgcloseOnMouseClicked(MouseEvent mouseEvent) {

        subrootnote.setDisable(true);
        imgclose.setVisible(false);
        txtnote.clear();

    }

    public void btnchangeOnAction(ActionEvent actionEvent) {
        subrootchange.setDisable(false);
        txtnewusername.requestFocus();


    }

    public void btnAddnoteOnMouseCleck(MouseEvent mouseEvent) {
        subrootnote.setDisable(false);
        imgclose.setVisible(true);
        txtnote.requestFocus();


    }

    public void txtaddOnAction(ActionEvent actionEvent) {
        addnote();
        txtnote.clear();
        subrootnote.setDisable(true);
        imgclose.setVisible(false);
    }

    public void btnaddOnAction(ActionEvent actionEvent) {
        addnote();
        txtnote.clear();
        subrootnote.setDisable(true);

    }

    public void btnsaveOnAction(ActionEvent actionEvent) {
        if(!txtnewpassword.getText().isEmpty() && !txtnewconfirmpassword.getText().isEmpty() && !txtnewusername.getText().isEmpty()){
            savenewemp();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You Want Change the User name and Password !", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if(buttonType.get().equals(ButtonType.YES)){
                txtnewusername.clear();
                txtnewconfirmpassword.clear();
                txtnewpassword.clear();
                txtnewusername.requestFocus();
            }
            else{
                txtnewusername.clear();
                txtnewconfirmpassword.clear();
                txtnewpassword.clear();
                subrootchange.setDisable(true);
            }
        }

    }
//txt field onaction method

    public void txtnewpasswordOnAction(ActionEvent actionEvent) {
        if(!txtnewpassword.getText().isEmpty()){
            txtnewconfirmpassword.requestFocus();
        }

    }

    public void txtconfirmOnAction(ActionEvent actionEvent) {

        if(!txtnewpassword.getText().isEmpty() && !txtnewconfirmpassword.getText().isEmpty() && !txtnewusername.getText().isEmpty()){
            savenewemp();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You Want Change the User name and Password !", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if(buttonType.equals(ButtonType.YES)){
                txtnewusername.clear();
                txtnewconfirmpassword.clear();
                txtnewpassword.clear();
                txtnewusername.requestFocus();
            }
            else{
                txtnewusername.clear();
                txtnewconfirmpassword.clear();
                txtnewpassword.clear();
                subrootchange.setDisable(true);
            }
        }
    }

    public void txtnewusernamOnActon(ActionEvent actionEvent) {
        if(!txtnewusername.getText().isEmpty()){
            txtnewpassword.requestFocus();
        }


    }
    //save new password ,username
    public void savenewemp(){

        if(txtnewpassword.getText().equals(txtnewconfirmpassword.getText())){
            colorborder("transparent");

            Connection connection = DB.getInstance().getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("update emp set username=?,password=? where empid=? ");
                preparedStatement.setObject(1,txtnewusername.getText());
                preparedStatement.setObject(2,txtnewpassword.getText());
                preparedStatement.setObject(3,Empid);
                int i = preparedStatement.executeUpdate();
                if(i>0){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Update Username or password!");
                    alert.showAndWait();
                    txtnewusername.clear();
                    txtnewpassword.clear();
                    txtnewconfirmpassword.clear();
                    subrootchange.setDisable(true);

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            colorborder("red");
            txtnewpassword.clear();
            txtnewconfirmpassword.clear();
            txtnewpassword.requestFocus();
        }

    }

    public void colorborder(String n){
        txtnewpassword.setStyle("-fx-border-color:"+n);
        txtnewconfirmpassword.setStyle("-fx-border-color:"+n);
    }

    public void addnote(){

        String note = txtnote.getText();
        Connection connection = DB.getInstance().getConnection();
        try {
            if(!note.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "If You Want Add The Note For Host",ButtonType.YES,ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if(buttonType.get().equals(ButtonType.YES)){
                    PreparedStatement preparedStatement = connection.prepareStatement("insert into note (empid,note) values(?,?) ");
                    preparedStatement.setObject(1,Empid);
                    preparedStatement.setObject(2,txtnote.getText());
                    int i = preparedStatement.executeUpdate();
                    txtnote.clear();
                    subrootnote.setDisable(true);
                    imgclose.setVisible(false);
                }


            }
            else{

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public void showhostpermission(){
        Connection connection = DB.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select *from host where hid=2");
            boolean next = resultSet.next();
            if(next){
                lblusernamehostpermission.setText(resultSet.getString(2));
                lblpasswordhostpermission.setText(resultSet.getString(3));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
