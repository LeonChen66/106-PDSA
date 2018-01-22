import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Player implements Comparable<Player>{
    /*
    type 6~0 ==> (Four of a kind > full house > flush > straight > two pair > one pair > high card)
     */
    private Card[] cards = new Card[7];
    private String name;


    // DO NOT MODIFY THIS
    public Player(String name) {
        this.name = name;
    }

    // DO NOT MODIFY THIS
    public String getName() {
        return this.name;
    }

    // DO NOT MODIFY THIS
    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    // TODO
    public int compareTo(Player that) {
        // complete this function so the Player can be sorted according to the cards he/she has.
        Card[] p1_cards = this.Self_maxcard(this.cards);
        Card[] p2_cards = that.Self_maxcard(that.cards);

        int player1 = cardType(p1_cards);
        int player2 = cardType(p2_cards);

        if (player1 > player2) return 1;
        else if (player1 < player2) return -1;
        else if (player1 == player2) {
            switch(player1){
                case 6:
                    return compare_four(p1_cards,p2_cards);
                case 5:
                    return compare_full(p1_cards,p2_cards);
                case 4:
                    return comare_flush(p1_cards,p2_cards);
                case 3:
                    return compare_straight(p1_cards,p2_cards);
                case 2:
                    return compare_twopair(p1_cards,p2_cards);
                case 1:
                    return compare_pair(p1_cards,p2_cards);
                case 0:
                    return compare_high(p1_cards,p2_cards);
            }
        }
        return 0;
    }

    public int compare_four(Card[] c1, Card[] c2){
        int[] allFace_c1 = getAllFace(c1);
        int[] allFace_c2 = getAllFace(c2);
        int result_c1 = -1;
        int result_c2 = -1;
        for (int i=0;i<13;i++){
            if(allFace_c1[i]==4)
                result_c1 = i;
        }

        for (int i=0;i<13;i++){
            if(allFace_c2[i]==4)
                result_c2 = i;
        }
        if (result_c1>result_c2) return 1;
        else if(result_c1 < result_c2) return -1;
        else return 0;
    }
    public int compare_full(Card[] c1, Card[] c2){
        int[] allFace_c1 = getAllFace(c1);
        int[] allFace_c2 = getAllFace(c2);
        int result_c1 = -1;
        int result_c2 = -1;
        for (int i=0;i<13;i++){
            if(allFace_c1[i]==3)
                result_c1 = i;
        }

        for (int i=0;i<13;i++){
            if(allFace_c2[i]==3)
                result_c2 = i;
        }
        if (result_c1>result_c2) return 1;
        else if(result_c1 < result_c2) return -1;
        else {
            for (int i=0;i<13;i++){
                if(allFace_c1[i]==2)
                    result_c1 = i;
            }

            for (int i=0;i<13;i++){
                if(allFace_c2[i]==2)
                    result_c2 = i;
            }
            if (result_c1>result_c2) return 1;
            else if(result_c1 < result_c2) return -1;
            else return 0;
        }
    }

    public int comare_flush(Card[] c1, Card[] c2){
        int[] allFace_c1 = getAllFace(c1);
        int[] allFace_c2 = getAllFace(c2);
        int[] result_c1 = new int[c1.length];
        int[] result_c2 = new int[c2.length];
        for (int i=0;i<5;i++){
            int weight_1 = faceIndex(c1[i].getFace())*100 + suitIndex(c1[i].getSuit());
            int weight_2 = faceIndex(c2[i].getFace())*100 + suitIndex(c2[i].getSuit());
            result_c1[i] = weight_1;
            result_c2[i] = weight_2;
        }
        Arrays.sort(result_c1);
        Arrays.sort(result_c2);
        for(int i=c1.length-1;i>=0;i--){
            if (result_c1[i]>result_c2[i]) return 1;
            else if(result_c1[i] < result_c2[i]) return -1;
        }
        return 0;
    }

    public int compare_straight(Card[] c1, Card[] c2){
        int[] allFace_c1 = getAllFace(c1);
        int[] allFace_c2 = getAllFace(c2);
        int[] result_c1 = new int[c1.length];
        int[] result_c2 = new int[c2.length];
        if (allFace_c1[12]==1 && allFace_c1[0]==1 && allFace_c1[1]==1 && allFace_c1[2]==1 && allFace_c1[3]==1 ) return -1;
        else if(allFace_c2[12]==1 && allFace_c2[0]==1 && allFace_c2[1]==1 && allFace_c2[2]==1 && allFace_c2[3]==1 ) return 1;
        for (int i=0;i<5;i++){
            int weight_1 = faceIndex(c1[i].getFace())*100 + suitIndex(c1[i].getSuit());
            int weight_2 = faceIndex(c2[i].getFace())*100 + suitIndex(c2[i].getSuit());
            result_c1[i] = weight_1;
            result_c2[i] = weight_2;
        }
        Arrays.sort(result_c1);
        Arrays.sort(result_c2);
        for(int i=c1.length-1;i>=0;i--){
            if (result_c1[i]>result_c2[i]) return 1;
            else if(result_c1[i] < result_c2[i]) return -1;
        }
        return 0;
    }

    public int compare_twopair(Card[] c1, Card[] c2){
        int[] allFace_c1 = getAllFace(c1);
        int[] allFace_c2 = getAllFace(c2);
        int result_c1 = -1;
        int result_c2 = -1;
        for (int i=12;i>=0;i--){
            if(allFace_c1[i]==2)
                result_c1 = i;
        }

        for (int i=12;i>=0;i--){
            if(allFace_c2[i]==2)
                result_c2 = i;
        }
        if (result_c1>result_c2) return 1;
        else if(result_c1 < result_c2) return -1;
        else {
            for (int i=0;i<13;i++){
                if(allFace_c1[i]==2)
                    result_c1 = i;
            }

            for (int i=0;i<13;i++){
                if(allFace_c2[i]==2)
                    result_c2 = i;
            }
            if (result_c1>result_c2) return 1;
            else if(result_c1 < result_c2) return -1;
            else return 0;
        }
    }

    public int compare_pair(Card[] c1, Card[] c2){
        int[] allFace_c1 = getAllFace(c1);
        int[] allFace_c2 = getAllFace(c2);
        int result_c1 = -1;
        int result_c2 = -1;
        for (int i=12;i>=0;i--){
            if(allFace_c1[i]==2)
                result_c1 = i;
        }

        for (int i=12;i>=0;i--){
            if(allFace_c2[i]==2)
                result_c2 = i;
        }
        if (result_c1>result_c2) return 1;
        else if(result_c1 < result_c2) return -1;
        else {
            Card[] c1_pair = new Card[2];
            Card[] c2_pair = new Card[2];
            int temp1 = 0;
            int temp2 = 0;
            for(int i=4;i>=0;i--){
                if (faceIndex(c1[i].getFace())==result_c1){
                    c1_pair[temp1] = c1[i];
                    temp1 ++;
                }
            }
            for(int i=4;i>=0;i--){
                if (faceIndex(c2[i].getFace())==result_c1){
                    c2_pair[temp2] = c2[i];
                    temp2 ++;
                }
            }
            Card max_c1;
            Card max_c2;
            Card second_c1;
            Card second_c2;
            if(c1_pair[0].compareTo(c1_pair[1])==1){
                max_c1 = c1_pair[0];
                second_c1 = c1_pair[1];
            }
            else {
                max_c1 = c1_pair[1];
                second_c1 = c1_pair[0];
            }

            if(c2_pair[0].compareTo(c2_pair[1])==1){
                max_c2 = c2_pair[0];
                second_c2 = c2_pair[1];
            }
            else {
                max_c2 = c2_pair[1];
                second_c2 = c2_pair[0];
            }
            if (max_c1.compareTo(max_c2)==1){
                return 1;
            }
            else if (max_c1.compareTo(max_c2)==-1){
                return -1;
            }
            else{
                if (second_c1.compareTo(second_c2)==1) return 1;
                else if (second_c1.compareTo(second_c2)==-1) return -1;
                else return 0;
            }
        }
    }

    public int compare_high(Card[] c1, Card[] c2){
        int[] allFace_c1 = getAllFace(c1);
        int[] allFace_c2 = getAllFace(c2);
        int[] result_c1 = new int[c1.length];
        int[] result_c2 = new int[c2.length];
        for (int i=0;i<5;i++){
            int weight_1 = faceIndex(c1[i].getFace())*100 + suitIndex(c1[i].getSuit());
            int weight_2 = faceIndex(c2[i].getFace())*100 + suitIndex(c2[i].getSuit());
            result_c1[i] = weight_1;
            result_c2[i] = weight_2;
        }
        Arrays.sort(result_c1);
        Arrays.sort(result_c2);
        for(int i=c1.length-1;i>=0;i--){
            if (result_c1[i]>result_c2[i]) return 1;
            else if(result_c1[i] < result_c2[i]) return -1;
        }
        return 0;
    }

    public int compare_pair_self(Card[] c1, Card[] c2){
        int[] allFace_c1 = getAllFace(c1);
        int[] allFace_c2 = getAllFace(c2);
        int result_c1 = -1;
        int result_c2 = -1;
        for (int i=12;i>=0;i--){
            if(allFace_c1[i]==2)
                result_c1 = i;
        }

        for (int i=12;i>=0;i--){
            if(allFace_c2[i]==2)
                result_c2 = i;
        }
        if (result_c1>result_c2) return 1;
        else if(result_c1 < result_c2) return -1;
        else return 0;
    }

    public int self_CompareTo(Card[] c1, Card[] c2) {
        // complete this function so the Player can be sorted according to the cards he/she has.
        int player1 = cardType(c1);
        int player2 = cardType(c2);


        if (player1 > player2) return 1;
        else if (player1 < player2) return -1;
        else if (player1 == player2) {
            switch(player1){
                case 6:
                    return compare_four(c1,c2);
                case 5:
                    return compare_full(c1,c2);
                case 4:
                    return comare_flush(c1,c2);
                case 3:
                    return compare_straight(c1,c2);
                case 2:
                    return compare_twopair(c1,c2);
                case 1:
                    return compare_pair_self(c1,c2);
                case 0:
                    return compare_high(c1,c2);
            }
        }
        return 0;
    }

    public Card[] Self_maxcard(Card[] c){
        Card[] self = new Card[5];
        self[0] = cards[0];
        self[1] = cards[1];
        self[2] = cards[2];
        self[3] = cards[3];
        self[4] = cards[4];
        Card[] max = Arrays.copyOf(self,self.length);
        for (int i=2;i<c.length;i++){
            for (int j=i+1;j<c.length;j++){
                for (int k = j+1;k<c.length;k++){
                    self[2] = cards[i];
                    self[3] = cards[j];
                    self[4] = cards[k];
//                    StdOut.println(self[0].getFace()+self[1].getFace()+self[2].getFace()+self[3].getFace()+self[4].getFace()+"type:"+cardType(self)+"type max:"+cardType(max)+isStraight(getAllFace(self)));
                    if (self_CompareTo(self,max)>0){
                        System.arraycopy(self, 0, max, 0, self.length );
                    }
                }
            }
        }
        return max;
    }

    private boolean isStraight(int[] allFace) {

        int counter = 0;
        for (int i = 0; i < allFace.length; i++) {
            try{
                if(allFace[0]==1&&allFace[12]==1){
                    if(allFace[1]==1&&allFace[2]==1&&allFace[3]==1) return true;
                }else if(allFace[i]==1&&allFace[i+1]==1&&allFace[i+2]==1&&allFace[i+3]==1&&allFace[i+4]==1) return true;
            }catch (Exception e){}
        }
        return false;
    }
    public boolean isFlush(Card[] c) {
        int[] allSuit = new int[4];
        for (int i = 0; i < c.length; i++) {
            String temp = c[i].getSuit();
            allSuit[suitIndex(temp)]++;
        }
        for (int j = 0; j < 4; j++) {
            if (allSuit[j] == 5) return true;
        }
        return false;
    }
    /* no use
    public int secondRound(int s, Card[] c) {
        int[] allFace = getAllFace(c);
        int secondValue = -1;
        if (s ==6) {
            for (int i=0;i<13;i++){
                if(allFace[i]==4)
                    return i;
            }
        }
        else if (s == 5) {
            for (int i = 0; i < 13; i++) {
                if (allFace[i] == 3) {
                    return i;
                }
            }
        }
        else if (s == 0 | s == 3 | s == 4) {
            for (int i = 0; i < 13; i++) {
                if (allFace[i] == 1) {
                    secondValue = i;
                }
            }
            return secondValue;
        } else if (s == 3 | s == 2) {
            for (int i = 0; i < 13; i++) {
                if (allFace[i] == 2) {
                    secondValue = i;
                }
            }
            return secondValue;
        }
        return secondValue;
    }
    */

    public int[] getAllFace(Card[] c) {
        int[] allFace = new int[13];

        for (int i = 0; i < allFace.length; i++) {
            allFace[i] = 0;
        }

        for (int i = 0; i < c.length; i++) {
            String temp = c[i].getFace();
            allFace[faceIndex(temp)]++;
        }

        return allFace;
    }

    public int cardType(Card[] c) {

        int[] allFace = getAllFace(c);

        int[] allType = new int[5];                  //  0~4 張牌種可能
        for (int i = 0; i < allFace.length; i++) {
            if (allFace[i]==4) allType[4]++;
            else if (allFace[i] == 3) allType[3]++;
            else if (allFace[i] == 2) allType[2]++;
            else if (allFace[i] == 1) allType[1]++;
        }

        int type = -1;
        if (allType[4]==1){
            type = 6;
        }
        else if (allType[3] == 1) {
            if (allType[2] == 1) {
                type = 5;
            } else if (allType[1] == 2) {
                type = 1;
            }
        } else if (allType[2] == 2) {
            type = 2;
        } else if (allType[2] == 1) {
            type = 1;
        } else if (allType[1] == 5) {
            if (isFlush(cards)) {
                type = 4;
            } else if (isStraight(allFace)) {
                type = 3;
            } else {
                type = 0;
            }
        }
        return type;
    }

    public int faceIndex(String f){
        String[] faces = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K","A"};
        int face_index = 0;
        while(!f.equals(faces[face_index])) face_index++;
        return face_index;
    }

    public int suitIndex(String s){
        String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        int suit_index = 0;
        while (!s.equals(suits[suit_index])) suit_index++;
        return suit_index;
    }

    public static void main(String[] args) {

    }
}