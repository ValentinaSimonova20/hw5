package org.example;

import java.util.function.BiFunction;

public class OperatorInfo {

    private final Integer priority;
    private final BiFunction<Double, Double, Double> function;

    private final Boolean isBi;

    public OperatorInfo(Integer priority, BiFunction<Double, Double, Double> function, Boolean isBi) {
        this.priority = priority;
        this.function = function;
        this.isBi = isBi;
    }

    public OperatorInfo(Integer priority, BiFunction<Double, Double, Double> biFunction) {
        this.priority = priority;
        this.function = biFunction;
        this.isBi = true;
    }

    public Integer getPriority() {
        return priority;
    }

    public BiFunction<Double, Double, Double> getFunction() {
        return function;
    }

    public Boolean isBi() {
        return isBi;
    }
}
