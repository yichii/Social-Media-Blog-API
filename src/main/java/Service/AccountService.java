package Service;

import java.util.List;
import DAO.AccountDAO;
import Model.Account;
import Model.Message;

public class AccountService {
    public AccountDAO accountDAO;

    /**
     * No args constructor
     */
    public AccountService() {
        accountDAO = new AccountDAO();
    }

    /**
     * Constructor for when messageDAO is provided
     * @param messageDAO
     */
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    /**
     * Retrive all messages for User
     * 
     * @param message
     * @return
     */
    public List<Message> getAllMessagesForUser(int id) {
        return accountDAO.getAllMessagesByUserId(id);
    }

    /**
     * Creates account for user
     * @param account
     * @return newAccount if account doesn't exist already, null if already exists
     */
    public Account registerAccount(Account account) {
        boolean isBlank = account.getUsername().isEmpty();
        boolean isCorrectPasswordLength = account.getPassword().length() > 4;
        Account existingAccount = accountDAO.loginAccount(account.getUsername(), account.getPassword());
        if (!isBlank && isCorrectPasswordLength && (existingAccount == null)) {
            return accountDAO.registerAccount(account);
        }
        return null;
    }

    /**
     * Sees if username and password combination exists in database
     * @param username
     * @param password
     * @return existingAccount if found, null if not found
     */
    public Account loginAccount(String username, String password) {
        Account existingAccount = accountDAO.loginAccount(username, password);
        return (existingAccount == null) ? null : existingAccount;
    }
}
