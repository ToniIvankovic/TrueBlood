package progi.megatron.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import progi.megatron.model.User;
import progi.megatron.service.UserService;

@Component
public class CurrentUserUtil {

    private final UserService userService;

    public CurrentUserUtil(UserService userService) {
        this.userService = userService;
    }

    public boolean checkIfCurrentUser(String idInQuestion) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return userId.equals(idInQuestion);
    }

    public String getCurrentUserRole() {
        String userId;
        try {
            userId = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch(NullPointerException ex) {
            System.out.println("Error retrieving authentication data.");
            return "";
        }
        User user = userService.findNotDeactivatedUserById(userId);
        return user.getUserRole();
    }

}
