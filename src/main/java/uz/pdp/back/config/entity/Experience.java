package uz.pdp.back.config.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Experience {
    private String experienceTitle;
    private String experienceCompany;
    private String experienceDescription;
    private String experienceStartDate;
    private String experienceEndDate;
}
