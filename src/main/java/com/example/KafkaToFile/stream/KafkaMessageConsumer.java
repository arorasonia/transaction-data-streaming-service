package com.example.KafkaToFile.stream;

import com.example.KafkaToFile.service.TransactionService;
import com.example.KafkaToFile.vo.TransactionDataVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageConsumer {
    private Logger LOGGER = LoggerFactory.getLogger(KafkaMessageConsumer.class);
    @Autowired
    TransactionService transactionService;

    @KafkaListener(topics = "jsonTopic", groupId = "my-group")
    public void consume(String value) {
        try {
            LOGGER.info("Transaction Data  consumed");
            ObjectMapper mapper = new ObjectMapper();
            TransactionDataVO transactionDataVO = mapper.readValue(value, TransactionDataVO.class);
            transactionService.saveTransaction(transactionDataVO);
            LOGGER.info("Transaction Data saved to Db");
        } catch (Exception e) {
            LOGGER.error("Exception occurred while mapping Data to Transaction: " + e.getMessage());
        }

    }

    /*@KafkaListener(topics = "jsonTopic", groupId = "my-group")
    public void consume(EmployeeDetail value , String topic){
        try {
            JsonObject jsonObject = new Gson().fromJson(value, JsonObject.class);
            LOGGER.trace(jsonObject.toString());
        }catch(Exception e){
            LOGGER.error(e.getMessage());
        }

    }*/
}

