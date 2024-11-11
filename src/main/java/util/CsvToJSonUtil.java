package util;

import com.example.KafkaToFile.vo.ItemVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CsvToJSonUtil {

    public static List<ItemVO> convertExcelSheetTOBasicGridData(InputStream inputStream) throws IOException {
        XSSFWorkbook sheets = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = sheets.getSheetAt(0);
        int rowNumber = 0;
        int mapRow = 1;
        List<ItemVO> itemVOList = new ArrayList<>();
        for (Row row : sheet) {
            if (rowNumber == 0) {
                rowNumber++;
                continue;
            }
            Iterator<Cell> cells = row.cellIterator();
            ItemVO itemVO = new ItemVO();
            Map<String, String> map;
            List<Map<String, String>> arrayList = new ArrayList<>();
            int cid = 0;
            while (cells.hasNext()) {
                Cell cell = cells.next();
                setExcelValues(cid, itemVO, cell);
                cid++;
            }
            itemVOList.add(itemVO);
            mapRow++;
        }
        return itemVOList;
    }


    private static void setExcelValues(int cid, ItemVO item, Cell cell) {

        switch (cid) {
            case 0:
                item.setItemId(cell.getStringCellValue());
            case 1:
                item.setName(cell.getStringCellValue());
            case 2:
                item.setDescription(cell.getStringCellValue());
            case 3:
                item.setPrice(new BigDecimal(cell.getStringCellValue()));
            case 4:
                item.setQuantity(Long.parseLong(cell.getStringCellValue()));
            case 5:
                item.setCategory(cell.getStringCellValue());
            case 6:
                item.setInStock(Boolean.valueOf(cell.getStringCellValue()));
            default:
                break;
        }
    }

    public static String csvToJsonProducer(MultipartFile file) throws IOException {
        Pattern pattern = Pattern.compile(",");
        try (BufferedReader lineReader =  new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<ItemVO> itemList = lineReader.lines().skip(1).map(line -> {
                String[] x = pattern.split(line);
                return ItemVO.builder().itemId(x[0]).name(x[1]).Description(x[2])
                        .price(new BigDecimal(x[3])).quantity(Long.parseLong(x[4])).category(x[5])
                        .inStock(Boolean.valueOf(x[6])).build();
            }).collect(Collectors.toList());
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(System.out, itemList);
            return itemList.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
