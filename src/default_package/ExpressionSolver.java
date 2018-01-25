/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package default_package;
import java.util.*;
import java.text.*;
/**
 *
 * @author lx_user
 */
public class ExpressionSolver {
	static HashMap<String, Integer> opPrecedence = new HashMap<String, Integer>();
	public static void initPrecedence(){
		opPrecedence.put("^", 4);
		opPrecedence.put("*", 3);
		opPrecedence.put("/", 3);
		opPrecedence.put("+", 2);
		opPrecedence.put("-", 2);
		opPrecedence.put(")", 1);
		opPrecedence.put("(", 1);
	}
        public static String getTokenString(String expression){
                String[] tokens = tokenize(expression);
                String ret = "[ ";
                for (String s: tokens)
                    ret += s + ", ";
                return ret.substring(0,ret.length()-2)+" ]";
        }
	public static String[] tokenize(String expression){
		ArrayList<String> tokens = new ArrayList<String>();
		String[] output;
		int outputSize = 0;
                boolean prevWasOperator = true;    //use this to check for unary operators (-)
		for(int i = 0; i < expression.length(); i++){
			switch(expression.charAt(i)){
			case '^':
				tokens.add("^");
				tokens.add("");
                                prevWasOperator = true;
				break;
			case '*':
				tokens.add("*");
				tokens.add("");
				prevWasOperator = true;
                                break;
			case '/':
				tokens.add("/");
				tokens.add("");
				prevWasOperator = true;
                                break;
			case '+':
				tokens.add("+");
				tokens.add("");
				prevWasOperator = true;
                                break;
			case '-':
                                if(prevWasOperator && expression.charAt(i+1) != '('){
                                        tokens.add("-");
                                }
                                else if(prevWasOperator && expression.charAt(i+1) == '('){
                                        tokens.add("-1");
                                        tokens.add("*");
                                }
                                else{
                                        tokens.add("-");
                                        tokens.add("");
                                        prevWasOperator = true;
                                }
                                break;
			case '(':
				tokens.add("(");
				tokens.add("");
                                prevWasOperator = true;
				break;
			case ')':
				tokens.add(")");
				tokens.add("");
                                prevWasOperator = false;
				break;
			case ' ':
				//remove whitespace
				break;
			default:	//alphanumeric characters
				if(tokens.size() < 1)
					tokens.add("");
				String item = tokens.get(tokens.size()-1) + expression.charAt(i);
				tokens.remove(tokens.size()-1);
				tokens.add(item);
                                prevWasOperator = false;
			}
		}
		for(int i = 0; i < tokens.size(); i++){
			if(tokens.get(i).length() > 0)
				outputSize++;
		}
		output = new String[outputSize];
		int j = 0;
		for (int i = 0; i < tokens.size(); i++){
			if(tokens.get(i).length() > 0){
				output[j] = tokens.get(i);
				j++;
			} 	
		}
		return output;
	}
	public static String infixToPostfix(String expression){
                if(expression == "") //empty string
                    return "";
		Stack<String> operationStack = new Stack<String>();
		String[] tokens = tokenize(expression);
		String ret = "";
		for(String tok: tokens){
			if(tok.equals("(")){
				operationStack.push(tok);
			}
			else if(tok.equals(")")){
				while(!operationStack.peek().equals("("))
					ret += operationStack.pop() +" ";
				operationStack.pop();
			}
			else if(opPrecedence.containsKey(tok)){
				while(!operationStack.isEmpty() && opPrecedence.get(operationStack.peek()) >= opPrecedence.get(tok))
					ret += operationStack.pop() +" ";
				operationStack.push(tok);
			}
			else{
				ret += tok + " ";
			}
		}
		while (!operationStack.isEmpty())
			ret += operationStack.pop() + " ";
		return ret;
	}
	public static double evaluatePostfix(String expression){
                if(expression == "")
                    return 0;
		Stack<Double> operandStack = new Stack<Double>();
		String[] tokens = expression.split(" ");
		for(String tok: tokens){
			double num1, num2;
			switch(tok){
			case "+":
				num2 = (double)operandStack.pop();
				num1 = (double)operandStack.pop();
				operandStack.push(num1+num2);
				break;
			case "-":
				num2 = (double)operandStack.pop();
				num1 = (double)operandStack.pop();
				operandStack.push(num1-num2);
				break;
			case "*":
				num2 = (double)operandStack.pop();
				num1 = (double)operandStack.pop();
				operandStack.push(num1*num2);
				break;
			case "/":
				num2 = (double)operandStack.pop();
				num1 = (double)operandStack.pop();
				operandStack.push(num1/num2);
				break;
			case "^":
				num2 = (double)operandStack.pop();
				num1 = (double)operandStack.pop();
				operandStack.push(Math.pow(num1, num2));
				break;
			default:
				operandStack.push(Double.parseDouble(tok));
			}
		}
		return (double)operandStack.pop();
	}
        public static boolean hasFunction(String expression){
            return expression.contains("log")   ||
                   expression.contains("sin")   ||
                   expression.contains("cos")   ||
                   expression.contains("tan")   ||
                   expression.contains("abs")
                   /*
                    * I did not add sqrt because its better to use n^0.5 than
                    * to use sqrt(n) which requires 2 more characters to type
                    */
            ;
        }
        public static boolean isOperator(char c){
            return c == '^' ||
                   c == '*' ||
                   c == '/' ||
                   c == '-' ||
                   c == '+';
        }
        public static String getImplicitMultiplication(String expression){
            if(expression == "")
                return "";
            String ret = ""+expression.charAt(0);
            for(int i = 1; i < expression.length(); i++){
                if((expression.charAt(i) == '(' || expression.charAt(i) == 'x') && !isOperator(expression.charAt(i-1)))
                    ret += "*(";
                else
                    ret += expression.charAt(i);
            }
            return ret;
        }
	public static double evaluate(String expression){
            expression = getImplicitMultiplication(expression);
            while(hasFunction(expression)){
                int start = 0;
                Stack<Integer> bracketStack = new Stack<Integer>();
                if(expression.indexOf("log") != -1)
                    start = expression.indexOf("log");
                else if(expression.indexOf("sin") != -1)
                    start = expression.indexOf("sin");
                else if(expression.indexOf("cos") != -1)
                    start = expression.indexOf("cos");
                else if(expression.indexOf("tan") != -1)
                    start = expression.indexOf("tan");
                else if(expression.indexOf("abs") != -1)
                    start = expression.indexOf("abs");
                for(int i = start+3; i < expression.length(); i++){
                    if (expression.charAt(i) == '(')
                        bracketStack.push(i);
                    else if (expression.charAt(i) == ')')
                        bracketStack.pop();
                    if(bracketStack.isEmpty()){
                        String simplifiedExpression = "";
                        String function = expression.substring(start,start+3);
                        simplifiedExpression += expression.substring(0, start);
                        switch(function){
                        case "sin":
                            simplifiedExpression += Math.sin(evaluate(expression.substring(start+4, i)));
                            break;
                        case "log":
                            simplifiedExpression += Math.log10(evaluate(expression.substring(start+4, i)));
                            break;
                        case "cos":
                            simplifiedExpression += Math.cos(evaluate(expression.substring(start+4, i)));
                            break;
                        case "tan":
                            simplifiedExpression += Math.tan(evaluate(expression.substring(start+4, i)));
                            break;
                        case "abs":
                            simplifiedExpression += Math.abs(evaluate(expression.substring(start+4, i)));
                            break;
                        }
                        simplifiedExpression += expression.substring(i+1);
                        return evaluate(simplifiedExpression);
                    }
                }    
            }
            return evaluatePostfix(infixToPostfix(expression));
	}
        
        public static void main(String[] args){
            initPrecedence();
            Scanner sc = new Scanner(System.in);
            while(true){
                String expression = sc.nextLine();
                if(expression.equals("quit") || expression.equals("q"))
                    break;
                System.out.println("evaluation: "+evaluate(expression));
            }
        }
}
