
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AttendanceDAO {

    public boolean updateAttendance(int enrollmentId,
                                    int totalClasses,
                                    int attendedClasses) {

        String sql = "UPDATE enrollments "
                   + "SET total_classes = ?, attended_classes = ? "
                   + "WHERE enrollment_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, totalClasses);
            ps.setInt(2, attendedClasses);
            ps.setInt(3, enrollmentId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                return true;
            } else {
                System.out.println("Enrollment ID not found.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Unable to update attendance: "
                    + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AttendanceDAO dao = new AttendanceDAO();

        try {
            System.out.print("Enter Enrollment ID: ");
            int id = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Enter Total Classes Conducted: ");
            int total = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Enter Classes Attended: ");
            int attended = Integer.parseInt(sc.nextLine().trim());

            if (id <= 0 || total < 0 || attended < 0
                    || attended > total) {
                System.out.println("Invalid attendance details.");
                return;
            }

            if (dao.updateAttendance(id, total, attended)) {
                System.out.println("Attendance Updated Successfully!");
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter valid whole numbers.");
        } finally {
            sc.close();
        }
    }
}
