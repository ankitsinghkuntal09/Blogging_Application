package com.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    private Integer categoryId;
    @NotBlank
    @Size(min = 4,message = "Minimum size of Category Title is 4!!")
    private String categoryTitle;

    @NotBlank
    @Size(min = 8,message = "Minimum size of Category Description is 8!!")
    private String categoryDescription;

}
