package com.nt.service;

import com.nt.entity.Product;

public interface IProductService {
	
    public String editProduct(Product product);

    public String deleteProduct(int id);

}
