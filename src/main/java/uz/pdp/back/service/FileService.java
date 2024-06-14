package uz.pdp.back.service;

import org.springframework.stereotype.Service;
import uz.pdp.back.model.FormDataDto;

import java.util.List;

@Service
public interface FileService {
    byte[] generatePdf(FormDataDto fileDataDto, byte[] bytes);

    List<String> getTechs();
}
