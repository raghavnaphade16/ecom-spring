package com.app.ecom.controller;

import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product/")
public class ProductController {

private final ProductService productService;

    @GetMapping("getallproduct")
    public ResponseEntity<List<ProductResponse>> getProductService() {
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @PostMapping("createproduct")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        return new ResponseEntity<ProductResponse>(productService.createProduct(productRequest), HttpStatus.CREATED);
    }

    @PutMapping("updateproduct/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest){
        return productService.updateProduct(id,productRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("deleteproduct/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long id){
        boolean deleted = productService.deleteUser(id);
       return deleted ? ResponseEntity.noContent().build(): ResponseEntity.notFound().build();
    }
    @GetMapping("search")
    public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam String searchText){
    return  ResponseEntity.ok(productService.searchProducts(searchText));
    }



}
