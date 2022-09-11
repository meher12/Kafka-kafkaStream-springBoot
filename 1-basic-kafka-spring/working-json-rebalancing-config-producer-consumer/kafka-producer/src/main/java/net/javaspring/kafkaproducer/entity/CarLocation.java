package net.javaspring.kafkaproducer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarLocation {

    @JsonProperty("car_id")
    private String carId;
    private long timestamp;
    private int distance;

}
