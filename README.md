Java ArsenalPay API SDK
=========

<b>Arsenal Media LLC</b>
<b>ArsenalPay payment system</b>

<p>Java ArsenalPay API SDK is software development kit for 
fast simple and seamless integration your java application with payments processing server of ArsenalPay.</p>

Version
----

0.9

<p>The functional of API</p>

<p>Now there is only mobile payments api methods:</p>

<p>RequestPayment method. Example code:</p>

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

<p>CheckPaymentStatus method. Example code:</p>

```java 
PaymentStatusRequest paymentStatusRequest = new PaymentStatusRequest(2096L, 123345L);

PaymentStatusResponse paymentStatusResponse = apiCommandsFacade.checkPaymentStatus(paymentStatusRequest);
``` 

