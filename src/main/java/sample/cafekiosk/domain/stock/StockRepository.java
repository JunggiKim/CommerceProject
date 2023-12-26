package sample.cafekiosk.domain.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.LockModeType;

@Repository
@Transactional
public interface StockRepository  extends JpaRepository<Stock,Long> {


    List<Stock> findAllByProductNumberIn(List<String> productNumbers);


}
