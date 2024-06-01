package sample.cafekiosk.api.service.stock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import sample.cafekiosk.domain.product.Product;
import sample.cafekiosk.domain.product.ProductRepository;
import sample.cafekiosk.domain.stock.Stock;
import sample.cafekiosk.domain.stock.StockRepository;

@SpringBootTest
class StockserviceTest {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private Stockservice stockservice;

	@Autowired
	private ProductRepository productRepository;

//	@DisplayName("재고가 동시성으로 요청이 올시 순차적으로 사라진다.")
//	@Test
//	@Rollback(value = false)
//	void deductStockQuantities() throws IllegalAccessException, InterruptedException {
//		LocalDateTime registeredDateTime = LocalDateTime.now();
//		Stock stock = Stock.create("001", 100);
//
//		stockRepository.save(stock);
//
//		List<Product> produts = productRepository.findAllByProductNumberIn(List.of("001"));
//
//		//  when
//
//		int threadCount = 100;  //100개의 요청을 보냄
//		ExecutorService executorService = Executors.newFixedThreadPool(32);
//		CountDownLatch latch = new CountDownLatch(threadCount);
//
//		for (int i = 0; i < threadCount; i++) {
//			int finalI = i;
//			executorService.submit(() -> {
//				try {
//					try {
//						stockservice.deductStockQuantities(produts);
//						System.out.println("도는 횟수!!!"+ finalI);
//					} catch (IllegalAccessException e) {
//						throw new RuntimeException(e);
//					}
//				} finally {
//					latch.countDown();
//				}
//			});
//		}
//		latch.await();
//		Assertions.assertThat(stock.getQuantity()).isEqualTo(0);
//	}

}