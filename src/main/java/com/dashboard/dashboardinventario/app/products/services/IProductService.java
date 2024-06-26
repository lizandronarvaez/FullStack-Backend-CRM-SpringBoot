package com.dashboard.dashboardinventario.app.products.services;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import com.dashboard.dashboardinventario.app.products.controllers.AuthResponse;
import com.dashboard.dashboardinventario.app.products.models.dto.ProductDto;
import com.dashboard.dashboardinventario.app.products.models.entity.ProductEntity;


public interface IProductService {

    AuthResponse saveProduct(MultipartFile file, ProductDto productDto);

    List<ProductEntity> findAllProduct();

    AuthResponse findProductById(Integer id);

    List<ProductEntity> findProductByWord(String word);

    AuthResponse updateProduct(MultipartFile file, ProductDto productDto, Integer id);

    AuthResponse deleteProduct(Integer id);
}
