package ru.arsenalpay.api.command;

/**
 * <p>Interface of producing API specific command (server api response)</p>
 *
 * @see ru.arsenalpay.api.command.impl.ApiCommandProducerImpl
 * @see ru.arsenalpay.api.command.ApiCommand
 *
 * @author adamether
 */
public interface ApiCommandProducer {

    ApiCommand getCommand();

}
