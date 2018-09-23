package org.sthuang.AntlrCalculator;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import org.sthuang.AntlrCalculator.calculatorParser.EquationContext;

public class App 
{
    public static void main( String[] args )
    {
        String expression = "(1+3)/(2+1) = x";
        calculator c = new calculator();
        calculatorParser parser = c.createParser(expression);
        
        AstPrinter ast = new AstPrinter();
        //ast.print(parser.equation());
        
        //EquationContext eq = parser.equation();
        //ast.print(eq);
        
        ParseTree tree = parser.equation();
        c.visit(tree);
        
        //System.out.println(new calculator().visit(tree));
        //System.out.println(tree.toStringTree(parser));
        
        //System.out.println(expression);
        //System.out.println(Double.valueOf("2e10") + (Double.valueOf("2e10")));
        //System.out.println(Double.valueOf("1"));
        
    }
}
