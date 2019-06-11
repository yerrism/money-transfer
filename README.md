# money-transfer
Money transfer PoC for monese

This application exposes two endpoints:
- /moneyTransfer: performs a credit transfer between two accounts
- /accountDetails: provides account details, including balance and transaction history

The application uses an in-memory h2 database that gets created on-the-fly using the src/main/resources/schema.sql script
and populated using src/main/resources/data.sql script.
The scripts create two tables (Account and Transaction) and populate the account table with two accounts (11111111 and 22222222), the former having a balance of 300 GBP, the latter having 200 GBP.
In order to install the application, please run the following command:
mvn clean install
from the project root folder (money-transfer).


The above command will also run the three test cases that are defined in src/test/java under the com.monese.marra.transfer.controller package.
The first test case uses the /accountDetails endpoint to verify the initial balance for 11111111 and 22222222.
It then invokes /moneyTransfer to transfer 25 GBP from 11111111 to 22222222 and verifies that the new balance for 11111111 is 275 GBP and 225 for 22222222.
Finally, it tries a transfer between 11111111 and a non-existing XXXXXXXX account, then verifies that the balance for 11111111 is still 275 GBP.
The second test case verifies that an attempt to transfer 1000 GBP from 11111111 results in an InsufficientFundsException.
The third test case verifies that an attempt to retrieve the details of a non-existing account results in an AccountNotFoundException.
The test cases can also be run separately from the install using the following command:
mvn clean test


In order to run the application in stand-alone mode, please run the following command:
mvn spring-boot:run
This will run the application on the 8081 port. If the port is already in use, please change the content of the src/main/resources/application.yml file accordingly (property server:port)
In order to test the /moneyTransfer endpoint, please use HTTP method = "POST", Content-type = "application/json" and use the content of src/main/resources/files/TransferRequest.json  as request body. 
The endpoint will return an HTTP status = 200 and the message "Money Transfer successfully executed" in the response body.
As for the /accountDetails endpoint, please specify HTTP method = "GET" and the following query string /accountDetails?accountNumber=11111111 or /accountDetails?accountNumber=22222222. Any other account specified in the query string should result in the "Account not found" error message.

Reference URLs:
http://localhost:8081/accountDetails?accountNumber=11111111
http://localhost:8081/moneyTransfer