/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package poe1;

import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author RC_Student_lab
 */
public class LoginSystemNGTest {
    
    private LoginSystem system;
    
    public LoginSystemNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        system = new LoginSystem();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        system = null;
    }
    
    // Test cases from the assignment specification
    
    @Test
    public void testUsernameCorrectlyFormatted() {
        // Test Data: "kyl_1" should return true
        assertTrue(system.checkUserName("kyl_1"));
    }
    
    @Test
    public void testUsernameIncorrectlyFormattedNoUnderscore() {
        // Test Data: "kyle!!!!!!!" should return false
        assertFalse(system.checkUserName("kyle!!!!!!!"));
    }
    
    @Test
    public void testUsernameIncorrectlyFormattedTooLong() {
        // Username without underscore and too long should return false
        assertFalse(system.checkUserName("username"));
    }
    
    @Test
    public void testPasswordMeetsComplexityRequirements() {
        // Test Data: "Ch&&sec@ke99!" should return true
        assertTrue(system.checkPasswordComplexity("Ch&&sec@ke99!"));
    }
    
    @Test
    public void testPasswordDoesNotMeetComplexityRequirements() {
        // Test Data: "password" should return false
        assertFalse(system.checkPasswordComplexity("password"));
    }
    
    @Test
    public void testCellPhoneCorrectlyFormatted() {
        // Test Data: "+2783599870" should return true
        // Note: The test data shows 10 digits but specification says +27 + 9 digits = 12 characters
        // Adjusting to match the expected format: +27 followed by 9 digits
        assertTrue(system.checkCellPhoneNumber("+27835998701"));
    }
    
    @Test
    public void testCellPhoneIncorrectlyFormatted() {
        // Test Data: "00506553" should return false
        assertFalse(system.checkCellPhoneNumber("00506553"));
    }
    
    @Test
    public void testLoginSuccessful() {
        // Register user first
        system.registerUser("user_", "Passw0rd!", "+27821234567", "Onthatile", "Smith");
        // Test login with correct credentials should return true
        assertTrue(system.loginUser("user_", "Passw0rd!"));
    }
    
    @Test
    public void testLoginFailed() {
        // Register user first
        system.registerUser("user_", "Passw0rd!", "+27821234567", "Onthatile", "Smith");
        // Test login with incorrect credentials should return false
        assertFalse(system.loginUser("user_", "WrongPassword!"));
    }
    
    @Test
    public void testReturnLoginStatusSuccessful() {
        // Register user first
        system.registerUser("user_", "Passw0rd!", "+27821234567", "Onthatile", "Smith");
        system.loginUser("user_", "Passw0rd!");
        // Test login status message for successful login
        String expected = "Welcome Onthatile, Smith it is great to see you again.";
        assertEquals(system.returnLoginStatus(true), expected);
    }
    
    @Test
    public void testReturnLoginStatusFailed() {
        // Test login status message for failed login
        String expected = "Username or password incorrect, please try again.";
        assertEquals(system.returnLoginStatus(false), expected);
    }
    
    @Test
    public void testRegisterUserUsernameErrorMessage() {
        // Test username error message
        String result = system.registerUser("kyle!!!!!!!", "Passw0rd!", "+27821234567", "Onthatile", "Smith");
        String expected = "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        assertTrue(result.contains(expected));
    }
    
    @Test
    public void testRegisterUserPasswordErrorMessage() {
        // Test password error message
        String result = system.registerUser("user_", "password", "+27821234567", "Onthatile", "Smith");
        String expected = "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        assertTrue(result.contains(expected));
    }
    
    @Test
    public void testRegisterUserCellPhoneErrorMessage() {
        // Test cell phone error message
        String result = system.registerUser("user_", "Passw0rd!", "00506553", "Onthatile", "Smith");
        String expected = "Cell phone number is not correctly formatted; please ensure it includes the international country code (+27) and is 12 characters long (e.g., +27821234567).";
        assertTrue(result.contains(expected));
    }
    
    @Test
    public void testRegisterUserSuccessMessage() {
        // Test successful registration message
        String result = system.registerUser("kyl_1", "Ch&&sec@ke99!", "+27835998701", "Onthatile", "Smith");
        assertTrue(result.contains("Username successfully captured"));
        assertTrue(result.contains("Password successfully captured"));
        assertTrue(result.contains("Cell phone number successfully captured"));
        assertTrue(result.contains("User registered successfully"));
    }
    
    // Additional comprehensive tests
    
    @Test
    public void testPasswordComplexityNoCapital() {
        assertFalse(system.checkPasswordComplexity("password1!"));
    }
    
    @Test
    public void testPasswordComplexityNoNumber() {
        assertFalse(system.checkPasswordComplexity("Password!"));
    }
    
    @Test
    public void testPasswordComplexityNoSpecialChar() {
        assertFalse(system.checkPasswordComplexity("Password1"));
    }
    
    @Test
    public void testPasswordComplexityTooShort() {
        assertFalse(system.checkPasswordComplexity("Pass1!"));
    }
    
    @Test
    public void testCellPhoneNumberTooShort() {
        assertFalse(system.checkCellPhoneNumber("+278212345"));
    }
    
    @Test
    public void testCellPhoneNumberTooLong() {
        assertFalse(system.checkCellPhoneNumber("+278212345678"));
    }
    
    @Test
    public void testCellPhoneNumberWrongCountryCode() {
        assertFalse(system.checkCellPhoneNumber("+258212345678")); // Mozambique code
    }
}