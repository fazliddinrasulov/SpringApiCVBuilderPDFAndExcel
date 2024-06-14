package uz.pdp.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EducationDto {
    private int educationId;
    private String educationSchool;
    private String educationStartDate;
    private String educationEndDate;

    // Getters and setters
}
