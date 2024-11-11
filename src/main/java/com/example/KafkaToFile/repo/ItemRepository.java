package com.example.KafkaToFile.repo;

import com.example.KafkaToFile.vo.ItemVO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<ItemVO,String> {
}
