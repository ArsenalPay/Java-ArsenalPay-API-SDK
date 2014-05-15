package ru.arsenalpay.api.enums;

/**
 * <p>HttpStatus class.</p>
 *
 * Any comments is superfluous.
 *
 * @author adamether
 */
public enum HttpStatus {

    OK(200),
    CREATED(201),
    ACCEPTED(202),
    PARTIAL_INFO(203),
    NO_RESPONSE(204),
    MOVED(301),
    FOUND(302),
    METHOD(303),
    NOT_MODIFIED(304),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    PAYMENT_REQUIRED(402),
    FORBIDDEN(403),
    NOT_FOUND(404),
    INTERNAL_ERROR(500),
    NOT_IMPLEMENTED(501),
    OVERLOADED(502),
    GATEWAY_TIMEOUT(503);

    private final int code;

    HttpStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static boolean success(int code) {
        return code / 100 == 2;
    }

    public static boolean redirect(int code) {
        return code / 100 == 3;
    }

    public static boolean error(int code) {
        return code / 100 == 4 || code / 100 == 5;
    }

}
