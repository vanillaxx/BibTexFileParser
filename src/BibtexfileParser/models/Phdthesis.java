package BibtexfileParser.models;

import BibtexfileParser.HasAuthor;
import BibtexfileParser.IEntry;
import BibtexfileParser.Required;

import java.util.List;
import java.util.Objects;

/**
 * Representative of Phdthesis, contains every possible field for that publication.
 */

public class Phdthesis implements IEntry, HasAuthor {
    private String address;
    @Required
    private List<String> author;
    private String key;
    private String month;
    private String note;
    @Required
    private String school;
    @Required
    private String title;
    private String type;
    @Required
    private String year;
    private String citationKey;

    @Override
    public String toString() {
        return "Phdthesis " +
                "address " + address +
                " author " + author +
                " key " + key +
                " month " + month +
                " note " + note +
                " school " + school +
                " title " + title +
                " type " + type +
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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        if (!(o instanceof Phdthesis)) return false;
        Phdthesis phdthesis = (Phdthesis) o;
        return Objects.equals(address, phdthesis.address) &&
                Objects.equals(author, phdthesis.author) &&
                Objects.equals(key, phdthesis.key) &&
                Objects.equals(month, phdthesis.month) &&
                Objects.equals(note, phdthesis.note) &&
                Objects.equals(school, phdthesis.school) &&
                Objects.equals(title, phdthesis.title) &&
                Objects.equals(type, phdthesis.type) &&
                Objects.equals(year, phdthesis.year) &&
                Objects.equals(citationKey, phdthesis.citationKey);
    }

    @Override
    public int hashCode() {

        return Objects.hash(address, author, key, month, note, school, title, type, year, citationKey);
    }
}
