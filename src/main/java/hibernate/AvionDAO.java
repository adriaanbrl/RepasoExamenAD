package hibernate;

import clases.Avion;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AvionDAO {

    //CREAR-PERSIST
    public static void crear(Avion avion) {
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            session.getTransaction().begin();
            session.persist(avion);
            session.getTransaction().commit();
        }
    }

    //ACTUALIZAR-MERGE
    public static void actualizar(Avion avion) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(avion);
            transaction.commit();
        } catch(Exception e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public static List<Avion> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("from Avion", Avion.class).list();
        }
    }
    //LEER-ENCONTAR-FIND
    public Avion findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.find(Avion.class, id);
        }
    }

}