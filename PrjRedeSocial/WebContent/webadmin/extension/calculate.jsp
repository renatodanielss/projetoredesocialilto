<%@ include file="../../webadmin.jsp" %><%@ page import="java.util.Date" %><%

String expression;
if ((myrequest.getAttribute("extension") != null) && (! myrequest.getAttribute("extension").equals(""))) {
	expression = ""+myrequest.getAttribute("extension");
} else {
	expression = mysession.get("extension");
}
String decimals = "";
Pattern p = Pattern.compile(":([0-9]+)$");
Matcher m = p.matcher(mysession.get("extension"));
if (m.find()) {
	decimals = "" + m.group(1);
	expression = mysession.get("extension").substring(0, mysession.get("extension").length() - m.group(1).length() - 1);
}

//expression = "(1+2)*3+4+(5/10)+(0.5)";
//expression = "E";
//expression = "PI";
//expression = "RANDOM";

String output = "" + evaluate(expression);
output = output.replaceAll("\\.0*$", "");
if (! decimals.equals("")) {
	try {
		output = "" + Common.numberformat(output, Integer.parseInt("0"+decimals));
	} catch (Exception e) {
	}
}
if (! debug.equals("")) output += debug;

%><%!

String debug = "";

// Algorithm based on http://www.vb-helper.com/howto_evaluate_expressions.html - http://www.vb-helper.com/howto_net_evaluate_expressions.html

private double evaluate(String expression) {
	if (! debug.equals("")) debug += "<p>DEBUG:evaluate:"+expression;

	HashMap primitives = new HashMap();
	primitives.put("PI", "3.141592653589793");
	primitives.put("E", "2.718281828459045");
	primitives.put("RANDOM", ""+Math.random());

	int Precedence_None = 11;
	int Precedence_Unary = 10;
	int Precedence_Power = 9;
	int Precedence_Times = 8;
	int Precedence_Div = 7;
	int Precedence_IntDiv = 6;
	int Precedence_Modulus = 5;
	int Precedence_Plus = 4;

	boolean is_unary;
	boolean next_unary;
	int parens = 0;
	int expr_len;
	String ch;
	String lexpr;
	String rexpr;
	long status;
	int best_pos = 0;
	int  best_prec;

	String expr = "" + expression;
	// Remove all spaces.
//	expr = expr.replaceAll(" ", "");
	expr_len = expr.length();
	if (expr_len == 0) return 0;

	// If we find + or - now, it is a unary operator.
	is_unary = true;

	// So far we have nothing.
	best_prec = Precedence_None;

	// Find the operator with the lowest Precedence
	// Look for places where there are no open parentheses.
	for (int pos=0; pos<expr_len; pos++) {
		// Examine the next character.
		ch = expr.substring(pos, pos+1);

		// Assume we will not find an operator. In that case, the next operator will not be unary.
		next_unary = false;

		if (ch.equals(" ")) {
			// Just skip spaces. We keep them here to make the error messages easier to
		} else if (ch.equals("(")) {
			// Increase the open parentheses count.
			parens += 1;

			// A + or - after "(" is unary.
			next_unary = true;
		} else if (ch.equals(")")) {
			// Decrease the open parentheses count.
			parens -= 1;

			// An operator after ")" is not unary.
			next_unary = false;

			// If parens < 0, too many ')'s.
			if (parens < 0) {
				if (! debug.equals("")) debug += "<p>ERROR:"+"Too many close parentheses in '" + expression + "'";
				return 0;
			}
		} else if (parens == 0) {
			// See if this is an operator.
			if (ch.equals("^") || ch.equals("*") || ch.equals("/") || ch.equals("\\") || ch.equals("%") || ch.equals("+") || ch.equals("-")) {
				// An operator after an operator is unary.
				next_unary = true;

				// See if this operator has higher precedence than the current one.
				if (ch.equals("^")) {
					if (best_prec >= Precedence_Power) {
						best_prec = Precedence_Power;
						best_pos = pos;
					}
				} else if (ch.equals("*") || ch.equals("/")) {
					if (best_prec >= Precedence_Times) {
						best_prec = Precedence_Times;
						best_pos = pos;
					}
				} else if (ch.equals("\\")) {
					if (best_prec >= Precedence_IntDiv) {
						best_prec = Precedence_IntDiv;
						best_pos = pos;
					}
				} else if (ch.equals("%")) {
					if (best_prec >= Precedence_Modulus) {
						best_prec = Precedence_Modulus;
						best_pos = pos;
					}
				} else if (ch.equals("+") || ch.equals("-")) {
					// Ignore unary operators for now.
					if ((! is_unary) && (best_prec >= Precedence_Plus)) {
						best_prec = Precedence_Plus;
						best_pos = pos;
					}
				}
			}
		}
		is_unary = next_unary;
	}

	// If the parentheses count is not zero, there's a ')' missing.
	if (parens != 0) {
		if (! debug.equals("")) debug += "<p>ERROR:"+"Missing close parenthesis in '" + expression + "'";
		return 0;
	}

	// Hopefully we have the operator.
	if (best_prec < Precedence_None) {
		lexpr = expr.substring(0, best_pos);
		rexpr = expr.substring(best_pos + 1);
		if (expr.substring(best_pos, best_pos+1).equals("^")) {
			if (! debug.equals("")) debug += "<p>DEBUG:^:"+evaluate(lexpr)+":::"+evaluate(rexpr);
			return Math.pow(evaluate(lexpr), evaluate(rexpr));
		} else if (expr.substring(best_pos, best_pos+1).equals("*")) {
			if (! debug.equals("")) debug += "<p>DEBUG:*:"+evaluate(lexpr)+":::"+evaluate(rexpr);
			return evaluate(lexpr) * evaluate(rexpr);
		} else if (expr.substring(best_pos, best_pos+1).equals("/")) {
			if (! debug.equals("")) debug += "<p>DEBUG:/:"+evaluate(lexpr)+":::"+evaluate(rexpr);
			return evaluate(lexpr) / evaluate(rexpr);
		} else if (expr.substring(best_pos, best_pos+1).equals("\\")) {
			if (! debug.equals("")) debug += "<p>DEBUG:\\:"+evaluate(lexpr)+":::"+evaluate(rexpr);
			return (long)(evaluate(lexpr) / evaluate(rexpr));
		} else if (expr.substring(best_pos, best_pos+1).equals("%")) {
			if (! debug.equals("")) debug += "<p>DEBUG:%:"+evaluate(lexpr)+":::"+evaluate(rexpr);
			return evaluate(lexpr) % evaluate(rexpr);
		} else if (expr.substring(best_pos, best_pos+1).equals("+")) {
			if (! debug.equals("")) debug += "<p>DEBUG:+:"+evaluate(lexpr)+":::"+evaluate(rexpr);
			return evaluate(lexpr) + evaluate(rexpr);
		} else if (expr.substring(best_pos, best_pos+1).equals("-")) {
			if (! debug.equals("")) debug += "<p>DEBUG:-:"+evaluate(lexpr)+":::"+evaluate(rexpr);
			return evaluate(lexpr) - evaluate(rexpr);
		}
	}

	// If we do not yet have an operator, there are several possibilities:
	// 1. expr is (expr2) for some expr2.
	// 2. expr is -expr2 or +expr2 for some expr2.
	// 3. expr is Fun(expr2) for a function Fun.
	// 4. expr is a primitive.
	// 5. It's a literal like "3.14159".

	// Look for (expr2).
	if (expr.startsWith("(") && expr.endsWith(")")) {
		// Remove the parentheses.
		if (! debug.equals("")) debug += "<p>DEBUG:():"+(expr.substring(1, expr_len - 1));
		return evaluate(expr.substring(1, expr_len - 1));
	}

	// Look for -expr2.
	if (expr.startsWith("-")) {
		if (! debug.equals("")) debug += "<p>DEBUG:--:"+(expr.substring(1));
		return -evaluate(expr.substring(1));
	}

	// Look for +expr2.
	if (expr.startsWith("+")) {
		if (! debug.equals("")) debug += "<p>DEBUG:++:"+(expr.substring(1));
		return evaluate(expr.substring(1));
	}

	// Look for Fun(expr2).
	if ((expr_len > 5) && expr.endsWith(")")) {
		if (! debug.equals("")) debug += "<p>DEBUG:function:"+(expr);
		// Find the first (.
		int paren_pos = expr.indexOf("(");
		if (paren_pos > 0) {
			// See what the function is.
			lexpr = expr.substring(0, paren_pos);
			rexpr = expr.substring(paren_pos + 1, expr_len - 1);
			if (lexpr.toLowerCase().equals("sin")) {
				return Math.sin(evaluate(rexpr));
			} else if (lexpr.toLowerCase().equals("cos")) {
				return Math.cos(evaluate(rexpr));
			} else if (lexpr.toLowerCase().equals("tan")) {
				return Math.tan(evaluate(rexpr));
			} else if (lexpr.toLowerCase().equals("sqrt")) {
				return Math.sqrt(evaluate(rexpr));
			} else if (lexpr.toLowerCase().equals("days")) {
				String[] dates = rexpr.split(",");
				if (dates.length == 2) {
					Date date_start = Common.strtodate("yyyy-mm-dd", dates[0]);
					Date date_end = Common.strtodate("yyyy-mm-dd", dates[1]);
					return Math.floor((date_end.getTime()-date_start.getTime())/24/60/60/1000)+1;
				} else {
					return 0;
				}
			} else if (lexpr.toLowerCase().equals("hours")) {
				String rounded = "";
				String[] dates;
				if (rexpr.startsWith("+")) {
					rounded = "+";
					dates = rexpr.substring(1).split(",");
				} else if (rexpr.startsWith("-")) {
					rounded = "-";
					dates = rexpr.substring(1).split(",");
				} else {
					rounded = "";
					dates = rexpr.split(",");
				}
				if (dates.length == 2) {
					Date date_start = Common.strtodate("yyyy-mm-dd HH:MM", dates[0]);
					Date date_end = Common.strtodate("yyyy-mm-dd HH:MM", dates[1]);
					double hours = (double)(date_end.getTime()-date_start.getTime())/60/60/1000;
					double whole_hours = Math.floor((date_end.getTime()-date_start.getTime())/60/60/1000);
					if (hours == whole_hours) {
						return hours;
					} else if (rounded.equals("+")) {
						return (double)whole_hours + 1;
					} else if (rounded.equals("-")) {
						return (double)whole_hours;
					} else {
						return hours;
					}
				} else {
					return 0;
				}
			// Add other functions (including program-defined functions) here.
			}
		}
	}

	// See if it's a primitive.
	if (primitives.get(expr) != null) {
		// Return the corresponding value, converted into a Double.
		try {
			// Try to convert the expression into a value.
			double value = Double.parseDouble("" + primitives.get(expr));
			return value;
		} catch (Exception ex) {
			if (! debug.equals("")) debug += "<p>ERROR:"+"Primitive '" + expr + "' has value '" + primitives.get(expr) + "' which is not a Double.";
			return 0;
		}
	}

	// It must be a literal like "2.71828".
	try {
		// Try to convert the expression into a Double.
		double value = Double.parseDouble(expr);
		if (! debug.equals("")) debug += "<p>DEBUG:value:"+expr+":::"+value;
		return value;
	} catch (Exception ex) {
		if (! debug.equals("")) debug += "<p>ERROR:"+"Error evaluating '" + expression + "' as a constant.";
		return 0;
	}
}

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>