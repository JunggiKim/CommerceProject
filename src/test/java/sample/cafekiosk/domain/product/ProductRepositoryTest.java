package sample.cafekiosk.domain.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.domain.product.ProductType.ProductSellingStatus;
import sample.cafekiosk.domain.product.ProductType.ProductType;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static sample.cafekiosk.domain.product.ProductType.ProductSellingStatus.*;
import static sample.cafekiosk.domain.product.ProductType.ProductType.*;

@ActiveProfiles("test")
@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;



    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }


    @DisplayName("원하는 판매상품을 조회한다")
    @Test
    void testSample(){
        //  given
        Product product1 = createProduct(HANDMADE, "001", 4000, SELLING, "아메리카노");
        Product product2 = createProduct(HANDMADE, "002", 4500, HOLD, "카페라떼");
        Product product3 = createProduct(HANDMADE, "003", 7000, STOP_SELLING, "팥빙수");

        productRepository.saveAll(List.of(product1,product2,product3));

        //  when
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(SELLING,HOLD));

        //  then

        assertThat(products).hasSize(2)
                            .extracting("productNumber","name","sellingStatus")
                            .containsExactlyInAnyOrder(
                                    tuple("001","아메리카노",SELLING),
                                    tuple("002","카페라떼",HOLD)
                            );
    }



    @DisplayName("상품번호 리스트로 상품들을 조회한다.")
    @Test()
    void findAllByProductNumberIn(){
//  given
        Product product1 = createProduct(HANDMADE, "001", 4000, SELLING, "아메리카노");
        Product product2 = createProduct(HANDMADE, "002", 4500, HOLD, "카페라떼");
        Product product3 = createProduct(HANDMADE, "003", 7000, STOP_SELLING, "팥빙수");
        productRepository.saveAll(List.of(product1,product2,product3));


        //  when

        List<Product> products = productRepository.findAllByProductNumberIn(List.of("001","002"));
        //  then



        assertThat(products).hasSize(2)
                .extracting("productNumber","name","sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001","아메리카노",SELLING),
                        tuple("002","카페라떼",HOLD)
                );
    }

    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어온다")
    @Test()
    void findLatesProductNumber(){
        //  given

        String targetProductNumber = "003";
        Product product1 = createProduct(HANDMADE, "001", 4000, SELLING, "아메리카노");
        Product product2 = createProduct(HANDMADE, "002", 4500, HOLD, "카페라떼");
        Product product3 = createProduct(HANDMADE, targetProductNumber, 7000, STOP_SELLING, "팥빙수");
        productRepository.saveAll(List.of(product1,product2,product3));
        //  when

        String lastProductNumber = productRepository.findLatesProductNumber() ;

        //  then
         assertThat(lastProductNumber).isEqualTo(targetProductNumber);
    }


    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어올 때, 상품히 하나도 없는 경우에는 null을 반환한다.")
    @Test()
    void findLatesProductNumberWhenProductIsEmpty(){
        // when

        String lastProductNumber = productRepository.findLatesProductNumber() ;
        //  then

        assertThat(lastProductNumber).isEqualTo(null);
    }

    private Product createProduct(ProductType type, String productNumber, int price, ProductSellingStatus productSellingStatus, String name) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(productSellingStatus)
                .name(name)
                .price(price)
                .build();

    }

}