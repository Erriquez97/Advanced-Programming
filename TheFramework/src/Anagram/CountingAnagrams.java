package Anagram;

import JobScheduler.AJob;
import JobScheduler.JobSchedulerStrategy;
import JobScheduler.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class CountingAnagrams implements JobSchedulerStrategy<String, String> {

    private final String RESULTFILE = "src/count_anagrams.txt";
    private String path;

    public CountingAnagrams(String path) {
        this.path = path;
    }

    // hot spot function that for each file in the path checks if the name of the file ends with ".txt" and
    // then creates a new Anagrams giving in input the path of the file.
    @Override
    public Stream<AJob<String, String>> emit() {
        try {
            return Files.walk(Paths.get(path))
                    .filter(file -> file.getFileName().toString().endsWith(".txt"))
                    .map(file -> new Anagrams(path + "/" + file.getFileName().toString()));
        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }

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
