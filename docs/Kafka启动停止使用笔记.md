1. 启动zookeeper
    ```text
    .\bin\zkServer.cmd
    ```
2. 启动kafka
    ```text
    .\bin\windows\kafka-server-start.bat .\config\server.properties
    ```
3. 创建topic
    ```text
    .\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic kafkatest
    ```
4. 查看topic
    ```text
    .\kafka-topics.bat --bootstrap-server localhost:9092 --list
    ```
5. 生产者
    ```text
    .\kafka-console-producer.bat --broker-list localhost:9092 --topic kafka-test
    ```
6. 消费者
    ```text
    .\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic outputChannel-in-0 --from-beginning
    ```