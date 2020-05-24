package functions.binary;

import functions.GeneralFunction;

import java.util.Map;

public class Rand extends BinaryFunction{
    /**
     * Constructs a new {@link Rand}
     * @param function1 The first {@link GeneralFunction} in the binary operation
     * @param function2 The second {@link GeneralFunction} in the binary operation
     */
    public Rand(GeneralFunction function1, GeneralFunction function2) {
        super(function1, function2);
    }

    @Override
    public BinaryFunction getInstance(GeneralFunction function1, GeneralFunction function2) {
        return new Rand(function1, function2);
    }

    @Override
    public String toString() {
        return "Rand( " + function2 + " , " + function1 + " )";
    }

    @Override
    public GeneralFunction clone() {
        return new Rand(function1.clone(), function2.clone());
    }

    @Override
    public GeneralFunction getDerivative(char varID) {
        throw new UnsupportedOperationException("Rand does not support derivatives.");
    }

    @Override
    public double evaluate(Map<Character, Double> variableValues) {
        double lowerBoundEvaluated = function2.evaluate(variableValues);
        return lowerBoundEvaluated + (function1.evaluate(variableValues) - lowerBoundEvaluated) * Math.random();
    }

    @Override
    public GeneralFunction simplify() {
        return new Rand(function1.simplify(), function2.simplify());
    }
}