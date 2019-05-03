package BibtexfileParser.models;

import BibtexfileParser.HasAuthor;
import BibtexfileParser.IEntry;
import BibtexfileParser.Required;

import java.util.List;
import java.util.Objects;

/**
 * Representative of Manual, contains every possible field for that publication.
 */

public class Manual implements IEntry, HasAuthor {
    private String address;
    private List<String> author;
    private String edition;
    private String key;
    private String month;
    private String note;
    private String organization;
    @Required
    private String title;
    private String year;
    private String citationKey;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manual)) return false;
        Manual manual = (Manual) o;
        return Objects.equals(address, manual.address) &&
                Objects.equals(author, manual.author) &&
                Objects.equals(edition, manual.edition) &&
                Objects.equals(key, manual.key) &&
                Objects.equals(month, manual.month) &&
                Objects.equals(note, manual.note) &&
                Objects.equals(organization, manual.organization) &&
                Objects.equals(title, manual.title) &&
                Objects.equals(year, manual.year) &&
                Objects.equals(citationKey, manual.citationKey);
    }

    @Override
    public int hashCode() {

        return Objects.hash(address, author, edition, key, month, note, organization, title, year, citationKey);
    }

    @Override
    public String toString() {
        return "Manual " +
                "address " + address +
                " author " + author +
                " edition " + edition +
                " key " + key +
                " month " + month +
                " note " + note +
                " organization " + organization +
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

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
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
