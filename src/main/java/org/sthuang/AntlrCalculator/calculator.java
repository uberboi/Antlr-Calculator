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
		Double relop = visit(ctx.relop());
		if(relop != null && relop == 0.0) {
			String variable = ctx.expression(0).getText();
			Double value = visit(ctx.expression(1));
			if(value != null) {
				variables.put(variable, value);
				return null;
			}else {
				return null;
			}
		}else {
			return visit(ctx.expression(0));
		}
		
		
		/*******************************************************
		 *Commented out messy code to try and handle errors
		 *******************************************************/
		/*
		//relop() returns 0.0, 1.0, 2.0 for EQ(), GT(), LT() respectively
		Double relop = visit(ctx.relop());
		if(relop != null && relop == 0.0) {
			//for "3x=1" relop() returns x=>
			if(ctx.relop().getText().equals("=")) {
				String variable = ctx.expression(0).getText();
				Double value = visit(ctx.expression(1));
				System.out.println("variable: " + variable);
				System.out.println("value: " + value);
				if(value != null) {
					variables.put(variable, value);
					return null;
				}
				//System.out.println("No specified value for " + "'" + variable + "'");
				//System.out.println("Variable has no specified value");
				//So result doesn't print
				// "return value" if you want value of variable returned as result
				return null;
				
			} else {
				//relop() returns "x=" for 3x=1
				System.err.println("Invalid Variable Name");
				return null;
			} 
		} else {
			return visit(ctx.expression(0));
		}
		*/
	}
	
	@Override
	public Double visitExpression(calculatorParser.ExpressionContext ctx) {
		Double sum = 0.0;
		if(ctx.PLUS(0) != null) {
			int size = ctx.multiplyingExpression().size();
			for(int i=0; i<size; i++) {
				//Numbers to add
				Double adder = visit(ctx.multiplyingExpression(i));
				if(adder != null) {
				sum += adder;
				} else {
					return null;
				}
			}
			return sum;
		}else if(ctx.MINUS(0) != null) {
			System.out.println("blah");
			//Number to subtract from
			sum = visit(ctx.multiplyingExpression(0));
			int size = ctx.multiplyingExpression().size();
			for(int i=1; i<size; i++) {
				//Numbers to subtract
				Double subtract = visit(ctx.multiplyingExpression(i));
				if(subtract != null) {
					sum -= subtract;
				} else {
					return null;
				}
			}
			return sum;

		} else if (ctx.multiplyingExpression(0) != null){
			return visit(ctx.multiplyingExpression(0));
		}
		return visitChildren(ctx);
	}
	
	@Override
	public Double visitMultiplyingExpression(calculatorParser.MultiplyingExpressionContext ctx) {
		Double sum = 1.0;
		if(ctx.TIMES(0) != null) {
			int size = ctx.powExpression().size();
			//Numbers being multiplied
			for(int i=0; i<size; i++) {
				//Value to multiply
				Double multiplier = visit(ctx.powExpression(i));
				if(multiplier != null) {
					sum *= multiplier;
				}else {
					return null;
				}
			}
			return sum;
		}else if(ctx.DIV(0) != null) {
			//dividend
			sum = visit(ctx.powExpression(0));
			int size = ctx.powExpression().size();
			//iterate and divide by all divisors
			for(int i=1; i<size; i++) {
				Double divisor = visit(ctx.powExpression(i));
				if(divisor != null) {
					sum /= divisor;
				}else{
					return null;
				}
			}
			return sum;
		}else if(ctx.powExpression() != null) {
			return visit(ctx.powExpression(0));
		}
		return visitChildren(ctx);
	}
	
	@Override
	public Double visitPowExpression(calculatorParser.PowExpressionContext ctx) {
		if(ctx.signedAtom() != null) {
			//Base value
			Double sum = visit(ctx.signedAtom(0));
			int size = ctx.signedAtom().size();
			for(int i=1; i<size; i++) {
				//Call math.pow on all exponents
				Double exponent = visit(ctx.signedAtom(i));
				if(exponent != null) {
					sum = Math.pow(sum, exponent);
				}else {
					return null;
				}
				
			}
			return sum;
		}

		return visitChildren(ctx);
	}
	
	@Override
	public Double visitSignedAtom(calculatorParser.SignedAtomContext ctx) {
		if(ctx.PLUS() != null) {
			return visit(ctx.signedAtom());
		} else if(ctx.MINUS() != null) {
			Double d = visit(ctx.signedAtom());
			if(d!= null) return d*-1;
			
			return null;
		} else if(ctx.atom() != null) {
			return visit(ctx.atom());
		} else if(ctx.func() != null){
			return(visit(ctx.func()));
		}
		return visitChildren(ctx);
	}
	
	@Override
	public Double visitAtom(calculatorParser.AtomContext ctx) {
		if(ctx.scientific() != null) {
			return Double.valueOf(ctx.getText());
		} else if(ctx.variable() != null) {
			return visit(ctx.variable());
		} else if(ctx.constant() != null) {
			return visit(ctx.constant());
		} else {
			return visit(ctx.expression());
		}
	}
	
	@Override
	public Double visitConstant(calculatorParser.ConstantContext ctx) {
		if(ctx.PI() != null) {
			System.out.println("Pi constant");
			return Math.PI;
		} else if(ctx.EULER() != null) {
			System.out.println("EULER constant");
			return Math.E;
		} else if(ctx.I() != null) {
			//What to return for imaginary?
		}
		return visitChildren(ctx);
	}
	
	@Override
	public Double visitVariable(calculatorParser.VariableContext ctx) {
		//If variable is already set then return value
		if(variables.containsKey(ctx.VARIABLE().getText())) {
			//Double x = variables.get(ctx.VARIABLE().getText());
			return variables.get(ctx.VARIABLE().getText());
		}else {
			//returns null
			return visit(ctx.VARIABLE());
		}
	}
	
	@Override
	public Double visitFunc(calculatorParser.FuncContext ctx) {
		int size = ctx.expression().size();
		String s = ctx.funcname().getText();
		switch (s) {
			case "cos":
				return Math.cos(visit(ctx.expression(0)));
			case "tan":
				return Math.tan(visit(ctx.expression(0)));
			case "sin":
				return Math.sin(visit(ctx.expression(0)));
			case "acos":
				return Math.acos(visit(ctx.expression(0)));
			case "atan":
				return Math.atan(visit(ctx.expression(0)));
			case "asin":
				return Math.asin(visit(ctx.expression(0)));
			case "log":
				if(size >= 2)
				return Math.log(visit(ctx.expression(1)))/Math.log(visit(ctx.expression(0)));
			case "ln":
				return Math.log(visit(ctx.expression(0)));
			case "sqrt":
				return Math.sqrt(visit(ctx.expression(0)));
		    default:
		    	return visitChildren(ctx);
		}
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
			return 0.0;
		}else if(ctx.GT() != null) {
			return 1.0;
		}else if(ctx.LT() != null) {
			return 2.0;
		}
		return null;
	}

}
