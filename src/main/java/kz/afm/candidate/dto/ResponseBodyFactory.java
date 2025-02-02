package kz.afm.candidate.dto;

import lombok.Getter;

@Getter
public class ResponseBodyFactory<T> {
    private T data;
    private final String message;

    private ResponseBodyFactory(String message) {
        this.message = message;
    }

    private ResponseBodyFactory(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public static <T> ResponseBodyFactory<T> success() {
        return new ResponseBodyFactory<>("Success");
    }

    public static <T> ResponseBodyFactory<T> success(T data) {
        return new ResponseBodyFactory<>(data, "Success");
    }

    public static <T> ResponseBodyFactory<T> error(String message) {
        return new ResponseBodyFactory<>(message);
    }

}
