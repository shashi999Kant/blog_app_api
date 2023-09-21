package com.blog.payLoads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	
	private Integer catgoryId;
	
	@NotBlank
	@Size(min = 4,message = "min size of category must be 4")
	private String categorytitle;

	@NotBlank
	@Size(min = 10 ,message = "min size of description should be 10")
	private String categoryDesc;
	
	
	
	

}
