package pro.sky.animal_shelter_telegram_bot.controller.ReportControllerTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import pro.sky.animal_shelter_telegram_bot.controller.ReportController;

import static org.assertj.core.api.Assertions.assertThat;
import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsForControllerTests.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReportControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ReportController reportController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads(){
        assertThat(reportController).isNotNull();
    }

    @Test
    public void testHelloMessage(){
        assertThat(this.restTemplate.getForObject(URL + port + "/" + REPORT_URL + "/", String.class))
                .isNotEmpty();
        assertThat(this.restTemplate.getForObject(URL + port + "/" + REPORT_URL + "/", String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject(URL + port + "/" + REPORT_URL + "/", String.class))
                .isEqualTo(HELLO_MESSAGE_REPORT_CONTROLLER);
    }
}