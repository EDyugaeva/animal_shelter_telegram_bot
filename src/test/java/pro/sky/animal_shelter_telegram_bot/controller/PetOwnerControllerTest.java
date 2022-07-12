package pro.sky.animal_shelter_telegram_bot.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsForControllerTests.URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PetOwnerControllerTest {

    @LocalServerPort
    private int port;

    private final String PET_OWNER_URL = "pet-owner";

    @Autowired
    private PetOwnerController petOwnerController;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String HELLO_MESSAGE = "You can do it by information of pet owner:\n" +
            "1. add information about the owner of the pet\n" +
            "2. get information about the owner of the pet\n" +
            "2. update information about the owner of the pet\n" +
            "4. remove information about the owner of the pet\n";

    @Test
    public void contextLoads(){
        assertThat(petOwnerController).isNotNull();
    }

    @Test
    public void testHelloMessage(){
        assertThat(this.restTemplate.getForObject(URL + port + "/" + PET_OWNER_URL + "/", String.class))
                .isNotEmpty();
        assertThat(this.restTemplate.getForObject(URL + port + "/" + PET_OWNER_URL + "/", String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject(URL + port + "/" + PET_OWNER_URL + "/", String.class))
                .isEqualTo(HELLO_MESSAGE);
    }
}