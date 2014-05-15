package ru.arsenalpay.api.client;

import ru.arsenalpay.api.command.ApiCommand;
import ru.arsenalpay.api.exception.InternalApiException;

import java.io.IOException;

/**
 *  <p>ApiClient interface is need for communications with server of ArsenalPay API.</p>
 *
 *  Implementation can be different wrappers for http clients.
 *  Feel free to add your favorite http client or use our choice.
 *
 *  @author adamether
 */
public interface ApiClient {

    /**
     * Execute api command and nothing else
     * @param command -- projection of server api command protocol
     * @return apiResponse -- server api response in xml format body
     * @throws IOException
     * @throws ru.arsenalpay.api.exception.InternalApiException
     */
    ApiResponse executeCommand(ApiCommand command) throws IOException, InternalApiException;

}
