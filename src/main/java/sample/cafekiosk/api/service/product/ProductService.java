package sample.cafekiosk.api.service.product;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.api.service.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.api.service.stock.Stockservice;
import sample.cafekiosk.domain.product.Product;
import sample.cafekiosk.domain.product.ProductRepository;
import sample.cafekiosk.api.service.product.response.ProductResponse;
import sample.cafekiosk.domain.product.ProductType.ProductSellingStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;



    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        String nextProductNumber = createNextProductNumber();

        Product product =  request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    private String createNextProductNumber() {
        String latesProdectNumber = productRepository.findLatesProductNumber();
        if(latesProdectNumber == null){
            return "001";
        }

        int latesProdectNumberInt = Integer.valueOf(latesProdectNumber);
        int nextProductNumberInt = latesProdectNumberInt + 1;
        return String.format("%03d", nextProductNumberInt);
    }

    public List<ProductResponse> getSellingProducts() {
        return productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay())
                .stream()
                .map(product -> ProductResponse.of(product))
                .collect(Collectors.toList());
    }


}
