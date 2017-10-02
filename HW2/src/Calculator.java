/*
Create by Leon for PDSA
HW2
Arithmetic Calculator
 */
public class Calculator {
    // Infix to Postfix Stack
    private Stack<String> stackString = new Stack<String>();
    // Calculate Stack
    private Stack<Double> stackPostfix = new Stack<Double>();

    // Split the input function
    private String[] splitInfix(String infix){
        String[] temp = infix.split(" ");
        return temp;
    }

    public boolean operator(String xar){
        if( (xar.equals("+"))||(xar.equals("-"))||(xar.equals("/"))||(xar.equals("*")) )
            return  true;
        return false;
    }

    public boolean openParentheses(String xar){
        if(xar.equals("("))
            return  true;
        return false;
    }
    public boolean closeParentheses(String xar){
        if(xar.equals(")"))
            return  true;
        return false;
    }
    public int priority(String op) {
        if (op.equals("+")||op.equals("-")) return 0;
        else if (op.equals("*")||op.equals("/")) return 1;
        else if (op.equals("(")||op.equals(")")) return -1;
        else throw new IllegalArgumentException("Operator unknown: " + op);
        }

    // Infix to Postfix function
    private String toPostfix(String[] infix) {
        String Postfix = new String();
        for (String temp : infix) {
            if (!operator(temp) && !openParentheses(temp) && !closeParentheses(temp)) {
                Postfix += temp;
            } else if (operator(temp)) {
                while (!stackString.isEmpty() && !openParentheses(temp) && priority(temp) <= priority(stackString.peek())) {
                    Postfix += stackString.pop();
                }
                stackString.push(temp);
            } else if (openParentheses(temp)) {
                stackString.push(temp);
            } else if (closeParentheses(temp)) {
                while (!stackString.isEmpty() && !openParentheses(stackString.peek())) {
                    Postfix += stackString.pop();
                }
                stackString.pop();
            }
        }
            while (!stackString.isEmpty()) {
                Postfix += stackString.pop();
            }
            return Postfix;
    }

    // Final Calculation and ans
    public Double ans(String e){
        String[] Infix = splitInfix(e);
        String Postfix = toPostfix(Infix);
        String[] splitPostfix = Postfix.split("");
        for (String temp : splitPostfix){
//            StdOut.print(temp+"\n");
            if(!operator(temp)){
                double value = Double.parseDouble(temp);
                stackPostfix.push(value);
            }
            else{
                double total = 0;
                if(temp.equals("+")){
                    total = stackPostfix.pop() + stackPostfix.pop();
                    stackPostfix.push(total);
                }
                else if(temp.equals("-")){
                    total = -stackPostfix.pop() + stackPostfix.pop();
                    stackPostfix.push(total);
                }
                else if(temp.equals("*")){
                    total = stackPostfix.pop()*stackPostfix.pop();
                    stackPostfix.push(total);
                }
                else if(temp.equals("/")){
                    double temp1 = stackPostfix.pop();
                    double temp2 = stackPostfix.pop();
                    total = temp2/temp1;
                    stackPostfix.push(total);
                }
//                StdOut.print(temp + " : "+total+"\n");
            }
        }

        return stackPostfix.peek();
    }
    public static void main(String[] args) {
        String input = "( 1 + 2 ) * 3 - 6 / 3";
//        String input = "1 + 1 * 3";
//        String[] temp = input.split(" ");
        Calculator test = new Calculator();
//        String doit = test.toPostfix(temp);
        StdOut.print(test.ans(input)+"\n");
//        StdOut.print(test.toPostfix(temp));
//        StdOut.print(temp[1]);
//        StdOut.print(operator(temp[1]));
    }
}
