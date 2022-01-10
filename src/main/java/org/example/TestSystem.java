package org.example;

import javax.persistence.*;
import java.util.List;

public class TestSystem {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hibernateTut");

    public static void main(String[] args) {

        getCustomers();
        addCustomer(5, "Joe", "Doe");
        addCustomer(6, "Joen", "Dunawan");
        getCustomers();
        changeName(5, "JoeX");
        getCustomer(5);
        deleteCustomer(6);
        getCustomers();
        entityManagerFactory.close();
    }

    private static void addCustomer(int id, String fName, String lName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            Customer customer = new Customer();
            customer.setCustomerId(id);
            customer.setFirstName(fName);
            customer.setLastName(lName);

            entityManager.persist(customer);
            entityTransaction.commit();
        } catch (Exception ex) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    private static void getCustomer(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String sql = "SELECT c FROM Customer c WHERE customer_id = :customer_id";
        TypedQuery<Customer> typedQuery = entityManager.createQuery(sql, Customer.class);
        typedQuery.setParameter("customer_id", id);
        try {
            Customer customer = typedQuery.getSingleResult();
            System.out.println("First name : " + customer.getFirstName() + ", last name : " + customer.getLastName());
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    private static void getCustomers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String sql = "SELECT c FROM Customer c";
        TypedQuery<Customer> typedQuery = entityManager.createQuery(sql, Customer.class);
        try {
            List<Customer> customerList = typedQuery.getResultList();
            customerList.forEach(customer -> System.out.println("First name : " + customer.getFirstName() + ", last name : " + customer.getLastName()));

        } catch (NoResultException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            entityManager.close();
        }
    }


    private static void changeName(int id, String fName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            Customer customer = entityManager.find(Customer.class, id);
            customer.setFirstName(fName);

            entityManager.persist(customer);
            entityTransaction.commit();
        } catch (Exception ex) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    private static void deleteCustomer(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            Customer customer = entityManager.find(Customer.class, id);
            entityManager.remove(customer);

            entityManager.persist(customer);
            entityTransaction.commit();
        } catch (Exception ex) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

}
