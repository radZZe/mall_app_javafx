package com.example.mall_app_javafx;

import com.example.mall_app_javafx.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminMainScreenController {

    private final ObservableList<Sale> sales = FXCollections.observableArrayList();
    private final ObservableList<Product> products = FXCollections.observableArrayList();
    private final ObservableList<Unit> units = FXCollections.observableArrayList();

    private final ObservableList<Manager> managers = FXCollections.observableArrayList();

    private final ObservableList<Department> departments = FXCollections.observableArrayList();

    private final ObservableList<User> users = FXCollections.observableArrayList();

    private final ObservableList<ReportEntry> reportEntries = FXCollections.observableArrayList();
    private final DatabaseHandler dbHandler = new DatabaseHandler();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField amountTF;

    @FXML
    private TextField articleTF;

    @FXML
    private TextField dateTF;

    @FXML
    private TextField departmentCodeTF;

    @FXML
    private Button productAddBtn;

    @FXML
    private TableColumn<Product, Integer> productArticleColumn;

    @FXML
    private TextField productArticleTF;

    @FXML
    private Button productDeleteBtn;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TextField productNameTF;

    @FXML
    private TableColumn<Product, Integer> productPriceColumn;

    @FXML
    private TextField productPriceTF;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> productUnitIDColumn;

    @FXML
    private TextField productUnitIDTF;

    @FXML
    private Button productUpdateBtn;

    @FXML
    private Button saleAddBtn;

    @FXML
    private TableColumn<Sale, Integer> saleAmountColumn;

    @FXML
    private TableColumn<Sale, Integer> saleArticleColumn;

    @FXML
    private TableColumn<Sale, Date> saleDateColumn;

    @FXML
    private Button saleDeleteBtn;

    @FXML
    private TableColumn<Sale, Integer> saleDepartmentCodeColumn;

    @FXML
    private TextField saleIDTF;

    @FXML
    private TableColumn<Sale, Integer> saleSaleIDColumn;

    @FXML
    private TableColumn<Sale, Integer> saleSumColumn;

    @FXML
    private TableView<Sale> saleTable;

    @FXML
    private Button saleUpdateBtn;

    @FXML
    private Button unitAddBtn;

    @FXML
    private Button unitDeleteBtn;

    @FXML
    private TableView<Unit> unitTable;

    @FXML
    private TableColumn<Unit, Integer> unitUnitIDColumn;

    @FXML
    private TextField unitUnitIDTF;

    @FXML
    private Button unitUpdateBtn;

    @FXML
    private TableColumn<Unit, String> unitValueColumn;

    @FXML
    private TextField unitValueTF;

    @FXML
    private Button managerAddBtn;

    @FXML
    private Button managerDeleteBtn;

    @FXML
    private TableColumn<Manager, String> managerManagerColumn;

    @FXML
    private TextField managerManagerIDTF;

    @FXML
    private TableColumn<Manager, Integer> managerManagerIdColumn;

    @FXML
    private TextField managerManagerTF;

    @FXML
    private TableColumn<Manager, String> managerPhoneColumn;

    @FXML
    private TextField managerPhoneTF;

    @FXML
    private TableView<Manager> managerTable;

    @FXML
    private Button managerUpdateBtn;

    @FXML
    private Button addDepartmentBtn;
    @FXML
    private TextField departmentDepartmentCodeTF;
    @FXML
    private Button deleteDepartmentBtn;
    @FXML
    private TableColumn<Department, Integer> departmentDepartmentCodeColumn;

    @FXML
    private TableColumn<Department, Integer> departmentManagerIDColumn;

    @FXML
    private TextField departmentManagerIDTF;

    @FXML
    private TextField departmentNameTF;

    @FXML
    private TableView<Department> departmentTable;

    @FXML
    private TableColumn<Department, String> departmentNameColumn;
    @FXML
    private Button updateDepartmentBtn;

    @FXML
    private Button userAddBtn;

    @FXML
    private Button userDeleteBtn;

    @FXML
    private TableColumn<User, String> userLoginColumn;

    @FXML
    private TextField userLoginTF;

    @FXML
    private TableColumn<User, String> userPasswordColumn;

    @FXML
    private TextField userPasswordTF;

    @FXML
    private TableView<User> userTable;

    @FXML
    private Button userUpdateBtn;

    @FXML
    private Tab reportTab;

    @FXML
    private TableView<ReportEntry> reportTable;

    @FXML
    private TableColumn<ReportEntry, String> reportUnitColumn;

    @FXML
    private TableColumn<ReportEntry, Integer> reportAmountColumn;

    @FXML
    private TableColumn<ReportEntry, String> reportNameColumn;

    @FXML
    private TableColumn<ReportEntry, Double> reportPriceColumn;

    @FXML
    private TableColumn<ReportEntry, Double> reportSumColumn;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        DataStorage dataStorage = DataStorage.getInstance();
        ArrayList<Integer> saleList = new ArrayList<Integer>();
        ArrayList<Integer> departmentCodeList = new ArrayList<Integer>();
        ArrayList<Integer> articleList = new ArrayList<Integer>();
        ArrayList<Integer> unitList = new ArrayList<Integer>();
        ArrayList<Integer> managerList = new ArrayList<Integer>();
        ArrayList<String> usersList = new ArrayList<String>();
//        ResultSet saleListReponse = dbHandler.getSales();
//        ResultSet departmentCodeListReponse = dbHandler.getDepartmentCodes();
//        ResultSet articleListResponse = dbHandler.getArticles();
//        ResultSet unitListResponse = dbHandler.getUnits();
//        ResultSet managerListResponse = dbHandler.getManagers();
//        ResultSet usersListResponse = dbHandler.getUsers();
        dbHandler.getSalesAsync().thenAccept(resultSet -> {
            while(true){
                try {
                    if (!resultSet.next()) break;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    saleList.add(resultSet.getInt(Const.SALE_SALE_ID));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        dbHandler.getArticlesAsync().thenAccept( resultSet -> {
            while(true){
                try {
                    if (!resultSet.next()) break;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    articleList.add(resultSet.getInt(Const.ARTICLE));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        dbHandler.getDepartmentCodesAsync().thenAccept(resultSet -> {
            while(true){
                try {
                    if (!resultSet.next()) break;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    departmentCodeList.add(resultSet.getInt(Const.DEPARTMENT_CODE));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        dbHandler.getUnitsAsync().thenAccept(resultSet -> {
            while(true){
                try {
                    if (!resultSet.next()) break;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    unitList.add(resultSet.getInt(Const.UNIT_ID));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        dbHandler.getManagersAsync().thenAccept( resultSet -> {
            while(true){
                try {
                    if (!resultSet.next()) break;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    managerList.add(resultSet.getInt(Const.MANAGER_ID));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        dbHandler.getUsersAsync().thenAccept(resultSet -> {
            while(true){
                try {
                    if (!resultSet.next()) break;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    usersList.add(resultSet.getString(Const.USER_LOGIN));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        setSales();
        setProducts();
        setUnits();
        setManagers();
        setDepartments();
        setUsers();
        saleUpdateBtn.setOnAction(actionEvent -> {
            Integer id = null;
            if(saleIDTF.getText().isEmpty() ){
                showAlert("Error","Не удалось изменить данные","Введите saleId");
                return;
            }else if (!saleIDTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось изменить данные","saleId это число");
                return;
            }else if(!saleList.contains(Integer.parseInt(saleIDTF.getText()))){
                showAlert("Error","Не удалось изменить данные","такого saleId не существует");
                return;
            }else{
                id = Integer.parseInt(saleIDTF.getText());
            }
            Integer departmentCode = null;
            if(departmentCodeTF.getText().isEmpty() ){
                departmentCode = null;
            }else if (!departmentCodeTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось изменить данные","departmentCode это число");
                return;
            }else if(!departmentCodeList.contains(Integer.parseInt(departmentCodeTF.getText()))){
                showAlert("Error","Не удалось изменить данные","такого departmentCode не существует");
                return;
            }else{
                departmentCode = Integer.parseInt(departmentCodeTF.getText());
            }
            Integer amount = null;
            if(amountTF.getText().isEmpty() ){
                amount = null;
            }else if (!amountTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось изменить данные","amount это число");
                return;
            }else{
                amount = Integer.parseInt(amountTF.getText());
            }
            Integer article = null;
            if(articleTF.getText().isEmpty() ){
                article = null;
            }else if (!articleTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось изменить данные","article это число");
                return;
            }else if(!articleList.contains(Integer.parseInt(articleTF.getText()))){
                showAlert("Error","Не удалось изменить данные","такого article не существует");
                return;
            }else{
                article = Integer.parseInt(articleTF.getText());
            }
            LocalDateTime date = null;
            if(dateTF.getText().isEmpty() ){
                date = null;
            }else if (!isDateInFormat(dateTF.getText())){
                showAlert("Error","Не удалось изменить данные","date должно быть в формате YYYY-MM-DD");
                return;
            }else{
                date = LocalDate.parse(dateTF.getText()).atStartOfDay();
            }
            Sale sale;
            if (date == null) {
                sale = new Sale(
                        id, article, null, amount, departmentCode, null
                );
            } else {
                sale = new Sale(
                        id, article, Date.from(date.atZone(ZoneId.systemDefault()).toInstant()), amount, departmentCode, null
                );
            }
            try {
//                dbHandler.updateSale(sale);
                dbHandler.updateSaleAsync(sale);
                updateSaleTable(dataStorage);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        saleAddBtn.setOnAction(actionEvent -> {
            Integer departmentCode = null;
            if(departmentCodeTF.getText().isEmpty() ){
                showAlert("Error","Не удалось добавить данные","введите departmentCode");
                return;
            }else if (!departmentCodeTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось добавить данные","departmentCode это число");
                return;
            }else if(!departmentCodeList.contains(Integer.parseInt(departmentCodeTF.getText()))){
                showAlert("Error","Не удалось добавить данные","такого departmentCode не существует");
                return;
            }else{
                departmentCode = Integer.parseInt(departmentCodeTF.getText());
            }
            Integer amount = null;
            if(amountTF.getText().isEmpty() ){
                showAlert("Error","Не удалось добавить данные","введите amount");
                return;
            }else if (!amountTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось добавить данные","amount это число");
                return;
            }else{
                amount = Integer.parseInt(amountTF.getText());
            }
            Integer article = null;
            if(articleTF.getText().isEmpty() ){
                showAlert("Error","Не удалось добавить данные","введите article");
                return;
            }else if (!articleTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось добавить данные","article это число");
                return;
            }else if(!articleList.contains(Integer.parseInt(articleTF.getText()))){
                showAlert("Error","Не удалось добавить данные","такого article не существует");
                return;
            }else{
                article = Integer.parseInt(articleTF.getText());
            }
            LocalDateTime date = null;
            if(dateTF.getText().isEmpty() ){
                showAlert("Error","Не удалось добавить данные","введите date");
                return;
            }else if (!isDateInFormat(dateTF.getText())){
                showAlert("Error","Не удалось добавить данные","date должно быть в формате YYYY-MM-DD");
                return;
            }else{
                date = LocalDate.parse(dateTF.getText()).atStartOfDay();
            }
            Sale sale = new Sale(
                    null, article, Date.from(date.atZone(ZoneId.systemDefault()).toInstant()), amount, departmentCode, null
            );
            try {
                dbHandler.addSale(sale);
                updateSaleTable(dataStorage);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        saleDeleteBtn.setOnAction(actionEvent -> {
            Integer id = null;
            if(saleIDTF.getText().isEmpty() ){
                showAlert("Error","Не удалось удалить данные","Введите saleId");
                return;
            }else if (!saleIDTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось удалить данные","saleId это число");
                return;
            }else if(!saleList.contains(Integer.parseInt(saleIDTF.getText()))){
                showAlert("Error","Не удалось удалить данные","такого saleId не существует");
                return;
            }else{
                id = Integer.parseInt(saleIDTF.getText());
            }
            try {
//                dbHandler.deleteSale(id);
                dbHandler.deleteSaleAsync(id);
                updateSaleTable(dataStorage);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        productUpdateBtn.setOnAction(actionEvent -> {
            Integer article = null;
            if(productArticleTF.getText().isEmpty() ){
                showAlert("Error","Не удалось изменить данные","введите article");
                return;
            }else if (!productArticleTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось изменить данные","article это число");
                return;
            }else if(!articleList.contains(Integer.parseInt(productArticleTF.getText()))){
                showAlert("Error","Не удалось изменить данные","такого article не существует");
                return;
            }else{
                article = Integer.parseInt(productArticleTF.getText());
            }
            String name = !productNameTF.getText().isEmpty() ? productNameTF.getText() : null ;
            Integer unitID = null;
            if(productUnitIDTF.getText().isEmpty() ){
                unitID = null;
            }else if (!productUnitIDTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось изменить данные","unitID это число");
                return;
            }else if(!unitList.contains(Integer.parseInt(productUnitIDTF.getText()))){
                showAlert("Error","Не удалось изменить данные","такого unitID не существует");
                return;
            }else{
                unitID = Integer.parseInt(productUnitIDTF.getText());
            }
            Integer price = null ;
            if(productPriceTF.getText().isEmpty() ){
                price = null;
            }else if (!productPriceTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось изменить данные","price это число");
                return;
            }else{
                price = Integer.parseInt(productPriceTF.getText());
            }
            Product product = new Product(
                    article, name, unitID, price
            );
            try {
                dbHandler.updateProduct(product);
                updateProductTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        productAddBtn.setOnAction(actionEvent -> {
            String name = null;
            if(productNameTF.getText().isEmpty() ){
                showAlert("Error","Не удалось добавить данные","введите name");
                return;
            }else{
                name = productNameTF.getText();
            }
            Integer unitID = null;
            if(productUnitIDTF.getText().isEmpty() ){
                showAlert("Error","Не удалось добавить данные","введите unitID");
                return;
            }else if (!productUnitIDTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось добавить данные","unitID это число");
                return;
            }else if(!unitList.contains(Integer.parseInt(productUnitIDTF.getText()))){
                showAlert("Error","Не удалось добавить данные","такого unitID не существует");
                return;
            }else{
                unitID = Integer.parseInt(productUnitIDTF.getText());
            }
            Integer price = null ;
            if(productPriceTF.getText().isEmpty() ){
                showAlert("Error","Не удалось добавить данные","введите price");
                return;
            }else if (!productPriceTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось изменить данные","price это число");
                return;
            }else{
                price = Integer.parseInt(productPriceTF.getText());
            }
            Product product = new Product(
                    null, name, unitID, price
            );
            try {
                dbHandler.addProduct(product);
                updateProductTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        productDeleteBtn.setOnAction(actionEvent -> {
            Integer article = null;
            if(productArticleTF.getText().isEmpty() ){
                showAlert("Error","Не удалось удалить данные","введите article");
                return;
            }else if (!productArticleTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось удалить данные","article это число");
                return;
            }else if(!articleList.contains(Integer.parseInt(productArticleTF.getText()))){
                showAlert("Error","Не удалось удалить данные","такого article не существует");
                return;
            }else{
                article = Integer.parseInt(productArticleTF.getText());
            }
            try {
                dbHandler.deleteProduct(article);
                updateProductTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        unitUpdateBtn.setOnAction(actionEvent -> {
            Integer unitID = null;
            if(productUnitIDTF.getText().isEmpty() ){
                showAlert("Error","Не удалось изменить данные","введите unitID");
                return;
            }else if (!productUnitIDTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось изменить данные","unitID это число");
                return;
            }else if(!unitList.contains(Integer.parseInt(productUnitIDTF.getText()))){
                showAlert("Error","Не удалось изменить данные","такого unitID не существует");
                return;
            }else{
                unitID = Integer.parseInt(productUnitIDTF.getText());
            }
            String value = null;
            if(unitValueTF.getText().isEmpty() ){
                showAlert("Error","Не удалось изменить данные","введите value");
                return;
            }else if (!isRussianTextWithDots(unitValueTF.getText())){
                showAlert("Error","Не удалось изменить данные"," некорректный value");
                return;
            }else{
                value = unitValueTF.getText();
            }
            Unit unit = new Unit(
                    unitID, value
            );
            try {
                dbHandler.updateUnit(unit);
                updateUnitTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        unitAddBtn.setOnAction(actionEvent -> {
            String value = null;
            if(unitValueTF.getText().isEmpty() ){
                showAlert("Error","Не удалось добавить данные","введите value");
                return;
            }else if (!isRussianTextWithDots(unitValueTF.getText())){
                showAlert("Error","Не удалось добавить данные"," некорректный value");
                return;
            }else{
                value = unitValueTF.getText();
            }
            Unit unit = new Unit(
                    null, value
            );
            try {
                dbHandler.addUnit(unit);
                updateUnitTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        unitDeleteBtn.setOnAction(actionEvent -> {
            Integer unitID = null;
            if(productUnitIDTF.getText().isEmpty() ){
                showAlert("Error","Не удалось удалить данные","введите unitID");
                return;
            }else if (!productUnitIDTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось удалить данные","unitID это число");
                return;
            }else if(!unitList.contains(Integer.parseInt(productUnitIDTF.getText()))){
                showAlert("Error","Не удалось удалить данные","такого unitID не существует");
                return;
            }else{
                unitID = Integer.parseInt(productUnitIDTF.getText());
            }
            try {
                dbHandler.deleteUnit(unitID);
                updateUnitTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        managerUpdateBtn.setOnAction(actionEvent -> {
            Integer managerID = null;
            if(managerManagerIDTF.getText().isEmpty() ){
                showAlert("Error","Не удалось изменить данные","введите managerID");
                return;
            }else if (!managerManagerIDTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось изменить данные","managerID это число");
                return;
            }else if(!managerList.contains(Integer.parseInt(managerManagerIDTF.getText()))){
                showAlert("Error","Не удалось изменить данные","такого managerID не существует");
                return;
            }else{
                managerID = Integer.parseInt(managerManagerIDTF.getText());
            }
            String manager = null;
            if(managerManagerTF.getText().isEmpty() ){
                manager = null;
            }else if (!isRussianTextWithDots(managerManagerTF.getText())){
                showAlert("Error","Не удалось изменить данные","некорректный manager");
                return;
            }else{
                manager = managerManagerTF.getText();
            }
            String phone = null;
            if(managerPhoneTF.getText().isEmpty() ){
                phone = null;
            }else if (!isValidPhone(managerPhoneTF.getText())){
                showAlert("Error","Не удалось изменить данные","некорректный phone");
                return;
            }else{
                phone = managerPhoneTF.getText();
            }
            Manager managerEntity = new Manager(managerID, manager, phone);
            try {
                dbHandler.updateManager(managerEntity);
                updateManagerTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        managerAddBtn.setOnAction(actionEvent -> {
            String manager = null;
            if(managerManagerTF.getText().isEmpty() ){
                showAlert("Error","Не удалось добавить данные","введите manager");
                return;
            }else if (!isRussianTextWithDots(managerManagerTF.getText())){
                showAlert("Error","Не удалось доавбить данные","некорректный manager");
                return;
            }else{
                manager = managerManagerTF.getText();
            }
            String phone = null;
            if(managerPhoneTF.getText().isEmpty() ){
                showAlert("Error","Не удалось добавить данные","введите phone");
                return;
            }else if (!isValidPhone(managerPhoneTF.getText())){
                showAlert("Error","Не удалось добавить данные","некорректный phone");
                return;
            }else{
                phone = managerPhoneTF.getText();
            }
            Manager managerEntity = new Manager(null, manager, phone);
            try {
                dbHandler.addManager(managerEntity);
                updateManagerTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        managerDeleteBtn.setOnAction(actionEvent -> {
            Integer managerID = null;
            if(managerManagerIDTF.getText().isEmpty() ){
                showAlert("Error","Не удалось удалить данные","введите managerID");
                return;
            }else if (!managerManagerIDTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось удалить данные","managerID это число");
                return;
            }else if(!managerList.contains(Integer.parseInt(managerManagerIDTF.getText()))){
                showAlert("Error","Не удалось удалить данные","такого managerID не существует");
                return;
            }else{
                managerID = Integer.parseInt(managerManagerIDTF.getText());
            }
            try {
                dbHandler.deleteManager(managerID);
                updateManagerTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        updateDepartmentBtn.setOnAction(actionEvent -> {
            Integer departmentCode = null;
            if(departmentDepartmentCodeTF.getText().isEmpty() ){
                showAlert("Error","Не удалось изменить данные","введите departmentCode");
                return;
            }else if (!departmentDepartmentCodeTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось изменить данные","departmentCode это число");
                return;
            }else if(!departmentCodeList.contains(Integer.parseInt(departmentDepartmentCodeTF.getText()))){
                showAlert("Error","Не удалось изменить данные","такого departmentCode не существует");
                return;
            }else{
                departmentCode = Integer.parseInt(departmentDepartmentCodeTF.getText());
            }
            String name = !departmentNameTF.getText().isEmpty() ? departmentNameTF.getText() : null;
            Integer managerID = null;
            if(departmentManagerIDTF.getText().isEmpty() ){
                managerID = null;
            }else if (!departmentManagerIDTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось изменить данные","managerID это число");
                return;
            }else if(!managerList.contains(Integer.parseInt(departmentManagerIDTF.getText()))){
                showAlert("Error","Не удалось изменить данные","такого managerID не существует");
                return;
            }else{
                managerID = Integer.parseInt(departmentManagerIDTF.getText());
            }

            Department department = new Department(departmentCode, name, managerID);
            try {
                dbHandler.updateDepartment(department);
                updateDepartmentTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        addDepartmentBtn.setOnAction(actionEvent -> {
            String name = !departmentNameTF.getText().isEmpty() ? departmentNameTF.getText() : null;
            if(departmentNameTF.getText().isEmpty() ){
                showAlert("Error","Не удалось добавить данные","введите name");
                return;
            }else{
                name = departmentNameTF.getText();
            }
            Integer managerID = null;
            if(departmentManagerIDTF.getText().isEmpty() ){
                managerID = null;
            }else if (!departmentManagerIDTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось добавить данные","managerID это число");
                return;
            }else if(!managerList.contains(Integer.parseInt(departmentManagerIDTF.getText()))){
                showAlert("Error","Не удалось добавить данные","такого managerID не существует");
                return;
            }else{
                managerID = Integer.parseInt(departmentManagerIDTF.getText());
            }
            Department department = new Department(null, name, managerID);
            try {
                dbHandler.addDepartment(department);
                updateDepartmentTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        deleteDepartmentBtn.setOnAction(actionEvent -> {
            Integer departmentCode = null;
            if(departmentDepartmentCodeTF.getText().isEmpty() ){
                showAlert("Error","Не удалось изменить данные","введите departmentCode");
                return;
            }else if (!departmentDepartmentCodeTF.getText().matches("\\d+")){
                showAlert("Error","Не удалось изменить данные","departmentCode это число");
                return;
            }else if(!departmentCodeList.contains(Integer.parseInt(departmentDepartmentCodeTF.getText()))){
                showAlert("Error","Не удалось изменить данные","такого departmentCode не существует");
                return;
            }else{
                departmentCode = Integer.parseInt(departmentDepartmentCodeTF.getText());
            }
            try {
                dbHandler.deleteDepartment(departmentCode);
                updateDepartmentTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        userUpdateBtn.setOnAction(actionEvent -> {
            String login = null;
            if(userLoginTF.getText().isEmpty() ){
                showAlert("Error","Не удалось изменить данные","введите login");
                return;
            }else if (!isEnglishTextWithDigits(userLoginTF.getText())){
                showAlert("Error","Не удалось изменить данные","некорректный login");
                return;
            }else if(!usersList.contains(userLoginTF.getText())){
                showAlert("Error","Не удалось изменить данные","такого login не существует");
                return;
            }else{
                login = userLoginTF.getText();
            }
            String password = !userPasswordTF.getText().isEmpty() ? userPasswordTF.getText() : null;
            if(userPasswordTF.getText().isEmpty() ){
                showAlert("Error","Не удалось изменить данные","введите password");
                return;
            }else if (!isEnglishTextWithDigits(userPasswordTF.getText())){
                showAlert("Error","Не удалось изменить данные","некорректный password");
                return;
            }else{
                password = userLoginTF.getText();
            }
            User user = new User(login, password);
            try {
                dbHandler.updateUser(user);
                updateUserTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        userAddBtn.setOnAction(actionEvent -> {
            String login = null;
            if(userLoginTF.getText().isEmpty() ){
                showAlert("Error","Не удалось добавить данные","введите login");
                return;
            }else if (!isEnglishTextWithDigits(userLoginTF.getText())){
                showAlert("Error","Не удалось добавить данные","некорректный login");
                return;
            }else{
                login = userLoginTF.getText();
            }
            String password = null;
            if(userPasswordTF.getText().isEmpty() ){
                showAlert("Error","Не удалось добавить данные","введите password");
                return;
            }else if (!isEnglishTextWithDigits(userPasswordTF.getText())){
                showAlert("Error","Не удалось добавить данные","некорректный password");
                return;
            }else{
                password = userLoginTF.getText();
            }
            User user = new User(login, password);
            try {
                dbHandler.addUser(user);
                updateUserTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        userDeleteBtn.setOnAction(actionEvent -> {
            String login = null;
            if(userLoginTF.getText().isEmpty() ){
                showAlert("Error","Не удалось добавить данные","введите login");
                return;
            }else if (!isEnglishTextWithDigits(userLoginTF.getText())){
                showAlert("Error","Не удалось добавить данные","некорректный login");
                return;
            }else if(!usersList.contains(userLoginTF.getText())){
                showAlert("Error","Не удалось добавить данные","такого login не существует");
                return;
            }else{
                login = userLoginTF.getText();
            }
            try {
                dbHandler.deleteUser(login);
                updateUserTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        reportTab.setOnSelectionChanged(event -> {
            try {
                reportTable.getItems().clear();
                madeReport();
                reportTable.refresh();
                reportTable.setItems(reportEntries);
                reportNameColumn.setCellValueFactory(new PropertyValueFactory<ReportEntry, String>(Const.NAME));
                reportUnitColumn.setCellValueFactory(new PropertyValueFactory<ReportEntry, String>(Const.REPORT_UNIT));
                reportPriceColumn.setCellValueFactory(new PropertyValueFactory<ReportEntry, Double>(Const.PRODUCT_PRICE));
                reportAmountColumn.setCellValueFactory(new PropertyValueFactory<ReportEntry, Integer>(Const.SALE_AMOUNT));
                reportSumColumn.setCellValueFactory(new PropertyValueFactory<ReportEntry, Double>(Const.SALE_SUM));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setUsers() throws SQLException, ClassNotFoundException {
//        ResultSet response = dbHandler.getUsers();
        dbHandler.getUsersAsync().thenAccept(resultSet -> {
            while (true) {
                try {
                    if (!resultSet.next()) break;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    users.add(new User(
                            resultSet.getString(Const.USER_LOGIN),
                            resultSet.getString(Const.USER_PASSWORD)
                    ));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        userTable.setItems(users);
        userLoginColumn.setCellValueFactory(new PropertyValueFactory<User, String>(Const.USER_LOGIN));
        userPasswordColumn.setCellValueFactory(new PropertyValueFactory<User, String>(Const.USER_PASSWORD));
    }

    private void setSales() throws SQLException, ClassNotFoundException {
        ResultSet response = dbHandler.getSales();
        dbHandler.getSalesAsync().thenAccept(resultSet -> {
            while (true) {
                try {
                    if (!resultSet.next()) break;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    sales.add(new Sale(
                            resultSet.getInt(Const.SALE_SALE_ID),
                            resultSet.getInt(Const.ARTICLE),
                            resultSet.getDate(Const.SALE_DATE),
                            resultSet.getInt(Const.SALE_AMOUNT),
                            resultSet.getInt(Const.DEPARTMENT_CODE),
                            resultSet.getInt(Const.SALE_SUM)

                    ));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        saleTable.setItems(sales);
        saleSaleIDColumn.setCellValueFactory(new PropertyValueFactory<Sale, Integer>(Const.SALE_SALE_ID));
        saleArticleColumn.setCellValueFactory(new PropertyValueFactory<Sale, Integer>(Const.ARTICLE));
        saleDateColumn.setCellValueFactory(new PropertyValueFactory<Sale, Date>(Const.SALE_DATE));
        saleAmountColumn.setCellValueFactory(new PropertyValueFactory<Sale, Integer>(Const.SALE_AMOUNT));
        saleDepartmentCodeColumn.setCellValueFactory(new PropertyValueFactory<Sale, Integer>(Const.DEPARTMENT_CODE));
        saleSumColumn.setCellValueFactory(new PropertyValueFactory<Sale, Integer>(Const.SALE_SUM));
    }

    private void setProducts() throws SQLException, ClassNotFoundException {
//        ResultSet response = dbHandler.getProducts();
        dbHandler.getProductsAsync().thenAccept(resultSet -> {
            while (true) {
                try {
                    if (!resultSet.next()) break;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    products.add(new Product(
                            resultSet.getInt(Const.ARTICLE),
                            resultSet.getString(Const.NAME),
                            resultSet.getInt(Const.UNIT_ID),
                            resultSet.getInt(Const.PRODUCT_PRICE)
                    ));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        productTable.setItems(products);
        productArticleColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>(Const.ARTICLE));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>(Const.NAME));
        productUnitIDColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>(Const.UNIT_ID));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>(Const.PRODUCT_PRICE));

    }

    private void setUnits() throws SQLException, ClassNotFoundException {
        ResultSet response = dbHandler.getUnits();
        dbHandler.getUnitsAsync().thenAccept(resultSet -> {
            while (true) {
                try {
                    if (!resultSet.next()) break;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    units.add(new Unit(
                            resultSet.getInt(Const.UNIT_ID),
                            resultSet.getString(Const.UNIT_VALUE)
                    ));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        unitTable.setItems(units);
        unitUnitIDColumn.setCellValueFactory(new PropertyValueFactory<Unit, Integer>(Const.UNIT_ID));
        unitValueColumn.setCellValueFactory(new PropertyValueFactory<Unit, String>(Const.UNIT_VALUE));
    }

    private void setManagers() throws SQLException, ClassNotFoundException {
//        ResultSet response = dbHandler.getManagers();
        dbHandler.getManagersAsync().thenAccept(resultSet -> {
            while (true) {
                try {
                    if (!resultSet.next()) break;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    managers.add(new Manager(
                            resultSet.getInt(Const.MANAGER_ID),
                            resultSet.getString(Const.MANAGER_FULL_NAME),
                            resultSet.getString(Const.MANAGER_PHONE)
                    ));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        managerTable.setItems(managers);
        managerManagerColumn.setCellValueFactory(new PropertyValueFactory<Manager, String>(Const.MANAGER_FULL_NAME));
        managerManagerIdColumn.setCellValueFactory(new PropertyValueFactory<Manager, Integer>(Const.MANAGER_ID));
        managerPhoneColumn.setCellValueFactory(new PropertyValueFactory<Manager, String>(Const.MANAGER_PHONE));
    }

    private void setDepartments() throws SQLException, ClassNotFoundException {
//        ResultSet response = dbHandler.getDepartments();
        dbHandler.getDepartmentsAsync().thenAccept(resultSet -> {
            while (true) {
                try {
                    if (!resultSet.next()) break;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    departments.add(new Department(
                            resultSet.getInt(Const.DEPARTMENT_CODE),
                            resultSet.getString(Const.NAME),
                            resultSet.getInt(Const.MANAGER_ID)
                    ));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        departmentTable.setItems(departments);
        departmentDepartmentCodeColumn.setCellValueFactory(new PropertyValueFactory<Department, Integer>(Const.DEPARTMENT_CODE));
        departmentNameColumn.setCellValueFactory(new PropertyValueFactory<Department, String>(Const.NAME));
        departmentManagerIDColumn.setCellValueFactory(new PropertyValueFactory<Department, Integer>(Const.MANAGER_ID));
    }

    public void updateUserTable() throws SQLException, ClassNotFoundException {
        userTable.getItems().clear();
        setUsers();
        userTable.refresh();
    }

    public void updateDepartmentTable() throws SQLException, ClassNotFoundException {
        departmentTable.getItems().clear();
        setDepartments();
        departmentTable.refresh();
    }

    public void updateManagerTable() throws SQLException, ClassNotFoundException {
        managerTable.getItems().clear();
        setManagers();
        managerTable.refresh();
    }

    public void updateSaleTable(DataStorage dataStorage) throws SQLException, ClassNotFoundException {
        saleTable.getItems().clear();
        setSales();
        saleTable.refresh();
    }

    public void updateProductTable() throws SQLException, ClassNotFoundException {
        productTable.getItems().clear();
        setProducts();
        productTable.refresh();
    }

    public void updateUnitTable() throws SQLException, ClassNotFoundException {
        unitTable.getItems().clear();
        setUnits();
        unitTable.refresh();
    }

    public void madeReport() throws SQLException, ClassNotFoundException {
        YearMonth yearMonth = YearMonth.now();
        String yearMonthString = yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        ResultSet departmentCodes = dbHandler.getDepartmentCodes();
        Integer amountMall = 0;
        Double sumMall = 0.0;
        while (departmentCodes.next()) {
            ResultSet sales = dbHandler.getSalesForDepartmentCodeAndDate(departmentCodes.getInt(Const.DEPARTMENT_CODE), yearMonthString);
            ResultSet departmentName = dbHandler.getDepartmentNameByCode(departmentCodes.getInt(Const.DEPARTMENT_CODE));
            departmentName.next();
            Integer amountDepartment = 0;
            Double sumDepartment = 0.0;
            while(sales.next()){
                ResultSet productName = dbHandler.getProductNameByArticle(sales.getInt(Const.ARTICLE));
                productName.next();
                ResultSet unitId = dbHandler.getUnitIdByArticle(sales.getInt(Const.ARTICLE));
                unitId.next();
                ResultSet unitValue = dbHandler.getUnitValueByUnitId(unitId.getInt(Const.UNIT_ID));
                unitValue.next();
                ResultSet price = dbHandler.getProductPriceByArticle(sales.getInt(Const.ARTICLE));
                price.next();
                String name = departmentName.getString(Const.NAME) + " " +productName.getString(Const.NAME);
                reportEntries.add(new ReportEntry(
                    name,unitValue.getString(Const.UNIT_VALUE),price.getInt(Const.PRODUCT_PRICE),sales.getInt(Const.SALE_AMOUNT),null
                ));
                amountDepartment += sales.getInt(Const.SALE_AMOUNT);
                sumDepartment += Double.valueOf(sales.getInt(Const.SALE_SUM));
                amountMall += sales.getInt(Const.SALE_AMOUNT);
                sumMall +=Double.valueOf(sales.getInt(Const.SALE_SUM));
            }
            reportEntries.add(new ReportEntry(
                    "Итого по отделу",null,null,amountDepartment,(sumDepartment / 1000.0)
            ));
        }
        reportEntries.add(new ReportEntry(
                "Итого по магазину",null,null,amountMall,(sumMall/1000.0)
        ));
        Integer test =6;
    }
    public  void showAlert(String title, String Header,String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(Header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public boolean isDateInFormat(String date) {
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    public boolean isRussianTextWithDots(String text) {
        return text.matches("[\u0400-\u04FF.]+");
    }

    public boolean isValidPhone(String str) {
        return str.matches("^\\d{11}$");
    }
    public boolean isEnglishTextWithDigits(String text) {
        return text.matches("[a-zA-Z0-9]+");
    }

}
