package View;

import Controller.TradeMenuController;
import View.Enums.Commands.TradeMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu extends Menu {
    private TradeMenuController tradeMenuController;

    public TradeMenu(){
        this.tradeMenuController = new TradeMenuController();
    }

    @Override
    public void run(Scanner scanner) {
        String command = null;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();
            if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.ACCEPT_TRADE)) != null)
                acceptTrade(matcher);
            else if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE_HISTORY)) != null)
                tradeHistory(matcher);
            else if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE_REQUEST)) != null)
                tradeRequest(matcher);
            else if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE_LIST)) != null)
                tradeList();
            else if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.BACK)) != null)
                return;
            else System.out.println("Invalid command!");
        }
    }

    private void tradeList() {
    }

    private void tradeRequest(Matcher matcher) {
    }

    private void tradeHistory(Matcher matcher) {
    }

    private void acceptTrade(Matcher matcher) {
    }
}
