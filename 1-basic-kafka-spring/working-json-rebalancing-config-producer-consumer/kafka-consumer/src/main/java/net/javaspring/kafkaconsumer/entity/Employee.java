package net.javaspring.kafkaconsumer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaspring.kafkaconsumer.entity.json.LocalDateDeserializer;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @JsonProperty("employee_id")
    private String employeeId;
    @JsonProperty("employee_name")
    private String name;

    @JsonProperty("brith_date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate brithDate;
}
