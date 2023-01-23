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
       Author author=new Author();
       author.setName(authorRequestDto.getName());
       author.setAge(authorRequestDto.getAge());
       author.setEmail(authorRequestDto.getEmail());
       author.setCountry(authorRequestDto.getCountry());
       return author;
   }
}
