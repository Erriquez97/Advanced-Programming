package Anagram;

import JobScheduler.AJob;
import JobScheduler.JobSchedulerStrategy;
import JobScheduler.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class CountingAnagrams implements JobSchedulerStrategy<String, String> {

    private final String RESULTFILE = "src/count_anagrams.txt";

    public CountingAnagrams() {

    }
 // DA VEDERE PERCHE RESTITUISCE AJOB<STRING,STRING>
    // hot spot function that ask to insert the path where to read the files and then for each file checks if
    // the name of the file ends with ".txt" and then creates a new Anagrams giving in input the path of the file.
    @Override
    public Stream<AJob<String, String>> emit() {
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream
        System.out.print("Enter the path: ");
        String path = sc.nextLine();   //reads string
        try {
            return Files.walk(Paths.get(path))
                    .filter(file -> file.getFileName().toString().endsWith(".txt"))
                    .map(file -> new Anagrams(path + "/" + file.getFileName().toString()));
        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();
        }

    }

    //DA VEDERE
/* Hot spot function that takes in input a stream from the collect method. This function creates a file and for each job
 of the stream transform it in a string formed by the job's key and the size of the job's value and then write it in the file */
    @Override
    public void output(Stream<Pair<String, List<String>>> collectedStream) {
        try {
            FileWriter fileWriter = new FileWriter(RESULTFILE);
            collectedStream.map(job -> job.getKey() + "," + job.getValue().size() + "\n")
                    .forEach(string -> {
                        try {
                            fileWriter.write(string);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
