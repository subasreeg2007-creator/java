
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class EnrollmentDAO {

    public boolean enrollStudent(String rollNo, String courseCode,
                                 int semester) {

        String sql = "INSERT INTO enrollments "
                   + "(roll_no, course_code, semester) "
                   + "VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rollNo);
            ps.setString(2, courseCode);
            ps.setInt(3, semester);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Enrollment failed: "
                               + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        EnrollmentDAO dao = new EnrollmentDAO();

        System.out.print("Enter Student Roll Number: ");
        String rollNo = sc.nextLine().trim();

        System.out.print("Enter Course Code: ");
        String courseCode = sc.nextLine().trim();

        System.out.print("Enter Semester (1-8): ");

        try {
            int semester = Integer.parseInt(sc.nextLine().trim());

            if (rollNo.isEmpty() || courseCode.isEmpty()
                    || semester < 1 || semester > 8) {
                System.out.println("Invalid input.");
                return;
            }

            if (dao.enrollStudent(rollNo, courseCode, semester)) {
                System.out.println("Student Enrolled Successfully!");
            }

        } catch (NumberFormatException e) {
            System.out.println("Semester must be a number.");
        } finally {
            sc.close();
        }
    }
}
