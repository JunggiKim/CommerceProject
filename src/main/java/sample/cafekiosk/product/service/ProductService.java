package sample.cafekiosk.product.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sample.cafekiosk.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.domain.product.Product;
import sample.cafekiosk.domain.product.ProductRepository;
import sample.cafekiosk.product.response.ProductResponse;
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
        return String.format("%03d", nextProductNumberInt);  // 상품 번호를 3자리로 포맷팅 하는 부분
    }

    public List<ProductResponse> getSellingProducts() {
        return productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay())
                .stream()
                .map(product -> ProductResponse.of(product))
                .collect(Collectors.toList());
    }


}
