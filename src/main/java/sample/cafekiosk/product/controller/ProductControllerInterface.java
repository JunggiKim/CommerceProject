package sample.cafekiosk.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestBody;
import sample.cafekiosk.common.CustomApiResponse;
import sample.cafekiosk.product.request.ProductCreateRequest;
import sample.cafekiosk.product.response.ProductResponse;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "상품 API")
public interface ProductControllerInterface {


    //새로운 판매 상품 등록하기
    @Operation(summary = "상품 등록" , description = "상품의 정보를 받아서 등록을 한다.")
    @ApiResponse(responseCode = "200" ,description = "판매 등록을 한 상품의 정보를 보여준다.")
    CustomApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request);

    @Operation(summary = "판매 상품 상태 가져오기" , description = "판매 중인것과 판매 보류중인 상품들의 정보를 가져온다")
    @ApiResponse(responseCode = "200" ,description = "판매 중인것과 판매 보류중인 상품들의 정보를 가져온다.")
    CustomApiResponse<List<ProductResponse>> getSellingProducts();


}
