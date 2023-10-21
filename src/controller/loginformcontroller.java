package controller;

import com.mysql.cj.PreparedQuery;
import db.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginformcontroller {
    public PasswordField txtpassword;

    public TextField txtusername;
 
    public AnchorPane root;
    public Button btnHost;
    public Button btnCreateAccount;
    public Button btnemp;

    public void initialize(){

        firstcommnvisible(false,true);
    }


   


    public void btnloginOnaction(ActionEvent actionEvent) throws IOException {
        if(btnemp.isVisible()){
            login();
        }
        else{
            txtusername.clear();
            txtpassword.clear();
            txtusername.requestFocus();
        }
    }




    public void btnCreateOnAction(ActionEvent actionEvent) throws IOException {

        Connection connection = DB.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select *from host where password =?");
            preparedStatement.setObject(1,txtpassword.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean next = resultSet.next();
            if(next){
                Parent parent = FXMLLoader.load(this.getClass().getResource("../veiw/useraccountform.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) root.getScene().getWindow();
                stage.setScene(scene);
                stage.centerOnScreen();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You Are Have Not Host Permission");
                alert.showAndWait();
                txtpassword.clear();
                txtpassword.requestFocus();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    public void btnhostOnAction(ActionEvent actionEvent) {

        firstcommnvisible(true,false);
    }

    public void btnempOnAction(ActionEvent actionEvent) {

        firstcommnvisible(false,true);
    }

    public void firstcommnvisible(boolean x,boolean y){
        txtusername.requestFocus();
        btnemp.setVisible(x);
        btnCreateAccount.setVisible(x);
        btnHost.setVisible(y);
    }

    public void txtpasswordOnAction(ActionEvent actionEvent) {

        if (!txtusername.getText().isEmpty()){
            txtpassword.requestFocus();
            if(btnemp.isVisible()){
                login();
            }
            else{
                txtusername.clear();
                txtpassword.clear();
                txtusername.requestFocus();
            }
        }

    }

    public void txtusernameOnAction(ActionEvent actionEvent) {
        if(!txtusername.getText().isEmpty()){
            txtpassword.requestFocus();
        }
    }







    public void login(){
        if(!txtpassword.getText().isEmpty()){
            String username = txtusername.getText();
            String password = txtpassword.getText();
            Connection connection = DB.getInstance().getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("select *from host where username =? and password=?");
                preparedStatement.setObject(1,username);
                preparedStatement.setObject(2,password);
                ResultSet resultSet = preparedStatement.executeQuery();
                boolean match = resultSet.next();
                if (match) {

                    Parent parent = FXMLLoader.load(this.getClass().getResource("../veiw/hostform.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage =(Stage) root.getScene().getWindow();
                    stage.setTitle("Host");
                    stage.setScene(scene);
                    stage.centerOnScreen();
                }


            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }


        }

    }
}
