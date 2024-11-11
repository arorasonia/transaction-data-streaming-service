package com.example.KafkaToFile.repo;

import com.example.KafkaToFile.vo.TransactionDataVO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<TransactionDataVO,String> {
}
