package com.share;

import com.share.general.concurrent.ThreadLocalWrapper;
import java.util.function.Consumer;
import java.util.function.Function;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        ThreadLocalWrapper<String> threadLocal = new ThreadLocalWrapper<>();
        threadLocal.set("Consumer");
        threadLocal.executeAndRemove(this::test1);
        assertNull(threadLocal.get());

        threadLocal.set("Function");
        String o = threadLocal.executeAndRemove(this::test2);
        System.out.println(o);
        assertNull(threadLocal.get());
    }

    private void test1(String s) {
        System.out.println(s);
    }

    private String test2(String s) {
        return s;
    }
}
