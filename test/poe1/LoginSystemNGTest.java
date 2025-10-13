/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package poe1;

import static org.testng.Assert.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Comprehensive tests for LoginSystem functionality
 */
public class LoginSystemNGTest {
    
    private LoginSystem system;
    
    @BeforeMethod
    public void setUpMethod() {
        system = new LoginSystem();
    }

    @AfterMethod
    public void tearDownMethod() {
        system = null;
    }
    
    // ===== USERNAME VALIDATION TESTS =====
    
    @Test
    public void testUsernameCorrectlyFormatted() {
        assertTrue(system.checkUserName("kyl_1"));
    }
    
    @Test
    public void testUsernameIncorrectlyFormattedNoUnderscore() {
        assertFalse(system.checkUserName("kyle!!!!!!!"));
    }
    
    @Test
    public void testUsernameIncorrectlyFormattedTooLong() {
        assertFalse(system.checkUserName("verylongusername"));
    }
    
    // ===== PASSWORD COMPLEXITY TESTS =====
    
    @Test
    public void testPasswordMeetsComplexityRequirements() {
        assertTrue(system.checkPasswordComplexity("Ch&&sec@ke99!"));
    }
    
    @Test
    public void testPasswordDoesNotMeetComplexityRequirements() {
        assertFalse(system.checkPasswordComplexity("password"));
    }
    
    @Test
    public void testPasswordNoCapital() {
        assertFalse(system.checkPasswordComplexity("password1!"));
    }
    
    @Test
    public void testPasswordNoNumber() {
        assertFalse(system.checkPasswordComplexity("Password!"));
    }
    
    @Test
    public void testPasswordNoSpecialChar() {
        assertFalse(system.checkPasswordComplexity("Password1"));
    }
    
    // ===== CELL PHONE VALIDATION TESTS =====
    
    @Test
    public void testCellPhoneCorrectlyFormatted() {
        assertTrue(system.checkCellPhoneNumber("+27835998701"));
    }
    
    @Test
    public void testCellPhoneIncorrectlyFormatted() {
        assertFalse(system.checkCellPhoneNumber("00506553"));
    }
    
    // ===== REGISTRATION TESTS =====
    
    @Test
    public void testRegisterUserSuccess() {
        String result = system.registerUser("user_", "Passw0rd!", "+27821234567", "John", "Doe");
        assertTrue(result.contains("successfully"));
    }
    
    @Test
    public void testRegisterUserInvalidUsername() {
        String result = system.registerUser("invalid", "Passw0rd!", "+27821234567", "John", "Doe");
        assertTrue(result.contains("Username is not correctly formatted"));
    }
    
    // ===== LOGIN TESTS =====
    
    @Test
    public void testLoginSuccessful() {
        system.registerUser("test_", "Passw0rd!", "+27821234567", "John", "Doe");
        assertTrue(system.loginUser("test_", "Passw0rd!"));
    }
    
    @Test
    public void testLoginFailed() {
        system.registerUser("test_", "Passw0rd!", "+27821234567", "John", "Doe");
        assertFalse(system.loginUser("test_", "WrongPassword!"));
    }
    
    @Test
    public void testReturnLoginStatusSuccessful() {
        system.registerUser("user_", "Passw0rd!", "+27821234567", "Onthatile", "Smith");
        system.loginUser("user_", "Passw0rd!");
        String expected = "Welcome Onthatile, Smith it is great to see you again.";
        assertEquals(system.returnLoginStatus(true), expected);
    }
}
