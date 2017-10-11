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
    
//simple plotter using a recursive expression solver
//there are many small neat tricks in expression evaluating for example
//-6*-6 becomes --36 which becomes +36
//-6+10 the minus sign is ignored as a delimiter
//for exponents there are three edge cases: (a+b)^c, c^(a+b), a^b, (a+b)^(c+d)
//next time use a expression tree instead of this nonsense
    static int recursionLevel = 0;
    static String[] DELIMITERS = {"+","-","+-","--","*","/","^"};
    public static String evaluate(String exp){
           //System.out.println(recursionLevel+"\t"+exp);
		recursionLevel++;
		String solution = new String(exp);			//copy exp into a new string
		//=================================================== TRIG AND LOG FUNCTIONS =======================================================================
		if(solution.contains("tan") || solution.contains("sin") || solution.contains("cos") || solution.contains("log")){
			for(int i = 0; i < solution.length() - 3; i++){
				if(solution.substring(i,i+4).equals("tan(") || solution.substring(i,i+4).equals("cos(") || solution.substring(i,i+4).equals("sin(") || solution.substring(i,i+4).equals("log(")){
					int levels = 0;
					for(int r = i+3; r < solution.length(); r++){
						if(solution.charAt(r) == '(')
							levels++;
						if(solution.charAt(r) == ')')
							levels--;
						if(levels == 0){
							String tmp = "";
							try{	tmp += solution.substring(0,i);	}catch(Exception e){}
							if(solution.substring(i,i+4).equals("tan(")){
								try{	
									NumberFormat formatter = new DecimalFormat("###.#####");
									String f = formatter.format(Math.tan(Double.parseDouble(evaluate(solution.substring(i+4,r)))));
									if(f.equals("�"))
										return "NaN";
                                                                        tmp += "("+f+")";
								}catch(Exception e){}		//i added brackets here because the function serves as a singular term
							}
							else if(solution.substring(i,i+4).equals("cos(")){
								try{	
									NumberFormat formatter = new DecimalFormat("###.#####");
									String f = formatter.format(Math.cos(Double.parseDouble(evaluate(solution.substring(i+4,r)))));
									if(f.equals("�"))
										return "NaN";
                                                                        tmp += "("+f+")";
								}catch(Exception e){}
							}
							else if(solution.substring(i,i+4).equals("sin(")){
								try{	
									NumberFormat formatter = new DecimalFormat("###.#####");
									String f = formatter.format(Math.sin(Double.parseDouble(evaluate(solution.substring(i+4,r)))));
									if(f.equals("�"))
										return "NaN";
                                                                        tmp += "("+f+")";
								}catch(Exception e){}
							}
							else if(solution.substring(i,i+4).equals("log(")){
								try{	
									NumberFormat formatter = new DecimalFormat("###.#####");
									String f = formatter.format(Math.log(Double.parseDouble(evaluate(solution.substring(i+4,r)))));
									if(f.equals("�"))
										return "NaN";
                                                                        tmp += "("+f+")";
								}catch(Exception e){}
							}
							try{	tmp += 	solution.substring(r+1);	}catch(Exception e){}
							solution = tmp;
							//System.out.println(solution);
							if(solution.charAt(0) == '+')
								solution = solution.substring(1);
							return evaluate(solution);
						}
					}
				}
			}
		}
		//===================================================== EXPONENTS =====================================================================================
		for(int i = 1; i < solution.length()-1; i++){
			if(solution.charAt(i) == '^'){
				//System.out.println("exponents: "+solution);
				if(solution.charAt(i-1) == ')'){			//bracket in front of exponent sign
					int levels = 0;
					for(int l = i-1; l >= 0; l--){
						if(solution.charAt(l) == ')')
							levels++;
						else if(solution.charAt(l) == '('){
							levels--;
							if(levels == 0){
								if(solution.charAt(i+1) == '('){
									int elevels = 0;
									for(int r = i+1; r < solution.length(); r++){
										if(solution.charAt(r) == '(')
											elevels++;
										else if(solution.charAt(r) == ')'){
											elevels--;
											if(elevels == 0){
												String tmp = "";
												try{	tmp += solution.substring(0,l);	} catch(Exception e){}
												try{	
													NumberFormat formatter = new DecimalFormat("###.#####");  
													String f = formatter.format(Math.pow(Double.parseDouble(evaluate(solution.substring(l+1,i-1))), Double.parseDouble(evaluate(solution.substring(i+2,r)))));  
													tmp += f;
												} catch(Exception e){} 
												try{	tmp += solution.substring(r+1);	} catch (Exception e){}
												solution = tmp;
												return evaluate(solution);
											}
										}
									}
								}
								else{
									int r = i+2;
									while(true){
										if(r >= solution.length())
											break;
										if(edgeFound(solution.charAt(r)))			
											break;
										r++;
									}
									String tmp = "";
									try{	tmp += solution.substring(0,l);	} catch(Exception e){}
									try{	
										NumberFormat formatter = new DecimalFormat("###.#####");
										String f = formatter.format(Math.pow(Double.parseDouble(evaluate(solution.substring(l+1,i-1))), Double.parseDouble(evaluate(solution.substring(i+1,r)))));
										tmp += f;
									} catch(Exception e){} 
									try{	tmp += solution.substring(r);	} catch (Exception e){}
									solution = tmp;
									return evaluate(solution);
								}
							}
						}
					}
				}
				else{
					if(solution.charAt(i+1) == '('){
						int levels = 0;
						for(int r = i+1; r< solution.length(); r++){
							if(solution.charAt(r) == '(')
								levels++;
							else if(solution.charAt(r) == ')'){
								levels--;
								if(levels == 0){
									int l = i-1;
									while(true){
										if(l < 0)
											break;
										else if(edgeFound(solution.charAt(l)))
											break;
										l--;
									}
									String tmp = "";
									try{	tmp += solution.substring(0,l+1);	}catch(Exception e){}
									try{	
										NumberFormat formatter = new DecimalFormat("###.#####");
										String f = formatter.format(Math.pow(Double.parseDouble(evaluate(solution.substring(l+1,i))), Double.parseDouble(evaluate(solution.substring(i+2,r)))));
										tmp += f;	
									}catch(Exception e){}
									try{	tmp += solution.substring(r+1);	}catch(Exception e){}
									solution = tmp;
									return evaluate(solution);
								}
							}
						}
					}
					else{
						int r = i+2, l = i-1;
						while(true){
							if(r >= solution.length())
								break;
							if(edgeFound(solution.charAt(r)))			
								break;
							r++;
						}
						while(true){
							if(l < 0)
								break;
							if(edgeFound(solution.charAt(l)))
								break;
							l--;
						}
						String tmp = "";
						try{	tmp += solution.substring(0,l+1);	}catch(Exception e){}
						try{	
							NumberFormat formatter = new DecimalFormat("###.#####");
							String f = formatter.format(Math.pow(Double.parseDouble(evaluate(solution.substring(l+1,i))), Double.parseDouble(evaluate(solution.substring(i+1,r)))));
							tmp += Math.pow(Double.parseDouble(evaluate(solution.substring(l+1,i))), Double.parseDouble(evaluate(solution.substring(i+1,r))));	
						}catch(Exception e){}
						try{	tmp += solution.substring(r);	}catch(Exception e){}
						solution = tmp;
						return evaluate(solution);
					}
				}	
			}
		}
		//===================================================== BRACKETS ======================================================================================
		//the brakets handle devide and conquer technique
		int levels = 0;								//each recursive call will solve only one bracketed expression
		int startIndex = 0;
		while(solution.contains("(")){
			for(int i = 0; i < solution.length(); i++){	//evaluate parent
				if(solution.charAt(i) == '('){
					if(levels == 0)
						startIndex = i;
					levels++;
				}
				if(solution.charAt(i) == ')'){  
					levels--;
					if(levels == 0){
						String value = evaluate(solution.substring(startIndex+1,i));
						if(solution.length() > 1)
							solution = solution.substring(0, startIndex) + value + solution.substring(i+1);
						break;
					}
				}
			}
		}
		//===================================================== SIGN SIMPLIFICATION =================================================================
		if(solution.charAt(0) == '+')
			solution = solution.substring(1);
		while(solution.contains("--") || solution.contains("+-") || solution.contains("*+") || solution.contains("/+")){
			for(int i = 0; i < solution.length()-1; i++){
				if(solution.substring(i,i+2).equals("+-")){
					solution = solution.substring(0,i) + "-" + solution.substring(i+2);
					break;
				}
				else if(solution.substring(i,i+2).equals("--")){
					solution = solution.substring(0,i) + "+" + solution.substring(i+2);
					break;
				}
                                else if(solution.substring(i,i+2).equals("*+")){
                                        solution = solution.substring(0,i) + "*" + solution.substring(i+2);
					break;
                                }
                                else if(solution.substring(i,i+2).equals("/+")){
                                        solution = solution.substring(0,i) + "/" + solution.substring(i=2);
                                        break;
                                }
			}
		}
                if(solution.charAt(0) == '+')
			solution = solution.substring(1);
 		//===================================================== BASIC ARITHMETIC ==============================================================================
		////System.out.println("multiplication and division: "+solution);
		if(solution.length() > 0 && (solution.contains("*") || solution.contains("/"))){
			for(int m = 1; m < solution.length()-1; m++){
				if(solution.charAt(m) == '*' || solution.charAt(m) == '/' || solution.substring(m,m+2).equals("*-") || solution.substring(m,m+2).equals("/-")){
					int r = m+1, l = m-1;
					while(true){
						if(r >= solution.length())
							break;
						if(edgeFound(solution.charAt(r)) && (!solution.substring(m,m+2).equals("*-") || r > m+1) && (!solution.substring(m,m+2).equals("/-") || r > m+1))			
							break;
						r++;
					}
					while(true){
						if(l < 0)
							break;
						if(edgeFound(solution.charAt(l)))
							break;
						l--;
					}
					String tmp = "";
					try{	tmp += solution.substring(0,l+1);	} catch(Exception e){}
					if(solution.charAt(m) == '*'){
						try{	
							NumberFormat formatter = new DecimalFormat("###.#####");
							String f = formatter.format(Double.parseDouble(solution.substring(l+1,m)) * Double.parseDouble(solution.substring(m+1,r)));
							tmp += f;	
						}catch(Exception e){}
					}
					else if(solution.charAt(m) == '/'){
						try{
							NumberFormat formatter = new DecimalFormat("###.#####");
							String f = formatter.format(Double.parseDouble(solution.substring(l+1,m)) / Double.parseDouble(solution.substring(m+1,r)));
							tmp += f;	
						}catch(Exception e){}
					}
					try{		tmp += solution.substring(r);	}catch(Exception e){}
					solution = tmp;
					if(solution.charAt(0) == '+')
						solution = solution.substring(1);
					return evaluate(solution);
				}
			}
		}
		int minusCounter = 0;
		for(int i = 0; i < solution.length(); i++)
			if(solution.charAt(i) == '-')
				minusCounter++;
		//System.out.println("addition and subtraction: "+solution);
		if(solution.length() > 0 && (solution.contains("+") || (solution.contains("-") && (solution.charAt(0) != '-' || minusCounter > 1) ) ) ){
			for(int m = 1; m < solution.length(); m++){
				if(solution.charAt(m) == '+' || solution.charAt(m) == '-'){
					int r = m+1, l = m-1;
					while(true){
						if(r >= solution.length())
							break;
						if(edgeFound(solution.charAt(r)))				
							break;
						r++;
					}
					while(true){
						if(l < 0)
							break;
						if(edgeFound(solution.charAt(l)) && (l != 0 || solution.charAt(0) != '-'))
							//there is a edge case here for the negative number as the first term might confuse it with a subtraction sign
							break;
						l--;
					}
					String tmp = "";
					try{	tmp += solution.substring(0,l+1);	}catch(Exception e){}
					if(solution.charAt(m) == '+'){
						try{	
							NumberFormat formatter = new DecimalFormat("###.#####");
							String f = formatter.format(Double.parseDouble(solution.substring(l+1,m)) + Double.parseDouble(solution.substring(m+1,r)));
							tmp += f;	
						}catch(Exception e){}
					}
					else{
						try{	
							NumberFormat formatter = new DecimalFormat("###.#####");
							String f = formatter.format(Double.parseDouble(solution.substring(l+1,m)) - Double.parseDouble(solution.substring(m+1,r)));
							tmp += f;
						}catch(Exception e){}
					}
					try{		tmp += solution.substring(r);	}catch(Exception e){}
					solution = tmp;
					if(solution.charAt(0) == '+')
						solution = solution.substring(1);
					return evaluate(solution);
				}
			}
		}
		if(solution.charAt(0) == '+')
			solution = solution.substring(1);
		//System.out.println("solution: "+solution);
		return solution;
    }
    public static boolean edgeFound(char symbol){
            boolean ret = true;
            try{
                    ret = symbol == '*' || symbol == '/' || symbol == '-' || symbol == '+' || symbol == '^' || symbol == ')' || symbol == '(';
            } catch (Exception e){
                    ret = false;
            }
            return ret;
    }
    public static String trim(String s){//trims the expression to make it acceptable
            while(s.indexOf(" ") != -1){
                    try{
                            s = s.substring(0,s.indexOf(" ")) + s.substring(s.indexOf(" ")+1, s.length());
                    } catch (Exception e){
                            //do nothing, for now
                    }
            }
            return s;
    }/*
    public static void main(String[] args){
            //testing code
            System.out.println("Default Test: ");
            evaluate(trim("1+5*(9-11+(1+2)) * (192+342) / (213*231) + (2*(888-(-9+5)))"));
            System.out.println("--------------------------------------------------------");
            Scanner sc = new Scanner(System.in);
            while(true){
                    recursionLevel = 0;
                    System.out.print("Testing: ");
                    System.out.println("Solution: "+evaluate(trim(sc.nextLine())));
                    System.out.println("--------------------------------------------------------");
            }
    }*//*
    public static void main(String[] args){
            //driver code for the GUI

    }*/
}
