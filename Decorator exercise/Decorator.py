import threading
import time
import statistics


def bench(fun, n_threads=1, seq_iter=1, iter=1, *args):
    # vector of threads
    threads = []
    # vector where it will be added the total time of each iteration
    times = []

    def wrapper_function():
        for _ in range(iter):
            begin = time.perf_counter()
            # time.sleep(i)
            # creation of threads
            for _ in range(n_threads):
                t = threading.Thread(
                    target=threadFunction, args=(fun, seq_iter, *args))
                # print("NOME FUNZIONE: ",fun.__name__)
                t.start()
                threads.append(t)
            # join of threads
            for i in threads:
                i.join()

            end = time.perf_counter()
            currentTime = end-begin
            times.append(currentTime)
            # print(currentTime)

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


def sum(*args):
    somma = 0
    for i in args:
        somma += i
    # print(somma)


def threadFunction(fun, seq_iter, *args):
    for _ in range(seq_iter):
        fun(*args)
        # print("COMPUTA")
        # funzione(x,y)


def just_wait(n):
    time.sleep(n*0.1)


def grezzo(n):
    for i in range(2**n):
        pass


n_threads = 1
seq_iter = 2
iter = 3
n = 10
# ff = bench(sum, n_threads, seq_iter, iter, 10,20)
# ff()


def test(iter, fun, *args):
    n_threads = 1
    seq_iter = 1
    # it cleans the file txt in case something is written
    
    while(iter >= 2):
        function = bench(fun, n_threads, seq_iter, iter, *args)
        dict = function()

        # with open("results.txt", "w") as f:
        #     print(dict, file=f)
        
        with open("Results.txt", 'a') as f:
            for key, value in dict.items(): 
                f.write('%s:%s\n' % (key, value))
            f.write('\n')    

       
        for i in dict:
            if(i=="mean"):
                print(dict[i])              
        iter = iter/2
        iter = int(iter)
        n_threads *= 2
open('Results.txt', 'w').close()
# for i in range(10):
#     print("ESECUZIONE CON Valore: ",i)

test(16, just_wait,10 )

# It has been executed the function grezzo and the function just_wait and the results are the following:

# On grezzo function the time of execution doubles increasing the threads, more preciseley passing like argument :
# n_threads:1	iter:16 average execution time: 0.0004475
# n_threads:2	iter:8  average execution time:	0.0009198
# n_threads:4	iter:4  average execution time:	0.0015986
# n_threads:8	iter:2  average execution time: 0.0031417





