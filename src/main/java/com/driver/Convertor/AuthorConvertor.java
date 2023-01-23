package com.driver.Convertor;

import com.driver.RequestDto.AuthorRequestDto;
import com.driver.models.Author;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class AuthorConvertor {
   public static Author DtoToEntity(AuthorRequestDto authorRequestDto){
       Author author=Author.builder().
           name(authorRequestDto.getName()).
           email(authorRequestDto.getEmail()).
           age(authorRequestDto.getAge()).
           country(authorRequestDto.getCountry()).
           build();
       return author;
   }
}
