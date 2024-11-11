package com.example.KafkaToFile.controller.v1;


import com.example.KafkaToFile.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import util.CsvToJSonUtil;

import java.io.IOException;

@RestController
@RequestMapping("/csvFile/v1.0")
public class ItemController {

    @Autowired
    TransactionService transactionService;

    @PostMapping(value="/itemData/save",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveItemData(@RequestParam("file")MultipartFile file){
        try {
            if (file.isEmpty())
                return ResponseEntity.badRequest().body("Invalid Request. File is Empty or null");
            transactionService.saveItemData(file);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
        return ResponseEntity.ok("Data is saved to db for basicLifeGrid");

    }

    @PostMapping(value="/ItemCSV/convert", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> convertItemCSVToJsonData(@RequestParam("file")MultipartFile file){
        if(file.isEmpty())
            return ResponseEntity.badRequest().body("Invalid Request. File is Empty or null");
        try {
            return ResponseEntity.ok(CsvToJSonUtil.csvToJsonProducer(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
