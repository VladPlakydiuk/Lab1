package lab1.moop.view;

import lab1.moop.model.VideoStore;
import lab1.moop.model.Genre;
import lab1.moop.model.Film;
import lab1.moop.services.ParserService;

public class View {

    //можна реалізувати будь-які сценарії

    public VideoStore populateXML() {
        Film walle = new Film(1, "WALL-E", 95, 152);
        Film dragged = new Film(2, "Dragged Across Concrete", 60, 158);
        Film interstellar = new Film(3, "Interstellar", 74, 148);
        Film shawshank = new Film(4, "The Shawshank Redemption", 80, 142);

        Genre thriller = new Genre(1, "Thriller");
        Genre scifi = new Genre(2, "Sci-Fi");
        Genre drama = new Genre(3, "Drama");
        Genre animation = new Genre(4, "Animation");

        VideoStore exfsVideostore = new VideoStore(1, "EX-FS");

        thriller.addFilm(dragged);
        scifi.addFilm(interstellar);
        drama.addFilm(shawshank);
        animation.addFilm(walle);

        exfsVideostore.addGenre(thriller);
        exfsVideostore.addGenre(scifi);
        exfsVideostore.addGenre(drama);
        exfsVideostore.addGenre(animation);
        return exfsVideostore;
    }

    public void showWork() {
        VideoStore exfs = populateXML();
        String fp = "d:\\Vlad\\Magistracy\\Course_1\\Term_1\\MOOP\\Lab_XML\\src\\lab1\\moop\\exfs.xml";
        ParserService parserService = new ParserService();
        parserService.createXML(fp, exfs);
    }
}
