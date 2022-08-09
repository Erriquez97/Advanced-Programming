package JobScheduler;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JobSchedulerContext<K, V> {

    private JobSchedulerStrategy<K, V> jobStrategy;


    public JobSchedulerContext(JobSchedulerStrategy<K, V> jobStrategy) {
        this.jobStrategy= jobStrategy;
    }

    /* Function that takes in input a stream<Ajob<K,V>> from the emit, it computes the Ajob's execute method on each stream
     and returns a Stream<Pair<K,V>> */
    public Stream<Pair<K, V>> compute(Stream<AJob<K, V>> jobs) {
        return jobs.flatMap(AJob::execute);
    }

    //Function that takes in input a Stream of pair(K,V) and group the pairs with the same key, so now there are pairs formed
    // by the key and the list of values
    public Stream<Pair<K, List<V>>> collect(Stream<Pair<K, V>> jobs) {
        return jobs
                .collect(Collectors.groupingBy(Pair::getKey, Collectors.mapping(Pair::getValue, Collectors.toList())))
                .entrySet().stream().map(x -> new Pair(x.getKey(), x.getValue()));
    }


    public void main() {
        jobStrategy.output(collect(compute(jobStrategy.emit())));

    }

}
