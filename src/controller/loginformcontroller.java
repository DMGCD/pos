package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class loginformcontroller {
    public PasswordField txtpassword;
    public TextField txthostusername;
    public TextField txtusername;
    public Label lblhostusername;
    public AnchorPane root;
    public Button btnHost;
    public Button btnEmp;
    public Label lblwarning;

    public void initialize(){
        txtusername.clear();
        txtusername.requestFocus();
        btnhost(false);
        btnEmp.setVisible(false);
        lblwarning.setVisible(false);
    }


    public void btnhostOnAction(ActionEvent actionEvent) {

        btnhost(true);
        btnHost.setVisible(false);
        btnEmp.setVisible(true);
        txtusername.clear();
        txtusername.requestFocus();

    }
    public void btnempOnaction(ActionEvent actionEvent) {
        btnhost(false);
        btnEmp.setVisible(false);
        btnHost.setVisible(true);
        txthostusername.clear();
        txtusername.requestFocus();
    }

    public void btnloginOnaction(ActionEvent actionEvent) throws IOException {
        if(!btnEmp.isVisible()){
             Parent parent =FXMLLoader.load(this.getClass().getResource("../veiw/employeform.fxml"));
            Scene scene = new Scene(parent);
           Stage stage =(Stage) root.getScene().getWindow();
           stage.setTitle("employe Form");
           stage.setScene(scene);
           stage.centerOnScreen();


        }
        else if(btnEmp.isVisible() && !txthostusername.getText().isEmpty()){

            Parent parent =FXMLLoader.load(this.getClass().getResource("../veiw/hostform.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Host form");
            stage.centerOnScreen();
            lblwarning.setVisible(false);

        }
        else{
            lblwarning.setVisible(true);
        }


    }

// btn host with show and hide host username
    public void btnhost(boolean x){
        lblhostusername.setVisible(x);
        txthostusername.setVisible(x);
    }


    public void btnCreateOnAction(ActionEvent actionEvent) {




    }
}
