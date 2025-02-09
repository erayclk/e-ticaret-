package com.ecommerce.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.ecommerce.model.User;
import com.ecommerce.util.HibernateUtil;

public class UserDAO {
    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());
    
    public UserDAO() {
        try {
            // Test the database connection
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                LOGGER.info("Database connection test successful");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize UserDAO: Database connection test failed", e);
            throw new RuntimeException("Database connection failed", e);
        }
    }
    
    public void save(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            LOGGER.info("User saved successfully: " + user.getUsername());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error saving user: " + e.getMessage(), e);
            throw new RuntimeException("Failed to save user", e);
        }
    }
    
    public User findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            if (user != null) {
                LOGGER.info("User found by ID: " + id);
            } else {
                LOGGER.info("No user found with ID: " + id);
            }
            return user;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding user by ID: " + e.getMessage(), e);
            throw new RuntimeException("Failed to find user by ID", e);
        }
    }
    
    public User findByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("FROM User WHERE username = :username", User.class);
            query.setParameter("username", username);
            User user = query.uniqueResult();
            if (user != null) {
                LOGGER.info("User found by username: " + username);
            } else {
                LOGGER.info("No user found with username: " + username);
            }
            return user;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding user by username: " + e.getMessage(), e);
            throw new RuntimeException("Failed to find user by username", e);
        }
    }
    
    public List<User> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<User> users = session.createQuery("FROM User", User.class).list();
            LOGGER.info("Found " + users.size() + " users");
            return users;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding all users: " + e.getMessage(), e);
            throw new RuntimeException("Failed to find all users", e);
        }
    }
    
    public void update(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            LOGGER.info("User updated successfully: " + user.getUsername());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error updating user: " + e.getMessage(), e);
            throw new RuntimeException("Failed to update user", e);
        }
    }
    
    public void delete(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
            LOGGER.info("User deleted successfully: " + user.getUsername());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting user: " + e.getMessage(), e);
            throw new RuntimeException("Failed to delete user", e);
        }
    }
    
    public boolean authenticate(String username, String password) {
        LOGGER.info("Attempting to authenticate user: " + username);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery(
                "FROM User WHERE username = :username AND password = :password", 
                User.class
            );
            query.setParameter("username", username);
            query.setParameter("password", password);
            
            User user = query.uniqueResult();
            boolean authenticated = user != null;
            
            if (authenticated) {
                LOGGER.info("Authentication successful for user: " + username);
            } else {
                LOGGER.info("Authentication failed for user: " + username);
            }
            
            return authenticated;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Authentication error: " + e.getMessage(), e);
            throw new RuntimeException("Authentication failed", e);
        }
    }
} 