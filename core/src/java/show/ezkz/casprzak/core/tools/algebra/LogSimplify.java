package show.ezkz.casprzak.core.tools.algebra;

import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.binary.Logb;
import show.ezkz.casprzak.core.functions.binary.Pow;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.commutative.Sum;
import show.ezkz.casprzak.core.functions.unitary.specialcases.Exp;
import show.ezkz.casprzak.core.functions.unitary.specialcases.Ln;

import java.util.Arrays;

public class LogSimplify {

    public static GeneralFunction expand(GeneralFunction input) {
        if (!(input instanceof Logb) && !(input instanceof Ln))
            throw new IllegalArgumentException("expand should not be called on a non logarithm.");


        return null;
    }

    public static GeneralFunction simplify(GeneralFunction input) {
        return null;
    }

    /**
     * Splits a logarithm of a product into a sum of logs. Ex: {@code log(xy) = log(x) +log(y)}
     * @param input The logarithm that is being expanded.
     * @return The split logarithm
     */
    public static GeneralFunction logarithmOfAProduct(Ln input) {
        GeneralFunction operand = input.operand;

        if (operand instanceof Product product) {
            GeneralFunction[] terms = Arrays.stream(product.getFunctions())
                    .map(Ln::new)
                    .toArray(GeneralFunction[]::new);
            return new Sum(terms);
        } else
            return input;
    }


    /**
     * Splits a logarithm of a exponent into a product. Ex: {@code log(x^y) = y*log(x)}
     * @param input The logarithm that is being expanded.
     * @return The expanded expression
     */
    public static GeneralFunction logarithmOfAnExponent(Ln input) {
        GeneralFunction operand = input.operand;

        if (operand instanceof Pow exponential)
            return new Product(exponential.getFunction1(), new Ln(exponential.getFunction2()));
        else if (operand instanceof Exp exponential)
            return exponential.operand;
        else
            return input;

    }





}
