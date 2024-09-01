package com.hari.controller;


import com.hari.exception.ResourceNotFoundException;
import com.hari.model.Product;
import com.hari.request.AddProductRequest;
import com.hari.request.ProductUpdateRequest;
import com.hari.response.ApiResponse;
import com.hari.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<Product> products=productService.getAllProducts();
        return ResponseEntity.ok(new ApiResponse("success", products));
    }


    @GetMapping("/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
        try {
            Product product=productService.getProductById(productId);
            return ResponseEntity.ok(new ApiResponse("success", product));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
       try {
           Product theProduct=productService.addProduct(product);
           return ResponseEntity.ok(new ApiResponse("Add product success", theProduct));
       }catch (Exception e){
           return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
       }
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request,@PathVariable Long productId){
        try {
            Product theProduct=productService.updateProduct(request,productId);
            return ResponseEntity.ok(new ApiResponse("Update product success", theProduct));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Delete product success", productId));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName,@RequestParam String productName){
      try {
          List<Product> products=productService.getProductsByBrandAndName(brandName,productName);
          if (products.isEmpty()){
              return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found",null));
          }
          return ResponseEntity.ok(new ApiResponse("success", products));
      }catch (Exception e){
          return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
      }
    }

    @GetMapping("/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category,@RequestParam String brand){
        try {
            List<Product> products=productService.getProductsByCategoryAndBrand(category,brand);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success", products));

        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{name}/products")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
        try {
            List<Product> products=productService.getProductsByName(name);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand){
        try {
            List<Product> products=productService.getProductsByBrand(brand);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success", products));

        }catch (Exception e){
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category){
        try {
            List<Product> products=productService.getProductsByCategory(category);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success", products));

        }catch (Exception e){
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }



    @GetMapping("/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductByBrandAndName(@RequestParam String brand,@RequestParam String name){
        try {
            var productCount=productService.countProductsByBrandAndName(brand,name);
            return ResponseEntity.ok(new ApiResponse("Product count", productCount));

        }catch (Exception e){
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

}
