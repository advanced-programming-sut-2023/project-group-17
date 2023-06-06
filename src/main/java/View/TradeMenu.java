package View;

import Controller.TradeMenuController;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class TradeMenu{
    private TradeMenuController tradeMenuController;
    private BorderPane mainBorderPane = new BorderPane();
    private Pane tradeMenuPane;
    private Pane newTradePane;
    private Pane tradeHistoryPane;

    public TradeMenu(){
        this.tradeMenuController = new TradeMenuController();
//        this.mainBorderPane = mainBorderPane;
//        this.tradeMenuPane = tradeMenuPane;
    }
//    public TradeMenu() {
//
//    }
//    public TradeMenu(BorderPane mainBorderPane) {
//        this.tradeMenuController = new TradeMenuController();
//        this.mainBorderPane = mainBorderPane;
//    }



    public void start() throws Exception {
//        tradeMenuPane = FXMLLoader.load(getClass().getResource("/fxml/TradeMenu.fxml"));
//        newTradePane = FXMLLoader.load(getClass().getResource("/fxml/NewTrade.fxml"));
//        tradeHistoryPane = FXMLLoader.load(getClass().getResource("/fxml/TradeHistory.fxml"));

//        Background background = new Background(new BackgroundImage(new Image(LoginMenu.class.getResource(
//                "/assets/Backgrounds/TradeMenu.jpg").toExternalForm()),
//                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
//                new BackgroundSize(1.0, 1.0, true, true, false, false)));
//        newTradePane.setBackground(background);
//        tradeHistoryPane.setBackground(background);
//        tradeMenuPane.setBackground(background);

//        mainBorderPane.setRight(tradeMenuPane);
    }

    public void setMainBorderPane(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

//    public void openNewTrade(MouseEvent mouseEvent) {
//        for (Node child : mainBorderPane.getChildren()) {
//            if (child.equals(tradeMenuPane)) {
//                mainBorderPane.getChildren().remove(child);
//                break;
//            }
//        }
//        mainBorderPane.getChildren().add(newTradePane);
//    }
//
//    public void openTradeHistory(MouseEvent mouseEvent) {
//        for (Node child : mainBorderPane.getChildren()) {
//            if (child.equals(tradeMenuPane)) {
//                mainBorderPane.getChildren().remove(child);
//                break;
//            }
//        }
//        mainBorderPane.getChildren().add(tradeHistoryPane);
//    }
//
//    public void closeTradeMenu(MouseEvent mouseEvent) {
//        for (Node child : mainBorderPane.getChildren()) {
//            if (child.equals(tradeMenuPane)) {
//                mainBorderPane.getChildren().remove(child);
//                break;
//            }
//        }
//    }



//    @Override
//    public void run() {
//        System.out.println("entered trade menu successfully");
//        showRequestsNotifications();
//        showUsersInTheGame();
//        String command = null;
//        Matcher matcher;
//
//        while (true) {
//            command = scanner.nextLine();
//            if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.ACCEPT_TRADE)) != null)
//                acceptTrade(matcher);
//            else if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE_HISTORY)) != null)
//                tradeHistory(matcher);
//            else if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE_REQUEST)) != null)
//                tradeRequest(matcher);
//            else if (TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE_LIST) != null)
//                tradeList();
//            else if (TradeMenuCommands.getMatcher(command, TradeMenuCommands.BACK) != null) {
//                System.out.println("entered game menu successfully");
//                return;
//            }
//            else if((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.SHOW_CURRENT_MENU)) != null)
//                System.out.println("Trade menu");
//            else System.out.println("Invalid Command");
//        }
//    }
//
//    private void showRequestsNotifications() {
//        System.out.print("your new trade requests: " + "\n" + controller.showRequestsNotification());
//    }
//
//    private void showUsersInTheGame(){
//        System.out.print(controller.showUsersInTheGame());
//    }
//
//    private void tradeList() {
//        System.out.print(controller.tradeList());
//    }
//
//    private void tradeRequest(Matcher matcher) {
//        if(checkBlankField(matcher.group("resourceType")) || checkBlankField(matcher.group("resourceAmount")) ||
//        checkBlankField(matcher.group("price")) || checkBlankField(matcher.group("message"))) {
//            System.out.println("send trade request failed : blank field");
//            return;
//        }
//
//        String resourceTypeName = matcher.group("resourceType");
//        int resourceAmount = Integer.parseInt(matcher.group("resourceAmount"));
//        int price = Integer.parseInt(matcher.group("price"));
//        String message = handleDoubleQuote(matcher.group("message"));
//
//        switch (controller.tradeRequest(resourceTypeName , resourceAmount , price , message)) {
//            case INVALID_AMOUNT:
//                System.out.println("send trade request failed : invalid amount");
//                break;
//            case INVALID_PRICE:
//                System.out.println("send trade request failed : invalid price");
//                break;
//            case INSUFFICIENT_ITEM_AMOUNT:
//                System.out.println("send trade request failed : insufficient resource inventory");
//                break;
//            case INVALID_ITEM_NAME:
//                System.out.println("send trade request failed : invalid resource type");
//                break;
//            case USERNAME_DOES_NOT_EXIST:
//                System.out.println("send trade request failed : username does not exist");
//                break;
//            case SUCCESS:
//                System.out.println("trade request sent successfully");
//                break;
//        }
//    }
//
//    private void tradeHistory(Matcher matcher) {
//        System.out.print(controller.tradeHistory());
//    }
//
//    private void acceptTrade(Matcher matcher) {
//        if(checkBlankField(matcher.group("id")) || checkBlankField(matcher.group("message"))) {
//            System.out.println("trade accept failed : blank field");
//            return;
//        }
//
//        int id = Integer.parseInt(matcher.group("id"));
//        String message = handleDoubleQuote(matcher.group("message"));
//
//        switch (controller.acceptTrade(id , message)) {
//            case ID_DOES_NOT_EXISTS:
//                System.out.println("trade accept failed : id does not exist");
//                break;
//            case SUCCESS:
//                System.out.println("trade accepted successfully");
//                break;
//        }
//    }
}
