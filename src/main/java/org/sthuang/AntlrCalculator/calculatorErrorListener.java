package org.sthuang.AntlrCalculator;

import java.util.*;

import org.antlr.v4.runtime.*;

public class calculatorErrorListener extends BaseErrorListener{
	
	@Override public void syntaxError(Recognizer<?, ?> recognizer,
									  Object offendingSymbol,
									  int line, int charPositionInLine,
									  String msg,
									  RecognitionException e)
	{
		List<String> stack = ((Parser)recognizer).getRuleInvocationStack();
		Collections.reverse(stack);
		System.err.println("rule stack: "+stack);
		System.err.println("line "+line+":"+charPositionInLine+" at "+
		offendingSymbol+": "+msg);
	}

}
