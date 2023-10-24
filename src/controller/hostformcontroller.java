package controller;

import TM.employemsgTM;
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
import java.util.Optional;

public class hostformcontroller {


    public AnchorPane root;
    public Pane subroot;
    public TextField txtusernameupdate;
    public TextField txtpasswordupdate;
    public Label lblemployeemsg;
    public ListView<employemsgTM> lstmsg;
    public ImageView imgclose;

    public void initialize(){
        subroot.setVisible(false);
        lstmsg.setVisible(true);
        imgclose.setVisible(false);

    }
    public void btnupdateOnAction(ActionEvent actionEvent) {
    }

    public void btncancelOnAction(ActionEvent actionEvent) {
    }

    public void btnprofileOnMouseClicked(MouseEvent mouseEvent) {
        closeimage(true);
    }



    public void BackOnMouselicked(MouseEvent mouseEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You Logout From Host", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get().equals(ButtonType.YES)){
            Parent parent = FXMLLoader.load(this.getClass().getResource("../veiw/loginform.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        }



    }


    public void closeimgeOnmouseclicked(MouseEvent mouseEvent) {
        closeimage(false);
    }
    public void closeimage(boolean x){
        imgclose.setVisible(x);
        subroot.setVisible(x);
    }

    public void btnItemimageMouseOnAction(MouseEvent mouseEvent) throws IOException {


        Parent parent =FXMLLoader.load(this.getClass().getResource("../veiw/itemvform.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }
}
