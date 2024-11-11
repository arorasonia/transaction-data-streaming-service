package com.example.KafkaToFile.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@Document(value = "Transactions")
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDataVO {

    public TransactionDataVO(){

    }

    @Id
    private String transactionId;
    private String userId;
    private BigDecimal amount;
    private String currency;
    private Date timestamp;
    private List<ItemVO> items;

}
