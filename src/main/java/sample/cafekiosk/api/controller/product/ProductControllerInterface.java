package sample.cafekiosk.api.controller.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sample.cafekiosk.api.ApiResponse;
import sample.cafekiosk.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.api.service.product.response.ProductResponse;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "상품 API")
public interface ProductControllerInterface {


    //새로운 판매 상품 등록하기
    @Operation(summary = "상품 등록" , description = "상품의 정보를 받아서 등록을 한다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200" ,description = "판매 등록을 한 상품의 정보를 보여준다.")
    ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request);

    @Operation(summary = "판매 상품 상태 가져오기" , description = "판매 중인것과 판매 보류중인 상품들의 정보를 가져온다")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200" ,description = "판매 중인것과 판매 보류중인 상품들의 정보를 가져온다.")
    ApiResponse<List<ProductResponse>> getSellingProducts();


}
