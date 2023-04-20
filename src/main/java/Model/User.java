package Model;


public class User {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String slogan;
    private String passwordRecoveryQuestion;
    private String passwordRecoveryAnswer;
    private Empire empire;

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
    }

    public Empire getEmpire() {
        return empire;
    }

    public void setEmpire(Empire empire) {
        this.empire = empire;
    }
}
