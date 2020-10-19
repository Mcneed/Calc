// Java program to create a simple calculator 
// with basic +, -, /, * using java swing elements 
  
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.*; 
import java.awt.*; 
class calculator extends JFrame implements ActionListener { 
    // create a frame 
    static JFrame f; 
  
    // create a textfield 
    static JTextField l; 
  
    // store oprerator and operands 
    String s0, s1, s2; 
  
    // default constrcutor 
    calculator() 
    { 
        s0 = s1 = s2 = ""; 
    } 
  
    // main function 
    public static void main(String args[]) 
    { 
        // create a frame 
        f = new JFrame("calculator"); 
  
        try { 
            // set look and feel 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } 
        catch (Exception e) { 
            System.err.println(e.getMessage()); 
        } 
  
        // create a object of class 
        calculator c = new calculator(); 
  
        // create a textfield 
       l = new JTextField(18); 
  
        // set the textfield to non editable 
        l.setEditable(false); 
  
        // create number buttons and some operators 
        JButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, ba, bs, bd, bm, be, beq, beq1, bp1, bp2; 
  
        // create number buttons 
        b0 = new JButton("0"); 
        b1 = new JButton("1"); 
        b2 = new JButton("2"); 
        b3 = new JButton("3"); 
        b4 = new JButton("4"); 
        b5 = new JButton("5"); 
        b6 = new JButton("6"); 
        b7 = new JButton("7"); 
        b8 = new JButton("8"); 
        b9 = new JButton("9"); 
  
        // equals button 
        beq1 = new JButton("="); 
  
        // create operator buttons 
        ba = new JButton("+ "); 
        bs = new JButton("-"); 
        bd = new JButton("/"); 
        bm = new JButton("*"); 
        bp1 = new JButton("(");
        bp2 = new JButton(")");
        beq = new JButton("C"); 
  
        // create . button 
        be = new JButton("."); 
  
        // create a panel 
        JPanel p = new JPanel(); 
  
        // add action listeners 
        bm.addActionListener(c); 
        bd.addActionListener(c); 
        bs.addActionListener(c); 
        ba.addActionListener(c); 
        b9.addActionListener(c); 
        b8.addActionListener(c); 
        b7.addActionListener(c); 
        b6.addActionListener(c); 
        b5.addActionListener(c); 
        b4.addActionListener(c); 
        b3.addActionListener(c); 
        b2.addActionListener(c); 
        b1.addActionListener(c); 
        b0.addActionListener(c); 
        be.addActionListener(c); 
        beq.addActionListener(c); 
        beq1.addActionListener(c); 
        bp1.addActionListener(c);
        bp2.addActionListener(c);
  
        // add elements to panel 
        p.add(l);
      
        p.add(ba); 
        p.add(b1); 
        p.add(b2); 
        p.add(b3); 
        p.add(bs); 
        p.add(b4); 
        p.add(b5); 
        p.add(b6); 
        p.add(bm); 
        p.add(b7); 
        p.add(b8); 
        p.add(b9); 
        p.add(bd); 
        p.add(bp1); 
        p.add(b0); 
        p.add(bp2);
        p.add(be);
        p.add(beq); 
        p.add(beq1); 
       
  
        // set Background of panel 
        p.setBackground(Color.orange); 
  
        // add panel to frame 
        f.add(p); 
  
        f.setSize(210, 220); 
        f.show(); 
      
        
    } 
   
    public static void input(String equation) throws Exception {
    	StringBuilder spaced = new StringBuilder();
    	for (int i = 0; i < equation.length(); i++) {
    	   if (i > 0) {
    	      spaced.append(" ");
    	    }

    	   spaced.append(equation.charAt(i));
    	}//add spacing for infix func
    	
    	
    	System.out.println(spaced);
        String s = infixToPostfix(spaced.toString());
        
        Stack<String> tks = new Stack<String>();
        tks.addAll(Arrays.asList(s.trim().split("[ \t]+")));
    
        try {
          double r = evalrpn(tks);
          if (!tks.empty()) throw new Exception();
          System.out.println(r);

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
	

    public void actionPerformed(ActionEvent e) 
    { 
    	
        String s = e.getActionCommand();
        if(s == "=") {
        	try {
				input(l.getText());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        else {
        	  l.setText(l.getText() + s);
  
        }
      
    
            
        } 
    
} 