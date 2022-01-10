package progi.megatron.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import progi.megatron.exception.WrongDonorException;
import progi.megatron.model.Donor;
import progi.megatron.repository.DonorRepository;

import java.time.LocalDate;
import java.util.Optional;

import progi.megatron.repository.SecureTokenRepository;
import progi.megatron.util.Role;
import progi.megatron.validation.DonorValidator;
import progi.megatron.validation.IdValidator;
import progi.megatron.validation.OibValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@EnableAutoConfiguration(exclude={FlywayAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@SpringBootTest(classes ={DonorService.class, UserService.class})
public class DonorServiceUnitTest {
    @MockBean
    private DonorRepository donorRepository;
    @MockBean
    private  DonorValidator donorValidator;
    @MockBean
    private  IdValidator idValidator;
    @MockBean
    private  OibValidator oibValidator;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private ModelMapper modelMapper;
    @MockBean
    private SecureTokenRepository secureTokenRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private EmailService emailService;
    @MockBean
    private SecureTokenService secureTokenService;
    @Autowired
    private DonorService donorService;






    @Test
    void testUpdateDonorByBankWorker(){
        Donor donor = new Donor(1234L, "Josip", "Josipovic", "12345678", "M", LocalDate.of(2000, 9, 23), "Zagreb", "Ilica 2", "Fer", "0911234123", "0991234123", "josip@email.io", "A-", null);
        Donor newDonor = new Donor(1234L, "Josip", "Horvat", "12345678", "M", LocalDate.of(2000, 9, 23), "Zagreb", "Ilica 2", "Fer", "0911234123", "0991234123", "josip@email.io", "A-", null);

        Mockito.when(donorRepository.getNotDeactivatedDonorByDonorId(Mockito.any())).thenReturn(donor);
        Mockito.when(donorRepository.save(Mockito.any())).thenReturn(Optional.empty());

        Donor retDonor = donorService.updateDonorByBankWorker(newDonor);

        assertEquals(retDonor, donor);
    }

    @Test
    void testUpdateDonorByBankWorkerNoId(){
        Donor donor = new Donor(null, "Josip", "Josipovic", "12345678", "M", LocalDate.of(2000, 9, 23), "Zagreb", "Ilica 2", "Fer", "0911234123", "0991234123", "josip@email.io", "A-", null);

        WrongDonorException ex = assertThrows(
            WrongDonorException.class,
            () -> donorService.updateDonorByBankWorker(donor),
            "Expected findNotDeactivatedUserById to throw, but it didnt");

    assertEquals(ex.getMessage(), "Donor id is not given. ");
}

    @Test
    void testUpdateDonorByBankWorkerNoUser(){
        Donor donor = new Donor(1234L, "Josip", "Josipovic", "12345678", "M", LocalDate.of(2000, 9, 23), "Zagreb", "Ilica 2", "Fer", "0911234123", "0991234123", "josip@email.io", "A-", null);
        Mockito.when(donorRepository.getNotDeactivatedDonorByDonorId(Mockito.any())).thenReturn(null);

        WrongDonorException ex = assertThrows(
                WrongDonorException.class,
                () -> donorService.updateDonorByBankWorker(donor),
                "Expected findNotDeactivatedUserById to throw, but it didnt");

        assertEquals(ex.getMessage(), "There is no donor with that id.");
    }


}
