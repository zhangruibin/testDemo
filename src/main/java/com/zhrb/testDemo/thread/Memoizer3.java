package com.zhrb.testDemo.thread;

import org.apache.commons.lang3.concurrent.Computable;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @ClassName Memoizer3
 * @Description
 * @Author Administrator
 * @Date 2019/10/29 15:44
 * @Version
 */
public class Memoizer3<A,V> implements Computable<A, V> {
    private final Map<A,Future<V>> catche = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A,V> computable;

    public Memoizer3(Computable<A, V> computable) {
        this.computable = computable;
    }


    @Override
    public V compute(final A arg) throws InterruptedException {
        Future<V> f = catche.get(arg);
        if (f == null){
            Callable<V> eval = new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return computable.compute(arg);
                }
            };
            FutureTask<V> futureTask = new FutureTask<V>(eval);
            f = futureTask;
            catche.put(arg,futureTask);
            futureTask.run();//调用computable.compute();
        }
        try {
            return f.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            return null;
        }
    }
}
