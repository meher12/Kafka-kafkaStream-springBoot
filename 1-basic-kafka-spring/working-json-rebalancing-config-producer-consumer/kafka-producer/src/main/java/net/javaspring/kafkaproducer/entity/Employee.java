package net.javaspring.kafkaproducer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaspring.kafkaproducer.entity.json.LocalDateSerializer;


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
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate brithDate;
}
