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
     * @return newMessage if valid, null if not
     */
    public Message addMessage(Message message) {
        if ((message.getMessage_text() != "") && (message.getMessage_text().length() < 255) && (message.getPosted_by() != 0)) {
            return messageDAO.insertMessage(message);
        }
        return null;
    }

    /**
     * Delete Message by Message Id
     * @param message_id
     * @return message if exists, null if not
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
     * @param message_id
     * @return Object: either message if exists or empty message
     */
    public Object getMessage(int message_id) {
        Message message = messageDAO.getMessageByMessageId(message_id);
        return (message != null) ? message : "";
    }

    /**
     * Update Message Text
     * @param id
     * @param message
     * @return updatedMessage if valid, null if not
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
