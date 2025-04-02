package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    /**
     * Create Message
     * 
     * @param message
     * @return message that was created
     */
    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            ps.executeUpdate();
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int generatedMessageId = pkeyResultSet.getInt(1);
                return new Message(generatedMessageId, message.getPosted_by(), message.getMessage_text(),
                        message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieve All Messages
     * 
     * @return all messages in database
     */
    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message message = (new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /**
     * Delete Message by Message Id
     * 
     * @param message
     * @return message that was deleted
     */
    public Message deleteMessageById(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message.getMessage_id());
            ps.executeUpdate();
            return message;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieve All Messages For User
     * 
     * @param message
     * @return all messages that matches user id
     */
    public List<Message> getAllMessagesByUserId(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message.getPosted_by());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message newMessage = (new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")));
                messages.add(newMessage);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /**
     * Retrives message based on given message id
     * 
     * @param id
     * @return
     */
    public Message getMessageByMessageId(int messageId) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, messageId);
            ResultSet rs = ps.executeQuery();

            // Create new message object to return
            if (rs.next()) {
                return new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Update Message Text
     * 
     * @param message
     * @return message that was updated given message id
     */
    public Message updateMessageText(int id, Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, message.getMessage_text());
            ps.setInt(2, id);
            ps.executeUpdate();
            Message updatedMessage = this.getMessageByMessageId(id);
            return new Message(updatedMessage.getMessage_id(), updatedMessage.getPosted_by(), updatedMessage.getMessage_text(), updatedMessage.getTime_posted_epoch());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
