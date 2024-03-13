package com.lukushin.email_service.utils;

import com.lukushin.email_service.exception.XLSXException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.EmptyFileException;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Slf4j
@Component
public class FileUtils {

    // парсит XLSX-файл, проверяет email по шаблону,
    // отбраковывает повторяющиеся адреса, возвращает список уникальных адресов
    public Set<String> parseXLSXFile(MultipartFile file){
        log.info("Parsing XLSX file {}", file.getOriginalFilename());
        Set<String> result = new HashSet<>();
        // формат адреса -> *@*.*
        String emailPattern = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9]+$";

        try(XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())){
            XSSFSheet sheet = workbook.getSheetAt(0);
            for(Row row : sheet){
                for(Cell cell : row){
                    String str = cell.getStringCellValue();
                    var emailIsCorrect =
                            Pattern.matches(emailPattern, str);
                    if (str.isBlank() || !emailIsCorrect){
                        break;
                    }
                    if (!str.contains(",")){
                        result.add(str);
                    }
                    String[] split = str.split(",");
                    result.addAll(Arrays.asList(split));
                }
            }
        } catch (NotOfficeXmlFileException exception) {
            throw new XLSXException("this is NO XLSX file");
        } catch (EmptyFileException exception) {
            throw new XLSXException("this is empty XLSX file");
        } catch (IOException exception) {
            throw new XLSXException("some problems with XLSX file");
        }

        log.info("The file {} was successfully parsed", file.getOriginalFilename());
        return result;
    }
}
