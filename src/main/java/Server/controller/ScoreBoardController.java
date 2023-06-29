package Server.controller;

import Server.model.User;
import Server.model.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreBoardController {
    public String scoreBoard(Double scrollTimes) {
        List<User> users = new ArrayList<>();
        users = (ArrayList) Database.getUsers();

        Comparator<User> comp = Comparator.comparingInt(Server.model.User::getHighScore).reversed();
        Collections.sort(users , comp);
        String path = "";

        int j = 0;
        if (users.size() < scrollTimes * 10 + 10) j = users.size();
        else j = (int) (scrollTimes * 10 + 10);

        for (int i = (int) (scrollTimes * 10); i < j; i++) {
            path += (i + 1) + "- " + users.get(i).getUsername() + " " + users.get(i).getHighScore() + "\n";
        }
        return path;
    }
}
