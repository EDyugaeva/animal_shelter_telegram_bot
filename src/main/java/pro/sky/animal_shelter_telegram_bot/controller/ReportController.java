package pro.sky.animal_shelter_telegram_bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.animal_shelter_telegram_bot.model.Report;
import pro.sky.animal_shelter_telegram_bot.model.pets.PhotoOfPet;
import pro.sky.animal_shelter_telegram_bot.service.PhotoOfPetService;
import pro.sky.animal_shelter_telegram_bot.service.ReportService;

import java.io.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;
    private final PhotoOfPetService photoOfPetService;

    Logger logger = LoggerFactory.getLogger(PhotoOfPetService.class);

    private final String HELLO_MESSAGE = "You can do it by reports\n" +
            "1. add new report\n" +
            "2. find report\n" +
            "2. update report\n" +
            "4. remove report\n";

    public ReportController(ReportService reportService, PhotoOfPetService photoOfPetService) {
        this.reportService = reportService;
        this.photoOfPetService = photoOfPetService;
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
    public String helloMessage(){
        return HELLO_MESSAGE;
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
                            description = "If report not found"
                    )
            },
            tags = "Reports"
    )
    @GetMapping("{id}")
    public ResponseEntity<Report> findReport(@Parameter(description = "Report id", example = "1") @PathVariable Long id) {
        Report report = reportService.findReport(id);
        if (report == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(report);
    }

    @Operation(
            summary = "Find photo by reportId",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found photo:",
                            content = @Content(mediaType = MediaType.IMAGE_JPEG_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If photo not found"
                    )
            },
            tags = "Reports"
    )
    @GetMapping(value = "/{id}/photo")
    public ResponseEntity<byte[]> findPhotoByReportId(@PathVariable Long id) {
        PhotoOfPet photoOfPet = photoOfPetService.findPhotoByReportId(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(photoOfPet.getMediaType()));
        headers.setContentLength(photoOfPet.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(photoOfPet.getData());
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
        return reportService.addReport(report);
    }

    @Operation(
            summary = "Add photo of pet to report",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Add photo to report",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Add photo",
                            content = @Content(
                                    mediaType = MediaType.IMAGE_JPEG_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Add photo is to big"
                    )
            },
            tags = "Reports"
    )
    @PostMapping(value = "/{id}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upLoadPhotoOfPet(@PathVariable Long id, @RequestParam MultipartFile photo) throws IOException {
        if (photo.getSize() > 1024 * 300) {
            logger.warn("Warning: photo is to big");
            return ResponseEntity.badRequest().body("File is to big");
        }
        photoOfPetService.uploadPhotoOfPet(id, photo);
        return ResponseEntity.ok().build();
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
                                    schema = @Schema(implementation = Report.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If report not found"
                    )
            },
            tags = "Reports"
    )
    @PutMapping
    public ResponseEntity<Report> editReport(@RequestBody Report report) {
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
                            description = "Report is delete from Database"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If report not found"
                    )
            },
            tags = "Reports"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Report> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.ok().build();
    }
}
