package sample.cafekiosk.api.service.order;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;

import sample.cafekiosk.client.mail.MailSendClient;
import sample.cafekiosk.domain.history.mail.MailSendHistory;
import sample.cafekiosk.domain.history.mail.MailSendHistoryRepository;
import sample.cafekiosk.domain.order.Order;
import sample.cafekiosk.domain.order.OrderRepository;
import sample.cafekiosk.domain.order.OrderStatus;
import sample.cafekiosk.domain.orderproduct.OrderProductRepository;
import sample.cafekiosk.domain.product.Product;
import sample.cafekiosk.domain.product.ProductRepository;
import sample.cafekiosk.domain.product.ProductType.ProductType;
import sample.cafekiosk.order.service.OrderStatisticsService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static sample.cafekiosk.domain.product.ProductType.ProductSellingStatus.*;
import static sample.cafekiosk.domain.product.ProductType.ProductType.HANDMADE;


@SpringBootTest
@Profile("test")
class OrderStatisticsServiceTest {


    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @Autowired
    private  OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @MockBean
    private MailSendClient mailSendClient;


    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

//    @DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 전송한다.")
//    @Test
//    void sendOrderStatisticsMail(){
//        //  given
//        LocalDateTime now = LocalDateTime.of(2023, 3, 5, 0, 0);
//
//        Product product1 = createProduct(HANDMADE, "001", 1000);
//        Product product2 = createProduct(HANDMADE, "002", 2000);
//        Product product3 = createProduct(HANDMADE, "003", 3000);
//        List<Product> products = List.of(product1, product2, product3);
//        productRepository.saveAll(products);
//
//
//        Order order1 = createPaymentCompletedOrder(products, LocalDateTime.of(2023,3,4,23,59));
//        Order order2 = createPaymentCompletedOrder(products, now);
//        Order order3 = createPaymentCompletedOrder(products, LocalDateTime.of(2023,3,5,23,59));
//        Order order4 = createPaymentCompletedOrder(products, LocalDateTime.of(2023,3,6,0,0));
//
//
//        when(mailSendClient.sendEmail(any(String.class),any(String.class),any(String.class),any(String.class)))
//                    .thenReturn(true);
//
//
//
//        //  when
//        boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2023, 3, 5), "test@test.com");
//
//        //  then
//
//
//        assertThat(result).isTrue();
//
//        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
//
//        assertThat(histories).hasSize(1)
//                .extracting("content")
//                .contains("총 매출 합계는 12000원입니다.");
//    }

    private  Order createPaymentCompletedOrder(List<Product> products, LocalDateTime now) {
        return Order.builder()
                .products(products)
                .orderStatus(OrderStatus.PAYMENT_COMPLETED)
                .registeredDateTime(now)
                .build();
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