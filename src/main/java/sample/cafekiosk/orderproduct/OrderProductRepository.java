package sample.cafekiosk.orderproduct;

import org.springframework.data.jpa.repository.JpaRepository;

import sample.cafekiosk.domain.orderproduct.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {
}
