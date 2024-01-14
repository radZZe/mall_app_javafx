package com.example.mall_app_javafx;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.mall_app_javafx.models.Department;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ChooseDepartmentController {
    private final ObservableList<Department> departments = FXCollections.observableArrayList();
    private final DatabaseHandler dbHandler = new DatabaseHandler();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Department> departmentTable;

    @FXML
    private TableColumn<Department, Integer> managerIDColumn;

    @FXML
    private TableColumn<Department, String> nameColumn;

    @FXML
    private TableColumn<Department, Integer> idColumn;

    @FXML
    private TextField idTextField;

    @FXML
    private Button nextBtn;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        ResultSet response  = dbHandler.getDepartmentCodes();
        ArrayList<Integer> codes = new ArrayList<Integer>();
        while (response.next()){
            codes.add(response.getInt(Const.DEPARTMENT_CODE));
        }
        setDepartments();
        DataStorage dataStorage = DataStorage.getInstance();
        nextBtn.setOnAction(actionEvent -> {
            if(idTextField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Не удалось выбрать отдел");
                alert.setContentText("Введите id отдела");
                alert.showAndWait();
            } else if (!codes.contains(Integer.parseInt(idTextField.getText()))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Не удалось выбрать отдел");
                alert.setContentText("Введите id отдела из представленного списка");
                alert.showAndWait();
            }else{
                int id = Integer.parseInt(idTextField.getText());
                dataStorage.setIdDepartment(id);
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(MallApplication.class.getResource("main_screen.fxml"));
                    fxmlLoader.load();
                    Parent root = fxmlLoader.getRoot();
                    Scene currentScene = nextBtn.getScene();
                    Scene newScene = new Scene(root);
                    Stage currentStage = (Stage) currentScene.getWindow();
                    Stage newStage = new Stage();
                    newStage.setScene(newScene);
                    currentStage.close();
                    newStage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });

    }

    public void  setDepartments() throws SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet response = dbHandler.getDepartments();
        while(response.next()){
            departments.add(new Department(
                    response.getInt(Const.DEPARTMENT_CODE),
                    response.getString(Const.NAME),
                    response.getInt(Const.DEPARTMENT_MANAGER_ID)
            ));
        }
        departmentTable.setItems(departments);
        idColumn.setCellValueFactory(new PropertyValueFactory<Department, Integer>(Const.DEPARTMENT_CODE));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Department, String>(Const.NAME));
        managerIDColumn.setCellValueFactory(new PropertyValueFactory<Department, Integer>(Const.DEPARTMENT_MANAGER_ID));

    }


}
