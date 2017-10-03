/*
Create by Leon for PDSA
HW2
Arithmetic Calculator
 */
public class Calculator {
    // Infix to Postfix Stack
    public Stack<String> stackString = new Stack<String>();
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
    private int priority(String op) {
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
                Postfix +=" ";
                Postfix += temp;
            } else if (operator(temp)) {
                while (!stackString.isEmpty() && !openParentheses(temp) && priority(temp) <= priority(stackString.peek())) {
                    Postfix +=" ";
                    Postfix += stackString.pop();
                }
                Postfix +=" ";
                stackString.push(temp);
            } else if (openParentheses(temp)) {
                stackString.push(temp);
            } else if (closeParentheses(temp)) {
                while (!stackString.isEmpty() && !openParentheses(stackString.peek())) {
                    Postfix +=" ";
                    Postfix += stackString.pop();
                }
                stackString.pop();
            }
        }
            while (!stackString.isEmpty()) {
                Postfix +=" ";
                Postfix += stackString.pop();
            }
            return Postfix;
    }

    // Final Calculation and ans
    public Double ans (String e){
        String[] Infix = splitInfix(e);
        String Postfix = toPostfix(Infix);
//        StdOut.print(Postfix+"\n");
        for (String temp : Postfix.split(" ")){
//            StdOut.print(temp+"\n");
            if(!operator(temp)){
                try {
                    double value = Double.valueOf(temp);
                    stackPostfix.push(value);
                }catch (NumberFormatException f){

                }
            }
            else{
                double total = 0;
                if(temp.equals("+")){
                    total = stackPostfix.pop() + stackPostfix.pop();
//                    StdOut.print(total+"\n");
                    stackPostfix.push(total);
                }
                else if(temp.equals("-")){
                    total = -stackPostfix.pop() + stackPostfix.pop();
//                    StdOut.print(total+"\n");
                    stackPostfix.push(total);
                }
                else if(temp.equals("*")){
                    double temp1 = stackPostfix.pop();
                    double temp2 = stackPostfix.pop();
                    total = temp1*temp2;
//                    StdOut.print(total+"\n");
                    stackPostfix.push(total);
                }
                else if(temp.equals("/")){
                    double temp1 = stackPostfix.pop();
                    double temp2 = stackPostfix.pop();
                    total = temp2/temp1;
//                    StdOut.print(total+"\n");
                    stackPostfix.push(total);
                }
            }
        }
        double ans = stackPostfix.peek();
        return ans;
    }

    public static void main(String[] args) {
//        String input = "1 + 6 / 2 * ( 1 + 2 ) - 4 * ( 5 - 3 )";
        String input = "( ( 5 + 3 ) * 3.5 + 7 ) / 9";
//        String input = "5 * 5 / 3 * 3 - 7 * ( 8 + 9 )";
//        String input = "7 + 9 * ( ( 6 + 5 ) * 2.5 + 7.5 )";
//        String[] temp = input.split(" ");
        Calculator test = new Calculator();
//        String doit = test.toPostfix(temp);
        StdOut.print(test.ans(input)+"\n");
//        StdOut.print(test.toPostfix(temp));
//        StdOut.print(temp[1]);
//        StdOut.print(operator(temp[1]));

    }
}
