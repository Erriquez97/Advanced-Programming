import Anagram.CountingAnagrams;
import JobScheduler.JobSchedulerContext;

public class Main {

    public static void main(String[] args) {

        CountingAnagrams countingAnagrams= new CountingAnagrams();
        JobSchedulerContext jobSchedulerContext = new JobSchedulerContext(countingAnagrams);
        jobSchedulerContext.main();


    }
}
