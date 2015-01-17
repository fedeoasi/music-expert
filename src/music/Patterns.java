package music;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class Patterns {
    File file;
    FileReader fr;
    BufferedReader br;
    ArrayList<ArrayList<Integer>> gradi = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> durate = new ArrayList<ArrayList<Integer>>();
    Pattern pat1 = null;
    Scanner s;

    ArrayList<Integer> g = new ArrayList<Integer>();
    ArrayList<Integer> d = new ArrayList<Integer>();


    public Patterns(){
        file = new File("Patterns/pattern.txt");
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            s = new Scanner(br);


            while(s.hasNextInt()){
                g = new ArrayList<Integer>();
                int n=0;
                while(s.hasNextInt()&& n!=100){
                    n = s.nextInt();
                    g.add(n);
                }
                g.remove(g.size()-1);
                gradi.add(g);

                n=0;
                d = new ArrayList<Integer>();
                while(s.hasNextInt()&& n!=100){
                    n = s.nextInt();
                    d.add(n);
                }
                d.remove(d.size()-1);
                durate.add(d);

            }

            /*for(int i=0; i<gradi.size(); i++){
                System.out.print("gradi("+i+"): ");
                for(int j=0; j<gradi.get(i).size(); j++)
                    System.out.print(gradi.get(i).get(j)+ " ");
                System.out.println();
            }

            for(int i=0; i<durate.size(); i++){
                System.out.print("durate("+i+"): ");
                for(int j=0; j<durate.get(i).size(); j++)
                    System.out.print(durate.get(i).get(j)+ " ");
                System.out.println();
            }
            */
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Patterns();
    }
}
