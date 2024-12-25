package com.nt.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nt.binding.ProductBinding;
import com.nt.entity.Product;
import com.nt.repository.ProductRepository;
import com.nt.service.IProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductRepository repo;

	@Autowired
	private IProductService serv;

	@GetMapping("/")
	public String homepage(Model model) {
		List<Product> list = repo.findAll();
		list.stream().map(Product::getImageFileName).forEach(System.out::println);
		model.addAttribute("productList", list);
		return "index";
	}

	@GetMapping("/create")
	public String createProduct(Model model) {
		ProductBinding binding = new ProductBinding();
		model.addAttribute("product", binding);
		return "createProduct";
	}

	@PostMapping("/create")
	public String createProduct(@ModelAttribute("product") ProductBinding binding, BindingResult result) {

		System.out.println(binding.getImageFileName());
		if (binding.getImageFileName().isEmpty()) {
			result.addError(new FieldError("binding", "imageFileName", "Image is required"));
		}
		if (result.hasErrors()) {
			return "createProduct";
		}

		// '1735119004727_samsungs5.jpg' '2024-12-25T15:06:26.258208500_2.jpg'
		MultipartFile image = binding.getImageFileName();
		String storageFileName = image.getOriginalFilename();

		try {
			// Set the upload directory
			String uploadDir = "src/main/resources/static/images/";
			java.nio.file.Path uploadPath = Paths.get(uploadDir);

			// Check if the directory exists; if not, create it
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			// Save the file to the upload directory
			try (InputStream inputStream = image.getInputStream()) {
				Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
			}

		} catch (Exception ex) {
			// Log or handle exceptions
			System.err.println("Exception occurred during file upload: " + ex.getMessage());
		}

		Product p1 = new Product();
		p1.setName(binding.getName());
		p1.setBrand(binding.getBrand());
		p1.setCategory(binding.getCategory());
		p1.setPrice(binding.getPrice());
		p1.setCreatedAt(LocalDateTime.now());
		p1.setDescription(binding.getDescription());
		p1.setImageFileName(storageFileName);
		repo.save(p1);
		System.out.println(p1);

		return "redirect:/";
	}

	@GetMapping("/edit")
	public String editProduct(@ModelAttribute("product") Product product, @RequestParam("id") int id) {
		Product product1 = repo.findById(id).get();
		
		BeanUtils.copyProperties(product1, product);
		System.out.println(product1);
		return "EditProduct";
	}

	@PostMapping("/edit")
	public String updateProduct(@ModelAttribute("product") Product product, RedirectAttributes attr) {
		
		String editProduct = serv.editProduct(product);
		
		attr.addFlashAttribute("resultMsg", editProduct);
		

		return "redirect:/";

	}
	
	@GetMapping("/delete")
	public String deleteProduct(@RequestParam("id") int id, Model model) {
		String deleteProduct = serv.deleteProduct(id);
		
		model.addAttribute("resultMsg", deleteProduct);
		return "forward:/";
	}

}
