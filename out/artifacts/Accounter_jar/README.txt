Параметры:
Для environment.properties: -Dprops
Для логгера: -Dlog4j2.configurationFile

Методы:
manageBalance(String action, long transactionId) — Корневой метод для управления текущим балансом
calculateBalance() — Метод для вычисления текущего баланса, используя все записанные транзацкии, и добавления его в список балансов
displayIncomesAndOutcomes() — Метод для отображения всех записанных транзакций 
repeatTransaction(long transactionId) — Метод для повторения выбранной транзакции
makePlanBasedOnTransaction(long transactionId) — Метод для создания плана на основе выбранной транзакции
managePlans(long planId, boolean execute) — Корневой метод для управления существующими планами
displayPlans() — Метод для отображения всех записанных планов
executePlanNow(long planId) — Метод добавления транзакции выбранного плана

Типы источников данных:
CSV
XML
JDBC

Параметры запуска:
java -jar -Dprops=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./Accounter.jar XML MANAGEBALANCE
java -jar -Dprops=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./Accounter.jar XML MANAGEBALANCE repeat 1
java -jar -Dprops=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./Accounter.jar XML MANAGEBALANCE plan 1
java -jar -Dprops=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./Accounter.jar XML MANAGEPLANS
java -jar -Dprops=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./Accounter.jar XML MANAGEPLANS 1
java -jar -Dprops=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./Accounter.jar XML MANAGEPLANS 1 true