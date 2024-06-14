package uz.pdp.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExperienceDto {
    private int experienceId;
    private String experienceTitle;
    private String experienceCompany;
    private String experienceDescription;
    private String experienceStartDate;
    private String experienceEndDate;

}