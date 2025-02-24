package mongodb;

import clases.Aeropuerto;
import clases.Avion;
import clases.Vuelo;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class AeropuertoDAO {
    private static MongoCollection<Document> collection = MongoDBConfig.getCollection();

    public static boolean insertAeropuerto(Aeropuerto aeropuerto) {
        Document doc = new Document
        ("codigo", aeropuerto.getCodigo())
        .append("nombre", aeropuerto.getNombre())
        .append("vuelos", aeropuerto.getVuelos())
        .append("aviones", aeropuerto.getAviones());
        collection.insertOne(doc);
        return true;
    }

    public static List<Aeropuerto> getAeropuertos() {
        collection = MongoDBConfig.getCollection();
        List<Aeropuerto> aeropuertos = new ArrayList<>();
        for (Document doc : collection.find()) {
            aeropuertos.add(new Aeropuerto(doc.getString("codigo"), doc.getString("nombre"), (List) doc.get("vuelos"), (List) doc.get("aviones")));
        }
        return aeropuertos;
    }

    public static boolean updateAeropuerto(Aeropuerto aeropuerto) {
        List<Document> vuelosDocs = new ArrayList<>();
        if (aeropuerto.getVuelos() != null) {
            for (Vuelo vuelo : aeropuerto.getVuelos()) {
                // Convert the embedded Avion to a Document.
                Document avionDoc = new Document("marca", vuelo.getAvion().getMarca())
                        .append("modelo", vuelo.getAvion().getModelo());
                // Convert Vuelo to a Document.
                Document vueloDoc = new Document("codigo", vuelo.getCodigo())
                        .append("duracion", vuelo.getDuracion())
                        .append("pasajeros", vuelo.getPasajeros())
                        .append("avion", avionDoc);
                vuelosDocs.add(vueloDoc);
            }
        }

        List<Document> avionesDocs = new ArrayList<>();
        if (aeropuerto.getAviones() != null) {
            for (Avion avion : aeropuerto.getAviones()) {
                Document avionDoc = new Document("marca", avion.getMarca())
                        .append("modelo", avion.getModelo());
                avionesDocs.add(avionDoc);
            }
        }

        Document updateFields = new Document("nombre", aeropuerto.getNombre())
                .append("vuelos", vuelosDocs)
                .append("aviones", avionesDocs);

        collection.updateOne(new Document("codigo", aeropuerto.getCodigo()),
                new Document("$set", updateFields));
        return true;
    }

    public static boolean deleteAeropuerto(Aeropuerto aeropuerto) {
        collection.deleteOne(new Document("codigo", aeropuerto.getCodigo()));
        return true;
    }

}
