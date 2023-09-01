package oa.mingdao.com.utils;

import com.google.common.util.concurrent.AtomicDouble;
import org.guiceside.commons.lang.NumberUtils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicCalUtils {

    public static Double add(Double value, Double addValue) {
        AtomicDouble atomicDouble = new AtomicDouble(value);
        return NumberUtils.multiply(atomicDouble.addAndGet(addValue), 1, 2);
    }

    public static Integer add(Integer value, Integer addValue) {
        AtomicInteger atomicInteger = new AtomicInteger(value);
        return atomicInteger.addAndGet(addValue);
    }

    public static Long add(Long value, Long addValue) {
        AtomicLong atomicLong = new AtomicLong(value);
        return atomicLong.addAndGet(addValue);
    }

    public static Integer increment(Integer value) {
        AtomicInteger atomicInteger = new AtomicInteger(value);
        return atomicInteger.incrementAndGet();
    }

    public static Long increment(Long value) {
        AtomicLong atomicLong = new AtomicLong(value);
        return atomicLong.incrementAndGet();
    }


    public static Double subtract(Double value, Double subtractValue) {
        AtomicDouble atomicDouble = new AtomicDouble(value);
        return NumberUtils.multiply(atomicDouble.addAndGet(-subtractValue), 1, 2);
    }

    public static Integer subtract(Integer value, Integer subtractValue) {
        AtomicInteger atomicInteger = new AtomicInteger(value);
        return atomicInteger.addAndGet(-subtractValue);
    }

    public static Long subtract(Long value, Long subtractValue) {
        AtomicLong atomicLong = new AtomicLong(value);
        return atomicLong.addAndGet(-subtractValue);
    }

    public static Integer decrement(Integer value) {
        AtomicInteger atomicInteger = new AtomicInteger(value);
        return atomicInteger.decrementAndGet();
    }

    public static Long decrement(Long value) {
        AtomicLong atomicLong = new AtomicLong(value);
        return atomicLong.decrementAndGet();
    }

}
