package juniverse.application.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import juniverse.application.dtos.ApiResponse;
import juniverse.domain.services.FolderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/folder")
@Tag(name = "FOLDERS")
public class FolderController {

    private final FolderService folderService;



}
