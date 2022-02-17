# Accounter

Course project for SFedU. This API provides some tools for control your money.

### Version 0.2:
- Refactoring for all DataProviders done
- Tests for all DataProviders' CRUD are ready with the cleanest code
- Added tests for Use Cases methods
- Added good CLI for Use Cases (but not for CRUD);

## Methods
- _**manageBalance**(String action, long transactionId)_ — Root use case for managing current balance
- _**calculateBalance**()_ — Calculates current balance using all written transactions and appends it to Balance list
- _**displayIncomesAndOutcomes**()_ — Displays all written transactions
- _**repeatTransaction**(long transactionId)_ — Repeats selected transaction
- _**makePlanBasedOnTransaction**(long transactionId)_ — Creates plan based on selected transaction
- _**managePlans**(long planId, boolean execute)_ — Root use case for managing existing plans
- _**displayPlans**()_ — Displays all written plans
- _**executePlanNow**(long planId)_ — Appends transaction of selected plan

## Parameters

- For **environment.properties** file: ```-Dprops```
- For **log4j2** file: ```-Dlog4j2.configurationFile```

### Datasources types:
- ```CSV```
- ```XML```
- ```JDBC```

## Run commands constructor
### 1. Start with this:
```java -jar```
### 2. Continue with these (if needed):
- ```-Dprops=<your .properties file>```
- ```-Dlog4j2.configurationFile=<your log4j2 file>```
### 3. Specify .jar file:
``` ./Accounter.jar```
### 4. End with any of these:
- ```XML MANAGEBALANCE```
- ```CSV MANAGEBALANCE repeat <transactionId>```
- ```JDBC MANAGEBALANCE plan <transactionId>```
- ```XML MANAGEPLANS```
- ```CSV MANAGEPLANS <planId>```
- ```JDBC MANAGEPLANS <planId> true```

### For example:
- ```java -jar ./Accounter.jar XML MANAGEBALANCE REPEAT 123```
- ```java -jar -Dprops="environment.properties" ./Accounter.jar CSV MANAGEPLANS```
- ```java -jar -Dprops="environment.properties" -Dlog4j2.configurationFile=log4j2.xml ./Accounter.jar JDBC MANAGEPLANS 123 TRUE```

## TODO's:
- Maybe make CRUD for CLI?

## Known issues (*):
(づ｡◕‿‿◕)づ

All known fixed.
