package org.sthuang.AntlrCalculator;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.sthuang.AntlrCalculator.calculatorParser.EquationContext;

import java.util.*;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	//(1+1)-(2) evalutes 0
    	//1 + 1 - 2 evaluates 4
        String expression = "(1+1)/2";
        Scanner sc = new Scanner(System.in);
        //while(sc.hasNextLine()) System.out.println(sc.nextLine())
        calculator c = new calculator();
        
        /*
        while(sc.hasNextLine()){
        	calculatorParser parser = c.createParser(sc.nextLine());
        	ParseTree tree = parser.equation();
        	Double result = c.visit(tree);
        	if(result != null) {
        		System.out.println("result: " + result);
        	}
        }
        */
        
  
        calculatorParser parser = c.createParser(expression);
        
        AstPrinter ast = new AstPrinter();
        //ast.print(parser.equation());
        
        //EquationContext eq = parser.equation();
        //ast.print(eq);
        
        ParseTree tree = parser.equation();
        Double result = c.visit(tree);
        if(result != null) {
        	System.out.println("result: " + result);
        }
        //System.out.println(new calculator().visit(tree));
        //System.out.println(tree.toStringTree(parser));
        
        //System.out.println(expression);
        //System.out.println(Double.valueOf("2e10") + (Double.valueOf("2e10")));
        //System.out.println(Double.valueOf("1"));
        
    }
}
