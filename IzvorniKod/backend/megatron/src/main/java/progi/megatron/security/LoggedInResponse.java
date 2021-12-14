package progi.megatron.security;

public class LoggedInResponse {

    private Boolean loggedIn;

    public LoggedInResponse(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

}
