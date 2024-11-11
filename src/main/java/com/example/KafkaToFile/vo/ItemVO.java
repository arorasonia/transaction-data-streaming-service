package com.example.KafkaToFile.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@Document(value = "Items")
public class ItemVO {

    public ItemVO() {
    }

    @Id
    private String itemId;
    private String name;
    private BigDecimal price;
    private Long quantity;
    private String Description;
    private Boolean inStock;
    private String category;
}
