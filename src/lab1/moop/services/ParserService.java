package lab1.moop.services;

import lab1.moop.model.VideoStore;
import lab1.moop.model.Genre;
import lab1.moop.model.Film;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ParserService {

    Document doc = null;

    public void validateXML(String filepath) throws SAXException {
        DocumentBuilder db = null;
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema s = null;
        try {
            s = sf.newSchema(new File("store.xsd"));
        } catch (SAXException e) {
            e.printStackTrace();
        }
        assert s != null;
        Validator validator = s.newValidator();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        dbf.setSchema(s);
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        assert db != null;
        Source source = new StreamSource(filepath);
        db.setErrorHandler(new CustomErrorHandler());
        try {
            doc = db.parse(new File(filepath));
            validator.validate(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public VideoStore parseXML() {
        assert doc != null;
        return createVideoStore(doc);
    }

    public VideoStore createVideoStore(Document doc) {
        assert doc != null;
        VideoStore videostore = null;
        Element root = doc.getDocumentElement();
        if (root.getTagName().equals("Store")) {
            int id = Integer.parseInt(root.getAttribute("id"));
            String name = root.getAttribute("title");
            videostore = new VideoStore(id, name);
            NodeList listOfGenres = root.getElementsByTagName("Genre");
            for (int i = 0; i < listOfGenres.getLength(); i++) {
                Element genre = (Element) listOfGenres.item(i);
                String genreId = genre.getAttribute("id");
                String genreName = genre.getAttribute("name");
                Genre newGenre = new Genre(Integer.parseInt(genreId), genreName);
                videostore.addGenre(newGenre);
                NodeList listOfFilms = genre.getElementsByTagName("Film");
                for (int j = 0; j < listOfFilms.getLength(); j++) {
                    Element film = (Element) listOfFilms.item(j);
                    String code = film.getAttribute("code");
                    String filmTitle = film.getAttribute("title");
                    String filmMetascore = film.getAttribute("metascore");
                    String filmDuration = film.getAttribute("duration");
                    Film newFilm = new Film(Integer.parseInt(code), filmTitle, Integer.parseInt(filmMetascore), Integer.parseInt(filmDuration));
                    newGenre.addFilm(newFilm);
                }
            }
        }
        return videostore;
    }

    public void createXML(String filepath, VideoStore videostore) {
        DocumentBuilder db = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        assert db != null;
        db.setErrorHandler(new CustomErrorHandler());
        Document doc = db.newDocument();

        Element root = doc.createElement("Store");
        root.setAttribute("id", String.valueOf(videostore.getId()));
        root.setAttribute("title", videostore.getTitle());
        doc.appendChild(root);

        ArrayList<Genre> genres = videostore.getGenres();
        ArrayList<Film> films;

        for (Genre g : genres) {
            Element genre = doc.createElement("Genre");
            genre.setAttribute("id", String.valueOf(g.getId()));
            genre.setAttribute("name", g.getName());
            root.appendChild(genre);

            films = g.getFilms();

            for (Film f : films) {
                Element film = doc.createElement("Film");
                film.setAttribute("code", String.valueOf(f.getCode()));
                film.setAttribute("title", f.getTitle());
                film.setAttribute("metascore", String.valueOf(f.getMetascore()));
                film.setAttribute("duration", String.valueOf(f.getDuration()));
                genre.appendChild(film);
            }
        }

        Source domSource = new DOMSource(doc);
        Result fileResult = new StreamResult(new File(filepath));
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = null;

        try {
            transformer = factory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        assert transformer != null;
        transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
        try {
            transformer.transform(domSource, fileResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
