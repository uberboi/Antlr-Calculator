package org.sthuang.AntlrCalculator;

import java.util.*;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ErrorNode;

public class Listener extends calculatorBaseListener{
	HashSet<String> variables = new HashSet<String>();
	
	calculatorParser createParser(String expression) {
		CharStream stream = CharStreams.fromString(expression);
		calculatorLexer lexer = new calculatorLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		return new calculatorParser(tokenStream);
		
	}
	
	@Override public void enterEquation(calculatorParser.EquationContext ctx) { 
		System.out.println("Enter Equation");
		System.out.println("relop: " + ctx.relop().EQ());
		//check ctx.expression(0)->VARIABLE()?
		if(ctx.relop().EQ() != null) {
			variables.add(ctx.expression(0).getText());
		}
		System.out.println("expression(0): " + ctx.expression(0).getText());
		System.out.println("expression(1): " + ctx.expression(1).getText());
	}
	
	@Override public void exitEquation(calculatorParser.EquationContext ctx) { 
		System.out.println("Exit Equation");
		System.out.println("expression(0): " + ctx.expression(0).getText());
		System.out.println("expression(1): " + ctx.expression(1).getText());
	}
	
	@Override public void enterVariable(calculatorParser.VariableContext ctx) { 
		System.out.println("Enter Variable");
		System.out.println("Variable: " + ctx.VARIABLE());
		if(!variables.contains(ctx.VARIABLE().getText())) {
			System.err.println("Variable '" + ctx.VARIABLE() + "' is undefined.");
		}
	}
	
	@Override public void exitVariable(calculatorParser.VariableContext ctx) {
		System.out.println("Exit Variable");
	}
	
	@Override public void visitErrorNode(ErrorNode node) { 
		System.out.println("Error: " + node.getText());
	}
	
	@Override public void enterFunc(calculatorParser.FuncContext ctx) { 
		System.out.println("Enter func: " + ctx.funcname().LOG());
		System.out.println("Commas: " + ctx.COMMA().size());
		//
		//TODO: This check needs to be implemented in Visitor
		//
		if(ctx.COMMA().size() >= 1 &&  ctx.funcname().LOG() == null) {
			System.err.println(ctx.funcname().getText() + "() only takes one valid argument");
		}else if(ctx.COMMA().size() >= 2) {
			System.err.println(ctx.funcname().getText() + "() only takes one or two arguments");
		}
	}
	
	@Override public void exitFunc(calculatorParser.FuncContext ctx) { 
		
	}
	
}
