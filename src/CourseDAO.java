
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CourseDAO {

    public boolean addCourse(String code, String title,
                             int credits, String type) {

        String sql = "INSERT INTO courses "
                   + "(course_code, title, credits, course_type) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, code);
            ps.setString(2, title);
            ps.setInt(3, credits);
            ps.setString(4, type);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Unable to add course: "
                               + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        CourseDAO dao = new CourseDAO();

        System.out.print("Enter Course Code: ");
        String code = sc.nextLine().trim();

        System.out.print("Enter Course Title: ");
        String title = sc.nextLine().trim();

        System.out.print("Enter Credits: ");
        int credits;

        try {
            credits = Integer.parseInt(sc.nextLine().trim());

            System.out.print(
                "Enter Course Type (THEORY/PRACTICAL/EMBEDDED): ");
            String type = sc.nextLine().trim().toUpperCase();

            if (code.isEmpty() || title.isEmpty()
                    || credits < 1 || credits > 10
                    || !(type.equals("THEORY")
                    || type.equals("PRACTICAL")
                    || type.equals("EMBEDDED"))) {

                System.out.println("Invalid course details.");
                return;
            }

            if (dao.addCourse(code, title, credits, type)) {
                System.out.println("Course Added Successfully!");
            }

        } catch (NumberFormatException e) {
            System.out.println("Credits must be a number.");
        } finally {
            sc.close();
        }
    }
}
