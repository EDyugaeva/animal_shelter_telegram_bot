package pro.sky.animal_shelter_telegram_bot.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsForControllerTests.URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PetControllerTest {

    @LocalServerPort
    private int port;
    private final String PET_URL = "pet";
    private final String petControllerURL = URL + port + "/" + PET_URL + "/";
    private final String HELLO_MESSAGE = "You can do it by information of pet:\n" +
            "1. add pet information\n" +
            "2. get pet information\n" +
            "2. update pet information\n" +
            "4. remove pet information";

    @Autowired
    private PetController petController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads(){
        assertThat(petController).isNotNull();
    }

    @Test
    public void helloMessage() {
        assertThat(this.restTemplate.getForObject(petControllerURL, String.class))
                .isNotEmpty();
        assertThat(this.restTemplate.getForObject(petControllerURL, String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject(petControllerURL, String.class))
                .isEqualTo(HELLO_MESSAGE);
    }

    @Test
    void findPet() {
    }

    @Test
    void addPet() {
    }

    @Test
    void editPet() {
    }

    @Test
    void deletePet() {
    }
}