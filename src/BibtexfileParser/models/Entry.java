package BibtexfileParser.models;

import BibtexfileParser.IEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A class which stores information about individual publication
 * Made to be tranfsormed into a particular instance of class from package models.
 */

public class Entry implements IEntry {

    private String name;
    private String citationKey;
    private Map<String,String> content = new HashMap<>();

    @Override
    public String toString() {
        return "Entry " +
                "name " + name +
                " citationKey " + citationKey +
                " content " + content.values();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getCitationKey() {
        return citationKey;
    }

    public void setCitationKey(String key) {
        this.citationKey = key;
    }

    public Map<String, String> getContent() {
        return content;
    }

    public void setContent(Map<String, String> content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entry)) return false;
        Entry entry = (Entry) o;
        return Objects.equals(name, entry.name) &&
                Objects.equals(citationKey, entry.citationKey) &&
                Objects.equals(content, entry.content);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, citationKey, content);
    }
}
