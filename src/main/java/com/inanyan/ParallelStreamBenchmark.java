package com.inanyan;

import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 10, time = 2, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 20, time = 2, timeUnit = TimeUnit.MILLISECONDS)
@Fork(value = 1)
public class ParallelStreamBenchmark {
    @Benchmark
    public int sum(IntegersState state) {
        return state.list.parallelStream().reduce(0, Integer::sum);
    }

    @Benchmark
    public float average1(IntegersState state) {
        return (float) state.list.parallelStream().reduce(0, Integer::sum) / state.list.size();
    }

    @Benchmark
    public float average2(IntegersState state) {
        return state.list.parallelStream().map(i -> ((float) i) / state.list.size()).reduce(0.0F, Float::sum);
    }

    @Benchmark
    public double stdev(IntegersState state) {
        return Math.sqrt(
                state.list.parallelStream()
                        .map(i -> Math.pow(i - state.average, 2))
                        .reduce(0.0, Double::sum)
                        / (state.list.size() - 1)
        );
    }

    @Benchmark
    public List<Integer> twice(IntegersState state) {
        return state.list.parallelStream().map(i -> i * 2).toList();
    }

    @Benchmark
    public List<Integer> filter(IntegersState state) {
        return state.list.parallelStream().filter(i -> i % 6 == 0).toList();
    }
}
