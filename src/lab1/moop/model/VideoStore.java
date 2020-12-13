package lab1.moop.model;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

public class VideoStore {
    private int id;
    private String title;
    private ArrayList<Genre> genres = new ArrayList<>();

    public VideoStore(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public void addGenre(Genre genre) {
        boolean exists = genres.stream().anyMatch(genre1 -> genre1.getId() == genre.getId());
        if (!exists) {
            genres.add(genre);
        }
    }

    public void addFilm(Film film, int genreId) {
        Optional<Genre> genre = getGenreById(genreId);
        genre.ifPresent(value -> value.addFilm(film));
    }

    public void deleteFilm(Film film, int genreId) {
        Optional<Genre> genre = getGenreById(genreId);
        genre.ifPresent(value -> value.deleteFilm(film));
    }

    public void deleteGenre(Genre department) {
        genres.remove(department);
    }

    public Predicate<Genre> isGenreIdEqual(int id) {
        return p -> p.getId() == id;
    }

    public Optional<Genre> getGenreById(int id) {
        return genres.stream().filter(isGenreIdEqual(id)).findFirst();
    }

    public Predicate<Genre> isGenreNameEqual(String name) {
        return p -> p.getName().equals(name);
    }

    public Optional<Genre> getGenreByName(String name) {
        return genres.stream().filter(isGenreNameEqual(name)).findFirst();
    }

    public Optional<Film> getFilmsByCode(int code) {
        Optional<Film> searchedFilm = Optional.empty();
        for (Genre genre :
                genres) {
            if (genre.getFilmByCode(code).isPresent())
                searchedFilm = genre.getFilmByCode(code);
        }
        return searchedFilm;
    }

    public Genre getGenreByIndex(int index) {
        Genre returnGenre = null;
        try {
            returnGenre = genres.get(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Wrong index. Please try again");
        }
        return returnGenre;
    }

    public int countGenres() {
        return genres.size();
    }

    public int countFilms() {
        int countFilms = 0;
        for (Genre g :
                genres) {
            countFilms += g.countFilmsForGenre();
        }
        return countFilms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public String printGenres(ArrayList<Genre> listOfGenres) {
        StringBuilder sb = new StringBuilder();
        sb.append("VideoStore{");
        for (Genre g :
                listOfGenres) {
            sb.append("\nGenre{\n").append("id=").append(g.getId()).append(",\n title='").append(g.getName()).append('\'');
        }
        sb.append("\n}");
        return sb.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "VideoStore{\n" +
                "id=" + id +
                ",\n title='" + title + '\'' +
                ",\n genres=" + genres +
                '}';
    }
}
