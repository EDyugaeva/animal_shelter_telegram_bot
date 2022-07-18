package pro.sky.animal_shelter_telegram_bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
import pro.sky.animal_shelter_telegram_bot.model.pets.PhotoOfPet;
import pro.sky.animal_shelter_telegram_bot.service.PhotoOfPetService;

import java.io.IOException;

@RestController
@RequestMapping("/report")
public class PhotoOfPetController {

    private final PhotoOfPetService photoOfPetService;
    Logger logger = LoggerFactory.getLogger(PhotoOfPetService.class);

    public PhotoOfPetController(PhotoOfPetService photoOfPetService) {
        this.photoOfPetService = photoOfPetService;
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
                            description = "If photo not found",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    )
            },
            tags = "Reports"
    )
    @GetMapping(value = "/{id}/photo")
    public ResponseEntity<byte[]> findPhotoByReportId(@PathVariable Long id) {
        PhotoOfPet photoOfPet = photoOfPetService.findPhotoByReportId(id);
        if (photoOfPet.getFileSize() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(photoOfPet.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(photoOfPet.getData());
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
                            description = "If adding photo is to big",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
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
}
