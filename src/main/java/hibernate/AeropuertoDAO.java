package hibernate;

import clases.Aeropuerto;
import clases.Avion;
import clases.Vuelo;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class AeropuertoDAO {

    //CREAR-PERSIST
    public static void crear(Aeropuerto aeropuerto) {
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            session.getTransaction().begin();
            session.persist(aeropuerto);
            session.getTransaction().commit();
        }
    }

    //ACTUALIZAR-MERGE
    public void merge(Aeropuerto aeropuerto) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(aeropuerto);
            transaction.commit();
        } catch(Exception e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    //LEER-FIND
    public static Aeropuerto leer(int id) {
        Aeropuerto aeropuerto= null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            aeropuerto = session.find(Aeropuerto.class, id);
        }
        return aeropuerto;
    }

    //BORRAR-REMOVE
    public static void remove(Aeropuerto aeropuerto) {
        Session session = null; // Declarar la sesión fuera del try-with-resources
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.getTransaction().begin();

            // 1. Cargar el aeropuerto desde la base de datos para asegurar que está adjunto a la sesión
            aeropuerto = session.get(Aeropuerto.class, aeropuerto.getId());

            if (aeropuerto != null) {
                // 2. Desvincular vuelos del aeropuerto
                List<Vuelo> vuelos = aeropuerto.getVuelos();
                if (vuelos != null) {
                    for (Vuelo vuelo : vuelos) {
                        vuelo.setAeropuerto(null); // Establecer aeropuerto a null
                        session.merge(vuelo); // Actualizar el vuelo en la base de datos
                    }
                }

                // 3. Desvincular aviones del aeropuerto (si es necesario)
                List<Avion> aviones = aeropuerto.getAviones();
                if (aviones != null) {
                    for (Avion avion : aviones) {
                        avion.setAeropuerto(null); // Establecer aeropuerto a null
                        session.merge(avion); // Actualizar el avión en la base de datos
                    }
                }

                // 4. Ahora se puede eliminar el aeropuerto de forma segura
                session.remove(aeropuerto);
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace(); // O mejor, loguear la excepción usando un logger
            throw e; // Re-lanzar la excepción después de manejarla
        } finally {
            if (session != null) {
                session.close(); // Cerrar la sesión en el bloque finally
            }
        }
    }

    public Aeropuerto findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.find(Aeropuerto.class, id);
        }
    }
    public static List<Aeropuerto> leerTodosAeropuertos(){
        try(Session session = HibernateUtil.getSessionFactory().openSession();){
            String hql = "FROM Aeropuerto";
            Query query = session.createQuery(hql,Aeropuerto.class);
            return query.getResultList();
        }
    }

    public static List<Aeropuerto> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Aeropuerto", Aeropuerto.class).list();
        }
    }
}