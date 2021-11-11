package progi.megatron.model.dto;

import progi.megatron.model.User;

public class UserDTO {

    private String id;
    private String role;

    public UserDTO(String id, String role) {
        this.id = id;
        this.role = role;
    }

    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user.getUserId().toString(), user.getPassword());
    }

}
