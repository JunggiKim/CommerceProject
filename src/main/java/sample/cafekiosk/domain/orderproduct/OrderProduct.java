package sample.cafekiosk.domain.orderproduct;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.domain.BaseEntity;
import sample.cafekiosk.domain.order.Order;
import sample.cafekiosk.domain.product.Product;


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


    public OrderProduct(Order order, Product product) {
        this.order = order;
        this.product =product;
    }
}
