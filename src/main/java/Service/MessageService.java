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
    public Object deleteMessage(int message_id) {
        Message message = messageDAO.deleteMessageById(message_id);
        return (message != null) ? message : "";
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
    public Message updateMessage(int id, Message message) {
        Message existingMessage = messageDAO.getMessageByMessageId(id);
        boolean isEmpty = message.getMessage_text().isEmpty();
        boolean isOverCharLimit = message.getMessage_text().length() > 255;
        if ((existingMessage != null) && !isEmpty && !isOverCharLimit) {
            return messageDAO.updateMessageText(id, message);
        }
        return null;
    }

}
