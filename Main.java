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

        if (m.processer(myArray)) {
            System.out.println("There is a path!");
        }
    }

    // new idea: use connection to call where it is and find the next piece
    // depending on direction, have a different method for each that is called
    // method returns final area
    public boolean processer(int[][] myArray) {
        Main m = new Main();
        int location = 0, height = 0;
        int[] afterRun = new int[2];
        int lastNumber = 0;
        // find the initial 2
        location = Arrays.binarySearch(myArray[0],2);
        // System.out.println(location);
        if (myArray[0][location-1]==1) {
            System.out.println(Arrays.toString(m.goLeft(myArray, 0, location)));
            afterRun = m.goLeft(myArray, 0, location);
            System.out.println(Arrays.toString(afterRun));
            lastNumber = myArray[afterRun[0]][afterRun[1]];
        } else if (myArray[0][location+1]==1) {
            System.out.println(Arrays.toString(m.goRight(myArray, 0, location)));
            afterRun = m.goLeft(myArray, 0, location);
            System.out.println(Arrays.toString(afterRun));
            lastNumber = myArray[afterRun[0]][afterRun[1]];
        } else if (myArray[1][location]==1) {
            System.out.println(Arrays.toString(m.goDown(myArray, 1, location)));
            afterRun = m.goLeft(myArray, 1, location);
            System.out.println(Arrays.toString(afterRun));
            lastNumber = myArray[afterRun[0]][afterRun[1]];
        }
        System.out.println(Arrays.toString(afterRun));
        System.out.println(myArray[afterRun[0]][afterRun[1]]);
        System.out.println(lastNumber);
        height = afterRun[0];
        System.out.println("Height: " + height);
        location = afterRun[1];
        System.out.println("Location: " + location);
        int left = 0, right = 0, down = 0;
        boolean goingLeft = false, goingRight = false, goingDown = false;;
        // keep going until the 2 is found, eventually will need it to return false if it cannot be found
        while (lastNumber!=2) {
            System.out.println(myArray[height][location-1]);
            if (myArray[height][location-1]==1 && !goingRight) {
                System.out.println("Go left.");
                goingLeft = true;
                goingRight = false;
                goingDown = false;
                // System.out.println(Arrays.toString(m.goLeft(myArray, afterRun[0], afterRun[1])));
                afterRun = m.goLeft(myArray, height, location);
                System.out.println(Arrays.toString(afterRun));
                location = afterRun[1];
                lastNumber = myArray[afterRun[0]][afterRun[1]];
            } else if (myArray[height][location+1]==1 && !goingLeft) {
                System.out.println("Go right.");
                goingLeft = false;
                goingRight = true;
                goingDown = false;
                // System.out.println(Arrays.toString(m.goRight(myArray, afterRun[0], afterRun[1])));
                afterRun = m.goLeft(myArray, afterRun[0], afterRun[1]);
                System.out.println(Arrays.toString(afterRun));
                location = afterRun[1];
                lastNumber = myArray[afterRun[0]][afterRun[1]];
            } else if (myArray[height+1][location]==1) {
                goingLeft = false;
                goingRight = false;
                goingDown = true;
                System.out.println("Go down.");
                System.out.println(height + " " + location);
                // System.out.println(Arrays.toString(m.goDown(myArray, height, location)));
                afterRun = m.goDown(myArray, height, location);
                System.out.println(Arrays.toString(afterRun));
                height = afterRun[0];
                lastNumber = myArray[afterRun[0]][afterRun[1]];
                // down++;
            }
            // break;
        }
        System.out.println(lastNumber);
        if (lastNumber==2) {
            return true;
        } else {
            return false;
        }
    }

    // needs to return new place in myArray
    // if im going left, could use binarySearch to find the first 1, which would be at the end of the list
    public int[] goLeft(int[][] myArray, int height, int location) {
        // System.out.println("1");
        int[] newArray = new int[2];
        for (int i=location; i>=0; i--) {
            System.out.println(myArray[height][i]);
            if (myArray[height][i]==0) {
                // System.out.println("1");
                newArray[0] = height;
                newArray[1] = i+1;
                // System.out.println(newArray);
                return newArray; // return height, location
            } else if (myArray[height][i]==2) {
                newArray[0] = height;
                newArray[1] = i;
                return newArray;
            }
        }
        System.out.println("Returning null");
        return null;
    }

    // to go right, find first 0 and then go back, but it must go past the initial 0s
    public int[] goRight(int[][] myArray, int height, int location) {
        int[] newArray = new int[2];
        for (int i=location; i<=myArray[height].length; i++) {
            // System.out.println(myArray[height][i]);
            if (myArray[height][i]==0) {
                newArray[0] = height;
                newArray[1] = i-1;
                return newArray;
            }
        }
        return null;
    }

    public int[] goDown(int[][] myArray, int height, int location) {
        int[] newArray = new int[2];
        for (int i=height; i<=myArray.length; i++) {
            // System.out.println(myArray[i][location]);
            try {
                if (myArray[i][location]==0) { // getting caught here
                    System.out.println("Done");
                    newArray[0] = i-1;
                    newArray[1] = location;
                    return newArray;
                }
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                newArray[0] = i-1;
                newArray[1] = location;
                return newArray;
            }
        }
        return null;
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
                    // System.out.println(myArray[0][u]);
                    if (myArray[0][u-1]==0) {
                        for (int x=0; x<myArray.length; x++) {
                            // System.out.println(myArray[x][u]);
                            if (myArray[x+1][u]==0) {
                                for (int e=u; e>=0; e--) {
                                    // System.out.println(myArray[x][e]);
                                    if (myArray[x][e-1]==0) {
                                        for (int y=x; y<myArray.length; y++) {
                                            // System.out.println(myArray[y][e] + "teehee" + y + myArray.length);
                                            if (/*myArray[y+1][e]==0 || */y==myArray.length-1) { // for some reason the first statement made it crash
                                                for (int z=e; z>=0; z--) {
                                                    if (myArray[y][z]==2) {
                                                        // System.out.println("EOL");
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