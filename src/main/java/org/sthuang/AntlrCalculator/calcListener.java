package org.sthuang.AntlrCalculator;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class calcListener extends calculatorBaseListener {
	calculatorParser createParser(String expression) {
		CharStream stream = CharStreams.fromString(expression);
		calculatorLexer lexer = new calculatorLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		return new calculatorParser(tokenStream);	
	}
	
	@Override public void enterEquation(calculatorParser.EquationContext ctx) {
		System.out.println("Enter Equation");
	}
	
	@Override public void exitEquation(calculatorParser.EquationContext ctx) {
		System.out.println("Exit Equation");
	}
}
