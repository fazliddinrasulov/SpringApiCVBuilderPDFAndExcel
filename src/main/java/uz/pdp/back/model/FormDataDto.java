package uz.pdp.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FormDataDto {
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String phone;
    private String educations;
    private String experiences;
    private String selectedTech;
}
