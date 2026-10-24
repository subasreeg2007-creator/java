
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AssessmentDAO {

    public boolean addMarks(int enrollmentId,
                            double cia1,
                            double cia2,
                            double assignment,
                            double quiz,
                            double lab,
                            double ese,
                            boolean eseAbsent) {

        String sql = "INSERT INTO assessment_marks "
                + "(enrollment_id, cia1, cia2, "
                + "assignment_marks, quiz_marks, "
                + "lab_marks, ese_marks, ese_absent) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, enrollmentId);
            ps.setDouble(2, cia1);
            ps.setDouble(3, cia2);
            ps.setDouble(4, assignment);
            ps.setDouble(5, quiz);
            ps.setDouble(6, lab);

            if (eseAbsent) {
                ps.setNull(7, java.sql.Types.DECIMAL);
            } else {
                ps.setDouble(7, ese);
            }

            ps.setBoolean(8, eseAbsent);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Unable to add marks: "
                    + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AssessmentDAO dao = new AssessmentDAO();

        try {
            System.out.print("Enter Enrollment ID: ");
            int id = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Enter CIA 1 Marks (0-100): ");
            double cia1 = Double.parseDouble(sc.nextLine().trim());

            System.out.print("Enter CIA 2 Marks (0-100): ");
            double cia2 = Double.parseDouble(sc.nextLine().trim());

            System.out.print("Enter Assignment Marks (0-100): ");
            double assignment =
                    Double.parseDouble(sc.nextLine().trim());

            System.out.print("Enter Quiz Marks (0-100): ");
            double quiz = Double.parseDouble(sc.nextLine().trim());

            System.out.print("Enter Lab Marks (0-100): ");
            double lab = Double.parseDouble(sc.nextLine().trim());

            System.out.print("Was the student absent for ESE? (yes/no): ");
            boolean absent =
                    sc.nextLine().trim().equalsIgnoreCase("yes");

            double ese = 0;

            if (!absent) {
                System.out.print("Enter ESE Marks (0-100): ");
                ese = Double.parseDouble(sc.nextLine().trim());
            }

            double[] marks = {cia1, cia2, assignment, quiz, lab};
            boolean valid = id > 0;

            for (double mark : marks) {
                if (mark < 0 || mark > 100) {
                    valid = false;
                }
            }

            if (!absent && (ese < 0 || ese > 100)) {
                valid = false;
            }

            if (!valid) {
                System.out.println(
                        "Invalid input. Marks must be between 0 and 100.");
                return;
            }

            boolean saved = dao.addMarks(
                    id, cia1, cia2, assignment, quiz, lab, ese, absent);

            if (saved) {
                System.out.println(
                        "Assessment Marks Saved Successfully!");
            } else {
                System.out.println("Marks were not saved.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numeric values.");
        } finally {
            sc.close();
        }
    }
}
