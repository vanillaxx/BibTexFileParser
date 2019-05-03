package BibtexfileParser;

import BibtexfileParser.exceptions.LackOfRequiredArgumentException;
import BibtexfileParser.models.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class BibtexfileParserTest {

    @Test
    public void checkIfPublicationsAreParsedProperlyShouldThrowException(){
        BibtexfileParser bibtexfileParser = new BibtexfileParser();
        try {
            bibtexfileParser.parse("publikacjepoprawnosc.bib");
        }
        catch(Exception e){
            assertEquals(e.getMessage(),"ARTICLE1556{ART(...) in record number 1 was not recognized");
            return;
        }
        fail("Expected parse to throw exception");
    }

    @Test
    public void checkIfFieldsAreParsedProperlyShouldThrowException(){
        BibtexfileParser bibtexfileParser = new BibtexfileParser();
        try {
            bibtexfileParser.parse("polepoprawnosc.bib");
        }
        catch(Exception e){
            assertEquals(e.getMessage(),"java.lang.NoSuchFieldException: authorfh in publication Article");
            return;
        }
        fail("Expected parse to throw exception");
    }

    @Test
    public void checkIfRequiredFieldThrowsException(){
        BibtexfileParser bibtexfileParser = new BibtexfileParser();
        try{
            bibtexfileParser.parse("required.bib");
        } catch(Exception e){
            assertEquals(e.getMessage(),"Required field: PUBLISHER in Incollection doesn't exist in file");
            return;
        }
        fail("Expected parse to throw exception");
    }

    @Test
    public void checkIfAlternativeFieldThrowsException(){
        BibtexfileParser bibtexfileParser = new BibtexfileParser();
        try{
            bibtexfileParser.parse("alternative.bib");
        } catch (Exception e){
            assertEquals(e.getMessage(),"Required field: EDITOR in Book doesn't exist in file");
            return;
        }
        fail("Expected parse to throw exception");
    }

    @Test
    public void checkIfAlternativeInFieldThrowsException(){
        BibtexfileParser bibtexfileParser = new BibtexfileParser();
        try{
            bibtexfileParser.parse("alternativein.bib");
        } catch (Exception e){
            assertEquals(e.getMessage(),"Required field: PAGES in Inbook doesn't exist in file");
            return;
        }
        fail("Expected parse to throw exception");
    }

    @Test
    public void checkIfAuthorsAreInProperOrder(){
        BibtexfileParser bibtexfileParser = new BibtexfileParser();
        BibtexFile bibtexFile = new BibtexFile();
        Inproceedings inpr = new Inproceedings();
        inpr.setCitationKey("inproceedings-full");
        List<String> authors = new ArrayList<>();
        authors.add("Alfred V. Oaho");
        authors.add("Jeffrey D. Ullman");
        authors.add("Mihalis Yannakakis");
        inpr.setAuthor(authors);
        inpr.setTitle("On Notions of Information Transfer in VLSI Circuits");
        inpr.setBooktitle("Proc. Fifteenth Annual ACM");
        List<String> editors = new ArrayList<>();
        editors.add("Wizard V. Oz");
        editors.add("Mihalis Yannakakis");
        inpr.setEditor(editors);
        inpr.setYear("1983");
        Unpublished unp = new Unpublished();
        unp.setCitationKey("unpublished-full");
        List<String> unaut = new ArrayList<>();
        unaut.add("Ulrich Underwood");
        unaut.add("Oberleutnant Franz von Nogay");
        unaut.add("Paul Pot");
        unp.setAuthor(unaut);
        unp.setTitle("Lower Bounds for Wishful Research Results");
        unp.setNote("Talk at Fanstord University (this is a full UNPUBLISHED entry)");
        List<IEntry> mylist = new ArrayList<>();
        mylist.add(inpr);
        mylist.add(unp);
        bibtexFile.setEntries(mylist);
        try {
            assertEquals(bibtexFile.getEntries(),bibtexfileParser.parse("author.bib").getEntries());
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Test
    public void checkIfCrossRefWorks() {
        List<Entry> exp = new LinkedList<>();
        Entry art = new Entry();
        art.setName("ARTICLE");
        art.setCitationKey("article-crossref");
        Map<String,String> artMap = new HashMap<>();
        artMap.put("author", "Leslie A. Aamport");
        artMap.put("title", "The Gnats and Gnus Document Preparation System");
        artMap.put("pages", "73+");
        artMap.put("note","This is a cross-referencing ARTICLE entry");
        artMap.put("key","GAJ");
        artMap.put("journal","Animal's Journal");
        artMap.put("year","1986");
        artMap.put("number","7");
        artMap.put("month","jul");
        artMap.put("volume","41");
        art.setContent(artMap);
        Entry crossedArt = new Entry();
        crossedArt.setName("ARTICLE");
        crossedArt.setCitationKey("whole-journal");
        Map<String,String> crossedArtMap = new HashMap<>();
        crossedArtMap.put("title","The Gnats blablabla");
        crossedArtMap.put("author","  yhyhy");
        crossedArtMap.put("key","GAJ");
        crossedArtMap.put("journal","Animal's Journal");
        crossedArtMap.put("year","1986");
        crossedArtMap.put("number","7");
        crossedArtMap.put("month","jul");
        crossedArtMap.put("note","(this entry is a cross-referenced ARTICLE (journal))");
        crossedArtMap.put("volume","41");
        crossedArt.setContent(crossedArtMap);
        exp.add(art);
        exp.add(crossedArt);
        BibtexfileParser bibtexfileParser = new BibtexfileParser();
        String[] crossed = bibtexfileParser.readFileAsString("crossref.bib").split("@");
        try{
            assertEquals(exp,bibtexfileParser.makeListOfEntries(crossed));
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Test
    public void checkIfStringsAreReplaced() {
        List<Entry> expectedListOfEntries = new LinkedList<>();
        Entry inProc = new Entry();
        inProc.setName("INPROCEEDINGS");
        inProc.setCitationKey("inproceedings-full");
        Map<String,String> expectedMapOfContent = new HashMap<>();
        expectedMapOfContent.put("author","Alfred V. Oaho and Jeffrey D. Ullman and Mihalis Yannakakis");
        expectedMapOfContent.put("title" , "On Notions of Information Transfer in VLSI Circuits");
        expectedMapOfContent.put("booktitle" , "Proc. Fifteenth Annual ACM Symposium on the Theory of Computing");
        expectedMapOfContent.put("year", "1983");
        expectedMapOfContent.put("organization","The OX Association for Computing Machinery");
        inProc.setContent(expectedMapOfContent);
        Entry phdThesis = new Entry();
        phdThesis.setName("PHDTHESIS");
        phdThesis.setCitationKey("phdthesis-full");
        Map<String,String> expMapOfContent = new HashMap<>();
        expMapOfContent.put("author", "F. Phidias Phony-Baloney");
        expMapOfContent.put("title","Fighting Fire with Fire: Festooning French Phrases");
        expMapOfContent.put("school","Fanstord University");
        expMapOfContent.put("month","june-augustjun");
        expMapOfContent.put("year","1988");
        phdThesis.setContent(expMapOfContent);
        expectedListOfEntries.add(inProc);
        expectedListOfEntries.add(phdThesis);
        BibtexfileParser bibtexfileParser = new BibtexfileParser();
        String[] strings = bibtexfileParser.readFileAsString("string.bib").split("@");
        try {
            assertEquals(bibtexfileParser.makeListOfEntries(strings), expectedListOfEntries);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void replaceVariables() {
        Map<String,String> mapOfStringVariables = new HashMap<>();
        mapOfStringVariables.put("STOC-key","OX\\singleletterstoc");
        mapOfStringVariables.put("ACM","The OX Association for Computing Machinery");
        mapOfStringVariables.put("STOC"," Symposium on the Theory of Computing");
        BibtexfileParser bibtexfileParser = new BibtexfileParser();
        BibtexFile bibtexFile = new BibtexFile();
        List<IEntry> listOfEntries = new ArrayList<>();
        Proceedings proceedings = new Proceedings();
        proceedings.setCitationKey("whole-proceedings");
        proceedings.setKey("STOC-key");
        proceedings.setOrganization("The OX Association for Computing Machinery");
        proceedings.setTitle("Proc. Fifteenth Annual Symposium on the Theory of Computing");
        proceedings.setYear("1983");
        listOfEntries.add(proceedings);
        bibtexFile.setEntries(listOfEntries);
        assertEquals(proceedings.getTitle(),bibtexfileParser.replaceVariables(mapOfStringVariables,"\"Proc. Fifteenth Annual\"#STOC"));
        assertEquals(proceedings.getOrganization(),bibtexfileParser.replaceVariables(mapOfStringVariables,"ACM"));

    }

    @Test
    public void removeRightBrace() {
        BibtexfileParser bibtexfileParser = new BibtexfileParser();
        StringBuilder a = new StringBuilder("abcd} ");
        StringBuilder b = new StringBuilder("abcd");
        assertNotEquals(b,bibtexfileParser.removeRightBrace(a));

    }

    @Test
    public void removeWhiteSpaces() {
        BibtexfileParser bibtexfileParser = new BibtexfileParser();
        StringBuilder file1= new StringBuilder("  \"  h h h\" ");
        StringBuilder expectedFile1 = new StringBuilder("\"  h h h\"");
        assertEquals(expectedFile1.toString(),bibtexfileParser.removeWhiteSpaces(file1).toString());
    }

    @Test
    public void readFileAsString() {
        BibtexfileParser bibtexfileParser = new BibtexfileParser();
        String file = "% Copyright (C) 1988, 2010 Oren Patashnik.\n" +
                "% Unlimited copying and redistribution of this file are permitted if it\n" +
                "% is unmodified.  Modifications (and their redistribution) are also\n" +
                "% permitted, as long as the resulting file is renamed.\n" +
                "\n" +
                "@preamble{ \"\\newcommand{\\noopsort}[1]{} \"\n" +
                "        # \"\\newcommand{\\printfirst}[2]{#1} \"\n" +
                "        # \"\\newcommand{\\singleletter}[1]{#1} \"\n" +
                "        # \"\\newcommand{\\switchargs}[2]{#2#1} \" }\n" +
                "\n" +
                "\n" +
                "@ARTICLE{article-full,\n" +
                "   author = \"L[eslie] A. Aamport\",\n" +
                "   title = \"The Gnats and Gnus Document Preparation System\",\n" +
                "   journal = \"\\mboxG-Animal's Journal\",\n" +
                "   year = \"1986\",\n" +
                "   volume = \"41\",\n" +
                "   number = \"7\",\n" +
                "   pages = \"73+\",\n" +
                "   month = \"jul\", note = \"This is a full ARTICLE entry\",\n" +
                "}\n" +
                "\n" +
                "@STRING{STOC-key = \"OX\\singleletterstoc\"}\n" +
                "\n" +
                "@STRING{ACM = \"The OX Association for Computing Machinery\"}\n" +
                "\n" +
                "@STRING{STOC = \" Symposium on the Theory of Computing\"}\n" +
                "\n" +
                "The KEY field is here to override the KEY field in the journal being\n" +
                "cross referenced (so is the NOTE field, in addition to its imparting\n" +
                "information).\n" +
                "\n" +
                "@comment{\"article-crossref\",\n" +
                "   crossref = \"WHOLE-JOURNAL\",\n" +
                "   key = \"\",\n" +
                "   author = \"L[eslie] A. Aamport\",\n" +
                "   title = \"The Gnats and Gnus Document Preparation System\",\n" +
                "   pages = \"73+\",\n" +
                "   note = \"This is a cross-referencing ARTICLE entry\",\n" +
                "}\n" +
                "\n" +
                "@ARTICLE{article-crossref,\n" +
                "   crossref = \"whole-journal\",\n" +
                "   key = \"\",\n" +
                "   author = \"L[eslie] A. Aamport\",\n" +
                "   title = \"The Gnats and Gnus Document Preparation System\",\n" +
                "   pages = \"73+\",\n" +
                "   note = \"This is a cross-referencing ARTICLE entry\",\n" +
                "}\n" +
                "\n" +
                "@ARTICLE{whole-journal, title = \"The Gnats blablabla\", author = \"  yhyhy\",\n" +
                "   key = \"GAJ\",\n" +
                "   journal = \"\\mboxG-Animal's Journal\",\n" +
                "   year = \"1986\",\n" +
                "   volume = \"41\",\n" +
                "   number = \"7\",\n" +
                "   month = \"jul\",\n" +
                "   note = \"The entire issue is devoted to gnats and gnus\n" +
                "                (this entry is a cross-referenced ARTICLE (journal))\",\n" +
                "}\n" +
                "\n" +
                "\n" +
                "@INBOOK{inbook-full,\n" +
                "   author = \"Donald E. Knuth\",\n" +
                "   title = \"Fundamental Algorithms\",\n" +
                "   volume = \"1\",\n" +
                "   series = \"The Art of Computer Programming\",\n" +
                "   publisher = \"Addison-Wesley\",\n" +
                "   address = \"Reading Massachusetts\",\n" +
                "   edition = \"Second\",\n" +
                "   month = \"10 jan\",\n" +
                "   year = \"1973\",\n" +
                "   type = \"Section\",\n" +
                "   chapter = \"1.2\",\n" +
                "   pages = \"10 119\",\n" +
                "   note = \"This is a full INBOOK entry\",\n" +
                "}\n" +
                "\n" +
                "\n" +
                "@BOOK{book-full,\n" +
                "   author = \"Donald E. Knuth\",\n" +
                "   title = \"Seminumerical Algorithms\",\n" +
                "   volume = \"2\",\n" +
                "   series = \"The Art of Computer Programming\",\n" +
                "   publisher = \"Addison-Wesley\",\n" +
                "   address = \"Reading Massachusetts\",\n" +
                "   edition = \"Second\",\n" +
                "   month = \"10 jan\",\n" +
                "   year = \"\\noopsort1973c1981\",\n" +
                "   note = \"This is a full BOOK entry\",\n" +
                "}\n" +
                "\n" +
                "@BOOKLET{booklet-full,\n" +
                "   author = \"Jill C. Knvth\",\n" +
                "   title = \"The Programming of Computer Art\",\n" +
                "   howpublished = \"Vernier Art Center\",\n" +
                "   address = \"Stanford California\",\n" +
                "   month = \"feb\",\n" +
                "   year = \"1988\",\n" +
                "   note = \"This is a full BOOKLET entry\",\n" +
                "}\n" +
                "\n" +
                "@INCOLLECTION{incollection-full,\n" +
                "   author = \"Daniel D. Lincoll\",\n" +
                "   title = \"Semigroups of Recurrences\",\n" +
                "   editor = \"David J. Lipcoll and D. H. Lawrie and A. H. Sameh\",\n" +
                "   booktitle = \"High Speed Computer and Algorithm Organization\",\n" +
                "   number = \"23\",\n" +
                "   series = \"Fast Computers\",\n" +
                "   chapter = \"3\",\n" +
                "   type = \"Part\",\n" +
                "   pages = \"179 183\",\n" +
                "   publisher = \"Academic Press\",\n" +
                "   address = \"New York\",\n" +
                "   edition = \"Third\",\n" +
                "   month = \"sep\",\n" +
                "   year = \"1977\",\n" +
                "   note = \"This is a full INCOLLECTION entry\",\n" +
                "}\n" +
                "\n" +
                "@BOOK{whole-collection,\n" +
                "   editor = \"David J. Lipcoll and D. H. Lawrie and A. H. Sameh\",\n" +
                "   title = \"High Speed Computer and Algorithm Organization\",\n" +
                "   series = \"Fast Computers\",\n" +
                "   publisher = \"Academic Press\",\n" +
                "   address = \"New York\",\n" +
                "   edition = \"Third\",\n" +
                "   month = \"sep\",\n" +
                "   year = \"1977\",\n" +
                "   note = \"This is a cross-referenced BOOK (collection) entry\",\n" +
                "}\n" +
                "\n" +
                "@MANUAL{manual-full,\n" +
                "   author = \"Larry Manmaker\",\n" +
                "   title = \"The Definitive Computer Manual\",\n" +
                "   organization = \"Chips-R-Us\",\n" +
                "   address = \"Silicon Valley\",\n" +
                "   edition = \"Silver\",\n" +
                "   month = \"apr\",\n" +
                "   year = \"1986\",\n" +
                "   note = \"This is a full MANUAL entry\",\n" +
                "}\n" +
                "\n" +
                "@MASTERSTHESIS{mastersthesis-full,\n" +
                "   author = \"douard Masterly\",\n" +
                "   title = \"Mastering Thesis Writing\",\n" +
                "   school = \"Stanford University\",\n" +
                "   type = \"Master's project\",\n" +
                "   address = \"English Department\",\n" +
                "   month = \"jun aug\",\n" +
                "   year = \"1988\",\n" +
                "   note = \"This is a full MASTERSTHESIS entry\",\n" +
                "}\n" +
                "\n" +
                "@MISC{misc-full,\n" +
                "   author = \"Joe-Bob Missilany\",\n" +
                "   title = \"Handing out random pamphlets in airports\",\n" +
                "   howpublished = \"Handed out at O'Hare\",\n" +
                "   month = \"oct\",\n" +
                "   year = \"1984\",\n" +
                "   note = \"This is a full MISC entry\",\n" +
                "}\n" +
                "\n" +
                "@INPROCEEDINGS{inproceedings-full,\n" +
                "   author = \"Alfred V. Oaho and Jeffrey D. Ullman and Yannakakis| Mihalis \",\n" +
                "   title = \"On Notions of Information Transfer in VLSI Circuits\",\n" +
                "   editor = \"Wizard V. Oz and Mihalis Yannakakis\",\n" +
                "   booktitle = \"Proc. Fifteenth Annual ACM\" #STOC,\n" +
                "   number = \"17\",\n" +
                "   series = \"All ACM Conferences\",\n" +
                "   pages = \"133 139\",\n" +
                "   month = \"mar\",\n" +
                "   year = \"1983\",\n" +
                "   address = \"Boston\",\n" +
                "   organization = ACM,\n" +
                "   publisher = \"Academic Press\",\n" +
                "   note = \"This is a full INPROCEDINGS entry\",\n" +
                "}\n" +
                "@String{jun=\"june\"}\n" +
                "@String{aug=\"aug\"}\n" +
                "@PHDTHESIS{phdthesis-full,\n" +
                "   author = \"F. Phidias Phony-Baloney\",\n" +
                "   title = \"Fighting Fire with Fire: Festooning French Phrases\",\n" +
                "   school = \"Fanstord University\",\n" +
                "   type = \"PhD Dissertation\",\n" +
                "   address = \"Department of French\",\n" +
                "   month = jun # \"-\" # aug,\n" +
                "   year = \"1988\",\n" +
                "   note = \"This is a full PHDTHESIS entry\",\n" +
                "}\n" +
                "\n" +
                "@TECHREPORT{techreport-full,\n" +
                "   author = \"Tom Trrific\",\n" +
                "   title = \"AnSorting Algorithm\",\n" +
                "   institution = \"Fanstord University\",\n" +
                "   number = \"7\",\n" +
                "   address = \"Computer Science Department Fanstord California\",\n" +
                "   month = \"oct\",\n" +
                "   year = \"1988\",\n" +
                "   note = \"This is a full TECHREPORT entry\",\n" +
                "}\n" +
                "\n" +
                "@UNPUBLISHED{unpublished-full,\n" +
                "   author = \"Ulrich Underwood and Ned Net and Paul Pot\",\n" +
                "   title = \"Lower Bounds for Wishful Research Results\",\n" +
                "   year = \"1988\",\n" +
                "   note = \"Talk at Fanstord University (this is a full UNPUBLISHED entry)\",\n" +
                "}\n" +
                "\n" +
                "@MISC{random-note-crossref,\n" +
                "   key = \"Volume-2\",\n" +
                "   note = \"Volume~2 is listed under Knuth \\citebook-full\"\n" +
                "}\n" +
                "\n";
        assertEquals(file,bibtexfileParser.readFileAsString("test.bib"));
    }
}