import com.sun.org.apache.bcel.internal.generic.NEW;

public class NewBST<Key extends Comparable<Key>, Value> extends BST{
    public NewBST(){
        super();
    }
//    private Value vals = (Value)super.get(0);
    public Value predecessor(Key key, int m){
        if (get(key)==null) return null;
        int temp = rank(key)-m;
        if(temp>=0){
            Key temp_key = (Key)super.select(temp);
            Value ans = (Value)super.get(temp_key);
            return ans;
        } else {
            Value ans = predecessor((Key)super.max(), -temp-1);
            return ans;
        }
    }
    public Value successor(Key key, int m){
        if (get(key)==null) return null;
        int temp = rank(key)+m;
        if (temp<=super.size()-1) {
            Key temp_key = (Key) super.select(temp);
            Value ans = (Value) super.get(temp_key);
            return ans;
        }else{
            Value ans = successor((Key)super.min(),temp-super.size());
            return ans;
        }
    }

    public static void main(String[] args) {
        NewBST test = new NewBST();
        test.put(7, 'a');
        test.put(4,'f');
        test.put(-6,'h');
        test.put(9,'r');
        test.put(84,'e');
//        StdOut.println(test.select(5));
        StdOut.println(test.successor(84,2));
    }
}
