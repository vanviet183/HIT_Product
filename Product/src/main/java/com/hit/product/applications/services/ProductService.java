package com.hit.product.applications.services;

import com.hit.product.adapter.web.v1.transfer.responses.TrueFalseResponse;
import com.hit.product.domains.dtos.ProductDto;
import com.hit.product.domains.entities.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ProductService {

    List<Product> getAllByPage(Integer page);

    Product getProductById(Long id);

    Product createProduct(Long categoryId,
                          ProductDto productDto,
                          List<MultipartFile> multipartFiles);

    Product updateProduct(Long id, ProductDto productDto, List<MultipartFile> multipartFiles);

    TrueFalseResponse deleteProduct(Long id);

    List<Product> searchProducts(String nameProduct);

    List<Product> getProductsSort(String type);

    List<Product> getProductsNewest();

    List<Product> getProductsBestSeller();

    List<Product> getProductsByFilter(List<String> types, List<Integer> sizes, List<String> colors, List<String> brands);

    List<ProductRate> getProductRateByIdProduct(Long id);
}
