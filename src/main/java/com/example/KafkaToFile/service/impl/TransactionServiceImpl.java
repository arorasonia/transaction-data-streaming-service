package com.example.KafkaToFile.service.impl;

import com.example.KafkaToFile.repo.ItemRepository;
import com.example.KafkaToFile.repo.TransactionRepository;
import com.example.KafkaToFile.service.TransactionService;
import com.example.KafkaToFile.vo.ItemVO;
import com.example.KafkaToFile.vo.TransactionDataVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import util.CsvToJSonUtil;

import java.io.IOException;
import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveItemData(MultipartFile file) throws IOException {
        try {
            //List<ItemVO> gridList = CsvToJSonUtil.convertExcelSheetTOBasicGridData(file.getInputStream());
            if (!file.isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();
                ItemVO[] itemVOList = mapper.readValue(file.getBytes(), ItemVO[].class);
                itemRepository.saveAll(Arrays.asList(itemVOList));
            }
        } catch (IOException e) {
            throw e;
        } catch (MongoException e) {
            throw e;
        }
    }

    @Override
    public void saveTransactionData(List<TransactionDataVO> transactionDataVO) {
        transactionRepository.saveAll(transactionDataVO);
    }

    @Override
    public void saveTransaction(TransactionDataVO transactionDataVO) {
        transactionRepository.save(transactionDataVO);
    }

    @Override
    public List<TransactionDataVO> getTransactionData() {
        return transactionRepository.findAll();
    }

    @Override
    public Optional<TransactionDataVO> getTransactionById(String transactionId) {
        return transactionRepository.findById(transactionId);
    }

}
