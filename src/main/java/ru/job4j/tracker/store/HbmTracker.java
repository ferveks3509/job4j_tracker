package ru.job4j.tracker.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.tracker.model.Item;

import javax.persistence.Query;
import java.util.List;

public class HbmTracker implements Store, AutoCl {
    private static final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private static final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public Item add(Item item) {
       Session session = sf.openSession();
       session.beginTransaction();
       session.save(item);
       session.getTransaction().commit();
       session.close();
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        boolean rsl = false;
        Session session = sf.openSession();
        session.beginTransaction();
        Item tmp = findById(id);
        if (tmp != null) {
            session.delete(tmp);
            session.save(item);
            rsl = true;
        }
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    @Override
    public boolean delete(int id) {
        boolean rsl = false;
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = findById(id);
        if (item != null) {
            session.delete(item);
            rsl = true;
        }
        session.beginTransaction().commit();
        session.close();
        return rsl;
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("FROM Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public List<Item> findByName(String key) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Item i where i.name = :valueName");
        query.setParameter("valueName", key);
        List rsl = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    @Override
    public Item findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item result = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
