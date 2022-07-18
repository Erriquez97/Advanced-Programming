package Anagram;

import JobScheduler.AJob;
import JobScheduler.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Anagrams extends AJob {

    private String nameFile;

    public Anagrams(String nameFile) {
        this.nameFile = nameFile;
    }


    /*hot spot function overridden by Anagram class that read a file and takes only the strings in this file that have
     length at least 4 chars and it contains only alphanumeric characters. Then for each of these string creates a new pair
     made by the string with characters sorted and the string as it was*/
    @Override
    public Stream<Pair<String, String>> execute() {
        try {
            return
                  Stream.of(Files.readString(Paths.get(nameFile)).split("\\s+"))
                            .filter(w -> isAlphanumeric(w))
                            .filter(w -> w.length() >= 4)
                            .map(w -> new Pair(ciao(w), w));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // function that return true if the word taken in input has only alphanumeric characters, otherwise return false
    private boolean isAlphanumeric(String word) {
        char[] charArray = word.toCharArray();
        for (char c : charArray) {
            if (!Character.isLetterOrDigit(c))
                return false;
        }
        return true;
    }

    // function that takes in input a string and return the string with the letters sorted
    private String ciao(String word) {
        return Stream.of(word.split(""))
                .sorted()
                .map(w-> w.toLowerCase())
                .collect(Collectors.joining());
    }

}
