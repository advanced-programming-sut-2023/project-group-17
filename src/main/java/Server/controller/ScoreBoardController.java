package Server.controller;

import Server.model.User;
import Server.model.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreBoardController {
    public String scoreBoard(Double scrollTimes) {
        ArrayList<User> users = new ArrayList<>();
        users = Database.getUsers();

        Comparator<User> comp = Comparator.comparingInt(Server.model.User::getHighScore).reversed();
        users.sort(comp);
        String path = "";

        int j = 0;
        if (users.size() < scrollTimes * 10 + 10) j = users.size();
        else j = (int) (scrollTimes * 10 + 10);

        for (int i = (int) (scrollTimes * 10); i < j; i++) {
            path += (i + 1) + "- " + users.get(i).getUsername() + " score:" + users.get(i).getHighScore() +
                    " last online: ";
            if (users.get(i).getLastOnlineTime() == null) path += "online" + "\n";
            else path += users.get(i).getOnlineTime() + "\n";
        }
        return path;
    }

    public ArrayList<String> getUsernames(Double scrollTimes) {
        ArrayList<User> users = new ArrayList<>();
        users = Database.getUsers();

        Comparator<User> comp = Comparator.comparingInt(Server.model.User::getHighScore).reversed();
        users.sort(comp);
        ArrayList<String> usernames = new ArrayList<>();

        int j = 0;
        if (users.size() < scrollTimes * 10 + 10) j = users.size();
        else j = (int) (scrollTimes * 10 + 10);

        for (int i = (int) (scrollTimes * 10); i < j; i++) {
            usernames.add(users.get(i).getUsername());
        }
        return usernames;
    }

    public ArrayList<Integer> getScores(Double scrollTimes) {
        ArrayList<User> users = new ArrayList<>();
        users = Database.getUsers();

        Comparator<User> comp = Comparator.comparingInt(Server.model.User::getHighScore).reversed();
        users.sort(comp);
        ArrayList<Integer> scores = new ArrayList<>();

        int j = 0;
        if (users.size() < scrollTimes * 10 + 10) j = users.size();
        else j = (int) (scrollTimes * 10 + 10);

        for (int i = (int) (scrollTimes * 10); i < j; i++) {
            scores.add(users.get(i).getHighScore());
        }
        return scores;
    }

    public ArrayList<String> getOnlineTimes(Double scrollTimes) {
        ArrayList<User> users = new ArrayList<>();
        users = Database.getUsers();

        Comparator<User> comp = Comparator.comparingInt(Server.model.User::getHighScore).reversed();
        users.sort(comp);
        ArrayList<String> times = new ArrayList<>();

        int j = 0;
        if (users.size() < scrollTimes * 10 + 10) j = users.size();
        else j = (int) (scrollTimes * 10 + 10);

        for (int i = (int) (scrollTimes * 10); i < j; i++) {
            times.add(users.get(i).getOnlineTime());
        }
        return times;
    }

    public ArrayList<String> getAvatarsPaths(Double scrollTimes) {
        ArrayList<User> users = new ArrayList<>();
        users = Database.getUsers();

        Comparator<User> comp = Comparator.comparingInt(Server.model.User::getHighScore).reversed();
        users.sort(comp);
        ArrayList<String> paths = new ArrayList<>();

        int j = 0;
        if (users.size() < scrollTimes * 10 + 10) j = users.size();
        else j = (int) (scrollTimes * 10 + 10);

        for (int i = (int) (scrollTimes * 10); i < j; i++) {
            paths.add(users.get(i).getAvatarPath());
        }
        return paths;
    }
}
