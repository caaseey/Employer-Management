package controller;

import model.Employee;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author grier
 */
public class BBDDManager {
    private static final String dataConnect = "jdbc:mysql://localhost:3306/";
    private static final String database = "javaDAM";
    private static final String user = "root";
    private static final String password = "";
    private Connection con;

    public BBDDManager() {
        try {
            con = DriverManager.getConnection(dataConnect, user, password); // Conectar sin BBDD
            createBBDD(); // Crear BBDD si no existe
            con = DriverManager.getConnection(dataConnect + database, user, password); // Reconectar ya a javaDAM
            createTable(); // Crear la tabla
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createBBDD() {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + database + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void createTable() {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Employee (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(100), " +
                "age INT, " +
                "department VARCHAR(100), " +
                "salary DOUBLE);"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addEmployee(String name, int age, String department, double salary) {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO Employee(name, age, department, salary) " +
                               "VALUES('" + name + "', " + age + ", '" + department + "', " + salary + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateEmployee(int id, String name, int age, String department, double salary) {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("UPDATE Employee SET name = '" + name + "', age = " + age + 
                               ", department = '" + department + "', salary = " + salary + " WHERE id = " + id + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteEmployee(int id) {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM Employee WHERE id = " + id + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Employee> showEmployee() {
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Employee");
            while (rs.next()) {
                employees.add(new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("department"),
                    rs.getDouble("salary")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return employees;
    }
}