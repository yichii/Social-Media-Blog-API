package Controller;

import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in
     * the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * 
     * @return a Javalin app object which defines the behavior of the Javalin
     *         controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/messages", this::postMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.get("/messages/{message_id}", this::getMessageByMessageIdHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesForUserHandler);
        app.patch("/messages/{message_id}", this::updateMessageTextHandler);
        app.post("/register", this::userRegistrationHandler);
        app.post("/login", this::userLoginHandler);

        return app;
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if (addedMessage == null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(addedMessage));
            ctx.status(200);
        }
    }

    private void deleteMessageHandler(Context ctx) throws JsonProcessingException {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Object deletedMessage = messageService.deleteMessage(id);
        ctx.json(deletedMessage);
    }

    private void getMessageByMessageIdHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Object message = messageService.getMessage(messageId);
        ctx.json(message);
        ctx.status(200);
    }

    private void getAllMessagesHandler(Context ctx) {
        ctx.json(messageService.getAllMessages());
    }

    private void updateMessageTextHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessage(message_id, message);
        if (updatedMessage == null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(updatedMessage));
            ctx.status(200);
        }
    }

    private void getAllMessagesForUserHandler(Context ctx) {
        int userId = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(accountService.getAllMessagesForUser(userId));
        ctx.status(200);
    }

    private void userLoginHandler(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        String username = account.getUsername();
        String password = account.getPassword();
        Account retrievedAccount = accountService.loginAccount(username, password);
        if (retrievedAccount != null) { 
            ctx.json(retrievedAccount);
            ctx.status(200);
        } else {
            ctx.status(401);
        }
    }

    private void userRegistrationHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.registerAccount(account);
        if (addedAccount == null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(addedAccount));
            ctx.status(200);
        }
    }
}