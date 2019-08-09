package org.katheer.complex.domain;

import java.util.ArrayList;
import java.util.List;

public class Repository {
   List<Country> getAllCountries() {
      return new ArrayList<>();
   }
   List<City> getAllCities() {
      return new ArrayList<>();
   }
   List<Movie> getAllMovies() {
      return new ArrayList<>();
   }
   List<Director> getAllDirectors() {
      return new ArrayList<>();
   }
   List<Genre> getAllGenres() {
      return new ArrayList<>();
   }

   Director getDirectorById(int id) {
      return new Director();
   }

   Movie getMovieById(int id) {
      return new Movie();
   }
}
