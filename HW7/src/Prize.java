public class Prize {
    private static NewBST<Integer,String> myBST;
    public static void main(String[] args) {
        In in=new In(args[0]);
        StdOut.println(in.readInt());
    }
}
class NewBST<Key extends Comparable<Key>, Value> extends BST{
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
    public Value successor(Key key, int m) {
        if (get(key) == null) return null;
        int temp = rank(key) + m;
        if (temp <= super.size() - 1) {
            Key temp_key = (Key) super.select(temp);
            Value ans = (Value) super.get(temp_key);
            return ans;
        } else {
            Value ans = successor((Key) super.min(), temp - super.size());
            return ans;
        }
    }
}
