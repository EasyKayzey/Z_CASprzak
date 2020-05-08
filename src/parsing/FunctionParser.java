package parsing;

import config.Settings;
import functions.GeneralFunction;
import functions.special.Constant;
import functions.special.Variable;
import tools.ParsingTools;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static parsing.OperationLists.*;

public class FunctionParser {

	private FunctionParser(){}

	/**
	 * Parses infix to a {@link GeneralFunction}
	 * @param infix infix string
	 * @return a {@link GeneralFunction} corresponding to the infix string
	 */
	public static GeneralFunction parseInfix(String infix) {
		return FunctionParser.parsePostfix(toPostfix(infix));
	}

	/**
	 * Parses infix using {@link #parseInfix(String)}, then simplifies the output
	 * @param infix infix string
	 * @return a {@link GeneralFunction} corresponding to the infix string, simplified
	 */
	public static GeneralFunction parseSimplified(String infix) {
		return parseInfix(infix).simplify();
	}

	/**
	 * Parses an array of postfix tokens into a {@link GeneralFunction}
	 * @param postfix array of tokens in postfix
	 * @return a {@link GeneralFunction} corresponding to the postfix string
	 */
	public static GeneralFunction parsePostfix(List<String> postfix) {
		Deque<GeneralFunction> functionStack = new LinkedList<>();

		for (String token : postfix) {
			if (Constant.isSpecialConstant(token)) {
				functionStack.push(new Constant(token));
			} else if (binaryOperations.contains(token)) {
				GeneralFunction a = functionStack.pop();
				GeneralFunction b = functionStack.pop();
				functionStack.push(FunctionMaker.makeBinary(token, a, b));
			} else if (unitaryOperations.contains(token)) {
				GeneralFunction c = functionStack.pop();
				functionStack.push(FunctionMaker.makeUnitary(token, c));
			} else {
				try {
					functionStack.push(new Constant(Double.parseDouble(token)));
				} catch (NumberFormatException e) {
					functionStack.push(new Variable(ParsingTools.getCharacter(token)));
				}
			}
		}

		if (functionStack.size() != 1)
			throw new IndexOutOfBoundsException("functionStack size is " + functionStack.size() + ", current stack is " + functionStack);

		return functionStack.pop();
	}

	/**
	 * Turns an infix string into a postfix array
	 * @param infix input string in infix
	 * @return array of postfix tokens
	 */
	public static List<String> toPostfix(String infix) {
		if (!Settings.enforceEscapes)
			infix = LatexReplacer.addEscapes(infix);
		infix = LatexReplacer.encodeGreek(infix);
		List<String> tokens = InfixTokenizer.tokenizeInfix(infix);
		Deque<String> postfix = new LinkedList<>();
		Deque<String> operators = new LinkedList<>();

		for (String token : tokens) {
			if (Constant.isSpecialConstant(token)) {
				postfix.add(token);
			} else if ("(".equals(token)) {
				operators.push(token);
			} else if (")".equals(token)) {
				while (!"(".equals(operators.peek()))
					postfix.add(operators.pop());
				operators.pop();
			} else if (unitaryOperations.contains(token) || binaryOperations.contains(token)) {
				operators.push(token);
			} else {
				postfix.add(token);
			}
		}

		while (operators.size() != 0)
			postfix.add(operators.pop());

		return new ArrayList<>(postfix);
	}
}