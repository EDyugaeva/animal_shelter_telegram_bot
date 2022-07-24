package pro.sky.animal_shelter_telegram_bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.animal_shelter_telegram_bot.model.Report;
import pro.sky.animal_shelter_telegram_bot.service.ReportService;

import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsOfControllers.HELLO_MESSAGE_OF_REPORT_CONTROLLER;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    Logger logger = LoggerFactory.getLogger(ReportController.class);

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(
            summary = "Welcome message",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Send a welcome message",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    examples = @ExampleObject(value = "string"))
                    )
            },
            tags = "Reports"
    )
    @GetMapping
    public String helloMessage() {
        logger.info("Call helloMessage in Report Controller");
        return HELLO_MESSAGE_OF_REPORT_CONTROLLER;
    }

    @Operation(
            summary = "Find report by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found report:",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If report not found",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    )
            },
            tags = "Reports"
    )
    @GetMapping("{id}")
    public ResponseEntity<Report> findReport(@Parameter(description = "Report id", example = "1") @PathVariable Long id) {
        logger.info("Call findReport in Report Controller");
        Report report = reportService.findReport(id);
        if (report == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(report);
    }

    @Operation(
            summary = "Add new report",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Add report",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Report.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Add report",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class))
                    )
            },
            tags = "Reports"
    )
    @PostMapping
    public Report addReport(@RequestBody Report report) {
        logger.info("Call addReport in Report Controller");
        return reportService.addReport(report);
    }

    @Operation(
            summary = "Update report",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Edit report",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Report.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update report",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If report not found, will be received bad request",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    )
            },
            tags = "Reports"
    )
    @PutMapping
    public ResponseEntity<Report> editReport(@RequestBody Report report) {
        logger.info("Call editReport in Report Controller");
        Report editReport = reportService.changeReport(report);
        if (editReport == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(report);
    }

    @Operation(
            summary = "Delete report",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Report is delete from Database",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If report not found",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    )
            },
            tags = "Reports"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Report> deleteReport(@PathVariable Long id) {
        logger.info("Call deleteReport in Report Controller");
        if (reportService.deleteReport(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(reportService.deleteReport(id));
    }

    @Operation(
            summary = "Set mark on report",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Mark was sent correctly",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If report not found, will be received bad request",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    )
            },
            tags = "Reports"
    )
    @PutMapping("/markReport")
    public ResponseEntity<Report> setMarkOnReport(@RequestParam(name = "Id отчета") Long id,
                                                  @RequestParam(name = "Хороший/нормальный/плохой") String result) {
        logger.info("Call setMarkOnReport in ReportController");
        return ResponseEntity.ok(reportService.setMarkOnReport(id, result));
    }
}
