package controller;

import TM.lstEmployeTM;
import db.DB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class dataEmployeFormController {
    public AnchorPane root;
    public ListView<lstEmployeTM> lstEmploye;
    public Button btnRemove;
    lstEmployeTM selectedItem;
public void initialize(){
    loadlList();
lstEmploye.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<lstEmployeTM>() {
    @Override
    public void changed(ObservableValue<? extends lstEmployeTM> observable, lstEmployeTM oldValue, lstEmployeTM newValue) {
         selectedItem= lstEmploye.getSelectionModel().getSelectedItem();
        if(selectedItem==null){
            return;
        }
    }
});
}
    public void btnRemoveOnAction(ActionEvent actionEvent) {
        String empid = selectedItem.getEmpid();
        Connection connection = DB.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from emp where empid=? ");
            preparedStatement.setObject(1,empid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        lstEmploye.getSelectionModel().clearSelection();
        lstEmploye.getItems().clear();
        loadlList();
        lstEmploye.refresh();
    }
public void loadlList()
{
    ObservableList<lstEmployeTM> items = lstEmploye.getItems();
    items.clear();
    Connection connection = DB.getInstance().getConnection();
    try {
        PreparedStatement preparedStatement = connection.prepareStatement("select *from emp");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            String empid = resultSet.getString(1);
            String nameEmp = resultSet.getString(2);
            String phoneEmp = resultSet.getString(3);
            items.add(new lstEmployeTM(empid,nameEmp,phoneEmp));

        }

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    lstEmploye.refresh();


}
    public void lblBackOnMouseClicked(MouseEvent mouseEvent) throws IOException {

        Parent parent = FXMLLoader.load(this.getClass().getResource("../veiw/hostform.fxml"));
        Scene scene = new Scene(parent);
        Stage stage =(Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();

    }
}
