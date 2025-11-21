/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poe1;

import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * Main application class for QuickChat messaging system
 * Now includes Part 3 features for array management and reporting
 * 
 * @author RC_Student_lab
 */
public class POE1 {
    private static LoginSystem loginSystem = new LoginSystem();
    private static MessageSystem messageSystem = new MessageSystem();
    private static MessageManager messageManager = new MessageManager();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        displayWelcomeMessage();
        
        boolean exitProgram = false;
        
        while (!exitProgram) {
            if (!loginSystem.isUserLoggedIn()) {
                handleAuthentication();
            } else {
                exitProgram = handleChatApplication();
            }
        }
        
        scanner.close();
        JOptionPane.showMessageDialog(null, "Thank you for using QuickChat!");
    }
    
    /**
     * Displays welcome message
     */
    private static void displayWelcomeMessage() {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
        System.out.println("Welcome to QuickChat!");
        System.out.println("====================");
    }
    
    /**
     * Handles user authentication (registration and login)
     */
    private static void handleAuthentication() {
        boolean authenticated = false;
        
        while (!authenticated) {
            String choice = JOptionPane.showInputDialog(null,
                "--- Authentication ---\n" +
                "1. Register\n" +
                "2. Login\n" +
                "3. Exit\n" +
                "Choose an option:");
            
            if (choice == null) {
                System.exit(0);
            }
            
            switch (choice) {
                case "1":
                    handleRegistration();
                    break;
                case "2":
                    authenticated = handleLogin();
                    break;
                case "3":
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please choose 1, 2, or 3.");
            }
        }
    }
    
    /**
     * Handles user registration with retry logic
     */
    private static void handleRegistration() {
        boolean registrationSuccessful = false;
        
        while (!registrationSuccessful) {
            String firstName = JOptionPane.showInputDialog("Enter first name:");
            if (firstName == null) return;
            
            String lastName = JOptionPane.showInputDialog("Enter last name:");
            if (lastName == null) return;
            
            String username = JOptionPane.showInputDialog("Enter username (must contain _ and be ≤5 chars):");
            if (username == null) return;
            
            String password = JOptionPane.showInputDialog("Enter password (min 8 chars, capital, number, special char):");
            if (password == null) return;
            
            String cellPhone = JOptionPane.showInputDialog("Enter SA cell number (format: +27821234567):");
            if (cellPhone == null) return;
            
            String result = loginSystem.registerUser(username, password, cellPhone, firstName, lastName);
            JOptionPane.showMessageDialog(null, result);
            
            if (result.contains("successfully")) {
                registrationSuccessful = true;
            } else {
                int retry = JOptionPane.showConfirmDialog(null, 
                    "Registration failed. Would you like to try again?", 
                    "Retry", JOptionPane.YES_NO_OPTION);
                if (retry != JOptionPane.YES_OPTION) {
                    break;
                }
            }
        }
    }
    
    /**
     * Handles user login with retry logic
     */
    private static boolean handleLogin() {
        int attempts = 3;
        
        while (attempts > 0) {
            String username = JOptionPane.showInputDialog("Enter username:");
            if (username == null) return false;
            
            String password = JOptionPane.showInputDialog("Enter password:");
            if (password == null) return false;
            
            boolean loginResult = loginSystem.loginUser(username, password);
            String loginStatus = loginSystem.returnLoginStatus(loginResult);
            JOptionPane.showMessageDialog(null, loginStatus);
            
            if (loginResult) {
                return true;
            } else {
                attempts--;
                if (attempts > 0) {
                    JOptionPane.showMessageDialog(null, 
                        "Login failed. " + attempts + " attempts remaining.");
                }
            }
        }
        
        JOptionPane.showMessageDialog(null, "Too many failed attempts. Please try again later.");
        return false;
    }
    
    /**
     * Handles main chat application after login
     */
    private static boolean handleChatApplication() {
        while (true) {
            String choice = JOptionPane.showInputDialog(null,
                "--- QuickChat Menu ---\n" +
                "1. Send Messages\n" +
                "2. Show recently sent messages\n" +
                "3. Message Manager (Part 3 Features)\n" +
                "4. Quit\n" +
                "Choose an option:");
            
            if (choice == null) {
                return true; // Exit program
            }
            
            switch (choice) {
                case "1":
                    handleSendMessages();
                    break;
                case "2":
                    JOptionPane.showMessageDialog(null, "Coming Soon.");
                    break;
                case "3":
                    handleMessageManager();
                    break;
                case "4":
                    loginSystem.logout();
                    return false; // Return to authentication
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please choose 1, 2, 3, or 4.");
            }
        }
    }
    
    /**
     * Handles sending multiple messages
     */
    private static void handleSendMessages() {
        String numInput = JOptionPane.showInputDialog("How many messages do you wish to enter?");
        if (numInput == null) return;
        
        try {
            int numMessages = Integer.parseInt(numInput);
            
            for (int i = 0; i < numMessages; i++) {
                JOptionPane.showMessageDialog(null, "Entering message " + (i + 1) + " of " + numMessages);
                
                String recipient = JOptionPane.showInputDialog("Enter recipient cell number (format: +27821234567):");
                if (recipient == null) continue;
                
                String messageText = JOptionPane.showInputDialog("Enter your message (max 250 characters):");
                if (messageText == null) continue;
                
                String result = messageSystem.processMessage(recipient, messageText, i + 1);
                JOptionPane.showMessageDialog(null, result);
            }
            
            // Display total messages sent
            int totalSent = messageSystem.returnTotalMessages();
            JOptionPane.showMessageDialog(null, "Total messages sent in this session: " + totalSent);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
        }
    }
    
    /**
     * Handles Part 3 Message Manager features
     */
    private static void handleMessageManager() {
        boolean inMessageManager = true;
        
        while (inMessageManager) {
            String choice = JOptionPane.showInputDialog(null,
                "--- Message Manager (Part 3) ---\n" +
                "1. Display Sent Messages Details\n" +
                "2. Display Longest Message\n" +
                "3. Search Message by ID\n" +
                "4. Search Messages by Recipient\n" +
                "5. Delete Message by Hash\n" +
                "6. Load Messages from JSON File\n" +
                "7. Display Full Message Report\n" +
                "8. Back to Main Menu\n" +
                "Choose an option:");
            
            if (choice == null) {
                inMessageManager = false;
                continue;
            }
            
            switch (choice) {
                case "1":
                    String sentDetails = messageManager.displaySentMessagesDetails();
                    JOptionPane.showMessageDialog(null, sentDetails);
                    break;
                    
                case "2":
                    String longestMessage = messageManager.displayLongestMessage();
                    JOptionPane.showMessageDialog(null, longestMessage);
                    break;
                    
                case "3":
                    String searchID = JOptionPane.showInputDialog("Enter Message ID to search:");
                    if (searchID != null && !searchID.trim().isEmpty()) {
                        String searchResult = messageManager.searchMessageByID(searchID);
                        JOptionPane.showMessageDialog(null, searchResult);
                    }
                    break;
                    
                case "4":
                    String recipient = JOptionPane.showInputDialog("Enter recipient number to search:");
                    if (recipient != null && !recipient.trim().isEmpty()) {
                        String recipientResult = messageManager.searchMessagesByRecipient(recipient);
                        JOptionPane.showMessageDialog(null, recipientResult);
                    }
                    break;
                    
                case "5":
                    String hash = JOptionPane.showInputDialog("Enter message hash to delete:");
                    if (hash != null && !hash.trim().isEmpty()) {
                        String deleteResult = messageManager.deleteMessageByHash(hash);
                        JOptionPane.showMessageDialog(null, deleteResult);
                    }
                    break;
                    
                case "6":
                    String jsonResult = messageManager.readJSONFileIntoArray();
                    JOptionPane.showMessageDialog(null, jsonResult);
                    break;
                    
                case "7":
                    String report = messageManager.displayMessageReport();
                    JOptionPane.showMessageDialog(null, report);
                    break;
                    
                case "8":
                    inMessageManager = false;
                    break;
                    
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please choose 1-8.");
            }
        }
    }
}