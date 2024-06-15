package sample.cafekiosk.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.LockModeType;

import sample.cafekiosk.domain.stock.Stock;

@Repository
@Transactional
public interface StockRepository  extends JpaRepository<sample.cafekiosk.domain.stock.Stock,Long> {


    List<Stock> findAllByProductNumberIn(List<String> productNumbers);


}
