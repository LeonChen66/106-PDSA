import java.util.Arrays;
import java.io.FileReader;
import java.io.BufferedReader;

public class Game{

    // Judge System will Execute The Program Here
    public static void main(String[] args) throws Exception{

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {

            int idx = 0;
            int playerCount = Integer.parseInt(br.readLine());
            Player[] playerArray = new Player[playerCount];
            Card[] communityCard = new Card[5];
            for(int i=0;i<5;i++){
                String[] sepcard = br.readLine().split("_");
                Card card = new Card(sepcard[1], sepcard[0]);
                communityCard[i] = card;
            }
            for(String in = br.readLine(); in != null; in = br.readLine()) {
                String name = in;
                Player player = new Player(name);
                playerArray[idx++] = player;

                Card[] cardsArray = new Card[7];
                String[] cardStr = br.readLine().split(",");
                for(int i = 0; i < 2; i++){
                    String[] sep = cardStr[i].split("_");
                    Card card = new Card(sep[1], sep[0]);
                    cardsArray[i] = card;
                }
                for(int i = 2; i < 7; i++){
                    cardsArray[i] =  communityCard[i-2];
                }
                player.setCards(cardsArray);
                Card[] test = player.Self_maxcard(cardsArray);
                StdOut.println(player.cardType(test));
                StdOut.println(test[0].getFace()+test[1].getFace()+test[2].getFace()+test[3].getFace()+test[4].getFace());
            }

            Arrays.sort(playerArray);

            System.out.println(playerArray[playerCount-1].getName());


        }
    }
}
