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
    * Create promotion topic:
         kafka-topics.sh --bootstrap-server localhost:9092 --create --topic t.commodity.promotion --partitions 1 --replication-factor 1
         kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic t.commodity.promotion
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

### 3.1. Kafka Stream Commodity:
1. First step masked creditCardNumber then send the object order to t.commodity.order-masked topic
2. Sink Processors in CommodityOneStream (mapValues, filter, )
3. Additional stream operations in CommodityTwoStream (branch, filterNot, selectKey)
4. Branching Alternative in CommodityThreeStream (KafkaStreamBrancher)
5. Reward Each Location: To change original key by location value
6. Calling API or Other Process (Something Suspicious: Fraud processing)

### 3.2. Kafka Stream Feedback:  
1. Add the good feedback to "t.commodity.feedback-one-good" And find the good word in text in FeedbackOneStream "mapperGoodWords()"
2. Add branchLocation as the key and value "good word" (Who Owns This Feedback)
3. Stream to analyze feedback(good or bad) from t.commodity.feedback topic to t.commodity.feedback-good-word or t.commodity.feedback-bad-word
4. Group using KTable for count the good word and count the bad word after send it to stream
5. Delay on table
6. Send to topic and continue (with through)
7. Overall good or bad : additional requirement (use value as key mapper)

### 3.6. Flash Sale Vote Stream:
1. Use kTable for save the latest itemName choosing by customer in flashsaleVote
2. Statefull in kafka stream
3. transformValues and Timestamp (is a mechanism to associate a date and time to an event):
   * Use LocalDateTimeUtil class to convert LocalDateTime to epoch time.
   * We use transformValues to make an epoch (startTime and endTime) to vote 
   * groupedItem by itemValue in "t.commodity.flashsale.vote-two-result" <br/>
:: ==>> :: [The Processor API allows developers to define and connect custom processors and to interact with 
   state stores. With the Processor API, you can define arbitrary stream processors that 
   process one received record at a time, and connect these processors with their associated
   state stores to compose the processor topology that represents a customized processing logic.](https://docs.confluent.io/platform/current/streams/developer-guide/processor-api.html#kstreams-processor-api)

### 3.7. Feedback Rating Stream:
1. Average Rating (Feedback rating dashboard by country):
   * AVG = sum(ratings)/ count(ratings)
   * Need to know all ratings (use state store)
   * Use processor API to interact with state store
2. Detailed rating by Branch (location):
   * run postman random feedback to test

### 3.8. Inventory Stream:
1. Summing records by Item and quantity
2. Subtracting Value: if type not "ADD" remove quantity 
    - using Reduce
3. Timestamp Extractor (We need the transaction time when we work with legacy system that is not directly publish to kafka):
    - convert timestamp to epoch https://www.epochconverter.com/ (You will see that it has same exact time with value on message payload)
4. Windowing (Windowing in kafka stream control how to group records for same key: 
   - Tumbling Time window: test inventory window collection in postman: <br/>
     For example: Tell me the average number of visitors per booth over the last 10 seconds every 10 seconds
   - Hopping Time window - continued: <br/>
     For example: Tell me the average number of visitors per booth over the last 10 seconds every 5 seconds
   - Sliding Window: <br/>
     For example: Tell me the average number of visitors per booth in the last 10 seconds

### 3.9. Joining (innerJoin, leftJoin and outJoin) Order & Payment Streams (Stream - Stream)
       - join with timestamp

### 3.10. Joining (innerJoin, leftJoin and outJoin) Two Vote Tables (Table - Table)
    - With intermediary topic
    - With toTable() : stream.toTable()