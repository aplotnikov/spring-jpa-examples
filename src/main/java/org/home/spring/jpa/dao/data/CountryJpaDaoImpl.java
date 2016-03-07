package org.home.spring.jpa.dao.data;

import org.home.spring.jpa.model.Country;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CountryJpaDaoImpl implements CountryCustomeQueries {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Country searchForCountryCustomImpl(@Nonnull String name) {
        return entityManager.createQuery("SELECT c FROM Country c WHERE c.name LIKE :name", Country.class)
                            .setParameter("name", name)
                            .getSingleResult();
    }
}
