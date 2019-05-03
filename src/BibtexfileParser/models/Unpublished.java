package BibtexfileParser.models;

import BibtexfileParser.HasAuthor;
import BibtexfileParser.IEntry;
import BibtexfileParser.Required;

import java.util.List;
import java.util.Objects;

/**
 * Representative of Unpublished, contains every possible field for that publication.
 */

public class Unpublished implements IEntry, HasAuthor {
    @Required
    private List<String> author;
    private String key;
    private String month;
    @Required
    private String note;
    @Required
    private String title;
    private String year;
    private String citationKey;

    @Override
    public String toString() {
        return "Unpublished " +
                "author " + author +
                " key " + key +
                " month " + month +
                " note " + note +
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Unpublished)) return false;
        Unpublished that = (Unpublished) o;
        return Objects.equals(author, that.author) &&
                Objects.equals(key, that.key) &&
                Objects.equals(month, that.month) &&
                Objects.equals(note, that.note) &&
                Objects.equals(title, that.title) &&
                Objects.equals(year, that.year) &&
                Objects.equals(citationKey, that.citationKey);
    }

    @Override
    public int hashCode() {

        return Objects.hash(author, key, month, note, title, year, citationKey);
    }
}
