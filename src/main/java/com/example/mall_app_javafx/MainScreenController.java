package com.example.mall_app_javafx;

import com.example.mall_app_javafx.models.Manager;
import com.example.mall_app_javafx.models.Product;
import com.example.mall_app_javafx.models.ReportEntry;
import com.example.mall_app_javafx.models.Sale;
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

public class MainScreenController {

    private final ObservableList<Sale> sales = FXCollections.observableArrayList();
    private final ObservableList<Product> products = FXCollections.observableArrayList();

    private final ObservableList<ReportEntry> reportEntries = FXCollections.observableArrayList();
    private final  DatabaseHandler dbHandler = new DatabaseHandler();

    @FXML
    private TextField amountTF;

    @FXML
    private TextField articleTF;

    @FXML
    private Label contactNameLabel;

    @FXML
    private Label contactPhoneLabel;

    @FXML
    private TextField dateTF;

    @FXML
    private TextField departmentCodeTF;

    @FXML
    private Button saleAddBtn;

    @FXML
    private Button saleDeleteBtn;

    @FXML
    private TextField saleIDTF;

    @FXML
    private Button saleUpdateBtn;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button productAddBtn;
    @FXML
    private TextField productArticleTF;
    @FXML
    private Button productDeleteBtn;
    @FXML
    private TextField productNameTF;
    @FXML
    private TextField productPriceTF;
    @FXML
    private TextField productUnitIDTF;
    @FXML
    private Button productUpdateBtn;

    @FXML
    private TableColumn<Product, Integer> productArticleColumn;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> productPriceColumn;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> productUnitIDColumn;

    @FXML
    private TableColumn<Sale, Integer> saleAmountColumn;

    @FXML
    private TableColumn<Sale, Integer> saleArticleColumn;

    @FXML
    private TableColumn<Sale, Date>saleDateColumn;

    @FXML
    private TableColumn<Sale, Integer> saleDepartmentCodeColumn;

    @FXML
    private TableColumn<Sale, Integer> saleSaleIDColumn;

    @FXML
    private TableColumn<Sale, Integer> saleSumColumn;

    @FXML
    private TableView<Sale> saleTable;
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
        setSales(dataStorage.getIdDepartment());
        setProducts();
        Manager manager;
        ResultSet response = dbHandler.getManagerByDepartmentCode(dataStorage.getIdDepartment());
        ArrayList<Integer> saleList = new ArrayList<Integer>();
        ArrayList<Integer> departmentCodeList = new ArrayList<Integer>();
        ArrayList<Integer> articleList = new ArrayList<Integer>();
        ArrayList<Integer> unitList = new ArrayList<Integer>();

        ResultSet saleListReponse = dbHandler.getSales();
        ResultSet departmentCodeListReponse = dbHandler.getDepartmentCodes();
        ResultSet articleListResponse = dbHandler.getArticles();
        ResultSet unitListResponse = dbHandler.getUnits();

        while(saleListReponse.next()){
            saleList.add(saleListReponse.getInt(Const.SALE_SALE_ID));
        }
        while(articleListResponse.next()){
            articleList.add(articleListResponse.getInt(Const.ARTICLE));
        }
        while(departmentCodeListReponse.next()){
            departmentCodeList.add(departmentCodeListReponse.getInt(Const.DEPARTMENT_CODE));
        }
        while(unitListResponse.next()){
            unitList.add(unitListResponse.getInt(Const.UNIT_ID));
        }
        while (response.next()){
            manager = new Manager(
                    response.getInt(Const.MANAGER_ID),
                    response.getString(Const.MANAGER_FULL_NAME),
                    response.getString(Const.MANAGER_PHONE)
            );
            contactNameLabel.setText("ФИО: " + manager.getManager());
            contactPhoneLabel.setText("Номер телефона: "+manager.getPhone());
        }

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
            if (date==null){
                sale = new Sale(
                        id,article,null,amount,departmentCode,null
                );
            }else{
                sale = new Sale(
                        id,article,Date.from(date.atZone(ZoneId.systemDefault()).toInstant()),amount,departmentCode,null
                );
            }
            if(article!=null && (!articleList.contains(article)))
            try {
                dbHandler.updateSale(sale);
                updateSaleTable(dataStorage);;
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
                    null,article,Date.from(date.atZone(ZoneId.systemDefault()).toInstant()),amount,departmentCode,null
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
                dbHandler.deleteSale(id);
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
                    article,name,unitID,price
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
                    null,name,unitID,price
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

    private void setSales(int id) throws SQLException, ClassNotFoundException {
        ResultSet response = dbHandler.getSalesByDepartmentId(id);
        while(response.next()){
            sales.add(new Sale(
                    response.getInt(Const.SALE_SALE_ID),
                    response.getInt(Const.ARTICLE),
                    response.getDate(Const.SALE_DATE),
                    response.getInt(Const.SALE_AMOUNT),
                    response.getInt(Const.DEPARTMENT_CODE),
                    response.getInt(Const.SALE_SUM)

            ));
        }
        saleTable.setItems(sales);
        saleSaleIDColumn.setCellValueFactory(new PropertyValueFactory<Sale, Integer>(Const.SALE_SALE_ID));
        saleArticleColumn.setCellValueFactory(new PropertyValueFactory<Sale, Integer>(Const.ARTICLE));
        saleDateColumn.setCellValueFactory(new PropertyValueFactory<Sale, Date>(Const.SALE_DATE));
        saleAmountColumn.setCellValueFactory(new PropertyValueFactory<Sale, Integer>(Const.SALE_AMOUNT));
        saleDepartmentCodeColumn.setCellValueFactory(new PropertyValueFactory<Sale, Integer>(Const.DEPARTMENT_CODE));
        saleSumColumn.setCellValueFactory(new PropertyValueFactory<Sale, Integer>(Const.SALE_SUM));
    }

    private void setProducts() throws SQLException, ClassNotFoundException {
        ResultSet response = dbHandler.getProducts();
        while(response.next()){
            products.add(new Product(
                    response.getInt(Const.ARTICLE),
                    response.getString(Const.NAME),
                    response.getInt(Const.UNIT_ID),
                    response.getInt(Const.PRODUCT_PRICE)
            ));
        }
        productTable.setItems(products);
        productArticleColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>(Const.ARTICLE));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>(Const.NAME));
        productUnitIDColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>(Const.UNIT_ID));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>(Const.PRODUCT_PRICE));

    }

    public void  updateSaleTable(DataStorage dataStorage) throws SQLException, ClassNotFoundException {
        saleTable.getItems().clear();
        setSales(dataStorage.getIdDepartment());
        saleTable.refresh();
    }

    public void  updateProductTable() throws SQLException, ClassNotFoundException {
        productTable.getItems().clear();
        setProducts();
        productTable.refresh();
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

}
