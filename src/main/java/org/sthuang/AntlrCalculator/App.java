package org.sthuang.AntlrCalculator;


import org.antlr.v4.runtime.tree.*;

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
    	calculator c = new calculator();
    	LineReader reader = LineReaderBuilder.builder().build();
    	String prompt = "Enter a Math Expression: ";
    	while(true) {
    		String line = null;
	    	try {
	            
	            line = reader.readLine(prompt);
	            calculatorParser parser = c.createParser(line);
	            ParseTree tree = parser.equation();
	            Double result = c.visit(tree);
	            if(result != null) {
	        		System.out.println("result: " + result);
	        	}
	        } catch(UserInterruptException e) {
	        	
	        } catch (EndOfFileException e) {
	            return;
	        }
    	}
    	*/
    	
    	/***********************************************************
    	 * Current Expression examples that cause errors
    	 ***********************************************************
    	 * 8/2/2 * (2*3) evaluates 192
    	 * (8/2/2) * (2*3) evaluates 12
    	 * (1+1)-(2) evaluates 0
    	 * 1 + 1 - 2 evaluates 4
    	 * 3x>1 relop = x>
    	 * 3x + 1 = 1 evaluates 3
    	 * 3 + a = 1 '3+1' is inputted as variable
    	 * x = 1 variable context no visited
    	 * unable to handle invalid variable
    	 * unable to handle imaginary
    	 */
    	
        
    	/***************************************
    	 * Tester Code
    	****************************************/
    	String expression = "3x+1";
        calculator c = new calculator();
        calculatorParser parser = c.createParser(expression);
        //parser.removeErrorListeners();
        //parser.addErrorListener(new calculatorErrorListener());
        ParseTree tree = parser.equation();
        System.out.println(tree.toStringTree(parser));
        
        //Listener Code
        Listener l = new Listener();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(l, tree);
        
        Double result = c.visit(tree);
        if(result != null) {
        	System.out.println("result: " + result);
        }
   
    	
        /********Print AST Tree Diagram*********/
    	//AstPrinter ast = new AstPrinter();
        //ast.print(parser.equation());
        /**************************************** 
    	 */
    }
}
