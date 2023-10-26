package controller;

import db.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Optional;

public class useraccountformcontroller {
    public TextField txtname;
    public TextField txtusername;
    public TextField txtphone;
    public PasswordField txtpassword;
    public PasswordField txtconfirmpassword;
    public AnchorPane root;

    public void initialize(){
        colurborder("transparent");
    }




    public void btnRegisterOnAction(ActionEvent actionEvent) {
        storedata();

    }


    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("../veiw/loginform.fxml")));
        Scene scene = new Scene(parent);
        Stage stage =(Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }


    public void txtnameOnAction(ActionEvent actionEvent) {
        if(!txtname.getText().isEmpty()){
            txtusername.requestFocus();
        }

    }

    public void txtusernameOnAction(ActionEvent actionEvent) {
        if(!txtusername.getText().isEmpty()){
            txtphone.requestFocus();
        }

    }

    public void txtphoneOnaction(ActionEvent actionEvent) {
        if(!txtphone.getText().isEmpty()) {
            txtpassword.requestFocus();
        }
    }

    public void txtpassowrdOnaction(ActionEvent actionEvent) {
        if(!txtpassword.getText().isEmpty()){
            txtconfirmpassword.requestFocus();
        }

    }

    public void txtconfirmpasswordOnAction(ActionEvent actionEvent) {
        storedata();

    }

    public void storedata(){

        String password = txtpassword.getText();
        String confirmp = txtconfirmpassword.getText();
        String empid = empid();
        if(password.equals(confirmp)){
            colurborder("transparent");
            Connection connection = DB.getInstance().getConnection();
            try {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Registered As Employee !", ButtonType.OK);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if(buttonType.get().equals(ButtonType.OK)){
                    PreparedStatement preparedStatement = connection.prepareStatement("insert into emp(empid,name,phone,username,password) values(?,?,?,?,?)");
                    preparedStatement.setObject(1,empid);
                    preparedStatement.setObject(2,txtname.getText());
                    preparedStatement.setObject(3,txtphone.getText());
                    preparedStatement.setObject(4,txtusername.getText());
                    preparedStatement.setObject(5,txtpassword.getText());
                    preparedStatement.executeUpdate();
                    Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "If You Want To Add Another Employe !", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> buttonType1 = alert1.showAndWait();
                    if(buttonType1.equals(ButtonType.NO)){
                        Parent parent =FXMLLoader.load(this.getClass().getResource("../veiw/loginform.fxml"));
                        Scene scene = new Scene(parent);
                        Stage stage = (Stage) root.getScene().getWindow();
                        stage.setScene(scene);
                        stage.centerOnScreen();
                    }
                    else{

                        clear();
                    }
                }
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{

            txtpassword.clear();
            txtpassword.requestFocus();
            txtconfirmpassword.clear();
            colurborder("red");

        }


    }

    public String empid(){
        String eid;

        Connection connection = DB.getInstance().getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select empid from emp order by empid desc limit 1 ");
            boolean next = resultSet.next();
            if(next){
                eid = resultSet.getString(1);
                eid =eid.substring(1,eid.length());
                int empidI =Integer.parseInt(eid);
                empidI++;
                if(empidI<10){
                    eid ="E00"+empidI;
                }
                else if(empidI<100){
                    eid ="E0"+empidI;
                }
                else if(empidI<1000){
                    eid="E"+empidI;
                }
                return eid;
            }
            else {
                eid ="E001";
                return eid;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void clear(){
        txtname.clear();
        txtpassword.clear();
        txtconfirmpassword.clear();
        txtphone.clear();
        txtusername.clear();
        txtname.requestFocus();
    }

    public void colurborder(String n){
        txtpassword.setStyle("-fx-border-color:"+n);
        txtconfirmpassword.setStyle("-fx-border-color:"+n);
    }
}
