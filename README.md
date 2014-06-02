Java ArsenalPay API SDK
=========

<b>Arsenal Media LLC</b>
<b>ArsenalPay payment system</b>

<p>Java ArsenalPay API SDK is software development kit for 
fast simple and seamless integration your java application with payments processing server of ArsenalPay.</p>

Version
----

1.0

Building
----

``mvn package`` for building single jar file with all dependencies.

Functions of API
----

- Mobile commerce.

<b>RequestPayment method. Example code for mobile charge:</b>

```java 
PaymentRequest paymentRequest = new PaymentRequest.MobileBuilder()
                .payerId(9140001111L)
                .recipientId(123456789L)
                .amount(1.25D)
                .currency("RUR")
                .comment("Java-SDK-Test")
                .setTestMode()
                .build();

PaymentResponse paymentResponse = apiCommandsFacade.requestPayment(paymentRequest);
```        

<b>CheckPaymentStatus method. Example code:</b>

```java  
PaymentStatusRequest paymentStatusRequest = new PaymentStatusRequest(123345L);

// where 123345 is transaction id

PaymentStatusResponse paymentStatusResponse = apiCommandsFacade.checkPaymentStatus(paymentStatusRequest);
``` 

