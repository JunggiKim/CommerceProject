package sample.cafekiosk.api.controller.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import sample.cafekiosk.api.CustomApiResponse;
import sample.cafekiosk.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.api.service.order.response.OrderResponse;

import javax.validation.Valid;


@Tag(name = "주문 API")
public interface OrderControllerInterface {


        @Operation(summary = "주문 등록" , description = "상품 번호 리스트를 받아서 주문 등록을 한다.")
        @ApiResponse(responseCode = "200" ,description = "주문 등록을 한 주문의 정보를 보여준다.")
        CustomApiResponse<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest request) throws Exception;

}
