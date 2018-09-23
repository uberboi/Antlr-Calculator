package org.sthuang.AntlrCalculator;

import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.sthuang.AntlrCalculator.calculatorParser.SignedAtomContext;

public class calculator<T> extends calculatorBaseVisitor<T>{
	
	calculatorParser createParser(String expression) {
		CharStream stream = CharStreams.fromString(expression);
		calculatorLexer lexer = new calculatorLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		return new calculatorParser(tokenStream);
		
	}
	
	@Override
	public T visitEquation(calculatorParser.EquationContext ctx) {
		System.out.println("visitEquation: " + ctx.getText());
		return visitChildren(ctx);
	}
	
	@Override
	public T visitExpression(calculatorParser.ExpressionContext ctx) {
		System.out.println("visitExpression: " + ctx.getText());
		return visitChildren(ctx);
	}
	
	@Override
	public T visitMultiplyingExpression(calculatorParser.MultiplyingExpressionContext ctx) {
		System.out.println("visitMultiplyingExpression: " + ctx.getText());
		return visitChildren(ctx);
	}
	
	@Override
	public T visitPowExpression(calculatorParser.PowExpressionContext ctx) {
		if(ctx.signedAtom() != null) {
			//prints out base value then exponents
			List<SignedAtomContext> values = ctx.signedAtom();
			for(int i=0; i<values.size(); i++) {
				System.out.println(values.get(i).getText());
			}
			//Base value index 0
			System.out.println("Base Pow: " + ctx.signedAtom(0).getText());
			
		}
		System.out.println("visitPowExpression: " + ctx.getText());
		return visitChildren(ctx);
	}
	
	@Override
	public T visitSignedAtom(calculatorParser.SignedAtomContext ctx) {
		System.out.print("SignedAtom ");
		if(ctx.PLUS() != null) {
			System.out.println("PLUS: " + ctx.getText());
		} else if(ctx.MINUS() != null) {
			System.out.println("MINUS: " + ctx.getText());
		} else if(ctx.atom() != null) {
			System.out.println("Atom: " + ctx.getText());
		} else {
			System.out.println("FUNC: " + ctx.getText());
		}
		//System.out.println("visitSignedAtom: " + ctx.getText());
		return visitChildren(ctx);
	}
	
	@Override
	public T visitAtom(calculatorParser.AtomContext ctx) {
		if(ctx.scientific() != null) {
			System.out.println("Scientific Atom: " + ctx.getText());
		} else if(ctx.variable() != null) {
			System.out.println("Variable Atom: " + ctx.getText());
		} else if(ctx.constant() != null) {
			System.out.println("Constnat Atom: " + ctx.getText());
		} else {
			System.out.println("Parethesis Atom: " + ctx.getText());
			//ctx.expression();
		}
		//System.out.println("visitAtom: " + ctx.getText());
		return visitChildren(ctx);
		/*
		return Double.valueOf(ctx.getText());
		*/
	}
	
	@Override
	public T visitConstant(calculatorParser.ConstantContext ctx) {
		System.out.println("visitConstant: " + ctx.getText());
		return visitChildren(ctx);
	}
	
	@Override
	public T visitVariable(calculatorParser.VariableContext ctx) {
		System.out.println("visitVariable: " + ctx.getText());
		return visitChildren(ctx);
	}
	
	@Override
	public T visitFunc(calculatorParser.FuncContext ctx) {
		System.out.println("visitFunc: " + ctx.getText());
		return visitChildren(ctx);
	}
	
	@Override
	public T visitFuncname(calculatorParser.FuncnameContext ctx) {
		System.out.println("visitFuncname: " + ctx.getText());
		return visitChildren(ctx);
	}
	
	@Override
	public T visitRelop(calculatorParser.RelopContext ctx) {
		System.out.println("visitRelop: " + ctx.getText());
		return visitChildren(ctx);
	}

}
