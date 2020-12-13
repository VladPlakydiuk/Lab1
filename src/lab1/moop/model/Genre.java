package lab1.moop.model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class Genre {

    private int id;
    private String name;
    private ArrayList<Film> films = new ArrayList<>();

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addFilm(Film film) {
        boolean exists = films.stream().anyMatch(film1 -> film1.getCode() == film.getCode());
        if (!exists) {
            films.add(film);
        }
    }

    public void deleteFilm(Film film) {
        films.remove(film);
    }

    public Predicate<Film> isFilmCodeEqual(int code) {
        return f -> f.getCode() == code;
    }

    public Optional<Film> getFilmByCode(int code) {
        return films.stream().filter(isFilmCodeEqual(code)).findFirst();
    }

    public int countFilmsForGenre() {
        return films.size();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Film> getFilms() {
        return films;
    }

    public void setFilms(ArrayList<Film> films) {
        this.films = films;
    }

    public String printGenre(Genre genre) {
        return "\nGenre{\n" +
                "id=" + genre.getId() +
                ",\n title='" + genre.getName() + '\'' +
                '}';
    }

    @Override
    public String toString() {
        return "\nGenre{\n" +
                "id=" + id +
                ",\n title='" + name + '\'' +
                ",\n films=" + films +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre that = (Genre) o;
        return id == that.id &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
