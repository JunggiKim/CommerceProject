package sample.cafekiosk.domain.stock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import sample.cafekiosk.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.api.service.stock.Stockservice;
import sample.cafekiosk.domain.product.Product;
import sample.cafekiosk.domain.product.ProductRepository;
import sample.cafekiosk.domain.product.ProductType.ProductSellingStatus;
import sample.cafekiosk.domain.product.ProductType.ProductType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static sample.cafekiosk.domain.product.ProductType.ProductSellingStatus.*;
import static sample.cafekiosk.domain.product.ProductType.ProductType.*;

@SpringBootTest
@Transactional
@Profile("test")
class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private Stockservice stockservice;

    @Autowired
    private ProductRepository productRepository;

    // @AfterEach
    // void tearDown() {
    //     stockRepository.deleteAllInBatch();
    // }

    // @BeforeEach
    // void beforeAll() {
    //   stockRepository.deleteAllInBatch();
    // }


    @DisplayName("상품번호 리스트로 재고를 조회한다.")
    @Test
    void findAllByProductNumberIn(){
//  given
        Stock stock1 = Stock.create("001", 1);
        Stock stock2 = Stock.create("002", 2);
        Stock stock3 = Stock.create("003", 3);

        stockRepository.saveAll(List.of(stock1,stock2,stock3));
        //  when

        List<Stock> stocks = stockRepository.findAllByProductNumberIn(List.of("001", "002"));

        //  then

        for (Stock stock : stocks) {
            System.out.println("투 스트링 한부분" +stock.getProductNumber());
        }

        assertThat(stocks).hasSize(2)
                .extracting("productNumber","quantity")
                .containsExactlyInAnyOrder(
                        tuple("001",1),
                        tuple("002",2)
                );
    }

    @DisplayName("동시에 재고를 주문시 순차적으로 진행한다.")
    @Test
    void createOrderWithNoStock() throws IllegalAccessException, InterruptedException {
        //  given
        LocalDateTime registeredDateTime = LocalDateTime.now();

        Product product1 = createProduct(BOTTLE, "001", 1000);
        productRepository.save(product1);

        Stock stock = Stock.create("001", 100);
        stockRepository.save(stock);

        List<Product> products = List.of(product1);

        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);//비동기로 실행하는 작업을 단순화하여 사용
        CountDownLatch latch = new CountDownLatch(threadCount);
            //when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                   stockservice.deductStockQuantities(products);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();//다른 쓰레드에서 수행중인 작업이 완료될때까지 기다려줌
        //then
            assertThat(stock.getQuantity()).isEqualTo(0);
    }


      private Product createProduct(ProductType type, String productNumber,int price){
        return  Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(SELLING)
                .name("메뉴이름")
                .price(price)
                .build();

    }



}