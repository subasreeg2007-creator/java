
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentDAO {

    public boolean addStudent(String rollNo, String name,
                              String department, int semester) {

        String sql = "INSERT INTO students "
                   + "(roll_no, name, department, current_semester) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rollNo);
            ps.setString(2, name);
            ps.setString(3, department);
            ps.setInt(4, semester);

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Unable to add student: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StudentDAO dao = new StudentDAO();

        System.out.print("Enter Roll Number: ");
        String rollNo = sc.nextLine().trim();

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter Department (CSE/ECE/MECH/IT): ");
        String department = sc.nextLine().trim().toUpperCase();

        System.out.print("Enter Current Semester (1-8): ");
        int semester;

        try {
            semester = Integer.parseInt(sc.nextLine().trim());

            if (rollNo.isEmpty() || name.isEmpty()
                    || department.isEmpty()
                    || semester < 1 || semester > 8) {
                System.out.println("Invalid input. Please check the details.");
                return;
            }

            if (dao.addStudent(rollNo, name, department, semester)) {
                System.out.println("Student Registered Successfully!");
            }

        } catch (NumberFormatException e) {
            System.out.println("Semester must be a number.");
        } finally {
            sc.close();
        }
    }
}
