import threading
import time
import statistics

def bench(fun,n,n_threads,seq_iter,iter):
    # vector of threads
    threads = [] 
    # vector where it will be added the total time of each iteration
    times=[] 

    def inner1(*args, **kwargs):

        for i in range(iter): 
            begin = time.perf_counter()
            time.sleep(i)
            # creation of threads
            for j in range(n_threads):
                t = threading.Thread(target=funzioneDelThread, args=(fun, *args, seq_iter))  
                # print("NOME FUNZIONE: ",fun.__name__)
                t.start()
                threads.append(t)
            # join of threads
            for k in threads:
                k.join()

            end = time.perf_counter()
            currentTime = end-begin
            times.append(currentTime)

        d={ "Name function": fun.__name__,
            "args": (n),
            "n_threads": n_threads,
            "seq_iter": seq_iter, 
            "iter": iter, 
            "mean":statistics.mean(times),
            "variance":statistics.variance(times) 
          }
        return d

    return inner1



def funzione(x, y):
    print("ESECUZIONE FUNZIONE")
    x+y


def funzioneDelThread(fun, *args, seq_iter):
    for i in range(seq_iter):
        fun(*args)
        # print("COMPUTA")
        # funzione(x,y)

def just_wait(n):
    time.sleep(n*0.1)

def grezzo(n):
    for i in range(2**n):
        print(i)    

n_threads = 1
seq_iter = 2
iter = 3
n=10
ff= bench(funzione, n, n_threads,seq_iter,iter)
ff()

# def test(iter, fun, *args):
#     fun = bench(fun,args,1,1,iter)
#     fun()


# test(16,funzione,5)