package pro.sky.animal_shelter_telegram_bot.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsForControllerTests.URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VolunteerControllerTest {

    @LocalServerPort
    private int port;

    private final String VOLUNTEER_URL = "volunteer";

    @Autowired
    private VolunteerController volunteerController;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String HELLO_MESSAGE = "You can do it by information of volunteer:\n" +
            "1. add information about the volunteer\n" +
            "2. get information about the volunteer\n" +
            "2. update information about the volunteer\n" +
            "4. remove information about rhe volunteer\n";

    @Test
    public void contextLoads(){
        assertThat(volunteerController).isNotNull();
    }

    @Test
    public void testHelloMessage(){
        assertThat(this.restTemplate.getForObject(URL + port + "/" + VOLUNTEER_URL + "/", String.class))
                .isNotEmpty();
        assertThat(this.restTemplate.getForObject(URL + port + "/" + VOLUNTEER_URL + "/", String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject(URL + port + "/" + VOLUNTEER_URL + "/", String.class))
                .isEqualTo(HELLO_MESSAGE);
    }
}