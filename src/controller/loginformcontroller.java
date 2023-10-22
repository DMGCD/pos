package controller;

import com.mysql.cj.PreparedQuery;
import db.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Optional;

public class loginformcontroller {
    public PasswordField txtpassword;

    public TextField txtusername;
 
    public AnchorPane root;
    public Button btnHost;
    public Button btnCreateAccount;
    public Button btnemp;

    public void initialize(){
        txtusername.requestFocus();
        btnHost.setDisable(false);
        btnCreateAccount.setVisible(false);
        btnemp.setDisable(true);

    }


   


    public void btnloginOnaction(ActionEvent actionEvent) throws IOException {
        if(!btnemp.isDisabled()){
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
            PreparedStatement preparedStatement = connection.prepareStatement("select *from host where username =? and password =?");
            preparedStatement.setObject(1,txtusername.getText());
            preparedStatement.setObject(2,txtpassword.getText());

            ResultSet resultSet = preparedStatement.executeQuery();
            boolean next = resultSet.next();

            if(next){
                Parent parent =FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("../veiw/useraccountform.fxml")));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) root.getScene().getWindow();
                stage.setScene(scene);
                stage.centerOnScreen();

            }
            else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Wrong User Name Or Password !",ButtonType.OK);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if(buttonType.get().equals(ButtonType.OK )){
                    txtusername.clear();
                    txtpassword.clear();
                    txtusername.requestFocus();
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void btnhostOnAction(ActionEvent actionEvent) {
        txtusername.requestFocus();
        btnHost.setDisable(true);
        btnCreateAccount.setVisible(true);
        btnemp.setDisable(false);
    }

    public void btnempOnAction(ActionEvent actionEvent) {
        txtusername.requestFocus();
        btnemp.setDisable(true);
        btnHost.setDisable(false);
        btnCreateAccount.setVisible(false);
    }


    public void txtpasswordOnAction(ActionEvent actionEvent) {

        if (!txtusername.getText().isEmpty()){
            txtpassword.requestFocus();
            if(!btnemp.isDisable()){
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
                PreparedStatement preparedStatement = connection.prepareStatement("select *from host where username =? and password=? and hid=?");
                preparedStatement.setObject(1,username);
                preparedStatement.setObject(2,password);
                preparedStatement.setObject(3,1);
                ResultSet resultSet = preparedStatement.executeQuery();
                boolean match = resultSet.next();
                if (match) {

                    Parent parent = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("../veiw/hostform.fxml")));
                    Scene scene = new Scene(parent);
                    Stage stage =(Stage) root.getScene().getWindow();
                    stage.setTitle("Host");
                    stage.setScene(scene);
                    stage.centerOnScreen();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Wrong User Name Or Password !");
                    alert.showAndWait();
                }


            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }


        }

    }
}
