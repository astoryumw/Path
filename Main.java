import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        Main m = new Main();
        int[][] myArray = m.fileOpener("./shortMaze.txt");
        for (int i=0; i<myArray.length; i++) {
            System.out.println(Arrays.toString(myArray[i]));    
        }
        m.connection(myArray);
        
        m.fileWriter(myArray);
        // System.out.println(Arrays.toString(myArray[0]));
    }

    // need method that goes through the maze and see's if there is a way
    // to connect the 2s together
    public boolean connection(int[][] myArray) {
        // find the initial 2
        for (int i=0; i<myArray[0].length; i++) {
            // if find the 2, start search
            if (myArray[0][i]==2) {
                // go horizontal, thisll send it to the left
                for (int u=myArray[0].length-1; u>=0; u--) {
                    System.out.println(myArray[0][u]);
                    if (myArray[0][u-1]==0) {
                        for (int x=0; x<myArray.length; x++) {
                            System.out.println(myArray[x][u]);
                            if (myArray[x+1][u]==0) {
                                for (int e=u; e>=0; e--) {
                                    System.out.println(myArray[x][e]);
                                    if (myArray[x][e-1]==0) {
                                        for (int y=x; y<myArray.length; y++) {
                                            System.out.println(myArray[y][e] + "teehee" + y + myArray.length);
                                            if (/*myArray[y+1][e]==0 || */y==myArray.length-1) { // for some reason the first statement made it crash
                                                for (int z=e; z>=0; z--) {
                                                    if (myArray[y][z]==2) {
                                                        System.out.println("EOL");
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                // go vertical, send it down
                // for (int x=0; x<myArray.length; x++) {
                //     System.out.println(myArray[x][myArray.length-1]);
                //     if (myArray[x+1][myArray.length-1]==0) {
                //         System.out.println("EOL");
                //         break;
                //     }
                // }
            }
        }

        return true;
    }

    // open a file opens and retuns a listof 10? length?
    public int[][] fileOpener(String name) {
        
        int[][] myArray = new int[10][10];
        File file = new File(name);
        // System.out.println(file);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String lime = br.readLine();
            int i=0;
            while (lime!=null) {
                // System.out.println(lime);
                String[] splitMe = lime.split("");
                // System.out.println(Arrays.toString(splitMe));
                for (int u=0; u<10; u++) {
                    myArray[i][u] = Integer.parseInt(splitMe[u]);
                }
                i++;
                lime = br.readLine();
            }
            // System.out.println(br);
            // int r = 0;
            // while ((r=br.read())!=-1) {
            //     System.out.println();
            //     // r = char(r);
            //     // System.out.println(r);
            //     for (int i=0; i<10; i++) {
            //         for (int u=0; u<10; u++) {
            //             myArray[i][u] = r;
            //             // System.out.println(myArray[i][u]);
            //         }
            //     }
            // }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        return myArray;
    }

    // write the to file, will change to write the shortest path after connection method is finished
    public void fileWriter(int[][] myArray) {
        try {
            File myObject = new File("shortestPath.txt");
            myObject.createNewFile();
            FileWriter fw = new FileWriter("shortestPath.txt");
            for (int i=0; i<myArray.length; i++) {
                // System.out.println(Arrays.toString(myArray[i]));
                fw.write(Arrays.toString(myArray[i]));
            }
            // fw.write(Arrays.toString(myArray));
            fw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}