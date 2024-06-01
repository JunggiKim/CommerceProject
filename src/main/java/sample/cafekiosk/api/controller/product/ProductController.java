package sample.cafekiosk.api.controller.product;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.api.ApiResponse;
import sample.cafekiosk.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.api.service.product.ProductService;
import sample.cafekiosk.api.service.product.response.ProductResponse;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductControllerInterface{

    private final ProductService productService;

    @Override
    @PostMapping("/api/v1/products/new")
    public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request){
        return ApiResponse.ok(productService.createProduct(request.toServiceRequest()));
    }

    @Override
    @GetMapping("/api/v1/products/selling")
    public ApiResponse<List<ProductResponse>> getSellingProducts(){
        return ApiResponse.ok(productService.getSellingProducts());
    }


}
