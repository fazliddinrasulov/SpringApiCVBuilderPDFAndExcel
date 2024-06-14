package uz.pdp.back.config.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Education {
    private String EducationSchool;
    private String EducationStartDate;
    private String EducationEndDate;
}
