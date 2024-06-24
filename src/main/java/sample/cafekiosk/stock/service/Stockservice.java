package sample.cafekiosk.stock.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sample.cafekiosk.product.Product;
import sample.cafekiosk.product.ProductRepository;
import sample.cafekiosk.stock.Stock;
import sample.cafekiosk.stock.StockRepository;

@Transactional(isolation = Isolation.SERIALIZABLE)
@Service
@RequiredArgsConstructor
@Slf4j
public class Stockservice {

  private final StockRepository stockRepository;

  private final ProductRepository productRepository;

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public void deductStockQuantities(List<Product> products) throws IllegalAccessException {
        //재고 차감 체크가 필요한 상품들 filter
        List<String> stockProductNumbers = extractStockProductNumbers(products);

        //재고 엔티티 조회
        Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);

        //상품별 counting
        Map<String, Long> productCountingMap = createCuntingMapBy(stockProductNumbers);

        //재고 차감 시도
        for (String stockProductNumber : new HashSet<>(stockProductNumbers)) {
            Stock stock = stockMap.get(stockProductNumber);
            int quantity = productCountingMap.get(stockProductNumber).intValue();

            if(stock.isQuantityLessThan(quantity)){
                throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
            }
            stock.deductQuantity(quantity);
        }
    }


	private static Map<String, Long> createCuntingMapBy(List<String> stockProductNumbers) {
        return stockProductNumbers.stream()
                .collect(Collectors.groupingBy(productNumber -> productNumber, Collectors.counting()));

    }

    private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);

        return stocks.stream()
                .collect(Collectors.toMap(Stock::getProductNumber, stock -> stock));
    }

    private static List<String> extractStockProductNumbers(List<Product> products) {
        return products.stream().
                filter(product -> ProductType.containsStockType(product.getType()))
                .map(Product::getProductNumber)
                .collect(Collectors.toList());
    }





}
