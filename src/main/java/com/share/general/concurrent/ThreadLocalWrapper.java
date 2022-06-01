package com.share.general.concurrent;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Example of use
 * <pre>
 *     public void testApp() {
 *         ThreadLocalWrapper&lt;String&gt; threadLocal = new ThreadLocalWrapper<>();
 *         threadLocal.set("Consumer");
 *         threadLocal.executeAndRemove(this::test1);
 *         assertNull(threadLocal.get());
 *
 *         threadLocal.set("Function");
 *         String o = threadLocal.executeAndRemove(this::test2);
 *         System.out.println(o);
 *         assertNull(threadLocal.get());
 *     }
 *
 *     private void test1(String s) {
 *         System.out.println(s);
 *     }
 *
 *     private String test2(String s) {
 *         return s;
 *     }
 * </pre>
 * @author coder
 */
public class ThreadLocalWrapper<T> {
    private final ThreadLocal<T> delegate;

    public ThreadLocalWrapper() {
        this.delegate = new ThreadLocal<>();
    }

    /**
     * Call {@link ThreadLocal#remove()} after executing {@link Consumer#accept(T)}
     * @param consumer consumer
     */
    public void executeAndRemove(Consumer<T> consumer) {
        try {
            consumer.accept(delegate.get());
        } finally {
            delegate.remove();
        }
    }

    /**
     * Call {@link ThreadLocal#remove()} after executing {@link Function#apply(R)}
     * @param function function
     * @return R
     * @param <R> Return value type
     */
    public <R> R executeAndRemove(Function<T, R> function) {
        try {
            return function.apply(delegate.get());
        } finally {
            delegate.remove();
        }
    }

    public ThreadLocalWrapper(ThreadLocal<T> delegate) {
        super();
        this.delegate = delegate;
    }

    public T get() {
        return delegate.get();
    }

    public void set(T value) {
        delegate.set(value);
    }

    public void remove() {
        delegate.remove();
    }
}
