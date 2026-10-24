
import java.util.Scanner;

public class MainMenu {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice = -1;

        do {
            System.out.println("\n==================================");
            System.out.println(" KEC STUDENT ACADEMIC SYSTEM");
            System.out.println("==================================");
            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. Enroll Student");
            System.out.println("4. Add Assessment Marks");
            System.out.println("5. View Student Marks");
            System.out.println("6. Update Attendance");
            System.out.println("7. View Attendance Percentage");
            System.out.println("8. View Internal Marks");
            System.out.println("9. View Final Result");
            System.out.println("0. Exit");
            System.out.println("==================================");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            String className = null;

            switch (choice) {
                case 1: className = "StudentDAO"; break;
                case 2: className = "CourseDAO"; break;
                case 3: className = "EnrollmentDAO"; break;
                case 4: className = "AssessmentDAO"; break;
                case 5: className = "ViewMarksDAO"; break;
                case 6: className = "AttendanceDAO"; break;
                case 7: className = "AttendancePercentageDAO"; break;
                case 8: className = "InternalMarksDAO"; break;
                case 9: className = "FinalResultDAO"; break;
                case 0:
                    System.out.println("Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            if (className != null) {
                try {
                    String javaHome = System.getProperty("java.home");
                    String javaCommand = javaHome + java.io.File.separator
                            + "bin" + java.io.File.separator + "java";

                    String classPath = System.getProperty("java.class.path");

                    ProcessBuilder pb = new ProcessBuilder(
                            javaCommand, "-cp", classPath, className);

                    pb.inheritIO();
                    Process process = pb.start();
                    process.waitFor();

                } catch (Exception e) {
                    System.out.println("Unable to run program: "
                            + e.getMessage());
                }
            }

        } while (choice != 0);

        sc.close();
    }
}
