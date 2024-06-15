package sample.cafekiosk.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomApiResponse<T> {

    private int code;
    private HttpStatus status;


    private String message;

    private T data;

    public CustomApiResponse(HttpStatus httpStatus, String message ,T data) {
        this.code = httpStatus.value();
        this.status = httpStatus;
        this.message = message;
        this.data = data;
    }


    public static <T> CustomApiResponse<T> of(HttpStatus httpStatus,String message, T data) {
        return new CustomApiResponse<>(httpStatus, message ,data);
    }



    public static <T> CustomApiResponse<T> of(HttpStatus httpStatus, T data) {
    return new CustomApiResponse<>(httpStatus, httpStatus.name(),data);
    }

    public static <T> CustomApiResponse<T> ok(T data) {
        return of(HttpStatus.OK, data);
    }



}
