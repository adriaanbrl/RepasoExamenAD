package mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBConfig {
    private static final String URL = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "aeropuerto";
    private static final String COLLECTION_NAME = "aero-data";
    private static MongoCollection mongoCollection;

    public static MongoCollection getCollection() {
        if (mongoCollection == null) {
            MongoClient client = MongoClients.create(URL);
            MongoDatabase db = client.getDatabase(DATABASE_NAME);
            mongoCollection = db.getCollection(COLLECTION_NAME);
        }
        return mongoCollection;
    }
}
