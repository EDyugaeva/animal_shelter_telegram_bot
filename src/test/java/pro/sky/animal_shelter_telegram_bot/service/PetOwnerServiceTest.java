package pro.sky.animal_shelter_telegram_bot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.*;

import pro.sky.animal_shelter_telegram_bot.repository.PetOwnerRepository;
import static org.mockito.Mockito.when;




@ExtendWith(MockitoExtension.class)
public class PetOwnerServiceTest {

    @InjectMocks
    private PetOwnerService out;

    @MockBean
    private PetOwnerRepository petOwnerRepository;

    @Test
    void contextLoads() {
    }

    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void testSetPetOwnersPhoneNumber() {
        String testPhoneNumber = "7(888)221-42-85";
        String expected = "78882214285";

        Long id = 456789L;

        Assertions.assertEquals(out.setPetOwnersPhoneNumber(testPhoneNumber, id), expected);

        Assertions.assertThrows(IllegalArgumentException.class,() -> out.setPetOwnersPhoneNumber("7(888)221-42-85a", id));
        Assertions.assertThrows(IllegalArgumentException.class,() -> out.setPetOwnersPhoneNumber("7(888)221-42-85][]'", id));
        Assertions.assertThrows(IllegalArgumentException.class,() -> out.setPetOwnersPhoneNumber("895144598750", id));
        Assertions.assertThrows(NullPointerException.class,() -> out.setPetOwnersPhoneNumber("", id));


    }


}
