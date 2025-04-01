package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class MessageDAO {
    // Create Message
    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message(message_id, posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message.getMessage_id());
            ps.setInt(2, message.getPosted_by());
            ps.setString(3, message.getMessage_text());
            ps.setLong(4, message.getTime_posted_epoch());
            
            ps.executeUpdate();
            return message;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }

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
                    rs.getLong("time_posted_epoch")
                    ));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    // Delete Message by Message Id
    // Retrieve All Messages For User
    // Retrieve All Messages
    // Retrieve Message by Message Id
    // Update Message Text
    // User login
    // User Registration(insert account)
}
