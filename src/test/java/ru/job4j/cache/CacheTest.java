package ru.job4j.cache;

import org.junit.Assert;
import org.junit.Test;

public class CacheTest {

    @Test
    public void whenAdd() {
        Cache cache = new Cache();
        Base data = new Base(1, 1);
        Assert.assertTrue(cache.add(data));
        Assert.assertFalse(cache.add(data));
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base data = new Base(1, 1);
        Assert.assertTrue(cache.add(data));
        cache.delete(data);
        Assert.assertTrue(cache.add(data));
    }

    @Test
    public void whenUpdateProper() {
        Cache cache = new Cache();
        Base data = new Base(1, 1);
        Assert.assertTrue(cache.add(data));
        data.setName("New name");
        Assert.assertTrue(cache.update(data));
    }

    @Test
    public void whenUpdateWrong() {
        Cache cache = new Cache();
        Base data = new Base(1, 1);
        Assert.assertTrue(cache.add(data));
        data = new Base(2, 2);
        Assert.assertFalse(cache.update(data));
    }

    @Test(expected = OptimisticException.class)
    public void whenDoubleUpdate() {
        Cache cache = new Cache();
        Base data = new Base(1, 1);
        Assert.assertTrue(cache.add(data));
        data.setName("New name");
        Assert.assertTrue(cache.update(data));
        data.setName("New name 2");
        cache.update(data);
    }

}
