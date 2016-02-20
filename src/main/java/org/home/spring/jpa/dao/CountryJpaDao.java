package org.home.spring.jpa.dao;

import org.home.spring.jpa.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

@Repository
public interface CountryJpaDao extends JpaRepository<Country, Integer> {
    Country findByNameLike(@Nonnull String name);
}
