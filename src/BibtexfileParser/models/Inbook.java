package BibtexfileParser.models;

import BibtexfileParser.*;
import BibtexfileParser.Alternative;

import java.util.List;
import java.util.Objects;

/**
 * Representative of Inbook, contains every possible field for that publication.
 */

public class Inbook implements IEntry, HasAuthor {
    private String address;
    @Alternative
    private List<String> author;
    @Alternative
    private List<String> editor;
    private String edition;
    @AlternativeIn
    private String chapter;
    private String key;
    private String number;
    private String month;
    private String note;
    @AlternativeIn
    private String pages;
    @Required
    private String publisher;
    private String series;
    @Required
    private String title;
    private String type;
    private String volume;
    @Required
    private String year;
    private String citationKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inbook)) return false;
        Inbook inbook = (Inbook) o;
        return Objects.equals(address, inbook.address) &&
                Objects.equals(author, inbook.author) &&
                Objects.equals(editor, inbook.editor) &&
                Objects.equals(edition, inbook.edition) &&
                Objects.equals(chapter, inbook.chapter) &&
                Objects.equals(key, inbook.key) &&
                Objects.equals(number, inbook.number) &&
                Objects.equals(month, inbook.month) &&
                Objects.equals(note, inbook.note) &&
                Objects.equals(pages, inbook.pages) &&
                Objects.equals(publisher, inbook.publisher) &&
                Objects.equals(series, inbook.series) &&
                Objects.equals(title, inbook.title) &&
                Objects.equals(type, inbook.type) &&
                Objects.equals(volume, inbook.volume) &&
                Objects.equals(year, inbook.year) &&
                Objects.equals(citationKey, inbook.citationKey);
    }

    @Override
    public int hashCode() {

        return Objects.hash(address, author, editor, edition, chapter, key, number, month, note, pages, publisher, series, title, type, volume, year, citationKey);
    }

    @Override
    public String toString() {
        return "Inbook " +
                "address " + address +
                " author " + author +
                " editor " + editor +
                " edition " + edition +
                " chapter " + chapter +
                " key " + key +
                " number " + number +
                " month " + month +
                " note " + note +
                " pages " + pages +
                " publisher " + publisher +
                " series " + series +
                " title " + title +
                " type " + type +
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

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
