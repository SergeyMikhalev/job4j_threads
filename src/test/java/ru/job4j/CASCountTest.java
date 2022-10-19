package ru.job4j;

import org.junit.Assert;
import org.junit.Test;

public class CASCountTest {

    @Test
    public void whenMultiThreadDoubleAdd() throws InterruptedException {
        CASCount cnt = new CASCount(0);
        Thread thread1 = new Thread(cnt::increment);
        Thread thread2 = new Thread(cnt::increment);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        Assert.assertEquals(2, cnt.get());
    }

}
