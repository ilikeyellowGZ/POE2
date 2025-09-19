package poe1;
import java.util.regex.Pattern;

/**
 *
 * @author RC_Student_lab
 */
public class LoginSystem {
    private String registeredUsername;
    private String registeredPassword;
    private String registeredCellPhone;
    private String firstName;
    private String lastName;
    
    // Check if username meets requirements
    public Boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }
    
    // Check if password meets complexity requirements
    public Boolean checkPasswordComplexity(String password) {
        boolean hasUpperCase = !password.equals(password.toLowerCase());
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecialChar = !password.matches("[A-Za-z0-9 ]*");
        
        return password.length() >= 8 && hasUpperCase && hasNumber && hasSpecialChar;
    }

    public Boolean checkCellPhoneNumber(String cellPhoneNumber) {
        String regex = "^\\+27\\d{9}$";
        return Pattern.matches(regex, cellPhoneNumber);
    }
    
    // Register a new user
    public String registerUser(String username, String password, String cellPhoneNumber, 
                              String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        
        if (!checkUserName(username)) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        
        // FIX: Added cell phone number validation
        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            return "Cell phone number is not correctly formatted; please ensure it includes the international country code (+27) and is 12 characters long (e.g., +27821234567).";
        }
        
        this.registeredUsername = username;
        this.registeredPassword = password;
        this.registeredCellPhone = cellPhoneNumber;
        
        return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully captured.\nUser registered successfully!";
    }
    
    // Login user
    public Boolean loginUser(String username, String password) {
        return username.equals(registeredUsername) && password.equals(registeredPassword);
    }
    
    // Return login status message
    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}