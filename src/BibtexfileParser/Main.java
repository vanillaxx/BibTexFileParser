package BibtexfileParser;

import BibtexfileParser.exceptions.LackOfRequiredArgumentException;
import BibtexfileParser.exceptions.InvalidPublicationException;
import org.apache.commons.cli.*;

import java.io.IOException;

/**
 * Main program. Takes parametres from command line and applies them in order to parse bibtexfile.
 */

public class Main {
    public static void main(String[] args) throws LackOfRequiredArgumentException, InvalidPublicationException {


        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        Option file = Option.builder("f").longOpt("filepath").hasArg(true).argName("bibtexfilepath").build();
        Option sign = Option.builder("s").longOpt("sign").hasArg(true).argName("character").build();
        Option category = Option.builder("c").longOpt("category").argName("categoryname").hasArg(true).hasArgs().build();
        Option authors = Option.builder("a").longOpt("authors").hasArg(true).argName("authorsoreditors").hasArgs().build();
        Option help = Option.builder("h").longOpt("help").hasArg(false).build();
        options.addOption(help);
        options.addOption(category);
        options.addOption(file);
        options.addOption(authors);
        options.addOption(sign);
        try {
            if (args.length != 0) {
                CommandLine line = parser.parse(options, args);
                if (line.hasOption("h")) {
                    HelpFormatter formatter = new HelpFormatter();
                    formatter.printHelp("Bibtex Parser", options);
                }
                if (line.hasOption("f")) {
                    String givenFile = line.getOptionValue("f");
                    String givenSign = new String("*");
                    if (line.hasOption("s")) {
                        givenSign = line.getOptionValue("s");
                    }
                    BibtexfileParser bibtexfileParser = new BibtexfileParser();
                    BibtexFile bibtexFile = bibtexfileParser.parse(givenFile);
                    BibtexfileDisplayer bibtexfileDisplayer = new BibtexfileDisplayer();
                    System.out.println(bibtexfileDisplayer.displayBibtexfile(bibtexFile, givenSign));

                    if (line.hasOption("a")) {
                        String[] givenAuthors = line.getOptionValues("a");
                        System.out.println();
                        System.out.println("Publications with given authors:");
                        System.out.println();
                        System.out.println(bibtexfileDisplayer.seekForAuthorAndDisplayBibtexFile(bibtexFile, givenAuthors, givenSign));
                        System.out.println();
                    }
                    if (line.hasOption("c")) {
                        String[] givenCategories = line.getOptionValues("c");
                        System.out.println();
                        System.out.println("Publications with given categories:");
                        System.out.println();
                        System.out.println(bibtexfileDisplayer.seekForCategoryAndDisplayBibtexfile(bibtexFile, givenCategories, givenSign));
                    }
                }
                else {
                    System.out.println("Press -h for help");
                }
            } else {
                System.out.println("Press -h for help");


            }
        } catch (ParseException e) {
            System.out.println("unexpected exception" + e);
        }
    }
}
