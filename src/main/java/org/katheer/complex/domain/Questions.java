package org.katheer.complex.domain;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Questions {
   private static Repository repository = new Repository();
   private List<Country> allCountries = repository.getAllCountries();
   public static void main(String[] args) {

      //Q1) Find the highest populated city of each country
      repository.getAllCountries()
            .stream()
            .map(country -> country.getCities().stream().max(Comparator.comparing(City::getPopulation)))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());

      //Q2) Find the most populated city of each continent
      //group countries into continent
      Map<String, List<Country>> groupedByContinent = repository.getAllCountries()
            .stream()
            .collect(Collectors.groupingBy(Country::getContinent));

      //Lambda to find max populated city in a country
      Function<Country, Optional<City>> maxPopulatedCityInACountry = (country -> country.getCities()
            .stream()
            .max(Comparator.comparing(City::getPopulation)));

      //continent with most populated cities in each country of the continent
      Map<String, List<City>> result = new HashMap<>();
      groupedByContinent.forEach((continent, countries) -> result.put(continent, countries
                  .stream()
                  .map(maxPopulatedCityInACountry)
                  .filter(Optional::isPresent)
                  .map(Optional::get)
                  .collect(Collectors.toList())
            )
      );

      //now find the most populated in each continent
      Map<String, City> finalResult = new HashMap<>();
      result.forEach((continent, cities) -> finalResult.put(continent, cities
                  .stream()
                  .max(Comparator.comparing(City::getPopulation))
                  .get()
            )
      );

      //Q3) Find the highest populated capital city
      //collect all capital city ids
      List<Integer> allCapitalIds = repository.getAllCountries().stream()
            .map(country -> country.getCapital())
            .collect(Collectors.toList());

      List<City> allCapitalCities = repository.getAllCities()
            .stream()
            .filter(city -> allCapitalIds.contains(city.getCountryCode()))
            .collect(Collectors.toList());

      City mostPopulatedCapitalCity = allCapitalCities.stream()
            .max(Comparator.comparing(City::getPopulation))
            .get();

      //Q4) Find the highest populated capital city of each continent
      //store city with their id as key in a map
      Map<Integer, City> cityMap = new HashMap<>();
      repository.getAllCities()
            .forEach(city -> cityMap.put(city.getId(), city));

      Map<String, List<Integer>> citiesIdsGroupedByContinent = repository.getAllCountries()
            .stream()
            .collect(Collectors.groupingBy(Country::getContinent,
                  Collectors.mapping(Country::getCapital, Collectors.toList())));

      Map<String, List<City>> citiesGroupedByContinent = new HashMap<>();

      citiesIdsGroupedByContinent.forEach((continent, capitals) -> citiesGroupedByContinent.put(continent, capitals
            .stream()
            .map(cityMap::get)
            .collect(Collectors.toList())));

      //Q5) Find the number of movies of each director
      // Assuming that Director class has not the member movies
      List<Director> allMoviesDirectors = repository.getAllMovies()
            .stream()
            .flatMap(movie -> movie.getDirectors().stream())
            .collect(Collectors.toList());

      Map<Integer, Long> directorsIdsAndMovieCounts = allMoviesDirectors
            .stream()
            .collect(Collectors.groupingBy(Director::getId, Collectors.counting()));

      Map<Director, Long> q5Result = new HashMap<>();
      directorsIdsAndMovieCounts
            .forEach((directorId,
                      moviesCount) -> q5Result.put(repository.getDirectorById(directorId), moviesCount));

      //Q6) Find the number of genres of each director's movies
      final Map<Director, List<Movie>> directorAndHisMovies = new HashMap<>();
      repository.getAllDirectors()
            .forEach(director -> directorAndHisMovies.put(director, director.getMovies()));

      Map<Director, Set<Genre>> directorAndGenresOfHisMovies = new HashMap<>();
      directorAndHisMovies
            .forEach((director, movies) -> directorAndGenresOfHisMovies.put(director,
                  movies.stream()
                     .flatMap(movie -> movie.getGenres().stream())
                     .collect(Collectors.toSet())));

      //Q7) Sort the countries by number of their cities in descending order
      Comparator<Country> sortByNoOfCities = Comparator.comparing(country -> country.getCities().size());
      repository.getAllCountries()
            .stream()
            .sorted(sortByNoOfCities.reversed())
            .collect(Collectors.toList());
   }
}
