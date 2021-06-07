package ShopAppBackend.Logs;


import ShopAppBackend.Entities.Log;
import ShopAppBackend.Repositories.LogsRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
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

    private final LogsRepository logsRepository;

    @Autowired
    public LogsApplication(LogsRepository logsRepository) {
        this.logsRepository = logsRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(LogsApplication.class);

    public  void SaveLogToDatabase(String message){


            LocalDateTime localDateTime = LocalDateTime.now();
            String date = localDateTime.toString();
            String dateFormat = date.replace("T"," ");

            StringBuilder sb = new StringBuilder(dateFormat);
            String finalDataFormat =  sb.delete(19,29).toString();
            logsRepository.save(new Log(finalDataFormat,message));

        }
    }

