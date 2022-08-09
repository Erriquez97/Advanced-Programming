import Anagram.CountingAnagrams;
import JobScheduler.JobSchedulerContext;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // It reads the path containing the Books
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the path: ");
        String path = sc.nextLine();
        CountingAnagrams countingAnagrams= new CountingAnagrams(path);
        JobSchedulerContext jobSchedulerContext = new JobSchedulerContext(countingAnagrams);
        jobSchedulerContext.main();


    }
}
