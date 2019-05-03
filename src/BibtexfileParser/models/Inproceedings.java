package BibtexfileParser.models;

import BibtexfileParser.Alternative;
import BibtexfileParser.HasAuthor;
import BibtexfileParser.IEntry;
import BibtexfileParser.Required;

import java.util.List;
import java.util.Objects;

/**
 * Representative of Inproceedings, contains every possible field for that publication.
 */

public class Inproceedings implements IEntry, HasAuthor {
    private String address;
    @Required
    private List<String> author;
    @Required
    private String booktitle;
    private List<String> editor;
    private String key;
    private String number;
    private String month;
    private String note;
    private String organization;
    private String pages;
    private String publisher;
    private String series;
    @Required
    private String title;
    private String volume;
    @Required
    private String year;
    private String citationKey;

    @Override
    public String toString() {
        return "Conference " +
                "address " + address +
                " author " + author +
                " booktitle " + booktitle +
                " editor " + editor +
                " key " + key +
                " number " + number +
                " month " + month +
                " note " + note +
                " organization " + organization +
                " pages " + pages +
                " publisher " + publisher +
                " series " + series +
                " title " + title +
                " volume " + volume +
                " year " + year +
                " citationKey";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inproceedings)) return false;
        Inproceedings that = (Inproceedings) o;
        return Objects.equals(address, that.address) &&
                Objects.equals(author, that.author) &&
                Objects.equals(booktitle, that.booktitle) &&
                Objects.equals(editor, that.editor) &&
                Objects.equals(key, that.key) &&
                Objects.equals(number, that.number) &&
                Objects.equals(month, that.month) &&
                Objects.equals(note, that.note) &&
                Objects.equals(organization, that.organization) &&
                Objects.equals(pages, that.pages) &&
                Objects.equals(publisher, that.publisher) &&
                Objects.equals(series, that.series) &&
                Objects.equals(title, that.title) &&
                Objects.equals(volume, that.volume) &&
                Objects.equals(year, that.year) &&
                Objects.equals(citationKey, that.citationKey);
    }

    @Override
    public int hashCode() {

        return Objects.hash(address, author, booktitle, editor, key, number, month, note, organization, pages, publisher, series, title, volume, year, citationKey);
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

    public String getBooktitle() {
        return booktitle;
    }

    public void setBooktitle(String booktitle) {
        this.booktitle = booktitle;
    }

    public List<String> getEditor() {
        return editor;
    }

    public void setEditor(List<String> editor) {
        this.editor = editor;
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
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
