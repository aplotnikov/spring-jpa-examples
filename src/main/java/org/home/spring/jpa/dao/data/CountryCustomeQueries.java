package org.home.spring.jpa.dao.data;

import org.home.spring.jpa.model.Country;

import javax.annotation.Nonnull;

public interface CountryCustomeQueries {
    Country searchForCountryCustomImpl(@Nonnull String name);
}
