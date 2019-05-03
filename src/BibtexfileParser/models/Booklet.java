package BibtexfileParser.models;

import BibtexfileParser.HasAuthor;
import BibtexfileParser.IEntry;
import BibtexfileParser.Required;

import java.util.List;
import java.util.Objects;

/**
 * Representative of Booklet, contains every possible field for that publication.
 */

public class Booklet implements IEntry, HasAuthor {

    private String address;
    private List<String> author;
    private String howpublished;
    private String key;
    private String month;
    private String note;
    @Required
    private String title;
    private String year;
    private String citationKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booklet)) return false;
        Booklet booklet = (Booklet) o;
        return Objects.equals(address, booklet.address) &&
                Objects.equals(author, booklet.author) &&
                Objects.equals(howpublished, booklet.howpublished) &&
                Objects.equals(key, booklet.key) &&
                Objects.equals(month, booklet.month) &&
                Objects.equals(note, booklet.note) &&
                Objects.equals(title, booklet.title) &&
                Objects.equals(year, booklet.year) &&
                Objects.equals(citationKey, booklet.citationKey);
    }

    @Override
    public int hashCode() {

        return Objects.hash(address, author, howpublished, key, month, note, title, year, citationKey);
    }

    @Override
    public String toString() {
        return "Booklet " +
                "address " + address +
                " author " + author +
                " howpublished " + howpublished +
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




    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

