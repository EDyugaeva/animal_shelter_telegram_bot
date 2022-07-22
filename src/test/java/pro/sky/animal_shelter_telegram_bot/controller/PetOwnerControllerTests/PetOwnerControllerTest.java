package pro.sky.animal_shelter_telegram_bot.controller.PetOwnerControllerTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import pro.sky.animal_shelter_telegram_bot.controller.PetOwnerController;

import static org.assertj.core.api.Assertions.assertThat;
import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsForControllerTests.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PetOwnerControllerTest {

    private final String LOCAL_URL = URL + PORT + "/" + PET_OWNER_URL + "/";

    @Autowired
    private PetOwnerController petOwnerController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads(){
        assertThat(petOwnerController).isNotNull();
    }

    @Test
    public void testHelloMessage(){
        assertThat(this.restTemplate.getForObject(LOCAL_URL, String.class))
                .isNotEmpty();
        assertThat(this.restTemplate.getForObject(LOCAL_URL, String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject(LOCAL_URL, String.class))
                .isEqualTo(HELLO_MESSAGE_PET_OWNER);
    }
}