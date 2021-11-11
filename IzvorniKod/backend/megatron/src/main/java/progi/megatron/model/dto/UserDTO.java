package progi.megatron.model.dto;

import org.springframework.stereotype.Component;
import progi.megatron.model.User;

@Component
public class UserDTO {

    private String id;
    private String role;

    public UserDTO() {}

    public UserDTO(String id, String role) {
        this.id = id;
        this.role = role;
    }

    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user.getUserId().toString(), user.getPassword());
    }

}
