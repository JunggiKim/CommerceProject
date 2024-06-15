package sample.cafekiosk.orderproduct;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.BaseEntity;
import sample.cafekiosk.order.Order;
import sample.cafekiosk.product.Product;

@Getter
@Entity
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class OrderProduct extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;


    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;



    @Builder
    private OrderProduct(Long id, Order order, Product product) {
        this.id = id;
        this.order = order;
        this.product = product;
    }

    @Builder
    private OrderProduct(Order order, Product product) {
        this.order = order;
        this.product =product;
    }

    public static OrderProduct of(Order order, Product product) {
    return  OrderProduct.builder()
        .order(order)
        .product(product)
        .build();
    }



}
