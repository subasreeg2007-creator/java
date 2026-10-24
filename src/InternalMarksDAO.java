
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class InternalMarksDAO {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String sql = "SELECT s.roll_no, s.name, c.title, "
                   + "a.cia1, a.cia2, a.assignment_marks, "
                   + "a.quiz_marks, a.lab_marks "
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

                        double cia1 = rs.getDouble("cia1");
                        double cia2 = rs.getDouble("cia2");
                        double assignment =
                                rs.getDouble("assignment_marks");
                        double quiz = rs.getDouble("quiz_marks");
                        double lab = rs.getDouble("lab_marks");

                        double total = cia1 + cia2
                                     + assignment + quiz + lab;

                        System.out.println("\n--- INTERNAL MARKS REPORT ---");
                        System.out.println("Roll Number: "
                                + rs.getString("roll_no"));
                        System.out.println("Student Name: "
                                + rs.getString("name"));
                        System.out.println("Course: "
                                + rs.getString("title"));
                        System.out.println("CIA 1 Marks: " + cia1);
                        System.out.println("CIA 2 Marks: " + cia2);
                        System.out.println("Assignment Marks: "
                                + assignment);
                        System.out.println("Quiz Marks: " + quiz);
                        System.out.println("Lab Marks: " + lab);
                        System.out.println("Combined Raw Total: "
                                + total + " / 500");
                        System.out.println("-----------------------------");
                    }

                    if (!found) {
                        System.out.println(
                                "No assessment marks found for this roll number.");
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Unable to calculate internal marks: "
                    + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
