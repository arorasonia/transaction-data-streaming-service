package com.example.KafkaToFile.controller.v1;

import com.example.KafkaToFile.service.TransactionService;
import com.example.KafkaToFile.vo.TransactionDataVO;
import com.mongodb.MongoException;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transaction/v1.0")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    @PostMapping(value = "/saveAll")
    public ResponseEntity<String> saveTransactions(@RequestBody List<TransactionDataVO> transactionDataList) {
        try {
            LOGGER.info("save Transactions started: ");
            transactionService.saveTransactionData(transactionDataList);
        } catch (MongoException e) {
            LOGGER.error("Error occurred while saving Transactions: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        LOGGER.info("save Transactions completed");
        return ResponseEntity.ok("Transaction Data Successfully Saved");
    }

    @PostMapping(value = "/save")
    public ResponseEntity<String> saveTransaction(@RequestBody TransactionDataVO transactionData) {
        try {
            LOGGER.info("save Transaction started: ");
            transactionService.saveTransaction(transactionData);
        } catch (MongoException e) {
            LOGGER.error("Error occurred while saving Transaction: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        LOGGER.info("save Transaction completed ");
        return ResponseEntity.ok("Transaction Successfully Saved");
    }

    @GetMapping(value = "/getAllTransactions")
    public ResponseEntity<List<TransactionDataVO>> getTransactions() {
        List<TransactionDataVO> transactionDataList = null;
        try {
            LOGGER.info("get all Transactions started");
            transactionDataList = transactionService.getTransactionData();
        } catch (MongoException e) {
            LOGGER.error("Error occurred while getting Transactions: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        LOGGER.info("save Transactions completed");
        return new ResponseEntity<>(transactionDataList, HttpStatus.OK);
    }

    @GetMapping(value = "/getTransactionById")
    public ResponseEntity<TransactionDataVO> getTransactionById(@RequestParam("id") String transactionId) {
        LOGGER.info("get Transaction by Id started");
        Optional<TransactionDataVO> transaction = transactionService.getTransactionById(transactionId);
        if (!transaction.isPresent()) {
            LOGGER.error("Error occurred while getting Transaction By Id: " + transactionId);
            throw new ResourceNotFoundException("Transaction Data not found for transaction ID: " + transactionId);
        }
        LOGGER.info("get Transaction by Id completed");
        return new ResponseEntity<>(transaction.get(), HttpStatus.OK);
    }
}
