package progi.megatron.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import progi.megatron.model.User;

@Getter
@Setter
@Component
public class UserDTO {

    private String userId;
    private String userRole;

    public UserDTO() {}

    public UserDTO(String userId, String userRole) {
        this.userId = userId;
        this.userRole = userRole;
    }

}
