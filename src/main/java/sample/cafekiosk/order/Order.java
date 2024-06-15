package sample.cafekiosk.order;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int totalPrice;

    private LocalDateTime registeredDateTime;

    private String userEmail;


    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Builder
    private Order(String userEmail  ,List<Product> products, OrderStatus orderStatus, LocalDateTime registeredDateTime) {
        this.orderStatus = orderStatus;
        this.totalPrice = calculateTotalPrice(products);
        this.registeredDateTime = registeredDateTime;
        this.orderProducts = products.stream()
                .map(product -> OrderProduct.of(this , product))
                .collect(Collectors.toList());
        this.userEmail = userEmail;
    }


    public static Order create(List<Product>  products,LocalDateTime registeredDateTime,String userEmail) {
    return Order.builder()
            .orderStatus(OrderStatus.INIT)
            .products(products)
            .registeredDateTime(registeredDateTime)
            .userEmail( userEmail)
            .build();
    }

    public static Order create(List<Product>  products,LocalDateTime registeredDateTime) {
    return Order.builder()
            .orderStatus(OrderStatus.INIT)
            .products(products)
            .registeredDateTime(registeredDateTime)
            .build();
    }


    private static int calculateTotalPrice(List<Product> products) {
        return products.stream()
                .mapToInt(Product::getPrice)
                .sum();
    }


}
