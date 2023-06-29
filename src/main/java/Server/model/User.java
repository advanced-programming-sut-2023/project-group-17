package Server.model;


import com.google.gson.Gson;

import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Comparable<User> {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String slogan;
    private String passwordRecoveryQuestion;
    private String passwordRecoveryAnswer;
    private int highScore;
    private Empire empire;
    private String avatarPath;
    private static transient final Gson gson = Global.gson;
    private LocalDateTime lastScoreChangedTime;
    private LocalDateTime lastOnlineTime;
    private String authToken;
    private ArrayList<String> friendReqs = new ArrayList<>();
    private ArrayList<String> friends = new ArrayList<>();
    private HashMap<String, String> friendReqsSent = new HashMap<>();

    public User(String username, String password, String nickname, String email, String slogan,
                String passwordRecoveryQuestion, String passwordRecoveryAnswer) {
        this.username = username;
        this.lastScoreChangedTime = LocalDateTime.now();
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
        this.passwordRecoveryQuestion = passwordRecoveryQuestion;
        this.passwordRecoveryAnswer = passwordRecoveryAnswer;
        this.highScore = 0;
    }

    @Override
    public int compareTo(User o) {
        Integer myScore = this.getHighScore();
        Integer otherScore = o.getHighScore();
        if (!otherScore.equals(myScore))
            return otherScore.compareTo(myScore);
        if (!lastScoreChangedTime.equals(o.lastScoreChangedTime))
            return this.lastScoreChangedTime.compareTo(o.lastScoreChangedTime);
        return this.getUsername().compareTo(o.getUsername());
    }

    public ArrayList<String> getFriendReqs() {
        if (friendReqs == null) friendReqs = new ArrayList<>();
        return friendReqs;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public static String SHA256Code(String value) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(value.getBytes());
            return bytesToHex(digest.digest());
        } catch(Exception exception){
            throw new RuntimeException(exception);
        }
    }
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes)
            result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }

//    public static void deleteAccountOfLoggedInPlayer(User user) {
//        Database.getUsers().remove(user);
//    }

    public static User fromJson(String json) {
        return gson.fromJson(json, User.class);
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public LocalDateTime getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(LocalDateTime lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
        Database.saveUsers();
    }

    public String getOnlineTime() {
        if (this.lastOnlineTime != null)
            return this.lastOnlineTime.format(DateTimeFormatter.ofPattern("d MMM, uuuu HH:mm:ss"));
        else
            return "null";
    }

//    public static User getLoggedInUser() {
//        return loggedInUser;
//    }
//    public static void setLoggedInUser(User loggedInUser) {
//        User.loggedInUser = loggedInUser;
//    }


    public LocalDateTime getLastScoreChangedTime() {
        return lastScoreChangedTime;
    }

    public void setLastScoreChangedTime(LocalDateTime lastScoreChangedTime) {
        this.lastScoreChangedTime = lastScoreChangedTime;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public Integer getHighScore() {
        return highScore;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public String getPasswordRecoveryQuestion() {
        return passwordRecoveryQuestion;
    }

    public void setPasswordRecoveryQuestion(String passwordRecoveryQuestion) {
        this.passwordRecoveryQuestion = passwordRecoveryQuestion;
    }

    public String getPasswordRecoveryAnswer() {
        return passwordRecoveryAnswer;
    }

    public void setPasswordRecoveryAnswer(String passwordRecoveryAnswer) {
        this.passwordRecoveryAnswer = passwordRecoveryAnswer;
        this.avatarPath = randomPathGenerator();
    }

    public Empire getEmpire() {
        return empire;
    }

    public void setEmpire(Empire empire) {
        this.empire = empire;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String randomPathGenerator() {
        int x = (int) (Math.random() * 4 + 1);
        return User.class.getResource("/assets/avatars/" + x + ".png").toString();
    }

    public HashMap<String, String> getFriendReqsSent() {
        if (friendReqsSent == null) friendReqsSent = new HashMap<>();
        return friendReqsSent;
    }

    public void setFriendReqsSent(HashMap<String, String> friendReqsSent) {
        this.friendReqsSent = friendReqsSent;
    }
}
