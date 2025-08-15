package EmployeeDB.src;

 import java.sql.*;
import java.util.Scanner;

public class EmployeeDBApp {

    private static final String URL = "jdbc:mysql://localhost:3306/employee_db";
    private static final String USER = "root";  
    private static final String PASSWORD = "722247"; 

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addEmployee(String name, String position, double salary) {
        String sql = "INSERT INTO employees (name, position, salary) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, position);
            pstmt.setDouble(3, salary);
            pstmt.executeUpdate();
            System.out.println("Employee added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewEmployees() {
        String sql = "SELECT * FROM employees";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n--- Employee List ---");
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %.2f\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getDouble("salary"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(int id, String name, String position, double salary) {
        String sql = "UPDATE employees SET name=?, position=?, salary=? WHERE id=?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, position);
            pstmt.setDouble(3, salary);
            pstmt.setInt(4, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println(" Employee updated successfully!");
            } else {
                System.out.println(" Employee not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id=?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println(" Employee deleted successfully!");
            } else {
                System.out.println("Employee not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EmployeeDBApp app = new EmployeeDBApp();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Employee Database Menu ===");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter position: ");
                    String position = sc.nextLine();
                    System.out.print("Enter salary: ");
                    double salary = sc.nextDouble();
                    app.addEmployee(name, position, salary);
                    break;

                case 2:
                    app.viewEmployees();
                    break;

                case 3:
                    System.out.print("Enter employee ID to update: ");
                    int idU = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new name: ");
                    String newName = sc.nextLine();
                    System.out.print("Enter new position: ");
                    String newPosition = sc.nextLine();
                    System.out.print("Enter new salary: ");
                    double newSalary = sc.nextDouble();
                    app.updateEmployee(idU, newName, newPosition, newSalary);
                    break;

                case 4:
                    System.out.print("Enter employee ID to delete: ");
                    int idD = sc.nextInt();
                    app.deleteEmployee(idD);
                    break;

                case 5:
                    System.out.println(" Goodbye!");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println(" Invalid choice.");
            }
        }
    }
}
