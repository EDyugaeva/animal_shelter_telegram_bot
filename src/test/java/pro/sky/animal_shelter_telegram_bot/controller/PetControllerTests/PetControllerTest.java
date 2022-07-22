package pro.sky.animal_shelter_telegram_bot.controller.PetControllerTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import pro.sky.animal_shelter_telegram_bot.controller.PetController;

import static org.assertj.core.api.Assertions.assertThat;
import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsForControllerTests.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PetControllerTest {

    private final String LOCAL_URL = URL + PORT + "/" + PET_URL + "/";

    @Autowired
    private PetController petController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads(){
        assertThat(petController).isNotNull();
    }

    @Test
    public void testHelloMessage(){
        assertThat(this.restTemplate.getForObject(LOCAL_URL, String.class))
                .isNotEmpty();
        assertThat(this.restTemplate.getForObject(LOCAL_URL, String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject(LOCAL_URL, String.class))
                .isEqualTo(HELLO_MESSAGE_PET_CONTROLLER);
    }

    @Test
    public void testFindPet(){
        assertThat(this.restTemplate.getForObject(LOCAL_URL + ID, String.class))
                .isNotEmpty();
        assertThat(this.restTemplate.getForObject(LOCAL_URL + ID, String.class))
                .isNotNull();
    }

    @Test
    public void testFindAllPets(){
        assertThat(this.restTemplate.getForObject(LOCAL_URL + "all/", String.class))
                .isNotEmpty();
        assertThat(this.restTemplate.getForObject(LOCAL_URL + "all/", String.class))
                .isNotNull();
    }
}