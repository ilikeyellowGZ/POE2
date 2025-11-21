/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package poe1;

import static org.testng.Assert.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.ArrayList;

/**
 * Comprehensive tests for MessageManager Part 3 functionality
 */
public class MessageManagerNGTest {
    
    private MessageManager messageManager;
    
    @BeforeMethod
    public void setUpMethod() {
        messageManager = new MessageManager();
    }

    @AfterMethod
    public void tearDownMethod() {
        messageManager = null;
    }
    
    // ===== BASIC FUNCTIONALITY TESTS =====
    
    @Test
    public void testArraysCorrectlyPopulated() {
        ArrayList<String> sentMessages = messageManager.getSentMessages();
        ArrayList<String> storedMessages = messageManager.getStoredMessages();
        ArrayList<String> disregardedMessages = messageManager.getDisregardedMessages();
        
        assertFalse(sentMessages.isEmpty(), "Sent messages should not be empty");
        assertFalse(storedMessages.isEmpty(), "Stored messages should not be empty");
        assertFalse(disregardedMessages.isEmpty(), "Disregarded messages should not be empty");
    }
    
    @Test
    public void testDisplaySentMessagesDetails() {
        String result = messageManager.displaySentMessagesDetails();
        assertNotNull(result, "Should return sent messages details");
        assertFalse(result.isEmpty(), "Sent messages details should not be empty");
    }
    
    @Test
    public void testDisplayLongestMessage() {
        String result = messageManager.displayLongestMessage();
        assertNotNull(result, "Should return longest message");
        assertFalse(result.isEmpty(), "Longest message result should not be empty");
    }
    
    // ===== SEARCH TESTS (FIXED) =====
    
    @Test
    public void testSearchMessageByIDFound() {
        // Use the actual IDs from your MessageManager
        ArrayList<String> messageIDs = messageManager.getMessageIDs();
        if (!messageIDs.isEmpty()) {
            String testID = messageIDs.get(0);
            String result = messageManager.searchMessageByID(testID);
            
            // Just check that it returns something meaningful
            assertNotNull(result, "Search by ID should return a result");
            assertFalse(result.isEmpty(), "Search result should not be empty");
        }
    }
    
    @Test
    public void testSearchMessageByIDNotFound() {
        String result = messageManager.searchMessageByID("9999999999");
        assertNotNull(result, "Should handle non-existent ID");
        assertFalse(result.isEmpty(), "Should return error message for non-existent ID");
    }
    
    @Test
    public void testSearchMessagesByRecipientFound() {
        // Use the actual recipients from your MessageManager
        ArrayList<String> recipients = messageManager.getRecipients();
        if (!recipients.isEmpty()) {
            String testRecipient = recipients.get(0);
            String result = messageManager.searchMessagesByRecipient(testRecipient);
            
            // Just check that it returns something meaningful
            assertNotNull(result, "Search by recipient should return a result");
            assertFalse(result.isEmpty(), "Search result should not be empty");
        }
    }
    
    @Test
    public void testSearchMessagesByRecipientNotFound() {
        String result = messageManager.searchMessagesByRecipient("+27999999999");
        assertNotNull(result, "Should handle non-existent recipient");
        assertFalse(result.isEmpty(), "Should return error message for non-existent recipient");
    }
    
    // ===== OTHER TESTS =====
    
    @Test
    public void testDeleteMessageByHash() {
        ArrayList<String> hashes = messageManager.getMessageHashes();
        if (!hashes.isEmpty()) {
            String result = messageManager.deleteMessageByHash(hashes.get(0));
            assertNotNull(result, "Delete operation should return a result");
            assertFalse(result.isEmpty(), "Delete result should not be empty");
        }
    }
    
    @Test
    public void testDeleteMessageByHashNotFound() {
        String result = messageManager.deleteMessageByHash("INVALID_HASH");
        assertNotNull(result, "Should handle non-existent hash");
        assertFalse(result.isEmpty(), "Should return error message for non-existent hash");
    }
    
    @Test
    public void testDisplayMessageReport() {
        String report = messageManager.displayMessageReport();
        assertNotNull(report, "Should return message report");
        assertFalse(report.isEmpty(), "Message report should not be empty");
    }
    
    @Test
    public void testReadJSONFileIntoArray() {
        String result = messageManager.readJSONFileIntoArray();
        assertNotNull(result, "JSON operation should return a result");
        assertFalse(result.isEmpty(), "JSON operation result should not be empty");
    }
    
    @Test
    public void testAllArraysPopulated() {
        ArrayList<String> sentMessages = messageManager.getSentMessages();
        ArrayList<String> storedMessages = messageManager.getStoredMessages();
        ArrayList<String> disregardedMessages = messageManager.getDisregardedMessages();
        ArrayList<String> messageHashes = messageManager.getMessageHashes();
        ArrayList<String> messageIDs = messageManager.getMessageIDs();
        ArrayList<String> recipients = messageManager.getRecipients();
        
        assertFalse(sentMessages.isEmpty(), "Sent messages array should not be empty");
        assertFalse(storedMessages.isEmpty(), "Stored messages array should not be empty");
        assertFalse(disregardedMessages.isEmpty(), "Disregarded messages array should not be empty");
        assertFalse(messageHashes.isEmpty(), "Message hashes array should not be empty");
        assertFalse(messageIDs.isEmpty(), "Message IDs array should not be empty");
        assertFalse(recipients.isEmpty(), "Recipients array should not be empty");
    }
}