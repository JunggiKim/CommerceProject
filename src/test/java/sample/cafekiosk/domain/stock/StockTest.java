package sample.cafekiosk.domain.stock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class StockTest {


    @DisplayName("재고의 수량이 제공된 수량보다 작은지 확인한다.")
    @Test
    void isQuantityLessThan(){
        //  given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        //  when
        boolean result = stock.isQuantityLessThan(quantity);

        //  then

        assertThat(result).isTrue();
    }

    @DisplayName("재고를 주어진 개수만큼 차감할 수 있다.")
    @Test
    void deductQuantity() throws IllegalAccessException {
        //  given
        Stock stock = Stock.create("001", 1);
        int quantity = 1;

        //  when
        stock.deductQuantity(quantity);

        //  then

        assertThat(stock.getQuantity()).isZero();
    }

    @DisplayName("재고 보다 많은 수의 수량으로 차감되는 경우 예외가 발생한다.")
    @Test
    void deductQuantity2() throws IllegalAccessException {
        //  given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        //  then when
        assertThatThrownBy(() -> stock.deductQuantity(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("차감할 재고 수량이 없습니다.");
    }


//    @DisplayName("동시 요청시 락을 걸고 정상흐름으로 흘러간다")
//    @Test
//    public void 동시요청발생시락처리후정상흐름으로흘러간다() throws InterruptedException {
//    int threadCount = 100;
//    //멀티스레드 이용 ExecutorService : 비동기를 단순하게 처리할 수 있또록 해주는 java api
//    ExecutorService executorService = Executors.newFixedThreadPool(32);
//        Stock stock = Stock.create("001", 100);
//        //다른 스레드에서 수행이 완료될 때 까지 대기할 수 있도록 도와주는 API - 요청이 끝날때 까지 기다림
//    CountDownLatch latch = new CountDownLatch(threadCount);
//
//    for (int i = 0; i < threadCount; i++) {
//        executorService.submit(() -> {
//                try {
//                    stock.deductQuantity(1);
//                } catch (IllegalAccessException e) {
//                    throw new RuntimeException(e);
//                } finally {
//                    latch.countDown();
//                }
//            }
//        );
//    }
//
//    latch.await();
//
//
//
//    assertThat(stock.getQuantity()).isEqualTo(0);
//
//}



}