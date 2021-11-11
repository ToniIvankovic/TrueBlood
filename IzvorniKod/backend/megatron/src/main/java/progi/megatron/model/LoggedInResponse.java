package progi.megatron.model;

public class LoggedInResponse {
    private Boolean loggedIn;

    public LoggedInResponse(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}
