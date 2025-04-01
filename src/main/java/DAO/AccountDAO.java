package DAO;
import Model.Account;
import Model.Message;
import Util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    /**
     * Register user
     * @param account
     * @return account that was created
     */
    public Account registerAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account(account_id, username, password) VALUES (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, account.getAccount_id());
            ps.setString(2, account.getUsername());
            ps.setString(3, account.getPassword());
            ps.executeUpdate();
            return account;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Login
}
