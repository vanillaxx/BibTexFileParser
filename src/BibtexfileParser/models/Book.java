package BibtexfileParser.models;

import BibtexfileParser.Alternative;
import BibtexfileParser.HasAuthor;
import BibtexfileParser.IEntry;
import BibtexfileParser.Required;


import java.util.List;
import java.util.Objects;

/**
 * Representative of Book, contains every possible field for that publication.
 */

public class Book implements IEntry, HasAuthor {

    private String address;
    @Alternative
    private List<String> author;
    @Alternative
    private List<String> editor;
    private String edition;
    private String key;
    private String month;
    private String note;
    @Required
    private String publisher;
    private String series;
    @Required
    private String title;
    private String volume;
    @Required
    private String year;
    private String citationKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(address, book.address) &&
                Objects.equals(author, book.author) &&
                Objects.equals(editor, book.editor) &&
                Objects.equals(edition, book.edition) &&
                Objects.equals(key, book.key) &&
                Objects.equals(month, book.month) &&
                Objects.equals(note, book.note) &&
                Objects.equals(publisher, book.publisher) &&
                Objects.equals(series, book.series) &&
                Objects.equals(title, book.title) &&
                Objects.equals(volume, book.volume) &&
                Objects.equals(year, book.year) &&
                Objects.equals(citationKey, book.citationKey);
    }

    @Override
    public int hashCode() {

        return Objects.hash(address, author, editor, edition, key, month, note, publisher, series, title, volume, year, citationKey);
    }

    @Override
    public String toString() {
        return "Book " +
                "address " + address +
                " author " + author +
                " editor " + editor +
                " edition " + edition +
                " key " + key +
                " month " + month +
                " note " + note +
                " publisher " + publisher +
                " series " + series +
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

    public List<String> getEditor() {
        return editor;
    }

    public void setEditor(List<String> editor) {
        this.editor = editor;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
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
