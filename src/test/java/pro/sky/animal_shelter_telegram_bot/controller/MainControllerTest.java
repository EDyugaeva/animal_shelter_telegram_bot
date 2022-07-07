package pro.sky.animal_shelter_telegram_bot.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsForControllerTests.URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MainControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MainController mainController;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String HELLO_MESSAGE = "Welcome to our pet shelter!";

    @Test
    public void contextLoads(){
        assertThat(mainController).isNotNull();
    }

    @Test
    public void testHelloMessage(){
        assertThat(this.restTemplate.getForObject(URL + port +"/", String.class))
                .isNotEmpty();
        assertThat(this.restTemplate.getForObject(URL + port +"/", String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject(URL + port +"/", String.class))
                .isEqualTo(HELLO_MESSAGE);
    }
}