
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
class calculator extends JFrame implements ActionListener {
	// Sources for where some inspiration/snippets came from
	// https://www.math.ucla.edu/~akrieger/teaching/18f/pic20a/examples/calculator/CalculatorApp.java.html modified for evaluation
	// https://www.geeksforgeeks.org/java-swing-simple-calculator/took the basics of the caculator swing from here
	
	
	// create a frame
	static JFrame f;
	// create a textfield
	static JTextField l;
	// store oprerator and operands
	String s0;
	// default constrcutor
	calculator() {
		s0 = "";
	}
	@SuppressWarnings("deprecation")
	public static void main(String args[]) {
		// create a frame
		f = new JFrame("calculator");

		try {
			// set look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		// create a object of class
		calculator c = new calculator();
		// create a text field
		l = new JTextField(22);
		// set the text field to non editable
		l.setEditable(false);
		// create number buttons and some operators
		JButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, ba, bs, bd, bm, be,
		bclr, beq1, bp, bp1, bp2, bsin, bcos, btan, bln, blog, bcot, blank;

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
		//decimal
		be = new JButton(".");
		// create operator buttons
		ba = new JButton("+");
		bs = new JButton("-");
		bd = new JButton("/");
		bm = new JButton("*");
		bp = new JButton("^");
		bp1 = new JButton("(");
		bp2 = new JButton(")");
		bclr = new JButton("Clear");
		//Trigonometry functions
     	bsin = new JButton("sin");
        bcos = new JButton("cos");
	    btan = new JButton("tan");
	    bcot = new JButton("cot");
	    bln = new JButton("ln");
		blog = new JButton("log");
		blank = new JButton("");
		// create a panel
		JPanel p = new JPanel();
		p.setLayout((new GridLayout(0,3)));
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
		bp.addActionListener(c);
		bclr.addActionListener(c);
		beq1.addActionListener(c);
		bp1.addActionListener(c);
		bp2.addActionListener(c);
		bsin.addActionListener(c);
		bcos.addActionListener(c);
		bcot.addActionListener(c);
		btan.addActionListener(c);
		blog.addActionListener(c);
		bln.addActionListener(c);
		// add elements to panel
		p.add(l); // text box
		
		p.add(bclr);
		p.add(blank);
		p.add(b1);
		p.add(b2);
		p.add(b3);
		p.add(b4);
		p.add(b5);
		p.add(b6);
		p.add(b7);
		p.add(b8);
		p.add(b9);
		p.add(b0);
		p.add(be);
		p.add(bp);
		p.add(bd);
		p.add(ba);
		p.add(bs);
		p.add(bm);
		p.add(bp1);
		p.add(bp2);
		p.add(btan);
		p.add(bsin);
		p.add(bcos);
		p.add(btan);
		p.add(blog);
		p.add(bln);
		p.add(beq1);
		
		// set Background of panel
		p.setBackground(Color.orange);
		// add panel to frame
		f.add(p);
		f.setSize(400 , 600);
		f.show(); 
	}

	public static void input(String equation) throws Exception {

		//System.out.println("Entered " + equation);
		double result = calculate(equation);
		//System.out.println("Result " + result);
		l.setText(String.valueOf(result)); 
	
		}
		
	  public static double calculate(final String str) {
	        // [AK]: The code `new Object() { ... }` defines an anonymous
	        // class. This is an unnamed class extending Object, and with
	        // the members and methods defined below.
	        return new Object() {
	            int pos = -1, ch;

	            void nextChar() {
	                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
	            }

	            boolean eat(int charToEat) {
	                while (ch == ' ') nextChar();
	                if (ch == charToEat) {
	                    nextChar();
	                    return true;
	                }
	                return false;
	            }

	            double parse() {
	                nextChar();
	                double x = parseExpression();
	                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
	                return x;
	            }

	            // Grammar:
	            // expression = term | expression `+` term | expression `-` term
	            // term = factor | term `*` factor | term `/` factor
	            // factor = `+` factor | `-` factor | `(` expression `)`
	            //        | number | functionName factor | factor `^` factor

	            double parseExpression() {
	            	//That builds a recursive tree of Expression objects representing the compiled expression (an abstract syntax tree).
	            	//Then you can compile it once and evaluate it repeatedly with different values:

	                double x = parseTerm();
	                for (;;) {
	                    if      (eat('+')) x += parseTerm(); // addition
	                    else if (eat('-')) x -= parseTerm(); // subtraction
	                    else return x;
	                }
	            }

	            double parseTerm() {
	                double x = parseFactor();
	                for (;;) {
	                    if      (eat('*')) x *= parseFactor(); // multiplication
	                    else if (eat('/')) x /= parseFactor(); // division
	                    else return x;
	                }
	            }

	            double parseFactor() {
	                if (eat('+')) return parseFactor(); // unary plus
	                if (eat('-')) return -parseFactor(); // unary minus

	                double x;
	                int startPos = this.pos;
	                if (eat('(')) { // parentheses
	                    x = parseExpression();
	                    eat(')');
	                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
	                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
	                    x = Double.parseDouble(str.substring(startPos, this.pos));
	                } else if (ch >= 'a' && ch <= 'z') { // functions
	                    while (ch >= 'a' && ch <= 'z') nextChar();
	                    String func = str.substring(startPos, this.pos);
	                    x = parseFactor();
	                    if (func.equals("sqrt")) x = Math.sqrt(x);
	                    else if (func.equals("sin")) x = Math.sin((x));
	                    else if (func.equals("cos")) x = Math.cos(x);
	                    else if (func.equals("tan")) x = Math.tan(x);
	                    else if (func.equals("cot")) x = (1/Math.tan(x));
	                    else if (func.equals("log")) x = Math.tan(Math.log10(x));
	                    else if (func.equals("ln")) x = Math.tan(Math.log(x));
	                    else throw new RuntimeException("Unknown function: " + func);
	                } else {
	                	l.setText("Unexpected: " + (char)ch);
	                    throw new RuntimeException("Error Unexpected: " + (char)ch);
	                }

	                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

	                return x;
	            }
	        }.parse();
	    }
	
	
	public void actionPerformed(ActionEvent button) {
		String entered = "";
		entered = button.getActionCommand();
		if (entered == "=") {
				try {
					input(l.getText());
				} catch (Exception e1) {
					e1.printStackTrace();
					
				}	
		} else if (entered == "Clear") {
			l.setText("");
		} 
		else if (entered == "sin") {
			l.setText(l.getText() + "sin(");
			
		}
		else if (entered == "cos") {
			l.setText(l.getText() + "cos(");
			
		}
		else if (entered == "tan") {
			l.setText(l.getText() + "tan(");
			
		}
		else if (entered == "cot") {
			l.setText(l.getText() + "cot(");
			
		}
		else if (entered == "ln") {
			l.setText(l.getText() + "ln(");
			
		}
		else if (entered == "log") {
			l.setText(l.getText() + "log(");
			
		} 
		else {
			l.setText(l.getText() + entered);
		}

	}
	}

