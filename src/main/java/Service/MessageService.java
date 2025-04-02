package Service;
import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;

    /**
     * No args constructor
     */
    public MessageService() {
        messageDAO = new MessageDAO();
    }

    /**
     * Constructor for when messageDAO is provided
     * @param messageDAO
     */
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }
    
    /**
     * Create message
     * @param message
     * @return
     */
    public Message addMessage(Message message) {
        if ((message.getMessage_text() != "") && (message.getMessage_text().length() < 255) && (message.getPosted_by() != 0)) {
            return messageDAO.insertMessage(message);
        }
        return null;
    }

    /**
     * Delete Message by Message Id
     * @param message
     * @return
     */
    public Message deleteMessage(Message message) {
        if (messageDAO.getAllMessagesByUserId(message) != null) {
            return messageDAO.deleteMessageById(message);
        }
        return null; 
    }

    /**
     * Retrive all messages for User
     * @param message
     * @return
     */
    public List<Message> getAllMessagesForUser(Message message) {
        return messageDAO.getAllMessagesByUserId(message);
    }
    
    /**
     * Uses the messageDAO to get all messages
     * @return all messages
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * Retrive message by message id
     * @param message
     * @return Object: either message or empty message
     */
    public Object getMessage(int message_id) {
        Message message = messageDAO.getMessageByMessageId(message_id);
        return (message != null) ? message : "";
    }

    /**
     * Update Message Text
     * @param newMessage
     * @param message
     * @return
     */
    public Message updateMessage(String newMessage, Message message) {
        if ((messageDAO.getMessageByMessageId(message.getMessage_id()) != null) && (newMessage != null) && newMessage.length() < 255) {
            return messageDAO.updateMessageText(newMessage, message);
        }
        return null;
    }

}
