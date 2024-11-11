package com.example.KafkaToFile.service;

import com.example.KafkaToFile.vo.TransactionDataVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TransactionService {

    void saveItemData(MultipartFile file) throws IOException;

    void saveTransaction(TransactionDataVO transactionDataVO);

    void saveTransactionData(List<TransactionDataVO> transactionDataVO);

    List<TransactionDataVO> getTransactionData();

    Optional<TransactionDataVO> getTransactionById(String transactionId);
}
