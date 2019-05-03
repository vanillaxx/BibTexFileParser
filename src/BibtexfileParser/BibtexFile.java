package BibtexfileParser;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Contains all parsed publications; ready to be displayed.
 */

public class BibtexFile {
    private List<IEntry> entries = new LinkedList<>();

    public List<IEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<IEntry> entries) {
        this.entries = entries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BibtexFile)) return false;
        BibtexFile that = (BibtexFile) o;
        return Objects.equals(entries, that.entries);
    }

    @Override
    public int hashCode() {

        return Objects.hash(entries);
    }
}
