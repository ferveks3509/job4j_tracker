package ru.job4j.tracker.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.tracker.model.Item;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class HbmTracker implements Store, AutoCl {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();


    @Override
    public Item add(Item item) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.merge(item);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        var session = sf.openSession();
        int rsl = 0;
        try {
            session.beginTransaction();
            rsl = session.createQuery("DELETE Item WHERE id = :Id")
                    .setParameter("Id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl > 0;
    }

    @Override
    public List<Item> findAll() {
        List<Item> rsl = new ArrayList<>();
        var session = sf.openSession();
        try {
            session.beginTransaction();
            rsl = session.createQuery("FROM Item", Item.class)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> rsl = new ArrayList<>();
        var session = sf.openSession();
        try {
            session.beginTransaction();
            rsl = session.createQuery("FROM Item WHERE name = :key", Item.class)
                    .setParameter("key", key)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public Item findById(int id) {
        Item rsl = null;
        var session = sf.openSession();
        try {
            session.beginTransaction();
            rsl = session.createQuery("FROM Item WHERE id = :Id", Item.class)
                    .setParameter("Id", id)
                    .uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
