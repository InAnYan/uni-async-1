package com.inanyan;

import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.stream.Collectors;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 2)
@Warmup(iterations = 2)
@Measurement(iterations = 1)
public class JavaStreamBenchmark {
    @Benchmark
    public int sum(IntegersState state) {
        return state.list.stream().reduce(0, Integer::sum);
    }

    @Benchmark
    public float average1(IntegersState state) {
        return (float) state.list.stream().reduce(0, Integer::sum) / state.list.size();
    }

    @Benchmark
    public float average2(IntegersState state) {
        return state.list.stream().map(i -> ((float) i) / state.list.size()).reduce(0.0F, Float::sum);
    }

    @Benchmark
    public double stdev(IntegersState state) {
        return Math.sqrt(
                state.list.stream()
                        .map(i -> Math.pow(i - state.average, 2))
                        .reduce(0.0, Double::sum)
                        / (state.list.size() - 1)
        );
    }

    @Benchmark
    public List<Integer> twice(IntegersState state) {
        return state.list.stream().map(i -> i * 2).toList();
    }

    @Benchmark
    public List<Integer> filter(IntegersState state) {
        return state.list.stream().filter(i -> i % 6 == 0).toList();
    }
}