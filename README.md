Java ArsenalPay API SDK
=========

<p><a href="http://www.arsenalmedia.ru/index.php/en">Arsenal Media LLC</a></p>
<p><a href="https://arsenalpay.ru">ArsenalPay processing server</a></p>


<p>Java ArsenalPay API SDK is software development kit for 
fast simple and seamless integration your java application with processing server of ArsenalPay.</p>

Version
----

1.0

JDK version requirements
----

min 1.6

Source
----

<a href="https://arsenalpay.ru/developers.html">Official integration guide page</a>

Building artifact
----

``mvn package`` for building single jar file with all dependencies.

Configuration
----

Copy ``conf`` with properties to the root directory of your project 
another you will get ``ConfigurationLoadingException`` 


Functions of API
----

- Mobile commerce.

<b>RequestPayment method. Example code for mobile charge:</b>

```java 

ApiCommandsFacade apiCommandsFacade = new ApiCommandsFacadeImpl(
        new MerchantCredentials("2096", "qwerty")
);

PaymentRequest paymentRequest = new PaymentRequest.MobileBuilder()
        .payerId(9140001111L)
        .recipientId(123456789L)
        .amount(12.5D)
        .currency("RUR")
        .comment("Java-SDK-Test")
        .setTestMode()
        .build();

PaymentResponse paymentResponse = apiCommandsFacade.requestPayment(paymentRequest);

```        

<p>See more details in JavaDoc.</p>

<b>CheckPaymentStatus method. Example code:</b>

```java  

PaymentStatusResponse paymentStatusResponse = apiCommandsFacade.checkPaymentStatus(
        new PaymentStatusRequest(1228221L)
);

// where 1228221 is payment transaction id

```

<p>See more details in JavaDoc.</p>

