package poe1;
import java.util.Scanner;

/**
 *
 * @author RC_Student_lab
 */
public class POE1 {
    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to Chat App!");
        System.out.println("====================");
        
        // Registration
        System.out.println("\n--- Registration ---");
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        
        System.out.print("Enter username (must contain _ and be ≤5 chars): ");
        String username = scanner.nextLine();
        
        System.out.print("Enter password (min 8 chars, with capital, number, special char): ");
        String password = scanner.nextLine();
        
        System.out.print("Enter South African cell phone number (format: +27821234567): ");
        String cellPhone = scanner.nextLine();
        
        String registrationResult = loginSystem.registerUser(username, password, cellPhone, firstName, lastName);
        System.out.println("\n" + registrationResult);
        
        // Only proceed to login if registration was successful
        if (registrationResult.contains("successfully")) {
            // Login
            System.out.println("\n--- Login ---");
            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();
            
            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();
            
            boolean loginResult = loginSystem.loginUser(loginUsername, loginPassword);
            String loginStatus = loginSystem.returnLoginStatus(loginResult);
            System.out.println(loginStatus);
        } else {
            System.out.println("Registration failed. Please fix the errors and try again.");
        }
        
        scanner.close();
    }
}