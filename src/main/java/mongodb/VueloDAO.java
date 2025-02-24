package mongodb;

import clases.Avion;
import clases.Vuelo;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class VueloDAO {
    private static MongoCollection<Document> collection = MongoDBConfig.getCollection();

    public static boolean insertVuelo(Vuelo vuelo) {
        Avion avion = vuelo.getAvion();
        // Convert Avion to a Document.
        Document avionDoc = new Document("marca", avion.getMarca())
                .append("modelo", avion.getModelo())
                .append("aerolinea", avion.getAerolinea());
        Document doc = new Document("codigo", vuelo.getCodigo())
                .append("duracion", vuelo.getDuracion())
                .append("avion", avionDoc)
                .append("pasajeros", vuelo.getPasajeros());
        collection.insertOne(doc);
        return true;
    }

    public static boolean updateVuelo(Vuelo vuelo) {
        Avion avion = vuelo.getAvion();
        // Convert Avion to a Document.
        Document avionDoc = new Document("marca", avion.getMarca())
                .append("modelo", avion.getModelo())
                .append("aerolinea", avion.getAerolinea());
        Document doc = new Document("codigo", vuelo.getCodigo())
                .append("duracion", vuelo.getDuracion())
                .append("avion", avionDoc)
                .append("pasajeros", vuelo.getPasajeros());
        collection.updateOne(new Document("codigo", vuelo.getCodigo()), new Document("$set", doc));
        return true;
    }

    public static List<Vuelo> getVuelos() {
        List<Vuelo> vuelos = new ArrayList<>();
        for (Document doc : collection.find()) {
            // Retrieve duracion safely, use default of 0.0 if null.
            Double duracion = doc.get("duracion", Double.class);
            if (duracion == null) {
                duracion = 0.0;
            }
            // Retrieve the embedded avion document.
            Document avionDoc = (Document) doc.get("avion");
            Avion avion;
            if (avionDoc != null) {
                avion = new Avion(
                        avionDoc.getString("marca"),
                        avionDoc.getString("modelo"),
                        avionDoc.getString("aerolinea")
                );
            } else {
                // Provide default values if avion document is missing.
                avion = new Avion("unknown", "unknown", "unknown");
            }
            Integer pasajeros = doc.getInteger("pasajeros", 0);
            vuelos.add(new Vuelo(doc.getString("codigo"), avion, duracion, pasajeros));
        }
        return vuelos;
    }

    public static List<Integer> getPasajeros15(){
        Bson filter = new Document("pasajeros", new Document("$gt", 15));
        Iterable<Document> busqueda = collection.find(filter);
        List<Integer> pasajeros = new ArrayList<>();
        for (Document doc : busqueda) {
            pasajeros.add(doc.getInteger("pasajeros"));
        }
        return pasajeros;
    }

    public static boolean deleteVuelo(String codigo) {
        collection.deleteOne(new Document("codigo", codigo));
        return true;
    }
}
