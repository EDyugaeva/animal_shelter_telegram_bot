package pro.sky.animal_shelter_telegram_bot.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsForControllerTests.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PetControllerTest {

    @LocalServerPort
    private int port;

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
        assertThat(this.restTemplate.getForObject(URL + port + "/" + PET_URL + "/", String.class))
                .isNotEmpty();
        assertThat(this.restTemplate.getForObject(URL + port + "/" + PET_URL + "/", String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject(URL + port + "/" + PET_URL + "/", String.class))
                .isEqualTo(HELLO_MESSAGE_PET_CONTROLLER);
    }
}