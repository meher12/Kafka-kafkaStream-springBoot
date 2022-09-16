## Kafka & Kafka Stream With Java Spring Boot tutorials
### 1. Basic kafka spring boot:
1. Multiple Consumers for each Topic
2.  Working with json, call commodity rest api by commodity scheduler, producer and consumer config, rebalancing and adding Message Filter for Listeners with "containerFactory"
3. Handling Exception:
  * KafkaListener Error Handler.
  * Global Error Handler
  * Retrying Consumer (RetryTemplate)
  * Dead Letter Topic (Dead Letter Queues):
     - root@1f282ce576e1:/# kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic t_invoice --offset earliest --partition 0
     - root@1f282ce576e1:/# kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic t_invoice --offset earliest --partition 1
     - root@1f282ce576e1:/# kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic t_invoice_dlt --offset earliest --partition 0
     - root@1f282ce576e1:/# kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic t_invoice_dlt --offset earliest --partition 1
### 2. Kafka in Microservice Architecture and Pattern:
1. Setting Up the Order producer project:
    * Create entities
    * Create producer
    * Create repository
    * Auto create topic
    * Create broker message and promotion
    * Handle kafka publish result (addCallback()/get()) for OrderProducer
    * Create Order, Promotion API (controller, service, request and response, action)
    *  * create promotion topic:
         kafka-topics.sh --bootstrap-server localhost:9092 --create --topic t.commodity.promotion --partitions 1 --replication-factor 1
    * kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic t.commodity.promotion
    * Test API with postman json file collection
    * Add header to order producer, pattern and reward consumer to read the headers and body message (by send the order from postman for get a surprise bonus)
   
2. Setting Up the pattern consumer project
3. Setting Up the reward consumer project
4. Setting Up the storage consumer project, send two types of message to the same topic with these annotations :
    ```
    @KafkaListener(topics = "t.commodity.promotion", groupId = "cg-storage")
    AND
    @KafkaHandler
    ```
5. Asynchronous Request/Reply (Order & Reward) --- Replying with @SendTo
### 3. Kafka Streams:
1. Create project kafka-stream
2. Use Serdes.String()
3. Using Json Serde
4. Use Custom Json Serde

### 4. Kafka Stream Commodity:
1. First step masked creditCardNumber then send the object order to t.commodity.order-masked topic
2. Sink Processors in CommodityOneStream (mapValues, filter, )
3. Additional stream operations in CommodityTwoStream (branch, filterNot, selectKey)
4. Branching Alternative in CommodityThreeStream (KafkaStreamBrancher)
5. Reward Each Location: To change original key by location value
6. Calling API or Other Process (Something Suspicious: Fraud processing)

  

