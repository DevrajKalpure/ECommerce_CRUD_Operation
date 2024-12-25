package com.nt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.entity.Product;
import com.nt.repository.ProductRepository;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	ProductRepository repo;
	
	@Override
	public String editProduct(Product product) {
		 repo.save(product);
		 return "Edit successfull"+product.getId();
	}

	@Override
	public String deleteProduct(int id) {

		repo.deleteById(id);
		return "Product whose Id is::"+id+"::SuccessFully Deleted";
	}

}
