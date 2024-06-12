package sample.cafekiosk.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.api.service.mail.OrderCreateMailEvent;
import sample.cafekiosk.api.service.order.request.OrderCreateServiceRequest;
import sample.cafekiosk.api.service.order.response.OrderResponse;
import sample.cafekiosk.api.service.stock.Stockservice;
import sample.cafekiosk.domain.order.Order;
import sample.cafekiosk.domain.order.OrderRepository;
import sample.cafekiosk.domain.product.Product;
import sample.cafekiosk.domain.product.ProductRepository;
import sample.cafekiosk.domain.product.ProductType.ProductType;
import sample.cafekiosk.domain.stock.Stock;
import sample.cafekiosk.domain.stock.StockRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional (readOnly = true)
@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    private final StockRepository stockRepository;

    private final Stockservice stockservice;

    private final ApplicationEventPublisher applicationEventPublisher;
    /*
*/
    @Transactional
    public OrderResponse createOrder(OrderCreateServiceRequest request, LocalDateTime registeredDateTime) throws IllegalAccessException {
        List<String> productNumbers = request.getProductNumbers();
        List<Product> products  = findProductsBy(productNumbers);

        stockservice.deductStockQuantities(products);

        Order order = Order.create(products, registeredDateTime,request.getUserEmail());
        Order savedOrder = orderRepository.save(order);
        OrderResponse orderResponse = OrderResponse.of(savedOrder);

        applicationEventPublisher.publishEvent(new OrderCreateMailEvent(orderResponse));

        return  orderResponse;
    }

    private List<Product> findProductsBy(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, product -> product));

        return productNumbers.stream()
                .map(productMap::get)
                .collect(Collectors.toList());
    }


        //list<stirng> = {a, a,b, c }
       //  map<a,2> map<b,1> map<c,1>




}
