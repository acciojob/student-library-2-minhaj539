package com.driver.RequestDto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRequestDto {
    private String name;
    private String email;
    private int age;
    private String country;


}
