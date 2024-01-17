module com.example.mall_app_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
//    requires mysql.connector.j;


    opens com.example.mall_app_javafx to javafx.fxml;
    opens com.example.mall_app_javafx.models to javafx.base;
    exports com.example.mall_app_javafx;
}