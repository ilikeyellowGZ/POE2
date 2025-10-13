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
 * Comprehensive tests for MessageSystem functionality
 */
public class MessageSystemNGTest {
    
    private MessageSystem messageSystem;
    
    @BeforeMethod
    public void setUpMethod() {
        messageSystem = new MessageSystem();
    }

    @AfterMethod
    public void tearDownMethod() {
        messageSystem = null;
    }
    
    // ===== MESSAGE LENGTH VALIDATION TESTS =====
    
    @Test
    public void testMessageLengthValid() {
        assertTrue(messageSystem.checkMessageLength("This is a valid message"));
    }
    
    @Test
    public void testMessageLengthInvalid() {
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 300; i++) longMessage.append("a");
        assertFalse(messageSystem.checkMessageLength(longMessage.toString()));
    }
    
    @Test
    public void testMessageLengthExactly250() {
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < 250; i++) message.append("a");
        assertTrue(messageSystem.checkMessageLength(message.toString()));
    }
    
    // ===== RECIPIENT VALIDATION TESTS =====
    
    @Test
    public void testRecipientCellValid() {
        assertTrue(messageSystem.checkRecipientCell("+27821234567"));
    }
    
    @Test
    public void testRecipientCellInvalid() {
        assertFalse(messageSystem.checkRecipientCell("0821234567"));
    }
    
    // ===== MESSAGE HASH CREATION TESTS =====
    
    @Test
    public void testCreateMessageHash() {
        String messageID = "1234567890";
        int messageNumber = 1;
        String messageText = "Hi Mike, can you join us for dinner tonight";
        
        String hash = messageSystem.createMessageHash(messageID, messageNumber, messageText);
        assertEquals(hash, "12:1:HITONIGHT");
    }
    
    @Test
    public void testCreateMessageHashSingleWord() {
        String messageID = "9876543210";
        int messageNumber = 2;
        String messageText = "Hello";
        
        String hash = messageSystem.createMessageHash(messageID, messageNumber, messageText);
        assertEquals(hash, "98:2:HELLOHELLO");
    }
    
    // ===== MESSAGE ID GENERATION TESTS =====
    
    @Test
    public void testGenerateMessageID() {
        String messageID = messageSystem.generateMessageID();
        assertNotNull(messageID);
        assertEquals(messageID.length(), 10);
        assertTrue(messageID.matches("\\d+"));
    }
    
    // ===== TOTAL MESSAGES TRACKING TESTS =====
    
    @Test
    public void testReturnTotalMessagesInitial() {
        assertEquals(messageSystem.returnTotalMessages(), 0);
    }
    
    // ===== PROCESS MESSAGE INTEGRATION TESTS =====
    
    @Test
    public void testProcessMessageValidLength() {
        String result = messageSystem.processMessage("+27821234567", 
            "Hi Mike, can you join us for dinner tonight", 1);
        assertTrue(result.contains("Message successfully") || result.contains("ready to send"));
    }
    
    @Test
    public void testProcessMessageInvalidLength() {
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 300; i++) longMessage.append("a");
        
        String result = messageSystem.processMessage("+27821234567", longMessage.toString(), 1);
        assertTrue(result.contains("exceeds 250 characters"));
    }
    
    @Test
    public void testProcessMessageInvalidRecipient() {
        String result = messageSystem.processMessage("0821234567", "Valid message", 1);
        assertTrue(result.contains("incorrectly formatted"));
    }
}