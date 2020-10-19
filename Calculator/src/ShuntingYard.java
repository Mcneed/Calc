import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;
 
public class ShuntingYard {
 
    public static void input() {
    	Scanner sc = new Scanner(System.in);
    	String input = sc.nextLine().toString();
    	sc.close();
        System.out.printf("infix:   %s%n", input);
        System.out.printf("postfix: %s%n", infixToPostfix(input));
        
        String s = infixToPostfix(input);
        
        Stack<String> tks = new Stack<String>();
        tks.addAll(Arrays.asList(s.trim().split("[ \t]+")));
    
        try {
          double r = evalrpn(tks);
          if (!tks.empty()) throw new Exception();
          System.out.println(r);
        }
        catch (Exception e) {System.out.println("error");}
    }
 
   public static String infixToPostfix(String infix) {
        /* To find out the precedence, we take the index of the
           token in the ops string and divide by 2 (rounding down). 
           This will give us: 0, 0, 1, 1, 2 */
        final String ops = "-+/*^";
 
        StringBuilder sb = new StringBuilder();
        Stack<Integer> s = new Stack<>();
 
        for (String token : infix.split("\\s")) {
            if (token.isEmpty())
                continue;
            char c = token.charAt(0);
            int idx = ops.indexOf(c);
 
            // check for operator
            if (idx != -1) {
                if (s.isEmpty())
                    s.push(idx);
 
                else {
                    while (!s.isEmpty()) {
                        int prec2 = s.peek() / 2;
                        int prec1 = idx / 2;
                        if (prec2 > prec1 || (prec2 == prec1 && c != '^'))
                            sb.append(ops.charAt(s.pop())).append(' ');
                        else break;
                    }
                    s.push(idx);
                }
            } 
            else if (c == '(') {
                s.push(-2); // -2 stands for '('
            } 
            else if (c == ')') {
                // until '(' on stack, pop operators.
                while (s.peek() != -2)
                    sb.append(ops.charAt(s.pop())).append(' ');
                s.pop();
            }
            else {
                sb.append(token).append(' ');
            }
        }
      
        while (!s.isEmpty())
            sb.append(ops.charAt(s.pop())).append(' ');
        return sb.toString();
    }
   public static double evalrpn(Stack<String> tks) throws Exception {
	    String tk = tks.pop();
	    double x,y;
	    try {x = Double.parseDouble(tk);}
	    catch (Exception e) {
	      y = evalrpn(tks);  x = evalrpn(tks);
	      if      (tk.equals("+"))  x += y;
	      else if (tk.equals("-"))  x -= y;
	      else if (tk.equals("*"))  x *= y;
	      else if (tk.equals("/"))  x /= y;
	      else throw new Exception();
	    }
	    return x;
	  }
	}
