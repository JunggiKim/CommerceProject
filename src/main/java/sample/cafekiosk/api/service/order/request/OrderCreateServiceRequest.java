package sample.cafekiosk.api.service.order.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class OrderCreateServiceRequest {


    @NotEmpty(message = "상품 번호 리스트는 필수입니다.")
    private List<String> productNumbers;

    private String userEmail;

    @Builder
    public OrderCreateServiceRequest(List<String> productNumbers,String userEmail) {
        this.productNumbers = productNumbers;
        this.userEmail = userEmail;
    }


}
