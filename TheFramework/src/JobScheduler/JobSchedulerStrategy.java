package JobScheduler;

import java.util.List;
import java.util.stream.Stream;

public interface JobSchedulerStrategy<K, V> {

    // hot spot function that will be overridden when an instance will be created. It's a function that generates a stream of Ajob
    public abstract Stream<AJob<K, V>> emit();

    // hot spot function that will be overridden when an instance will be created. It's a function used to print the result
    public abstract void output(Stream<Pair<K, List<V>>> collectedStream);
}
