package ru.arsenalpay.api.enums;

/**
 * <p>Server API OperationStatus -- this model describes the queries life status.</p>
 *
 * @author adamether
 */
public enum OperationStatus {

    /**
     * <p>The query was not registered on server equals that user doesn't send payment.</p>
     *
     * Api protocol equivalent: "NO_ANSWER"
     */
    NOT_REGISTERED,

    /**
     * <p>User send payment request.</p>
     *
     * Api protocol equivalent: "OK_ANSWER"
     */
    IN_PROGRESS,

    /**
     * <p>Payment has been performed.</p>
     *
     * Api protocol equivalent: "OK_PAY"
     */
    SUCCESS,

    /**
     * <p>An error occurred during the processing of payment.</p>
     *
     * Api protocol equivalent: "ERR_PAY"
     */
    REFUSED

}
