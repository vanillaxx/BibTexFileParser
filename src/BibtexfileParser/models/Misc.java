package BibtexfileParser.models;

import BibtexfileParser.HasAuthor;
import BibtexfileParser.IEntry;

import java.util.List;
import java.util.Objects;

/**
 * Representative of Misc, contains every possible field for that publication.
 */

public class Misc implements IEntry, HasAuthor {
    private List<String> author;
    private String howpublished;
    private String key;
    private String month;
    private String note;
    private String title;
    private String year;
    private String citationKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Misc)) return false;
        Misc misc = (Misc) o;
        return Objects.equals(author, misc.author) &&
                Objects.equals(howpublished, misc.howpublished) &&
                Objects.equals(key, misc.key) &&
                Objects.equals(month, misc.month) &&
                Objects.equals(note, misc.note) &&
                Objects.equals(title, misc.title) &&
                Objects.equals(year, misc.year) &&
                Objects.equals(citationKey, misc.citationKey);
    }

    @Override
    public int hashCode() {

        return Objects.hash(author, howpublished, key, month, note, title, year, citationKey);
    }

    @Override
    public String toString() {
        return "Misc " +
                "author " + author +
                " howpublished" + howpublished +
                " key" + key +
                " month" + month +
                " note" + note +
                " title " + title +
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

    public String getHowPublished() {
        return howpublished;
    }

    public void setHowPublished(String howPublished) {
        this.howpublished = howPublished;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
