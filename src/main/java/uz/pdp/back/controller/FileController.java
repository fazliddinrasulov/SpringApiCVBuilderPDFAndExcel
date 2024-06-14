package uz.pdp.back.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.back.model.FormDataDto;
import uz.pdp.back.service.FileService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/file")
public class FileController {
    private final FileService fileService;

    @PostMapping("generate")
    public HttpEntity<?> generate(@RequestParam(required = false) MultipartFile photo, FormDataDto formDataDto) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.add("Content-Disposition", "attachment; filename=cv.pdf"); // indicates browser to download the file:

        byte[] bytes = fileService.generatePdf(formDataDto, photo.getBytes());
        return ResponseEntity.ok().headers(headers).body(bytes);
    }

    @GetMapping("techs")
    public HttpEntity<?> getTechs() {
        List<String> techs = fileService.getTechs();
        return ResponseEntity.ok(techs);
    }

}
