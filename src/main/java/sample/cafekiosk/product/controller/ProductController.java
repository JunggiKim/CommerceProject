package sample.cafekiosk.product.controller;


import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.common.CustomApiResponse;
import sample.cafekiosk.product.request.ProductCreateRequest;
import sample.cafekiosk.product.service.ProductService;
import sample.cafekiosk.product.response.ProductResponse;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductControllerInterface{

    private final ProductService productService;

    @Override
    @PostMapping("/api/v1/products/new")
    public CustomApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request){
        return CustomApiResponse.ok(productService.createProduct(request.toServiceRequest()));
    }

    @Override
    @GetMapping("/api/v1/products/selling")
    public CustomApiResponse<List<ProductResponse>> getSellingProducts(){
        return CustomApiResponse.ok(productService.getSellingProducts());
    }


}
