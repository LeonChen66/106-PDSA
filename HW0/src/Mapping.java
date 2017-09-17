/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Leon
 */
import java.io.FileReader;
import java.io.BufferedReader;

public class Mapping {
    public static void main(String[] args) throws Exception {
        try(BufferedReader br = new BufferedReader(new FileReader(args[0]))){
            String data1 = br.readLine();
            int readCount = Integer.parseInt(data1);
            String[] in_str = new String[readCount];
            //System.out.print(readCount + "\n");
            for(int i=0;i<readCount;i++){
                String data = br.readLine();
                in_str[i] = data;
                //System.out.print(in_str[i] + "\n");
            }
            
            String compare_str = br.readLine();
            //System.out.print(compare_str + "\n");
            /*
            int ans = compare_str.indexOf(in_str[2]);
            int ans2 = compare_str.indexOf(in_str[0]);
            System.out.print(ans+"\n"+ans2 + "\n");
            */
            int num_map = 0;
            int twice = 0;
            for(int i=0;i<readCount;i++){
                if(compare_str.indexOf(in_str[i])!=-1){
                    num_map += 1;
                };
            int iIndex=0;
            int iCount=0;
            while((iIndex =  compare_str.indexOf(in_str[i],iIndex)) != -1){
                  //System.out.print("Before length"+iIndex+"\n");
                  iIndex = iIndex + in_str[i].length();
                 // System.out.print(iIndex);
                  iCount++;
            };
            if(iCount>=2){
                twice += 1;
            }
            };
            System.out.print(num_map+"\n" + twice + "\n");
            

            //System.out.print(iCount);
        }


    }
}

