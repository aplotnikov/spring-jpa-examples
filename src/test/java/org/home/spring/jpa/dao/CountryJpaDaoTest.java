package org.home.spring.jpa.dao;

import org.home.spring.jpa.context.ApplicationContext;
import org.home.spring.jpa.model.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContext.class)
@ActiveProfiles("dev")
public class CountryJpaDaoTest {
    @Inject
    private CountryJpaDao countryJpaDao;

    @Test
    public void shouldCountryBeSavedToDatabase() {
        assertThat(countryJpaDao.findAll()).isEmpty();

        Country country = new Country("Ukraine", "UA");

        countryJpaDao.save(country);

        assertThat(countryJpaDao.findByNameLike("Ukraine")).isEqualTo(country);
    }
}