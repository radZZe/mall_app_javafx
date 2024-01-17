package com.example.mall_app_javafx;

import com.example.mall_app_javafx.models.*;

import java.sql.*;
import java.util.concurrent.CompletableFuture;


public class DatabaseHandler extends Configs {
    Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    public ResultSet authUser(String login, String password) {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.USERS_TABLE + " WHERE " + Const.USERS_LOGIN + "=? AND "
                + Const.USERS_PASSWORD + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, login);
            prSt.setString(2, password);
            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resSet;
    }
    public CompletableFuture<ResultSet> getUsersAsync() {
        return CompletableFuture.supplyAsync(this::getUsers);
    }

    private ResultSet getUsers() {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.USERS_TABLE;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public CompletableFuture<ResultSet> getDepartmentsAsync() {
        return CompletableFuture.supplyAsync(() -> {
            return getDepartments();
        });
    }

    public ResultSet getDepartments() {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.DEPARTMENT_TABLE;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public CompletableFuture<ResultSet> getSalesByDepartmentIdAsync(int id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getSalesByDepartmentId(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public ResultSet getSalesByDepartmentId(int id) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.SALE_TABLE + " WHERE " + Const.DEPARTMENT_CODE + " =?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setInt(1, id);
        resSet = prSt.executeQuery();
        return resSet;
    }

    public CompletableFuture<ResultSet> getSalesAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getSales();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public ResultSet getSales() throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.SALE_TABLE;
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        resSet = prSt.executeQuery();
        return resSet;
    }

    public CompletableFuture<ResultSet> getProductsAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getProducts();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public ResultSet getProducts() throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.PRODUCT_TABLE;
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        resSet = prSt.executeQuery();
        return resSet;
    }

    public CompletableFuture<ResultSet> getUnitsAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getUnits();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public ResultSet getUnits() throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.UNIT_TABLE;
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        resSet = prSt.executeQuery();
        return resSet;
    }

    public CompletableFuture<Integer> updateSaleAsync(Sale sale) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return updateSale(sale);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public int updateSale(Sale sale) throws SQLException, ClassNotFoundException {
        int resSet;
        String update = "UPDATE " + Const.SALE_TABLE + " SET "
                + Const.ARTICLE + " = COALESCE(?, " + Const.ARTICLE + "), "
                + Const.SALE_DATE + " = COALESCE(?, " + Const.SALE_DATE + "), "
                + Const.SALE_AMOUNT + " = COALESCE(?, " + Const.SALE_AMOUNT + "), "
                + Const.DEPARTMENT_CODE + " = COALESCE(?, " + Const.DEPARTMENT_CODE + ") "
                + "WHERE " + Const.SALE_SALE_ID + " = ?;";
        PreparedStatement prSt = getDbConnection().prepareStatement(update);

        prSt.setObject(1, sale.getArticle() != null ? sale.getArticle() : null);
        prSt.setObject(2, sale.getDate() != null ? sale.getDate() : null);
        prSt.setObject(3, sale.getAmount() != null ? sale.getAmount() : null);
        prSt.setObject(4, sale.getDepartmentCode() != null ? sale.getDepartmentCode() : null);
        prSt.setInt(5, sale.getSaleID());
        resSet = prSt.executeUpdate();
        return resSet;
    }
    public CompletableFuture<Void> addSaleAsync(Sale sale) {
        return CompletableFuture.runAsync(() -> {
            addSaleAsync(sale);
        });
    }
    public void addSale(Sale sale) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.SALE_TABLE + " (article, date, amount,departmentCode)" +
                "VALUES (?, ?, ?, ? );";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setInt(1, sale.getArticle());
        prSt.setObject(2, sale.getDate());
        prSt.setInt(3, sale.getAmount());
        prSt.setInt(4, sale.getDepartmentCode());
        prSt.executeUpdate();
    }
    public void deleteSaleAsync(Integer id) {
        CompletableFuture.runAsync(() -> {
            try {
                deleteSale(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void deleteSale(Integer id) throws SQLException, ClassNotFoundException {
        String delete = "DELETE FROM " + Const.SALE_TABLE + " WHERE " + Const.SALE_SALE_ID + " =?;";
        PreparedStatement prSt = getDbConnection().prepareStatement(delete);
        prSt.setInt(1, id);
        prSt.executeUpdate();
    }

    public void updateProduct(Product product) throws SQLException, ClassNotFoundException {
        String update = "UPDATE " + Const.PRODUCT_TABLE + " SET "
                + Const.NAME + " = COALESCE(?, " + Const.NAME + "), "
                + Const.UNIT_ID + " = COALESCE(?, " + Const.UNIT_ID + "), "
                + Const.PRODUCT_PRICE + " = COALESCE(?, " + Const.PRODUCT_PRICE + ") "
                + "WHERE " + Const.ARTICLE + " = ?;";
        PreparedStatement prSt = getDbConnection().prepareStatement(update);

        prSt.setObject(1, product.getName() != null ? product.getName() : null);
        prSt.setObject(2, product.getUnitID() != null ? product.getUnitID() : null);
        prSt.setObject(3, product.getPrice() != null ? product.getPrice() : null);
        prSt.setObject(4, product.getArticle());
        prSt.executeUpdate();
    }

    public void addProduct(Product product) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.PRODUCT_TABLE + " (name, unitID,price)" +
                "VALUES (?, ?, ?);";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1, product.getName());
        prSt.setInt(2, product.getUnitID());
        prSt.setInt(3, product.getPrice());
        prSt.executeUpdate();
    }

    public void deleteProduct(Integer article) throws SQLException, ClassNotFoundException {
        String delete = "DELETE FROM " + Const.PRODUCT_TABLE + " WHERE " + Const.ARTICLE + " =?;";
        PreparedStatement prSt = getDbConnection().prepareStatement(delete);
        prSt.setInt(1, article);
        prSt.executeUpdate();
    }

    public void updateUnit(Unit unit) throws SQLException, ClassNotFoundException {
        String update = "UPDATE " + Const.UNIT_TABLE + " SET "
                + Const.UNIT_VALUE + " = COALESCE(?, " + Const.UNIT_VALUE + ")"
                + "WHERE " + Const.UNIT_ID + " = ?;";
        PreparedStatement prSt = getDbConnection().prepareStatement(update);

        prSt.setObject(1, unit.getValue() != null ? unit.getValue() : null);
        prSt.setObject(2, unit.getUnitID() != null ? unit.getUnitID() : null);
        prSt.executeUpdate();
    }

    public void addUnit(Unit unit) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.UNIT_TABLE + " (value)" +
                "VALUES (?);";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1, unit.getValue());
        prSt.executeUpdate();
    }

    public void deleteUnit(Integer unitID) throws SQLException, ClassNotFoundException {
        String delete = "DELETE FROM " + Const.UNIT_TABLE + " WHERE " + Const.UNIT_ID + " =?;";
        PreparedStatement prSt = getDbConnection().prepareStatement(delete);
        prSt.setInt(1, unitID);
        prSt.executeUpdate();
    }

    public CompletableFuture<ResultSet> getManagersAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getManagers();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public ResultSet getManagers() throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.MANAGER_TABLE;
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        resSet = prSt.executeQuery();
        return resSet;
    }

    public void updateManager(Manager manager) throws SQLException, ClassNotFoundException {
        String update = "UPDATE " + Const.MANAGER_TABLE + " SET "
                + Const.MANAGER_FULL_NAME + " = COALESCE(?, " + Const.MANAGER_FULL_NAME + "),"
                + Const.MANAGER_PHONE + " = COALESCE(?, " + Const.MANAGER_PHONE + ")"
                + "WHERE " + Const.MANAGER_ID + " = ?;";
        PreparedStatement prSt = getDbConnection().prepareStatement(update);
        prSt.setObject(1, manager.getManager() != null ? manager.getManager() : null);
        prSt.setObject(2, manager.getPhone() != null ? manager.getPhone() : null);
        prSt.setObject(3, manager.getManagerID());
        prSt.executeUpdate();
    }

    public void addManager(Manager manager) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.MANAGER_TABLE + " (manager, phone)" +
                "VALUES (?,?);";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1, manager.getManager());
        prSt.setString(2, manager.getPhone());
        prSt.executeUpdate();
    }

    public void deleteManager(Integer managerID) throws SQLException, ClassNotFoundException {
        String delete = "DELETE FROM " + Const.MANAGER_TABLE + " WHERE " + Const.MANAGER_ID + " =?;";
        PreparedStatement prSt = getDbConnection().prepareStatement(delete);
        prSt.setInt(1, managerID);
        prSt.executeUpdate();
    }

    public void updateDepartment(Department department) throws SQLException, ClassNotFoundException {
        String update = "UPDATE " + Const.DEPARTMENT_TABLE + " SET "
                + Const.NAME + " = COALESCE(?, " + Const.NAME + "),"
                + Const.MANAGER_ID + " = COALESCE(?, " + Const.MANAGER_ID + ")"
                + "WHERE " + Const.DEPARTMENT_CODE + " = ?;";
        PreparedStatement prSt = getDbConnection().prepareStatement(update);
        prSt.setObject(1, department.getName() != null ? department.getName() : null);
        prSt.setObject(2, department.getManagerID() != null ? department.getManagerID() : null);
        prSt.setObject(3, department.getDepartmentCode());
        prSt.executeUpdate();
    }

    public void addDepartment(Department department) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.DEPARTMENT_TABLE + " (name, managerID)" +
                "VALUES (?,?);";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1, department.getName());
        prSt.setInt(2, department.getManagerID());
        prSt.executeUpdate();
    }

    public void deleteDepartment(Integer departmentCode) throws SQLException, ClassNotFoundException {
        String delete = "DELETE FROM " + Const.DEPARTMENT_TABLE + " WHERE " + Const.DEPARTMENT_CODE + " =?;";
        PreparedStatement prSt = getDbConnection().prepareStatement(delete);
        prSt.setInt(1, departmentCode);
        prSt.executeUpdate();
    }

    public void updateUser(User user) throws SQLException, ClassNotFoundException {
        String update = "UPDATE " + Const.USERS_TABLE + " SET "
                + Const.USERS_PASSWORD + " = COALESCE(?, " + Const.USERS_PASSWORD + ")"
                + "WHERE " + Const.USER_LOGIN + " = ?;";
        PreparedStatement prSt = getDbConnection().prepareStatement(update);
        prSt.setObject(1, user.getPassword() != null ? user.getPassword() : null);
        prSt.setObject(2, user.getLogin());
        prSt.executeUpdate();
    }

    public void addUser(User user) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.USERS_TABLE + " (login, password)" +
                "VALUES (?,?);";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1, user.getLogin());
        prSt.setString(2, user.getPassword());
        prSt.executeUpdate();
    }

    public void deleteUser(String login) throws SQLException, ClassNotFoundException {
        String delete = "DELETE FROM " + Const.USERS_TABLE + " WHERE " + Const.USER_LOGIN + " =?;";
        PreparedStatement prSt = getDbConnection().prepareStatement(delete);
        prSt.setString(1, login);
        prSt.executeUpdate();
    }

    public CompletableFuture<ResultSet> getManagersByDepartmentCodeAsync(int code) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getManagerByDepartmentCode(code);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public ResultSet getManagerByDepartmentCode(Integer departmentCode) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT m.* FROM manager m INNER JOIN department d ON m.managerID = d.managerID WHERE d.departmentCode =?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setInt(1, departmentCode);
        resSet = prSt.executeQuery();
        return resSet;
    }

    public CompletableFuture<ResultSet> getDepartmentCodesAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getDepartmentCodes();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public ResultSet getDepartmentCodes() throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT departmentCode " + " FROM department;";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        resSet = prSt.executeQuery();
        return resSet;
    }

    public CompletableFuture<ResultSet> getDepartmentNameByCodeAsync(Integer code) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getDepartmentNameByCode(code);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public ResultSet getDepartmentNameByCode(Integer departmentCode) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT name " + " FROM department WHERE departmentCode =?;";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setInt(1, departmentCode);
        resSet = prSt.executeQuery();
        return resSet;
    }

    public ResultSet getProductNameByArticle(Integer article) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT name " + " FROM product WHERE article =?;";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setInt(1, article);
        resSet = prSt.executeQuery();
        return resSet;
    }

    public ResultSet getUnitValueByUnitId(Integer unitID) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT value " + " FROM unit WHERE unitID =?;";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setInt(1, unitID);
        resSet = prSt.executeQuery();
        return resSet;
    }

    public ResultSet getProductPriceByArticle(Integer article) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT price " + " FROM product WHERE article =?;";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setInt(1, article);
        resSet = prSt.executeQuery();
        return resSet;
    }

    public ResultSet getUnitIdByArticle(Integer article) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT unitID " + " FROM product WHERE article =?;";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setInt(1, article);
        resSet = prSt.executeQuery();
        return resSet;
    }

    public CompletableFuture<ResultSet> getArticlesAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getArticles();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public ResultSet getArticles() throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT article" + " FROM product ;";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        resSet = prSt.executeQuery();
        return resSet;
    }

    public CompletableFuture<ResultSet> getSalesForDepartmentCodeAndDateAsync(Integer departmentCode, String date) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getSalesForDepartmentCodeAndDate(departmentCode, date);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public ResultSet getSalesForDepartmentCodeAndDate(Integer departmentCode, String date) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT *" + " FROM sale " + "WHERE departmentCode =? AND date LIKE '" + date + "%';";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setInt(1, departmentCode);
        resSet = prSt.executeQuery();
        return resSet;
    }
}
