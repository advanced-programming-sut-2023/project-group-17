package Client.view;
import Client.ClientMain;
import Client.controller.Controller;
import Client.model.Chat;
import Client.model.ChatPayload;
import Client.model.Message;
import Client.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.util.*;

public class ChatController {

    @FXML
    private HBox commandBar;
    @FXML
    private Button sendButton;
    @FXML
    private TextField messageField;
    @FXML
    private VBox usersBar;
    @FXML
    private VBox mainSection;

    private VBox chatVBox;
    private ScrollPane scrollPane;
    private ArrayList<Chat> chats = new ArrayList<>();
    private Chat currentChat;
    private static boolean isInTheGame = false;
    private Timeline timeline;


    public void initialize() {

        timeline = new Timeline(
                new KeyFrame(Duration.millis(5000), event -> {
                    getChatsFromServer();
                    if (currentChat != null)
                        loadChats(currentChat.getName());
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        commandBar.getChildren().clear();
        Button deleteForMe = new Button("Delete for me");
        Button deleteForAll = new Button("Delete for all");
        Button edit = new Button("Edit");
        deleteForAll.setVisible(false);
        deleteForMe.setVisible(false);
        edit.setVisible(false);
        commandBar.getChildren().addAll(deleteForAll, edit);
        deleteForAll.setOnAction(event -> {
            for (Message message : currentChat.getAllMessages()) {
                if (message.isSelected()) {
                    if (!message.getSender().equals(Controller.send("get my user"))) {
                        errorMaker("Fault", "You can not delete message that not belong to you.", Alert.AlertType.ERROR);
                        return;
                    }
                }
            }

            Alert alert = new Alert(Alert.AlertType.NONE, "Delete selected Message(s)?", ButtonType.OK, ButtonType.CANCEL);
            alert.setTitle("Delete for all");
            alert.initOwner(ClientMain.stage);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.equals(ButtonType.OK)) {
                    for (Message message : currentChat.getAllMessages())
                        if (message.isSelected()) {
                            message.setContent("This message was deleted.");
                            updateSavedCurrentChat();
                            message.toggleSelected();
                        }
                    loadChats(currentChat.getName());
                }
                commandBarShowHide();
            });
        });

        deleteForMe.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.NONE, "Delete selected Message(s)?", ButtonType.OK, ButtonType.CANCEL);
            alert.setTitle("Delete for me");
            alert.initOwner(ClientMain.stage);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.equals(ButtonType.OK)) {
                    currentChat.getAllMessages().removeIf(Message::isSelected);
                    updateSavedCurrentChat();
                    loadChats(currentChat.getName());
                }
                commandBarShowHide();
            });
        });

        edit.setOnAction(event -> {
            Message selectedMessage = null;
            int counter = 0;
            for (Message message : currentChat.getAllMessages())
                if (message.isSelected()) {
                    selectedMessage = message;
                    counter++;
                    if (counter == 2)
                        break;
                }
            if (counter == 1) {
                if (!selectedMessage.getSender().equals(Controller.send("get my user"))) {
                    errorMaker("Fault", "You can not edit message that not belong to you.", Alert.AlertType.ERROR);
                    return;
                }
                messageField.setText(selectedMessage.getContent());
                sendButton.setText("Edit");
                Message finalSelectedMessage = selectedMessage;
                sendButton.setOnAction(event1 -> {
//                    editMessage(finalSelectedMessage);
                    finalSelectedMessage.setContent(messageField.getText());
                    finalSelectedMessage.setSelected(false);
                    updateSavedCurrentChat();
                    loadChats(currentChat.getName());
                    messageField.setText("");
                    sendButton.setText("Send");
                    sendButton.setOnAction(actionEvent -> sendMessage());
                });
            } else {
                Alert alert = new Alert(Alert.AlertType.NONE, "Cannot edit multiple messages at once.", ButtonType.OK);
                alert.initOwner(ClientMain.stage);
                alert.show();
            }
        });

        getChatsFromServer();

        showUsersBar();
    }

    private void editMessage(Message message) {
        message.setContent(messageField.getText());
        message.setSelected(false);
        updateSavedCurrentChat();
        loadChats(currentChat.getName());
        messageField.setText("");
        sendButton.setText("Send");
        sendButton.setOnAction(event1 -> sendMessage());
    }


    private void showUsersBar() {
        usersBar.getChildren().clear();
        for (Chat chat : chats) {
            Button button = new Button(chat.getName());
            button.setOnAction(event -> startChatting(chat.getName()));
            usersBar.getChildren().add(button);
        }
    }

    public void startChatting(String chatName) {
        timeline.stop();
        new Timeline(
                new KeyFrame(Duration.millis(5000), event -> {
                    getChatsFromServer();
                    if (currentChat != null)
                        loadChats(currentChat.getName());
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        mainSection.getChildren().clear();
        messageField.setDisable(false);
        sendButton.setDisable(false);
        mainSection.getChildren().clear();
        chatVBox = new VBox();
        chatVBox.setAlignment(Pos.BOTTOM_CENTER);
        chatVBox.setSpacing(15);
        chatVBox.setStyle("-fx-background-color: #0e1621");
        chatVBox.setFillWidth(true);
        scrollPane = new ScrollPane(chatVBox);
        scrollPane.setStyle("-fx-background: #0e1621; -fx-border-color: #0e1621; -fx-padding: 10");
        mainSection.getChildren().add(scrollPane);

        //unselect all
        for (Chat chat : chats)
            if (chat.getName().equals(chatName))
                for (Message message : chat.getAllMessages())
                    message.setSelected(false);

        loadChats(chatName);

        commandBarShowHide();
        sendButton.setText("Send");
        sendButton.setOnAction(event1 -> sendMessage());
        messageField.setText("");

        scrollPane.vvalueProperty().bind(chatVBox.heightProperty());
        messageField.requestFocus();
    }

    private void loadChats(String chatName) {
        chatVBox.getChildren().clear();
        for (Chat chat : chats) {
            if (chat.getName().equals(chatName)) {
                currentChat = chat;
                for (Message message : chat.getAllMessages()) {
                    showMessage(message);
                }
                break;
            }
        }
    }

    public void sendMessage() {
        String content = messageField.getText();
        if (content.equals("") || content.matches("^\\s+$"))
            return;
        Message message = new Message((String) Controller.send("get my user"), content);
        currentChat.addMessage(message);
        updateSavedCurrentChat();
        showMessage(message);
        messageField.setText("");
        scrollPane.vvalueProperty().bind(chatVBox.heightProperty());
    }

    private void getChatsFromServer() {
        System.out.println("getting chats from server");
        ChatPayload chatPayload = new ChatPayload("get all chats");
        String response = NetworkController.send(new Gson().toJson(chatPayload));
        chatPayload = new Gson().fromJson(response, ChatPayload.class);
        chats = chatPayload.getChats();

        for (Chat chat : chats) {
            System.out.print("chat name: " + chat.getName() + "  chat members: ");
            for (String user : chat.getUsers())
                System.out.print(user + ", ");
            System.out.println();
        }
    }

    //update chats on the server
    private void updateSavedCurrentChat() {
        ChatPayload payload = new ChatPayload("update chat", currentChat);
        NetworkController.send(new Gson().toJson(payload));
    }


    private void showMessage(Message message) {
        Calendar calendar = message.getCalendar();
        String date = calendar.getTime().toString();// + calendar.get(Calendar.DAY_OF_MONTH) + " " + new DateFormatSymbols().getShortMonths()[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
        date = date.substring(0, date.length() - 10);
        Text title = new Text(message.getSender());
        if (message.getSender().equals(Controller.send("get my user")))
            date += " Sent";
        Text msg = new Text(message.getContent() + "\n_________________\n" + date);

        Image image = AssetsController.getUserAvatarImages().get(0);
        for (int i = 0; i < UserIcon.getVALUES().size(); i++) {
            if (UserIcon.getVALUES().get(i) == message.getUser().getIcon() && message.getUser().getIcon() != UserIcon.CUSTOM)
                image = AssetsController.getUserAvatarImages().get(i);
        }

        ImageView avatar = new ImageView(image);
        avatar.setFitHeight(50);
        avatar.setFitWidth(50);

        Label senderName = getLabel(title, 30, message);
        Label messageLabel = getLabel(msg, 20, message);

        VBox totalMessage = new VBox(senderName, messageLabel);
        totalMessage.setOnMouseClicked(mouseEvent -> {
            message.toggleSelected();
            updateSavedCurrentChat();
            loadChats(currentChat.getName());
            commandBarShowHide();
        });
        chatVBox.getChildren().add(new HBox(avatar, totalMessage));
    }

    private void commandBarShowHide() {
        boolean weHaveSelectedMessages = false;
        for (Message message : currentChat.getAllMessages()) {
            if (message.isSelected()) {
                weHaveSelectedMessages = true;
                break;
            }
        }
        for (int i = 0; i < 3; i++) {
            try {
                commandBar.getChildren().get(i).setVisible(weHaveSelectedMessages);
            } catch (IndexOutOfBoundsException ignored) {
            }
        }
    }

    private Label getLabel(Text title, int fontSize, Message message) {
        Label label = new Label();
        label.setStyle("-fx-text-fill: white");
        if (message.isSelected())
            label.setBackground(new Background(new BackgroundFill(Color.rgb(46, 112, 164), null, null)));
        else
            label.setBackground(new Background(new BackgroundFill(Color.rgb(24, 37, 51), null, null)));
        label.setText(title.getText());
        label.setWrapText(true);
        label.setMaxWidth(800);
        label.setPadding(new Insets(10));
        label.setFont(new Font("Arial", fontSize));
        return label;
    }

    public void newChat() {
        messageField.setDisable(true);
        sendButton.setDisable(true);
        mainSection.setAlignment(Pos.CENTER);
        mainSection.getChildren().clear();
        Text text = new Text("Enter a username to start chatting:");
        text.setStyle("-fx-font-size: 30;-fx-fill: white");
        TextField field = new TextField();
        field.setMaxWidth(600);
        field.setPromptText("Enter a username");
        Text error = new Text();
        error.setStyle("-fx-font-size: 20;-fx-fill: white;");
        Button startChat = new Button("Start messaging");
        startChat.setOnAction(event1 -> startPrivateChat(field, error));
        Hyperlink link = new Hyperlink("You can create a chat room with multiple users.");
        link.setOnAction(event1 -> createRoom());
        mainSection.getChildren().addAll(text, field, error, startChat, link);

        field.setOnKeyReleased(event2 -> {
            error.setText("");
            if (event2.getCode().toString().equals("ENTER"))
                startPrivateChat(field, error);
        });

        field.requestFocus();
    }

    private void createRoom() {
        mainSection.getChildren().clear();
        Text title1 = new Text("Enter the room name:");
        title1.setStyle("-fx-font-size: 30;-fx-fill: white");

        TextField nameField = new TextField();
        nameField.setMaxWidth(600);
        nameField.setPromptText("Enter a name");

        Text title2 = new Text("Who would you like to add?");
        title2.setStyle("-fx-font-size: 30;-fx-fill: white");

        TextField userField = new TextField();
        userField.setMaxWidth(600);
        userField.setPromptText("Enter a username");

        Set<String> usersSet = new HashSet<>();
        Button add = new Button("Add user");
        Text users = new Text("Added users: ");
        users.setStyle("-fx-font-size: 15;-fx-fill: white;");

        Text error = new Text();
        error.setStyle("-fx-font-size: 20;-fx-fill: white;");

        ChatPayload payload = new ChatPayload("get all users");
        String response = NetworkController.send(new Gson().toJson(payload));
        ArrayList<String> usersList = new Gson().fromJson(response, new TypeToken<List<String>>() {
        }.getType());

        add.setOnAction(event -> {
            if (userField.getText().isEmpty())
                error.setText("Enter a username.");
            else {
                //username validation
                String addedUser = null;
                for (String user : usersList) {
                    if (user.equals(userField.getText())) {
                        addedUser = user;
                    }
                }
                if (addedUser != null) {
                    if (usersSet.add(addedUser))
                        users.setText(users.getText() + userField.getText() + ", ");
                    userField.setText("");
                } else {
                    error.setText("No User exists with this name.");
                }
            }
        });

        Button startChat = new Button("Create room");
        startChat.setOnAction(event1 -> startRoomChat(nameField, usersSet, error));
        mainSection.getChildren().addAll(title1, nameField, title2, userField, error, add, startChat, users);

        nameField.setOnKeyReleased(event2 -> {
            error.setText("");
            if (event2.getCode().toString().equals("ENTER"))
                startRoomChat(nameField, usersSet, error);
        });

        userField.setOnKeyReleased(event2 -> {
            error.setText("");
            if (event2.getCode().toString().equals("ENTER")) {//username validation
                String addedUser = null;
                for (String user : usersList) {
                    if (user.equals(userField.getText())) {
                        addedUser = user;
                    }
                }
                if (addedUser != null) {
                    if (usersSet.add(addedUser))
                        users.setText(users.getText() + userField.getText() + ", ");
                    userField.setText("");
                } else {
                    error.setText("No User exists with this name.");
                }
            }
        });

        nameField.requestFocus();
    }

    private void startRoomChat(TextField nameField, Set<String> usersSet, Text error) {
        if (nameField.getText().equals(""))
            error.setText("Enter a name for the room.");
        else if (usersSet.isEmpty())
            error.setText("Add at list one user to the room.");
        else {
            usersSet.add((String) Controller.send("get my user"));
            List<String> members = new ArrayList<>(usersSet);
            Chat chat = new Chat("room: " + nameField.getText(), members);
            chats.add(chat);
            showUsersBar();
            currentChat = chat;
            updateSavedCurrentChat();
            startChatting(chat.getName());
        }
    }

    private void startPrivateChat(TextField usernameField, Text error) {
        if (usernameField.getText().equals("")) {
            error.setText("Enter a username.");
            return;
        }
        ChatPayload payload = new ChatPayload("get all users");
        String response = NetworkController.send(new Gson().toJson(payload));
        ArrayList<User> usersList = new Gson().fromJson(response, new TypeToken<List<User>>() {
        }.getType());
        //username validation
        User addedUser = null;
        for (User user : usersList) {
            if (user.getUsername().equals(usernameField.getText())) {
                addedUser = user;
            }
        }
        if (addedUser == null) {
            error.setText("No User exists with this username.");
        } else {
            ArrayList<String> members = new ArrayList<>();
            members.add(addedUser.getUsername());
            members.add((String) Controller.send("get my user"));
            Chat chat = new Chat(addedUser.getUsername() + " and " + Controller.send("get my user"), members);
            chats.add(chat);
            showUsersBar();
            currentChat = chat;
            updateSavedCurrentChat();
            startChatting(chat.getName());
        }

    }


    public void back() {
//        if (isInGame)
//            Controller.send("change menu")
//            StageController.sceneChanger("diplomacy.fxml");
//        else {
//            timeline.stop();
//            ChatPayload payload = new ChatPayload("menu exit");
//            NetworkController.send(new Gson().toJson(payload));
//            Controller.send("change menu main")
//        }
//        isInGame = false;
        //TODO
    }

    public void checkEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER"))
            if (sendButton.getText().equals("Send"))
                sendMessage();
    }

    public static void setInGame(boolean inGame) {
        isInTheGame = inGame;
    }

    public static Alert errorMaker(String header, String content,Alert.AlertType type) {
        Alert errorAlert = new Alert(type);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.initOwner(ClientMain.stage);
        errorAlert.showAndWait();
        return errorAlert;
    }
}