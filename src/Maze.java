//*** Maze
//*** Forouraghi

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;


//***********************************************************************
public class Maze extends JFrame {
    //*** can keep track of visited cell positions here
    static boolean[][] visited;

    //*** the maze itself
    //***    0 means Power Pellet
    //***    1 means wall
    //***    2 means Stripes
    //***    3 means Pirate
    static int[][] mazePlan =
            {
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 0, 1, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                    {1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1},
                    {1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 3, 1, 2, 0, 0, 0, 0, 0, 3, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                    {1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1},
                    {1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1},
                    {1, 2, 1, 1, 1, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 3, 1, 1, 1, 0, 1},
                    {1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 3, 1},
                    {1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1},
                    {1, 0, 0, 3, 1, 0, 0, 1, 1, 1, 1, 1, 2, 0, 1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1},
                    {1, 0, 1, 1, 1, 3, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            };

    //*** set up the maze wall positions and set all visited states to false
    static MazePanel mp = new MazePanel(mazePlan, visited);

    //*** set up and display main characters' initial maze positions
    static int ghostX = 1, ghostY = 17;              //** Ghost
    static int pacmanX = 1, pacmanY = 1;             //*** Pacman

    //*** each maze cell is 37 pixels long and wide
    static int panelWidth = 37;

    //*** a simple random number generator for random search
    static int randomInt(int n) {
        return (int) (n * Math.random());
    }

    //******************************************************
    //*** main constructor
    //******************************************************
    public Maze() {
        //*** display the ghost
        mp.setupChar(ghostX, ghostY, "ghost.gif");

        //*** display Pacman
        mp.setupChar(pacmanX, pacmanY, "pacman.gif");

        //*** set up the display panel
        getContentPane().setLayout(new GridLayout());
        setSize(mazePlan[0].length * panelWidth, mazePlan[0].length * panelWidth);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(mp);
    }

    //******************************************************
    //*** a delay routine
    //******************************************************
    public static void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
        }
    }


    //******************************************************
    //*** move Pacman to position (i, j)
    //******************************************************

    public static void movePacman(int i, int j) {
        mp.setupChar(i, j, "pacman.gif");
    }


    //******************************************************
    //*** remove Pacman from position (i, j)
    //******************************************************
    public static void removePacman(int i, int j) {
        mp.removeChar(i, j);
    }


    //******************************************************
    //*** is position (i,j) a power-pellet cell/pirate/stripes?
    //******************************************************
    public static boolean openSpace(int i, int j) {
        return (mazePlan[i][j] == 0);
    }
    public static boolean pirate(int i, int j) {return (mazePlan[i][j] == 3);}
    public static boolean stripes(int i, int j) {return (mazePlan[i][j] == 2);}

    //******************************************************
    //***   MODIFY HERE --  MODIFY HERE  --  MODIFY HERE
    //******************************************************


    //Checks to see if cordanates x, y are in the maze
    public static boolean inbounds(int x, int y){
        if (x > 18 || y > 17){
            return false;
        }
        return true;
    }


    //returns the heuristic
    public static int getH(int x, int y){
        if (mazePlan[x][y] == 1){
            return 100000;
        }
        else {
            return Math.abs(x - ghostX) + Math.abs(y - ghostY);
        }
    }

    //gets the cost permove and if empty space adds cost by 10
    public static int getG(int cost, int x, int y){
        return cost + 10;

    }

    //returns cost + heuristic
    public static int getF(int x, int y, int cost){
        int f = (getH(x, y)+getG(cost, x, y));
        return f;
    }


    public static int cost = 0;

    //A* algorithm
    public static void aStar(){

        int rows = mazePlan.length-1;
        int cols = rows;

        //list of visited cells
        boolean [][] visited = new boolean[rows][cols];

        //priority queue of open cells
        //pushes priority of lowest f value
        PriorityQueue<int[]> heap = new PriorityQueue<>(5, Comparator.comparingInt(a -> ((int[]) a)[0]));

       //keeps the x, y and x parent, y parent so the program can trace back later
        Stack <int[]> fistPath = new Stack<>();

        int x = pacmanX;
        int y = pacmanY;

        //start position
        int [] start = {0, x, y, x, y};
        heap.add(start);
        fistPath.add(start);
        visited[x][y] = true;

        //while open list is not empty
        while (!heap.isEmpty()){

            //poll lowest f value in open list
            int [] tempArr = heap.poll();

            //adds to path
            fistPath.add(tempArr);

            int newX = tempArr[1];
            int newY = tempArr[2];

            //win condition
            if( newX == ghostX && newY == ghostY) {
                //ends loop and print win
                System.out.println("win");
                heap.clear();
                break;
            }




            /*
            i had to do this redundant if statements due to the ghosts
            if the ghost is in a location blocked off by pirates or stripes these if statements will make the value of
            going through pirates/ stripes significantly higher than just a normal power pellet
            it is essentially a fail-safe if and only if the ghost is trapped
            ideally pacman will never go through a pirate/stripe due to high cost(g value)
             */

            int tempcost = 0;

            //checks if inbounds for north child
            if ( inbounds(newX + 1, newY)){
                //checks if visited
                if(!visited[newX+1][newY]){
                    //checks if not wall
                    if ( openSpace(newX + 1, newY)){
                        int [] n = { getF(newX, newY, cost), newX + 1, newY, newX , newY};
                        heap.add(n);
                        visited[n[1]][n[2]] = true;
                    }
                    else if ( pirate(newX + 1, newY)){
                        tempcost = cost + 10000;
                        int [] n = { getF(newX, newY, tempcost), newX + 1, newY, newX , newY};
                        heap.add(n);
                        visited[n[1]][n[2]] = true;
                    }
                    else if ( stripes(newX + 1, newY)){
                        tempcost = cost + 1000;
                        int [] n = { getF(newX, newY, tempcost), newX + 1, newY, newX , newY};
                        heap.add(n);
                        visited[n[1]][n[2]] = true;
                    }
                    else{}

                }
            }

            //checks if inbounds for east child
            if ( inbounds(newX, newY + 1)){
                //checks if visited
                if(!visited[newX][newY + 1]){
                    //checks if not wall
                    if ( openSpace(newX, newY + 1)){
                        int [] e = { getF(newX, newY, cost), newX, newY + 1, newX , newY};
                        heap.add(e);
                        visited[e[1]][e[2]] = true;
                    }
                    else if ( pirate(newX, newY + 1)){
                        tempcost = cost + 10000;
                        int [] e = { getF(newX, newY, tempcost), newX, newY + 1, newX , newY};
                        heap.add(e);
                        visited[e[1]][e[2]] = true;
                    }
                    else if ( stripes(newX, newY + 1)){
                        tempcost = cost + 1000;
                        int [] e = { getF(newX, newY, tempcost), newX, newY + 1, newX , newY};
                        heap.add(e);
                        visited[e[1]][e[2]] = true;
                    }
                    else{}

                }
            }

            //checks if inbounds for north child
            if ( inbounds(newX - 1, newY)){
                //checks if visited
                if(!visited[newX-1][newY]){
                    //checks if not wall
                    if ( openSpace(newX - 1, newY)){
                        int [] s = { getF(newX, newY, cost), newX - 1, newY, newX , newY};
                        heap.add(s);
                        visited[s[1]][s[2]] = true;
                    }
                    else if ( pirate(newX - 1, newY)){
                        tempcost = cost + 10000;
                        int [] s = { getF(newX, newY, tempcost), newX - 1, newY, newX , newY};
                        heap.add(s);
                        visited[s[1]][s[2]] = true;
                    }
                    else if ( stripes(newX - 1, newY)){
                        tempcost = cost + 1000;
                        int [] s = { getF(newX, newY, tempcost), newX - 1, newY, newX , newY};
                        heap.add(s);
                        visited[s[1]][s[2]] = true;
                    }
                    else{}

                }
            }

            //checks if inbounds for east child
            if ( inbounds(newX, newY - 1)){
                //checks if visited
                if(!visited[newX][newY - 1]){
                    //checks if not wall
                    if ( openSpace(newX, newY - 1)){
                        int [] w = { getF(newX, newY, cost), newX, newY - 1, newX , newY};
                        heap.add(w);
                        visited[w[1]][w[2]] = true;
                    }
                    else if ( pirate(newX, newY - 1)){
                        tempcost = cost + 10000;
                        int [] w = { getF(newX, newY, tempcost), newX, newY - 1, newX , newY};
                        heap.add(w);
                        visited[w[1]][w[2]] = true;
                    }
                    else if ( stripes(newX, newY - 1)){
                        tempcost = cost + 1000;
                        int [] w = { getF(newX, newY, tempcost), newX, newY - 1, newX , newY};
                        heap.add(w);
                        visited[w[1]][w[2]] = true;
                    }
                    else{}

                }
            }


            //chenges x and y to the x and y coordinates where pacman is now
            x = newX;
            y = newY;

            //increases cost
            cost += 10;


        }


        //new statck filled with parent values
        Stack <int[]> finalPath = new Stack<>();


        //last value in fistPath added to shortestPathPoints (ghost location)
        int [] shortestPathPoints = fistPath.pop();


        //while fistPath is not empty loop will run
        while(!fistPath.isEmpty()){

            //add parent values to finalPoints
            int [] finalPoints = {shortestPathPoints[1], shortestPathPoints[2]};

            //creating parent array
            int [] parent = new int[5];
            finalPath.add(finalPoints);

            //turns false when found parent node
            boolean key = true;

            //loop pops fistPath till it finds the parent node if shortest path
            while(!fistPath.isEmpty() || key){
                if (parent[1] == shortestPathPoints[3] && parent[2] == shortestPathPoints[4]){

                    int [] finalPoints2 = {parent[1], parent[2]};
                    finalPath.add(finalPoints2);
                    shortestPathPoints = parent;
                    key = false;
                }
                parent= fistPath.pop();

            }




        }

        //moves pacman to ghost using the shortest path
        while (!finalPath.isEmpty()){
            int [] move = finalPath.pop();
            movePacman(move[0], move[1]);
            wait(200);
            removePacman(move[0], move[1]);
        }
    }


    public static void main(String [] args)
    {


        //*** create a new frame and make it visible
        Maze mz = new Maze();
        mz.setVisible(true);


        //*** Pacman's current board position
        int gbx = pacmanX, gby = pacmanY;



         //*** exhaustively search all open spaces one row at a time
        /*
         for (gbx = 1; gbx < mazePlan.length - 1; gbx++)
         for (gby = 1; gby < mazePlan.length - 1; gby++)

         if (openSpace(gbx, gby)) {
         //*** move Pacman to new location (gbx, gby)
         movePacman(gbx, gby);


         //*** delay updating the screen
         //*** change this parameter as you wish
         wait(200);


         //*** remove Pacman from location (gbx, gby)
         removePacman(gbx, gby);
         }

              */


        //runs A* algorithm and displays the shortest path to ghost
        aStar();


    } // main

} // Maze

