import threading
import time
import statistics
import functools

def bench(fun, n_threads=1, seq_iter=1, iter=1):
    # vector of threads
    threads = []
    # vector where it will be added the total time of each iteration
    times = []

    @functools.wraps(fun)
    def wrapper_function(*args):
        for _ in range(iter):
            begin = time.perf_counter() 
            # creation of threads
            for _ in range(n_threads):
                t = threading.Thread(target=threadFunction, args=(fun, seq_iter, *args))   
                t.start()  
                threads.append(t)
            # join of threads
            for i in threads:
                i.join()

            end = time.perf_counter()
            currentTime = end-begin
            times.append(currentTime)   
        
        d = {"Name function": fun.__name__,
             "args": args,
             "n_threads": n_threads,
             "seq_iter": seq_iter,
             "iter": iter,
             "mean": statistics.mean(times),
             "variance": statistics.variance(times)
             }
        return d

    return wrapper_function

# It's the function executed by each thread
def threadFunction(fun, seq_iter, *args):
    for _ in range(seq_iter):
        fun(*args)


def just_wait(n):
    time.sleep(n * 0.1)


def grezzo(n):
    for _ in range(2**n):
        pass



def test(iter, fun, *args):
    n_threads = 1
    seq_iter = 16
    
    while(seq_iter >= 2):
        function = bench(fun, n_threads, seq_iter, iter)
        dict = function(*args)       
        with open("Results.txt", 'a') as f:
            for key, value in dict.items(): 
                f.write('%s:%s\n' % (key, value))
            f.write('\n')                 
        seq_iter = seq_iter/2
        seq_iter = int(seq_iter)
        n_threads *= 2

# it cleans the file txt in case something is written
open('Results.txt', 'w').close()

test(2, just_wait, 10 )


# It has been executed the function grezzo and the function just_wait on a machine equipped with an
# AMD Ryzen AMD Ryzen 7 5800HS with 8 cores and 16 threads and the results are the following:

# On grezzo function the time of execution doubles increasing the number of threads, more preciseley passing to
# the function grezzo the argument 22 the results are the following:

# n_threads:1	iter:16 average execution time: 0.89180745
# n_threads:2	iter:8  average execution time:	0.7789035000000002
# n_threads:4	iter:4  average execution time:	0.8063874
# n_threads:8	iter:2  average execution time: 0.8157848999999997

# So on the grezzo's function increasing the number of threads doesn't lead to an improvement on the performance, but the
# time remain more or less constant on the various experiments. These results are due to the Global Interpreter Lock(GIL) that 
# allows only one thread to acquire the lock, so this means that only one thread ca be in state of execution at any point in time.
# So in this case the GIL is a bottleneck for CPU bound operations and the phrase "Two threads calling a function may take twice as much time as
# a single thread calling the function twice." is false because it's true that in the case of CPU bound operations there is not an improvment of
# performance but it's not true that there is so much difference in time of execution increasing the threads. 

# On just wait function the execution's time remain stable indipendently by the number of threads used,
# more precisely, passing 10 like argument to the function just_wait, the results are the following:

# n_threads:1	iter:16 average execution time: 16.17817615
# n_threads:2	iter:8  average execution time:	8.07899605
# n_threads:4	iter:4  average execution time:	4.0351754
# n_threads:8	iter:2  average execution time: 2.0276764999999983

# In the case of just wait function the time of execution is halved doubling the number of threads, this because the function is
# not a CPU bound operation












