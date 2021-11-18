package progi.megatron.model.dto;

import lombok.Data;

@Data
public class AuthRequest {

    private String userId;
    private String password;

    public AuthRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
