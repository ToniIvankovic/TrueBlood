package progi.megatron.model;

public class UserInfo {
    private String id;
    private String role;

    public UserInfo(String id, String role) {
        this.id = id;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
