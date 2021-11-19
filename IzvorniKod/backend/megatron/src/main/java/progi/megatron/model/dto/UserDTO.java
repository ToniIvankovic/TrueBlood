package progi.megatron.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import progi.megatron.model.User;

@Getter
@Setter
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
        return new UserDTO(user.getUserId().toString(), user.getUserRole());
    }

}
