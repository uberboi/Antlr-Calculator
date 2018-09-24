package org.sthuang.AntlrCalculator;

import java.util.*;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.sthuang.AntlrCalculator.calculatorParser.ExpressionContext;
import org.sthuang.AntlrCalculator.calculatorParser.MultiplyingExpressionContext;
import org.sthuang.AntlrCalculator.calculatorParser.PowExpressionContext;
import org.sthuang.AntlrCalculator.calculatorParser.SignedAtomContext;

public class calculator extends calculatorBaseVisitor<Double>{
	
	private HashMap<String, Double> variables = new HashMap<String, Double>();
	
	calculatorParser createParser(String expression) {
		CharStream stream = CharStreams.fromString(expression);
		calculatorLexer lexer = new calculatorLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		return new calculatorParser(tokenStream);
		
	}
	
	@Override
	public Double visitEquation(calculatorParser.EquationContext ctx) {
		System.out.println("visitEquation: " + ctx.getText());
		List<ExpressionContext> values =  ctx.expression();
		//always size 2
		for(int i=0; i<values.size(); i++) {
			System.out.println("equation: " + (visit(ctx.expression(i))));
			//System.out.println("Sum: " + sum)
		}
		System.out.println("size: " + values.size());
		//return Double.valueOf(visit(ctx.expression(1)));
		if(ctx.relop() != null) {
			
		}

		//variable passed correctly
		System.out.println(ctx.expression(1).getText());
		return 0.0;
		//return visitChildren(ctx);
	}
	
	@Override
	public Double visitExpression(calculatorParser.ExpressionContext ctx) {
		//System.out.println("PLUS: " + ctx.MINUS() != null);
		//System.out.println(ctx.MINUS(0));
		Double sum = 0.0;
		//System.out.println("visitExpression: " + ctx.getText());
		if(ctx.PLUS(0) != null) {
			
			List<MultiplyingExpressionContext> values = ctx.multiplyingExpression();
			//System.out.println("size: " + values.size());
			for(int i=0; i<values.size(); i++) {
				sum += Double.valueOf(visit(ctx.multiplyingExpression(i)));
				//System.out.println("Add: " + Double.valueOf(visit(ctx.multiplyingExpression(i))));
				//System.out.println("Sum: " + sum);
			}
			//System.out.println("sum: " + sum);
			return sum;

			//System.out.println("sum: " + sum);
		}else if(ctx.MINUS(0) != null) {
			sum = Double.valueOf(visit(ctx.multiplyingExpression(0)));
			List<MultiplyingExpressionContext> values = ctx.multiplyingExpression();
			//System.out.println("size: " + values.size());
			for(int i=1; i<values.size(); i++) {
				sum -= Double.valueOf(visit(ctx.multiplyingExpression(i)));
				//System.out.println("Subtract: " + Double.valueOf(visit(ctx.multiplyingExpression(i))));
				//System.out.println("Sum: " + sum);
			}

			//System.out.println("sum: " + sum);
			return sum;

		} else {
			return visit(ctx.multiplyingExpression(0));
		}
		//return visitChildren(ctx);
	}
	
	@Override
	public Double visitMultiplyingExpression(calculatorParser.MultiplyingExpressionContext ctx) {
		if(ctx.TIMES(0) != null) {
			//System.out.println("TIMES():");
			
			if(ctx.powExpression() != null) {
				List<PowExpressionContext> values = ctx.powExpression();
				//Numbers being multiplied
				//System.out.println(values.size());
				for(int i=0; i<values.size(); i++) {
					//Value to multiply
					//System.out.println(values.get(i).getText());
				}
			}
		}else if(ctx.DIV(0) != null) {
			System.out.println("DIV():");
			if(ctx.powExpression() != null) {
				List<PowExpressionContext>values = ctx.powExpression();
				//Numbers being divide
				//System.out.println(values.size());
				for(int i=0; i<values.size(); i++) {
					//Value to divide
					//System.out.println(values.get(i).getText());
				}
			}
		}else if(ctx.powExpression() != null) {

			return visit(ctx.powExpression(0));
			
			//return Double.valueOf(visit(ctx.powExpression(0)));
		}
		
		System.out.println("visitMultiplyingExpression: " + ctx.getText());
		return visitChildren(ctx);
	}
	
	@Override
	public Double visitPowExpression(calculatorParser.PowExpressionContext ctx) {
		if(ctx.signedAtom() != null) {
			//prints out base value then exponents
			List<SignedAtomContext> values = ctx.signedAtom();
			for(int i=0; i<values.size(); i++) {
				//System.out.println(values.get(i).getText());

			}
			//Base value index 0
			//System.out.println("Base Pow: " + ctx.signedAtom(0).getText());
			return visit(ctx.signedAtom(0));
			
			//return Double.valueOf(visit(ctx.signedAtom(0)));
		}
		//System.out.println("visitPowExpression: " + ctx.getText());

		return visitChildren(ctx);
	}
	
	@Override
	public Double visitSignedAtom(calculatorParser.SignedAtomContext ctx) {
		//System.out.print("SignedAtom ");
		if(ctx.PLUS() != null) {
			//System.out.println("PLUS: " + ctx.getText());
		} else if(ctx.MINUS() != null) {
			//System.out.println("MINUS: " + ctx.getText());
		} else if(ctx.atom() != null) {
			//System.out.println("Atom: " + ctx.getText());
			//System.out.println(visit(ctx.atom()).getClass().getSimpleName());
			//return Double.valueOf(visit(ctx.atom()));
			return visit(ctx.atom());
		} else if(ctx.func() != null){
			//System.out.println("FUNC: " + ctx.getText());
		}
		//System.out.println("visitSignedAtom: " + ctx.getText());
		return visitChildren(ctx);
	}
	
	@Override
	public Double visitAtom(calculatorParser.AtomContext ctx) {
		if(ctx.scientific() != null) {
			//System.out.println("Scientific Atom: " + ctx.getText());

			return Double.valueOf(ctx.getText());
		} else if(ctx.variable() != null) {
			System.out.println("Variable Atom: " + ctx.getText());

			return visit(ctx.variable());
			
		} else if(ctx.constant() != null) {
			//System.out.println("Constant Atom: " + ctx.getText());
		} else {
			//System.out.println("Parethesis Atom: " + ctx.getText());
			return Double.valueOf(visit(ctx.expression()));
		}
		//System.out.println("visitAtom: " + ctx.getText());
		return visitChildren(ctx);
		/*
		return Double.valueOf(ctx.getText());
		*/
	}
	
	@Override
	public Double visitConstant(calculatorParser.ConstantContext ctx) {
		//System.out.println("visitConstant: " + ctx.getText());
		return visitChildren(ctx);
	}
	
	@Override
	public Double visitVariable(calculatorParser.VariableContext ctx) {
		System.out.println("visitVariable: " + ctx.getText());
		return visit(ctx.VARIABLE());
	}
	
	@Override
	public Double visitFunc(calculatorParser.FuncContext ctx) {
		//System.out.println("visitFunc: " + ctx.getText());
		return visitChildren(ctx);
	}
	
	@Override
	public Double visitFuncname(calculatorParser.FuncnameContext ctx) {
		//System.out.println("visitFuncname: " + ctx.getText());
		return visitChildren(ctx);
	}
	
	@Override
	public Double visitRelop(calculatorParser.RelopContext ctx) {
		//System.out.println("visitRelop: " + ctx.getText());
		if(ctx.EQ() != null) {
			System.out.println("Relop: " + ctx.getText());
			return(visit(ctx.EQ()));
		}else if(ctx.GT() != null) {
			System.out.println("Relop: " + ctx.getText());
		}else if(ctx.LT() != null) {
			System.out.println("Relop: " + ctx.getText());
		}
		return visitChildren(ctx);
	}

}
