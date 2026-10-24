
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AttendancePercentageDAO {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String sql = "SELECT s.roll_no, s.name, c.title, "
                   + "e.total_classes, e.attended_classes "
                   + "FROM enrollments e "
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

                        int total = rs.getInt("total_classes");
                        int attended = rs.getInt("attended_classes");

                        double percentage = 0.0;

                        if (total > 0) {
                            percentage = (attended * 100.0) / total;
                        }

                        System.out.println("\n--- ATTENDANCE REPORT ---");
                        System.out.println("Roll Number: "
                                + rs.getString("roll_no"));
                        System.out.println("Student Name: "
                                + rs.getString("name"));
                        System.out.println("Course: "
                                + rs.getString("title"));
                        System.out.println("Total Classes: " + total);
                        System.out.println("Classes Attended: " + attended);
                        System.out.printf("Attendance Percentage: %.2f%%%n",
                                percentage);

                        if (total == 0) {
                            System.out.println(
                                    "Attendance has not been recorded yet.");
                        } else if (percentage >= 75) {
                            System.out.println("Status: Eligible by attendance");
                        } else {
                            System.out.println(
                                    "Status: Attendance below 75%");
                        }

                        System.out.println("-------------------------");
                    }

                    if (!found) {
                        System.out.println(
                                "No enrollment found for this roll number.");
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Unable to calculate attendance: "
                    + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
