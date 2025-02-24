package mongodb;

import clases.Avion;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class AvionDAO {
    private static MongoCollection<Document> collection = MongoDBConfig.getCollection();

    public static boolean insertAvion(Avion avion) {
        Document doc = new Document
        ("marca", avion.getMarca())
        .append("modelo", avion.getModelo())
        .append("aerolinea", avion.getAerolinea());
        collection.insertOne(doc);
        return true;
    }

    public static List<Avion> getAviones() {
        collection = MongoDBConfig.getCollection();
        List<Avion> aviones = new ArrayList<>();
        for (Document doc : collection.find()) {
            aviones.add(new Avion(doc.getString("marca"), doc.getString("modelo"), doc.getString("aerolinea")));
        }
        return aviones;
    }
}
