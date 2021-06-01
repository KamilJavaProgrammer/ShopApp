package ShopAppBackend.Logs;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Component
@Slf4j
public class LogsApplication {


    private static Logger logger = LoggerFactory.getLogger(LogsApplication.class);


    public  void SaveToFile(String save) throws IOException {
        try{
            Files.createFile(Paths.get("C:/ZdjęciaBaza/logi.txt"));
        } catch (IOException e){
            logger.info("ERROR");
        } finally {

            LocalDateTime localDateTime = LocalDateTime.now();
            String date = localDateTime.toString();
            String date1 = date.replace("T"," ");

            StringBuilder sb = new StringBuilder(date1);
            String date2 =  sb.delete(19,29).toString();


            FileWriter fw = new FileWriter("C:/ZdjęciaBaza/logi.txt",true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(date2);
            bw.append("  "+save);
            bw.newLine();
            bw.close();

        }
    }

}
