# Challenge

## :computer: How to execute

 * select project folder and build project using maven (mvn clean install)
   run jar file using "java -jar /target/payments-processor-system-0.0.1-SNAPSHOT.jar" 

## :memo: Notes

 * A spring boot app that listen to the message producer and process payment message

## :pushpin: Things to improve

 * introducing the transaction entity because is very rare that the Payment System use the unique Id generated from the Online/offline console, is more appropriate using a transaction id that is generated by the Payment System Process and correlated with the payment id of the original request.
 * stored every transactions using the log API, refering to a transaction status (the error entity) and to a payment. In this case you can log both errors and succesful operation (a transaction with null error);
 * Introducing in the architecture an "Account-Payment Adaptor" as part of the Account Manager module, just in case in the future want to extend the platform with an external third part online portal, with his own account portfolio.
 * see "Documetation" folder for the Overall Achitecture document.