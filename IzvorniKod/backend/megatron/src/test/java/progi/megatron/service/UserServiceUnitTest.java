package progi.megatron.service;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import progi.megatron.exception.WrongUserException;
import progi.megatron.model.User;
import progi.megatron.repository.UserRepository;
import progi.megatron.util.Role;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@EnableAutoConfiguration(exclude={FlywayAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@SpringBootTest(classes ={UserService.class})

public class UserServiceUnitTest{

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ModelMapper modelMapper;
    @MockBean
    private SecureTokenService secureTokenService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;



    //tests UserService.findNotDeactivatedUserById()
    @Test
    void testFindNotDeactivatedUserById(){
        User user = new User(Role.DONOR, "59263487");
        Mockito.when(userRepository.getNotDeactivatedUserByUserId(Mockito.any())).thenReturn(java.util.Optional.of(user));
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);
        User retUser = userService.findNotDeactivatedUserById("12355");

        assertEquals(retUser, user);
    }

    //tests UserService.findNotDeactivatedUserById()
    @Test
    void testFindNotDeactivatedUserByIdForNonExistingUser(){

        Mockito.when(userRepository.getNotDeactivatedUserByUserId(Mockito.any())).thenReturn(Optional.empty());
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);

        UsernameNotFoundException ex = assertThrows(
                UsernameNotFoundException.class,
                () -> userService.findNotDeactivatedUserById("12355"),
                "Expected findById to throw, but it didnt");

        assertEquals("No user '12355'", ex.getMessage());
    }


    @Test
    void testFindNotDeactivatedUserByIdForNonNumericId(){

        Mockito.when(userRepository.getNotDeactivatedUserByUserId(Mockito.any())).thenReturn(Optional.empty());
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);

        WrongUserException ex = assertThrows(
                WrongUserException.class,
                () -> userService.findNotDeactivatedUserById("abcd"),
                "Expected findNotDeactivatedUserById to throw, but it didnt");

        assertEquals("User id is not numeric. ", ex.getMessage());
    }

    //tests UserService.randomPassword()
    @Test
    void testRandomPassword(){
        String randomPass = userService.randomPassword();
        assertEquals(randomPass.length(), 8);
    }




}
