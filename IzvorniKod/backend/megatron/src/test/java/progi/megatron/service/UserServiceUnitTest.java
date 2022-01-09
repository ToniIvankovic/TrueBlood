package progi.megatron.service;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import progi.megatron.exception.WrongUserException;
import progi.megatron.model.User;
import progi.megatron.repository.UserRepository;
import progi.megatron.util.Role;


import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.xml.bind.ValidationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@EnableAutoConfiguration(exclude={FlywayAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@SpringBootTest(classes ={UserService.class})
public class UserServiceUnitTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    //tests UserService.findById()
    @Test
    void testFindById(){
        User user = new User(Role.DONOR, "59263487");
        Mockito.when(userRepository.getNotDeactivatedUserByUserId(Mockito.any())).thenReturn(java.util.Optional.of(user));
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);
        User retUser = userService.findNotDeactivatedUserById("12355");

        assertEquals(retUser, user);
    }

    //tests UserService.findById()
    @Test
    void testFindByIdForNonExistingUser(){

        Mockito.when(userRepository.getNotDeactivatedUserByUserId(Mockito.any())).thenReturn(Optional.empty());
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);

        UsernameNotFoundException ex = assertThrows(
                UsernameNotFoundException.class,
                () -> userService.findNotDeactivatedUserById("12355"),
                "Expected findById to throw, but it didnt");

        assertEquals(ex.getMessage(), "No user '12355'");
    }

    //tests UserService.findById() and UserService.isValidUserId
    @Test
    void testFindByIdForNonNumericId(){

        Mockito.when(userRepository.getNotDeactivatedUserByUserId(Mockito.any())).thenReturn(Optional.empty());
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);

        WrongUserException ex = assertThrows(
                WrongUserException.class,
                () -> userService.findNotDeactivatedUserById("abcd"),
                "Expected findNotDeactivatedUserById to throw, but it didnt");

        assertEquals(ex.getMessage(), "User id is not numeric. ");
    }

    //tests UserService.randomPassword()
    @Test
    void testRandomPassword(){
        String randomPass = userService.randomPassword();
        assertEquals(randomPass.length(), 8);
    }




}
