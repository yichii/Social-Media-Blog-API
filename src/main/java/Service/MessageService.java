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
     * Uses the messageDAO to get all messages
     * @return all messages
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    // public Message createMessage(Message message) {

    //     if ((message.getMessage_text() != null) && (message.getMessage_text().length() < 255) && (message.getPosted_by() != 0))
    //     messageDAO.insertMessage(message);
    //     return message;
    // }
}
