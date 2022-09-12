## Kafka & Kafka Stream With Java Spring Boot tutorials
### 1. Basic kafka spring boot
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
