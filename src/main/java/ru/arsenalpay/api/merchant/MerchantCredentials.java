package ru.arsenalpay.api.merchant;

/**
 * <p>Credentials of merchant.</p>
 *
 * <p>Assumed that these data were obtained via a secure protocol, confidential and is the primary identifier merchant
 * in information exchange with the payment center arsenal pay including all requests must be signed using this data</p>
 *
 * @author adamether
 */
public final class MerchantCredentials {

    private final String id;
    private final String secret;

    public MerchantCredentials(String id, String secret) {
        this.id = id;
        this.secret = secret;
    }

    public String getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

}
