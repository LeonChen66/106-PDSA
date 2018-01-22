import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Card implements Comparable<Card> {

    private String face; // should be one of [A, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K]
    private String suit; // should be one of [Spades, Hearts, Diamonds, Clubs]

    public static final Comparator<Card> SUIT_ORDER = new SuitOrder();
    public static final Comparator<Card> FACE_ORDER = new FaceOrder();

    // DO NOT MODIFY THIS
    public Card(String face, String suit) {
        this.face = face;
        this.suit = suit;
    }

    // DO NOT MODIFY THIS
    public String getFace() {
        return this.face;
    }

    // DO NOT MODIFY THIS
    public String getSuit() {
        return this.suit;
    }

    // TODO
    public int compareTo(Card that) {
        // complete this function so the Card can be sorted
        // (you must consider both face and suit)
        int compare_ans = FACE_ORDER.compare(this, that);
        if (compare_ans!=0){
            return compare_ans;
        }else{
            return SUIT_ORDER.compare(this ,that);
        }
    }

    // TODO
    private static class SuitOrder implements Comparator<Card> {
        private List<String> order;
        SuitOrder(){
            String[] suits = {"Clubs","Diamonds","Hearts","Spades"};
            order = new ArrayList<String>();
            for (String suit : suits){
                order.add(suit);
            }
        }
        @Override
        public int compare(Card c1, Card c2) {
            // complete this function so the Card can be sorted according to the suit
            int suit_1 = order.indexOf(c1.getSuit());
            int suit_2 = order.indexOf(c2.getSuit());
            if (suit_1 > suit_2) return 1;
            else if(suit_1 < suit_2) return -1;
            else return 0;
        }
    }
    // TODO
    private static class FaceOrder implements Comparator<Card>{
        private ArrayList<String> order = new ArrayList<>();
        FaceOrder(){
            String[] faces = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
            for (String face : faces){
                order.add(face);
            }
        }
        @Override
        public int compare(Card c1, Card c2){
            int face_1 = order.indexOf(c1.getFace());
            int face_2 = order.indexOf(c2.getFace());
            if(face_1 > face_2) return 1;
            else if (face_1 < face_2) return -1;
            else return 0;
        }
    }

    public static void main(String[] args) {

    }
}

