package progi.megatron.util;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import progi.megatron.model.User;
import progi.megatron.security.JwtTokenUtil;
import progi.megatron.service.DonorService;
import progi.megatron.service.UserService;
import javax.servlet.http.HttpServletRequest;

@Component
public class CurrentUserUtil {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    public CurrentUserUtil(UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public boolean checkIfCurrentUser(HttpServletRequest request, String idInQuestion) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String token = header.split(" ")[1].trim();
        String userId = jwtTokenUtil.getUserId(token);
        return userId.equals(idInQuestion);
    }

    public String getCurrentUserRole(HttpServletRequest request) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String token = header.split(" ")[1].trim();
        String userId = jwtTokenUtil.getUserId(token);
        User user = userService.findById(userId);
        return user.getUserRole();
    }

}
