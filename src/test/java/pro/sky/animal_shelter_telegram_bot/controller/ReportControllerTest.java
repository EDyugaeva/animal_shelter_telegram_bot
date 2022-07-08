package pro.sky.animal_shelter_telegram_bot.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsForControllerTests.URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReportControllerTest {

    @LocalServerPort
    private int port;

    private final String REPORT_URL = "report";

    @Autowired
    private ReportController reportController;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String HELLO_MESSAGE = "You can do it by reports\n" +
            "1. add new report\n" +
            "2. find report\n" +
            "2. update report\n" +
            "4. remove report\n";

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
                .isEqualTo(HELLO_MESSAGE);
    }
}