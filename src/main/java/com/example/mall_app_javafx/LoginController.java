package com.example.mall_app_javafx;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    void initialize() {
        loginBtn.setOnAction(actionEvent -> {
            String loginText = loginTextField.getText().trim();
            String passwordText = passwordTextField.getText().trim();
            if(!loginText.isEmpty() && !passwordText.isEmpty()){
                try {
                    loginUser(loginText,passwordText);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Не удалось авторизоваться");
                alert.setContentText("Неправильный логин или пароль");
                alert.showAndWait();
            }
        });

    }

    private void loginUser(String login , String password) throws SQLException, IOException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet response = dbHandler.authUser(login,password);
        int counter =0;
        while (response.next()){
            counter++;
        }
        if(counter>=1){
            if(Objects.equals(login, "admin") && Objects.equals(password, "admin")){
                screenLoader("admin_main_screen.fxml",loginBtn.getScene());
                DataStorage dataStorage = DataStorage.getInstance();
                dataStorage.setIsAdmin(true);
            }else{
                screenLoader("choose_department.fxml",loginBtn.getScene());
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Не удалось авторизоваться");
            alert.setContentText("Неправильный логин или пароль");
            alert.showAndWait();
        }
    }

    private void screenLoader(String res,Scene currentScene) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MallApplication.class.getResource(res));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Scene newScene = new Scene(root);
        Stage currentStage = (Stage) currentScene.getWindow();
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        currentStage.close();
        newStage.show();
    }

}
