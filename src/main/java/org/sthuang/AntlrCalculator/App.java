package org.sthuang.AntlrCalculator;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.*;
import java.util.*;

import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.TerminalBuilder;
import org.jline.terminal.Terminal;


public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	/*
    	//Terminal terminal = TerminalBuilder.builder().build();
    	LineReader reader = LineReaderBuilder.builder().build();
    	try {
            reader.readLine("Enter a Math Expression: ");
            //reader.getTerminal().writer().println("World!");
            //reader.getTerminal().writer().flush();
            String line = null;
            while ((line = reader.readLine()) != null) {
            	line = reader.readLine(line);
                //reader.getTerminal().writer().println(line);
            }
        } catch(UserInterruptException e) {
        	
        } catch (EndOfFileException e) {
            return;
        }
        */
    	
    	//8/2/2 * (2*3) evaluates 192
    	//(8/2/2) * (2*3) evaluates 12
    	//(1+1)-(2) evaluates 0
    	//1 + 1 - 2 evaluates 4
    	//3x>1 relop = x>
    	//3x + 1 = 1 evaluates 3
    	//unable to handle invalid variable
    	//unable to handle imaginary
        String expression = "cos(pi)";
        Scanner sc = new Scanner(System.in);
        //while(sc.hasNextLine()) System.out.println(sc.nextLine())
        calculator c = new calculator();
        
        
        while(sc.hasNextLine()){
        	calculatorParser parser = c.createParser(sc.nextLine());
        	ParseTree tree = parser.equation();
        	Double result = c.visit(tree);
        	if(result != null) {
        		System.out.println("result: " + result);
        	}
        }
        
        
        /*
        calculatorParser parser = c.createParser(expression);
        AstPrinter ast = new AstPrinter();
        ast.print(parser.equation());
        
        //EquationContext eq = parser.equation();
        //ast.print(eq);
        
        ParseTree tree = parser.equation();
        Double result = c.visit(tree);
        if(result != null) {
        	System.out.println("result: " + result);
        }
        */
    }
}
