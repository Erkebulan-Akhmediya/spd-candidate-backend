package kz.afm.candidate.dto;

import lombok.Getter;

@Getter
public class ResponseBodyWrapper<T> {
    private T data;
    private final String message;

    private ResponseBodyWrapper(String message) {
        this.message = message;
    }

    private ResponseBodyWrapper(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public static <T> ResponseBodyWrapper<T> success() {
        return new ResponseBodyWrapper<>("Success");
    }

    public static <T> ResponseBodyWrapper<T> success(T data) {
        return new ResponseBodyWrapper<>(data, "Success");
    }

    public static <T> ResponseBodyWrapper<T> error(String message) {
        return new ResponseBodyWrapper<>(message);
    }

}
