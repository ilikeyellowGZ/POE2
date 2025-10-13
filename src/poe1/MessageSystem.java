/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poe1;

import java.util.Random;
import javax.swing.JOptionPane;

/**
 * MessageSystem class handles all messaging functionality
 * Includes message validation, ID generation, and hash creation
 * 
 * @author RC_Student_lab
 */
public class MessageSystem {
    private int totalMessagesSent = 0;
    private int messageCounter = 0;
    
    /**
     * Processes a complete message with validation
     */
    public String processMessage(String recipient, String messageText, int messageNumber) {
        // Validate message length
        if (!checkMessageLength(messageText)) {
            int excessChars = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excessChars + ", please reduce size.";
        }
        
        // Validate recipient number
        if (!checkRecipientCell(recipient)) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        
        // Generate message components
        String messageID = generateMessageID();
        String messageHash = createMessageHash(messageID, messageNumber, messageText);
        
        // Display message ready confirmation
        JOptionPane.showMessageDialog(null, 
            "Message ready to send.\n" +
            "Message Hash: " + messageHash + "\n" +
            "Message ID: " + messageID);
        
        // Present send options
        return presentSendOptions();
    }
    
    /**
     * Validates message length (max 250 characters)
     */
    public boolean checkMessageLength(String message) {
        return message.length() <= 250;
    }
    
    /**
     * Validates recipient cell number format
     * Reuses validation from LoginSystem
     */
    public boolean checkRecipientCell(String cellNumber) {
        String regex = "^\\+27\\d{9}$";
        return cellNumber.matches(regex);
    }
    
    /**
     * Generates random 10-digit message ID
     */
    public String generateMessageID() {
        Random random = new Random();
        long id = 1000000000L + (long)(random.nextDouble() * 9000000000L);
        return String.valueOf(id);
    }
    
    /**
     * Creates message hash in format: firstTwoDigits:messageNumber:firstLastWords
     */
    public String createMessageHash(String messageID, int messageNumber, String messageText) {
        String firstTwoDigits = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        
        return (firstTwoDigits + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }
    
    /**
     * Presents send options to user and handles their choice
     */
    private String presentSendOptions() {
        String[] options = {"Send Message", "Disregard Message", "Store Message to send later"};
        int choice = JOptionPane.showOptionDialog(null,
            "Choose an option for this message:",
            "Message Options",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        
        return handleSendChoice(choice);
    }
    
    /**
     * Handles user's message sending choice
     */
    private String handleSendChoice(int choice) {
        switch (choice) {
            case 0: // Send Message
                totalMessagesSent++;
                return "Message successfully sent.";
            case 1: // Disregard Message
                return "Press 0 to delete message.";
            case 2: // Store Message
                // ChatGPT Assisted JSON Storage Implementation
                // Reference: Generated with assistance from ChatGPT for JSON file handling
                storeMessageAsJSON();
                return "Message successfully stored.";
            default:
                return "Operation cancelled.";
        }
    }
    
    /**
     * Stores message as JSON (ChatGPT assisted)
     */
    private void storeMessageAsJSON() {
        // Simplified JSON storage implementation
        // In production, use libraries like Jackson or Gson
        String jsonData = "{\"message\": \"stored\", \"timestamp\": \"" + System.currentTimeMillis() + "\"}";
        System.out.println("JSON Data: " + jsonData);
        // Actual file writing would be implemented here
    }
    
    /**
     * Returns total number of messages sent
     */
    public int returnTotalMessages() {
        return totalMessagesSent;
    }
}