package com.inanyan;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Benchmark)
public class IntegersState {
    public static final int STREAM_SIZE = 10_000_000;
    public static final int RANDOM_NUMBER_ORIGIN = 1;
    public static final int RANDOM_NUMBER_BOUND = 100;

    public List<Integer> list;

    public float average;

    @Setup()
    public void init() {
        list = ThreadLocalRandom.current().ints(STREAM_SIZE, RANDOM_NUMBER_ORIGIN, RANDOM_NUMBER_BOUND).boxed().toList();
        average = (float) list.stream().reduce(0, Integer::sum) / list.size();
    }
}
