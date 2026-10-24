
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class FinalResultDAO {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String sql = "SELECT s.roll_no, s.name, c.course_code, "
                   + "c.title, e.semester, a.cia1, a.cia2, "
                   + "a.assignment_marks, a.quiz_marks, "
                   + "a.lab_marks, a.ese_marks, a.ese_absent "
                   + "FROM assessment_marks a "
                   + "JOIN enrollments e ON a.enrollment_id = e.enrollment_id "
                   + "JOIN students s ON e.roll_no = s.roll_no "
                   + "JOIN courses c ON e.course_code = c.course_code "
                   + "WHERE s.roll_no = ?";

        try {
            System.out.print("Enter Student Roll Number: ");
            String rollNo = sc.nextLine().trim();

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, rollNo);

                try (ResultSet rs = ps.executeQuery()) {

                    boolean found = false;

                    while (rs.next()) {
                        found = true;

                        System.out.println("\n===== FINAL RESULT REPORT =====");
                        System.out.println("Roll Number: "
                                + rs.getString("roll_no"));
                        System.out.println("Student Name: "
                                + rs.getString("name"));
                        System.out.println("Course Code: "
                                + rs.getString("course_code"));
                        System.out.println("Course Name: "
                                + rs.getString("title"));
                        System.out.println("Semester: "
                                + rs.getInt("semester"));

                        System.out.println("CIA 1: "
                                + rs.getBigDecimal("cia1"));
                        System.out.println("CIA 2: "
                                + rs.getBigDecimal("cia2"));
                        System.out.println("Assignment: "
                                + rs.getBigDecimal("assignment_marks"));
                        System.out.println("Quiz: "
                                + rs.getBigDecimal("quiz_marks"));
                        System.out.println("Lab: "
                                + rs.getBigDecimal("lab_marks"));

                        boolean absent = rs.getBoolean("ese_absent");

                        if (absent) {
                            System.out.println("ESE Marks: Absent");
                            System.out.println("Result Status: ABSENT");
                        } else {
                            java.math.BigDecimal ese =
                                    rs.getBigDecimal("ese_marks");

                            System.out.println("ESE Marks: " + ese);

                            if (ese == null) {
                                System.out.println(
                                        "Result Status: ESE marks not entered");
                            } else if (ese.doubleValue() >= 50) {
                                System.out.println(
                                        "Result Status: PASS (demo rule)");
                            } else {
                                System.out.println(
                                        "Result Status: FAIL (demo rule)");
                            }
                        }

                        System.out.println("===============================");
                    }

                    if (!found) {
                        System.out.println(
                                "No result found. Check the roll number "
                                + "and saved assessment marks.");
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Unable to display result: "
                    + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
