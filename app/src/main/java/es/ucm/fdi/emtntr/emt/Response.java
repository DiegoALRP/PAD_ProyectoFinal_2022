package es.ucm.fdi.emtntr.emt;

public class Response<T> {

    private final String code;
    private final String message;
    private final T data;

    public Response(String code, String msg) {
        this(code, msg, null);
    }

    public Response(String code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
