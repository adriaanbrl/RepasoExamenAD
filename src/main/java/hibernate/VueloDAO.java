package hibernate;

import clases.Vuelo;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.List;

public class VueloDAO {

    //CREAR-PERSIST
    public static void crear(Vuelo vuelo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            session.getTransaction().begin();
            session.persist(vuelo);
            session.getTransaction().commit();
        }
    }

    //ACTUALIZAR-MERGE
    public static void actualizar(Vuelo vuelo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            session.getTransaction().begin();
            session.merge(vuelo);
            session.getTransaction().commit();
        }
    }

    //LEER-FIND
    public static Vuelo leer(int id) {
        Vuelo vuelo= null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            vuelo = session.find(Vuelo.class, id);
        }
        return vuelo;
    }

    public static List<Vuelo> leerTodosVuelos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            String hql = "FROM Vuelo";
            Query query = session.createQuery(hql,Vuelo.class);
            return query.getResultList();
        }
    }

    // Método para buscar vuelos con más de un número determinado de pasajeros
    public static List<Vuelo> findVuelosMasDePasajeros(int pasajeros) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "from Vuelo v where v.pasajeros > :pasajeros";
            return session.createQuery(hql, Vuelo.class)
                    .setParameter("pasajeros", pasajeros)
                    .list();
        }
    }

}