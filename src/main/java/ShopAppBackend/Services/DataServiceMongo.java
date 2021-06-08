package ShopAppBackend.Services;

import ShopAppBackend.DocumentsMongoDB.Data;
import ShopAppBackend.Exceptions.DataNotFoundException;
import ShopAppBackend.Logs.LogsApplication;
import ShopAppBackend.Repositories.DataRepositoryMongoDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class DataServiceMongo {

    private final DataRepositoryMongoDb dataRepositoryMongoDb;
    private final LogsApplication logsApplication;

    @Autowired
    public DataServiceMongo(DataRepositoryMongoDb dataRepositoryMongoDb,LogsApplication logsApplication) {
        this.dataRepositoryMongoDb = dataRepositoryMongoDb;
        this.logsApplication = logsApplication;
    }

    public Data GetDataById(String id) throws DataNotFoundException{
        Optional<Data> dataOptional = dataRepositoryMongoDb.findById(id);
        return dataOptional.orElseThrow(DataNotFoundException::new);
    }

    @Transactional
    public HttpStatus SaveDataToMongoDatabase(Data data){
        dataRepositoryMongoDb.save(data);
        logsApplication.SaveLogToDatabase("Add new data to MongoDb database");
        return HttpStatus.CREATED;
    }
}
