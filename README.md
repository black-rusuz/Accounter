# Accounter

Course project for SFedU. This API provides some tools for control your money.

### Version 0.2:
- Refactoring for all DataProviders done *
- Tests for all DataProviders' CRUD are ready with the cleanest code **
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

## Run commands examples
### 1. Start with this:
```
java -jar ./Accounter.jar
```
### 2. Continue with these (if needed):
- ```[-Dprops=<your .properties file>]```
- ```[-Dlog4j2.configurationFile=<your log4j2 file>]```
### 3. End with one of these:
- ```XML MANAGEBALANCE```
- ```XML MANAGEBALANCE repeat 1```
- ```XML MANAGEBALANCE plan 1```
- ```XML MANAGEPLANS```
- ```XML MANAGEPLANS 1```
- ```XML MANAGEPLANS 1 true```

## TODO's:
- To make more beautiful (tabular?) outputs with date and time

## Known issues (*):
- DataProviders for CSV and JSBC aren't perfect. Need to fix ```CsvConverters``` and ```JdbcUtil```
- Cheating in tests for passing them