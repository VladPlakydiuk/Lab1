package lab1.moop.model;

import java.util.Objects;

public class Film {

    private int code;
    private String title;
    private int metascore;
    private int duration;

    public Film(int code, String title, int metascore, int duration) {
        this.code = code;
        this.title = title;
        this.metascore = metascore;
        this.duration = duration;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMetascore() {
        return this.metascore;
    }

    public void setMetascore(int metascore) {
        this.metascore = metascore;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "\nFilm{\n" +
                "code='" + code + '\'' +
                ",\n title='" + title + '\'' +
                ",\n metascore=" + metascore +
                ",\n duration=" + duration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return code == film.code &&
                title.equals(film.title) &&
                metascore == film.metascore &&
                duration == film.duration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, title, metascore, duration);
    }
}
