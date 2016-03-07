package org.home.spring.jpa.dao;

import org.home.spring.jpa.model.Country;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
public class CountryDaoImpl implements CountryDao {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void save(@Nonnull Country country) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(country);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Nonnull
    @Override
    public List<Country> findAllCountries() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Country> countries = entityManager.createQuery("from Country", Country.class).getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return countries;
    }

    @Nonnull
    @Override
    public Country findCountryByName(@Nonnull String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Country country = entityManager.createQuery("SELECT c FROM Country c WHERE c.name LIKE :name", Country.class)
                                       .setParameter("name", name)
                                       .getSingleResult();

        entityManager.getTransaction().commit();
        entityManager.close();

        return country;
    }

    @Override
    public void deleteAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.createQuery("delete from Country");

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
