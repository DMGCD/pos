package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class employeformcontroller {
    public AnchorPane root;
 
    public Pane subrootnote;
    public Pane subrootchange;

    public void initialize(){
        subrootchange.setDisable(true);
        subrootnote.setDisable(true);

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


    public void btnWorkOnMouseClicked(MouseEvent mouseEvent) {

    }

    public void btnchangeOnAction(ActionEvent actionEvent) {
    }

    public void btnAddnoteOnMouseCleck(MouseEvent mouseEvent) {

    }

    public void txtaddOnAction(ActionEvent actionEvent) {
    }

    public void btnaddOnAction(ActionEvent actionEvent) {
    }

    public void btnsaveOnAction(ActionEvent actionEvent) {
    }
}
