package BibtexfileParser.models;

import BibtexfileParser.HasAuthor;
import BibtexfileParser.IEntry;
import BibtexfileParser.Required;

import java.util.List;
import java.util.Objects;

/**
 * Representative of Article, contains every possible field for that publication.
 */

public class Article implements IEntry, HasAuthor {
    @Required
    private List<String> author;
    @Required
    private String journal;
    private String key;
    private String number;
    private String month;
    private String note;
    private String pages;
    @Required
    private String title;
    private String volume;
    @Required
    private String year;
    private String citationKey;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return Objects.equals(author, article.author) &&
                Objects.equals(journal, article.journal) &&
                Objects.equals(key, article.key) &&
                Objects.equals(number, article.number) &&
                Objects.equals(month, article.month) &&
                Objects.equals(note, article.note) &&
                Objects.equals(pages, article.pages) &&
                Objects.equals(title, article.title) &&
                Objects.equals(volume, article.volume) &&
                Objects.equals(year, article.year) &&
                Objects.equals(citationKey, article.citationKey);
    }

    @Override
    public int hashCode() {

        return Objects.hash(author, journal, key, number, month, note, pages, title, volume, year, citationKey);
    }

    @Override
    public String toString() {
        return "Article " +
                " author " + author +
                " journal " + journal +
                " key " + key +
                " number " + number +
                " month " + month +
                " note " + note +
                " pages " + pages +
                " title " + title +
                " volume " + volume +
                " year " + year +
                " citationKey " + citationKey;
    }

    public String getCitationKey() {
        return citationKey;
    }



    public void setCitationKey(String citationKey) {
        this.citationKey = citationKey;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}