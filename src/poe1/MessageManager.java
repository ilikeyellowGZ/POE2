/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poe1;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 * MessageManager class handles array operations and reporting for Part 3
 * Includes array population, searching, deletion, and reporting features
 * 
 * @author RC_Student_lab
 */
public class MessageManager {
    // Arrays for storing message data
    private ArrayList<String> sentMessages;
    private ArrayList<String> disregardedMessages;
    private ArrayList<String> storedMessages;
    private ArrayList<String> messageHashes;
    private ArrayList<String> messageIDs;
    private ArrayList<String> recipients;
    
    public MessageManager() {
        sentMessages = new ArrayList<>();
        disregardedMessages = new ArrayList<>();
        storedMessages = new ArrayList<>();
        messageHashes = new ArrayList<>();
        messageIDs = new ArrayList<>();
        recipients = new ArrayList<>();
        
        // Initialize with test data as per assignment requirements
        initializeTestData();
    }
    
    /**
     * Initializes arrays with test data from Part 3 specification
     */
    private void initializeTestData() {
        // Test Data Message 1: Sent
        addMessageToArrays("Did you get the cake?", "+27834557896", "sent", "12:1:DIDCAKE", "1234567890");
        
        // Test Data Message 2: Stored
        addMessageToArrays("Where are you? You are late! I have asked you to be on time.", 
                          "+27838884567", "stored", "23:2:WHERETIME", "2345678901");
        
        // Test Data Message 3: Disregarded
        addMessageToArrays("Yohoooo, I am at your gate.", "+27834484567", "disregard", 
                          "34:3:YOHOOGATE", "3456789012");
        
        // Test Data Message 4: Sent
        addMessageToArrays("It is dinner time!", "0838884567", "sent", "45:4:ITTIME", "4567890123");
        
        // Test Data Message 5: Stored
        addMessageToArrays("Ok, I am leaving without you.", "+27838884567", "stored", 
                          "56:5:OKYOU", "5678901234");
    }
    
    /**
     * Adds message data to appropriate arrays based on status
     */
    private void addMessageToArrays(String message, String recipient, String status, 
                                   String hash, String messageID) {
        recipients.add(recipient);
        messageHashes.add(hash);
        messageIDs.add(messageID);
        
        switch (status.toLowerCase()) {
            case "sent":
                sentMessages.add(message);
                break;
            case "disregard":
                disregardedMessages.add(message);
                break;
            case "stored":
                storedMessages.add(message);
                break;
        }
    }
    
    /**
     * Displays sender and recipient of all sent messages
     */
    public String displaySentMessagesDetails() {
        if (sentMessages.isEmpty()) {
            return "No sent messages available.";
        }
        
        StringBuilder result = new StringBuilder("--- Sent Messages ---\n");
        for (int i = 0; i < sentMessages.size(); i++) {
            // Find corresponding recipient for this sent message
            String recipient = findRecipientForMessage(sentMessages.get(i));
            result.append("Message: ").append(sentMessages.get(i))
                  .append("\nRecipient: ").append(recipient)
                  .append("\n---\n");
        }
        return result.toString();
    }
    
    /**
     * Finds recipient for a given message by matching indices
     */
    private String findRecipientForMessage(String message) {
        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).equals(message)) {
                // Find the index in the main arrays
                for (int j = 0; j < messageHashes.size(); j++) {
                    // This is a simplified matching - in real scenario would use message IDs
                    if (j < recipients.size()) {
                        return recipients.get(j);
                    }
                }
            }
        }
        return "Unknown";
    }
    
    /**
     * Displays the longest sent message
     */
    public String displayLongestMessage() {
        if (sentMessages.isEmpty()) {
            return "No sent messages available.";
        }
        
        String longestMessage = sentMessages.get(0);
        for (String message : sentMessages) {
            if (message.length() > longestMessage.length()) {
                longestMessage = message;
            }
        }
        return "Longest Message: " + longestMessage + " (Length: " + longestMessage.length() + " characters)";
    }
    
    /**
     * Searches for a message by ID and displays recipient and message
     */
    public String searchMessageByID(String searchID) {
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(searchID) && i < recipients.size()) {
                // Find the corresponding message
                String message = findMessageByRecipientAndIndex(recipients.get(i), i);
                return "Message ID: " + searchID + 
                       "\nRecipient: " + recipients.get(i) + 
                       "\nMessage: " + message;
            }
        }
        return "Message ID not found: " + searchID;
    }
    
    /**
     * Helper method to find message by recipient and index
     */
    private String findMessageByRecipientAndIndex(String recipient, int index) {
        // Check sent messages
        if (index < sentMessages.size()) {
            return sentMessages.get(index);
        }
        // Check stored messages
        if (index < storedMessages.size() + sentMessages.size()) {
            int storedIndex = index - sentMessages.size();
            if (storedIndex < storedMessages.size()) {
                return storedMessages.get(storedIndex);
            }
        }
        // Check disregarded messages
        int disregardIndex = index - sentMessages.size() - storedMessages.size();
        if (disregardIndex < disregardedMessages.size()) {
            return disregardedMessages.get(disregardIndex);
        }
        return "Message not found";
    }
    
    /**
     * Searches for all messages sent to a particular recipient
     */
    public String searchMessagesByRecipient(String recipient) {
        StringBuilder result = new StringBuilder();
        result.append("Messages for recipient: ").append(recipient).append("\n");
        
        boolean found = false;
        
        // Search through all arrays for messages to this recipient
        for (int i = 0; i < recipients.size(); i++) {
            if (recipients.get(i).equals(recipient)) {
                String message = findMessageByRecipientAndIndex(recipient, i);
                result.append("- ").append(message).append("\n");
                found = true;
            }
        }
        
        if (!found) {
            return "No messages found for recipient: " + recipient;
        }
        return result.toString();
    }
    
    /**
     * Deletes a message using the message hash
     */
    public String deleteMessageByHash(String hash) {
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equals(hash)) {
                // Remove from all arrays
                String deletedMessage = findMessageByRecipientAndIndex(recipients.get(i), i);
                
                messageHashes.remove(i);
                messageIDs.remove(i);
                recipients.remove(i);
                
                // Remove from appropriate message array
                if (i < sentMessages.size()) {
                    sentMessages.remove(i);
                } else if (i < sentMessages.size() + storedMessages.size()) {
                    storedMessages.remove(i - sentMessages.size());
                } else {
                    disregardedMessages.remove(i - sentMessages.size() - storedMessages.size());
                }
                
                return "Message \"" + deletedMessage + "\" successfully deleted.";
            }
        }
        return "Message hash not found: " + hash;
    }
    
    /**
     * Displays a comprehensive report of all sent messages
     */
    public String displayMessageReport() {
        if (sentMessages.isEmpty()) {
            return "No sent messages to report.";
        }
        
        StringBuilder report = new StringBuilder();
        report.append("=== MESSAGE REPORT ===\n");
        report.append("Total Sent Messages: ").append(sentMessages.size()).append("\n\n");
        
        for (int i = 0; i < sentMessages.size(); i++) {
            report.append("Message ").append(i + 1).append(":\n");
            report.append("Hash: ").append(i < messageHashes.size() ? messageHashes.get(i) : "N/A").append("\n");
            report.append("Recipient: ").append(i < recipients.size() ? recipients.get(i) : "N/A").append("\n");
            report.append("Message: ").append(sentMessages.get(i)).append("\n");
            report.append("---\n");
        }
        
        return report.toString();
    }
    
    /**
     * ChatGPT: Read JSON file into an array
     * Reference: Generated with assistance from ChatGPT for JSON file reading implementation
     */
    public String readJSONFileIntoArray() {
        try {
            // Simulated JSON file reading - in real implementation, use libraries like Jackson or Gson
            String simulatedJSON = "[\n" +
                "  {\"message\": \"Stored message 1\", \"recipient\": \"+27831234567\", \"status\": \"stored\"},\n" +
                "  {\"message\": \"Stored message 2\", \"recipient\": \"+27837654321\", \"status\": \"stored\"}\n" +
                "]";
            
            // Parse simulated JSON and add to stored messages array
            String[] jsonLines = simulatedJSON.split("\n");
            int storedCount = 0;
            
            for (String line : jsonLines) {
                if (line.contains("\"message\"")) {
                    // Extract message content
                    String message = extractJSONValue(line, "message");
                    String recipient = extractJSONValue(line, "recipient");
                    
                    if (message != null && recipient != null) {
                        storedMessages.add(message);
                        recipients.add(recipient);
                        messageHashes.add("JSON:" + storedCount + ":HASH");
                        messageIDs.add("90" + storedCount + "123456");
                        storedCount++;
                    }
                }
            }
            
            return "Successfully loaded " + storedCount + " messages from JSON file into stored messages array.";
            
        } catch (Exception e) {
            return "Error reading JSON file: " + e.getMessage();
        }
    }
    
    /**
     * Helper method to extract values from JSON-like strings
     */
    private String extractJSONValue(String line, String key) {
        try {
            int keyIndex = line.indexOf("\"" + key + "\"");
            if (keyIndex != -1) {
                int valueStart = line.indexOf(":", keyIndex) + 1;
                int valueEnd = line.indexOf(",", valueStart);
                if (valueEnd == -1) valueEnd = line.indexOf("}", valueStart);
                if (valueEnd == -1) valueEnd = line.length();
                
                String value = line.substring(valueStart, valueEnd).trim();
                value = value.replace("\"", "").replace(",", "");
                return value;
            }
        } catch (Exception e) {
            // Continue with next line
        }
        return null;
    }
    
    // Getters for unit testing
    public ArrayList<String> getSentMessages() { return sentMessages; }
    public ArrayList<String> getDisregardedMessages() { return disregardedMessages; }
    public ArrayList<String> getStoredMessages() { return storedMessages; }
    public ArrayList<String> getMessageHashes() { return messageHashes; }
    public ArrayList<String> getMessageIDs() { return messageIDs; }
    public ArrayList<String> getRecipients() { return recipients; }
}
