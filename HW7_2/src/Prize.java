import sun.net.sdp.SdpSupport;

import java.util.Arrays;

public class Prize {
    Prize(In in){
        this.N = in.readInt();
        this.M = in.readInt();
        this.W = in.readInt();
        in.readLine();
        int i=0;
        while(in.hasNextLine()){
            String vals = in.readLine();
            String name = vals.split(" ")[0];
            int X = Integer.parseInt(vals.split(" ")[1]);
            if(myBST.contains(X)){
                StdOut.println(name+" fail");
            }else{
                myBST.put(X,name);
                i++;
            }
            if(i==N) break;
        }
//        StdOut.println(myBST.size());
    }

    public void lotto(){
        String winner = (String)myBST.get(W);
        int winner_rank = myBST.rank(W);
//        if (winner_rank<M){
//            int other_side = N-winner_rank-1;
//            StdOut.println(other_side);
//            String[] array = new String[other_side];
//            for(int i=0;i<other_side;i++){
//                array[i] = myBST.successor(W,i);
//                StdOut.print(array[i]);
//            }
//            return ;
//        }
        StdOut.println(winner);
        int total = 2*M;
        String[] array = new String[total];
        for(int i=0;i<total;i++) {
//            String previous = (String) myBST.predecessor(W, M);
//            String next = (String) myBST.successor(W, M);
            if(i%2==0){
                array[i] = myBST.predecessor(W,(i/2)+1);
            }else{
                array[i] = myBST.successor(W,(i+1)/2);
            }
        }
        Arrays.sort(array);
        for (String arr:array) {
            StdOut.println(arr);
        }

    }

    private static In in;
    private static int N;
    private static int M;
    private static int W;
    private NewBST<Integer,String> myBST = new NewBST<>();
    public static void main(String[] args) {
        In in=new In(args[0]);
        Prize test = new Prize(in);
        test.lotto();
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
