package org.home.spring.jpa.dao;

import org.home.spring.jpa.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

@Repository
public interface CountryJpaDao extends JpaRepository<Country, Integer> {
    Country findByNameLike(@Nonnull String name);

    @Query("select c from Country c where c.name like :name")
    Country findCountryByNameLike(@Param("name") @Nonnull String name);

    @Query("select c from Country c where c.name like ?1")
    Country findCountryByNameLike2(@Nonnull String name);
}
