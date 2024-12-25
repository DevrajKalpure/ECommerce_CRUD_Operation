package com.nt.binding;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductBinding {

	private Integer id;
    
	private String Name;
   
	private String brand;
   // @NonNull
	private String category;
//    @NonNull
	private Double price;
  //  @NonNull
	private String description;
    //@NonNull
	private LocalDateTime createdAt;
   // @NonNull
    private MultipartFile imageFileName;
	
}
