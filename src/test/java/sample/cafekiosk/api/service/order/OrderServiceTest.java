package sample.cafekiosk.api.service.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.api.service.order.response.OrderResponse;
import sample.cafekiosk.domain.order.OrderRepository;
import sample.cafekiosk.domain.orderproduct.OrderProductRepository;
import sample.cafekiosk.domain.product.Product;
import sample.cafekiosk.domain.product.ProductRepository;
import sample.cafekiosk.domain.product.ProductType.ProductType;
import sample.cafekiosk.domain.stock.Stock;
import sample.cafekiosk.domain.stock.StockRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;
import static sample.cafekiosk.domain.product.ProductType.ProductSellingStatus.*;
import static sample.cafekiosk.domain.product.ProductType.ProductType.*;

@ActiveProfiles("test")
//@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockRepository stockRepository;


    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderService orderService;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
    }

    @DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
    @Test
    void createOrder() throws IllegalAccessException {
        //  given
        LocalDateTime registeredDateTime = LocalDateTime.now();
        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 3000);
        Product product3 = createProduct(HANDMADE, "003", 5000);

        productRepository.saveAll(List.of(product1, product2, product3));


        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "002"))
                .build();
        //  when
        OrderResponse orderResponse = orderService.createOrder(request.toServiceRequest(), registeredDateTime);

        //  then


        assertThat(orderResponse.getId()).isNotNull();

        assertThat(orderResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 4000);

        assertThat(orderResponse.getProducts()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 1000),
                        tuple("002", 3000)
                );


    }

    @DisplayName("중복되는 상품번호 리스트로 주문을 생성할 수 있다.")
    @Test
    void createOrderWithDuplicateProductNumber() throws IllegalAccessException {
        //  given
        LocalDateTime registeredDateTime = LocalDateTime.now();


        Product product1 = createProduct(HANDMADE, "001", 1000);

        Product product2 = createProduct(HANDMADE, "002", 3000);

        Product product3 = createProduct(HANDMADE, "003", 5000);

        productRepository.saveAll(List.of(product1, product2, product3));


        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "001"))
                .build();
        //  when
        OrderResponse orderResponse = orderService.createOrder(request.toServiceRequest(), registeredDateTime);

        //  then


        assertThat(orderResponse.getId()).isNotNull();

        assertThat(orderResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 2000);

        assertThat(orderResponse.getProducts()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 1000),
                        tuple("001", 1000)
                );

    }


    @DisplayName("재고와 관련된 상품이 포함되어 있는 주문번호 리스트를 받아 주문을 생성한다.")
    @Test
    void createOrderWithStock() throws IllegalAccessException {
        //  given
        LocalDateTime registeredDateTime = LocalDateTime.now();

        Product product1 = createProduct(BOTTLE, "001", 1000);
        Product product2 = createProduct(BAKERY, "002", 3000);
        Product product3 = createProduct(HANDMADE, "003", 5000);
        productRepository.saveAll(List.of(product1, product2, product3));

        Stock stock = Stock.create("001", 2);
        Stock stock2 = Stock.create("002", 2);
        stockRepository.saveAll(List.of(stock, stock2));


        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "001", "002", "003"))
                .build();
        //  when
        OrderResponse orderResponse = orderService.createOrder(request.toServiceRequest(), registeredDateTime);

        //  then


        assertThat(orderResponse.getId()).isNotNull();

        assertThat(orderResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 10000);

        assertThat(orderResponse.getProducts()).hasSize(4)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 1000),
                        tuple("001", 1000),
                        tuple("002", 3000),
                        tuple("003", 5000)
                );
        List<Stock> stocks = stockRepository.findAll();


        assertThat(stocks).hasSize(2)
                .extracting("productNumber","quantity")
                .containsExactlyInAnyOrder(
                        tuple("001",0),
                        tuple("002",1)
                );


    }





    private Product createProduct(ProductType type, String productNumber, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(STOP_SELLING)
                .name("메뉴이름")
                .price(price)
                .build();
    }


}