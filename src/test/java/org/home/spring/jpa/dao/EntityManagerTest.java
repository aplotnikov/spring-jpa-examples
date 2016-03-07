package org.home.spring.jpa.dao;

import org.home.spring.jpa.context.ApplicationContext;
import org.home.spring.jpa.model.Country;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class)
@ActiveProfiles("dev")
public class EntityManagerTest {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Before
    public void setUp() throws Exception {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    @After
    public void tearDown() throws Exception {
        if (entityManager == null) {
            return;
        }

        entityManager.close();
    }

    @DirtiesContext
    @Test
    public void shouldCountryBeSavedThrowEntityManager() {
        Country country = new Country("Ukraine", "UA");

        entityManager.persist(country);

        entityManager.getTransaction().commit();

        entityManager.contains(country);
    }

    @DirtiesContext
    @Test
    public void shouldCountryBeMergedThrowEntityManager() {
        Country country = new Country("Ukraine", "UA");

        entityManager.merge(country);
        entityManager.getTransaction().commit();

        entityManager.contains(country);
    }

    @DirtiesContext
    @Test
    public void shouldCountryBeRemovedThrowEntityManager() {
        Country country = new Country("Ukraine", "UA");

        entityManager.persist(country);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();

        entityManager.remove(country);

        entityManager.getTransaction().commit();

        entityManager.contains(country);
    }

    @DirtiesContext
    @Test
    public void shouldCountryBeRefreshedThrowEntityManager() {
        Country country = new Country("Ukraine", "UA");

        entityManager.persist(country);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();

        country.setName("UKR");
        entityManager.refresh(country);

        entityManager.getTransaction().commit();

        entityManager.contains(country);
    }

    @DirtiesContext
    @Test
    public void shouldAttachedCountryBeUpdated() {
        Country country = entityManager.merge(new Country("Ukraine", "UA"));
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();

        country.setName("UKR");

        entityManager.getTransaction().commit();

        entityManager.contains(country);
    }

    @DirtiesContext
    @Test
    public void shouldDetachedCountryBeNotUpdated() {
        Country originalCountry = new Country("Ukraine", "UA");

        Country country = entityManager.merge(originalCountry);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();

        entityManager.detach(country);
        country.setName("UKR");

        entityManager.getTransaction().commit();

        entityManager.contains(originalCountry);
    }

    @DirtiesContext
    @Test
    public void shouldDetachedCountryBeNotUpdatedWithClearMethod() {
        Country originalCountry = new Country("Ukraine", "UA");

        Country country = entityManager.merge(originalCountry);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();

        entityManager.clear();
        country.setName("UKR");

        entityManager.getTransaction().commit();

        entityManager.contains(originalCountry);
    }

}
