package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class useraccountformcontroller {
    public TextField txtname;
    public TextField txtusername;
    public TextField txtphone;
    public PasswordField txtpassword;
    public PasswordField txtconfirmpassword;
    public AnchorPane root;




    public void btnRegisterOnAction(ActionEvent actionEvent) {






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
    }
}
