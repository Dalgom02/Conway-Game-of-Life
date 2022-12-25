package conwaygame;
import java.util.ArrayList;
import java.util.concurrent.Future;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {
        StdIn.setFile(file);
        int r = StdIn.readInt(); //row
        int c = StdIn.readInt(); //col
        
        grid = new boolean[r][c]; // boolean grid
        
        while (!StdIn.isEmpty())
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if(StdIn.hasNextLine())
                grid[i][j] = StdIn.readBoolean();
            }
        }

        

    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {
        
                
         if(grid[row][col] == DEAD) return false;
         else if(grid[row][col] == ALIVE) return true;
                
            
            return true;
         // update this line, provided so that code compiles
    }

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {
       
        for (int i = 0; i < grid.length; i++) 
        {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == ALIVE){
                    return true;
                }
            }
        }

        return false;
    
}

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {
        // WRITE YOUR CODE HERE
       int count = 0;
    if(grid[(row-1 + grid.length) % grid.length][(col-1 + grid[0].length) %grid[0].length] == ALIVE){
        count++;
    }
    if(grid[(row + grid.length) % grid.length][(col-1 + grid[0].length)% grid[0].length] == ALIVE){
        count++;
    }
    if(grid[(row+1 + grid.length)  % grid.length][(col-1 +grid[0].length) % grid[0].length] == ALIVE){
        count++;
    }
    if(grid[(row+1 + grid.length) % grid.length][(col+grid[0].length)%grid[0].length] == ALIVE){
        count++;
    }
    if(grid[(row+1 + grid.length)  % grid.length][(col+1 +grid[0].length) %grid[0].length] == ALIVE){
        count++;
    }
    if(grid[(row + grid.length) % grid.length][(col+1 + grid[0].length)%grid[0].length] == ALIVE){
        count++;
    }
    if(grid[(row-1 + grid.length)% grid.length][(col+1 + grid[0].length) % grid[0].length] == ALIVE){
        count++;
    }
    if(grid[(row-1 + grid.length)% grid.length][(col+grid[0].length)%grid[0].length] == ALIVE){
        count++;
    }

        return count; // update this line, provided so that code compiles
    }

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () {

        boolean[][] future = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) 
        {
            for (int j = 0; j < grid[0].length; j++) {

                //lonely
                if((grid[i][j] == ALIVE) && (numOfAliveNeighbors(i,j) < 2))
                future[i][j] = DEAD;
                //overpop
                else if ((grid[i][j] == ALIVE) && (numOfAliveNeighbors(i,j) > 3))
                future[i][j] = DEAD;
                //new cell is born
                else if((grid[i][j] == DEAD) && (numOfAliveNeighbors(i,j) == 3))
                future[i][j] = ALIVE;
                //live to next gen
                else
                future[i][j] = grid[i][j];

            }
        }
        // WRITE YOUR CODE HERE
        return future;// update this line, provided so that code compiles
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {
        grid = computeNewGrid();
        int alive = 0;
        for(int i =0; i<grid.length; i++){
            for(int j =0; j<grid[0].length; j++){
                if(grid[i][j]) alive++;
            }
        }
        totalAliveCells = alive;
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {
        for(int i = 0; i<n; i++){
            nextGeneration();
        }
    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {

        // WRITE YOUR CODE HERE
        int r = grid.length;
        int c = grid[0].length;

        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(r, c);

        for(int row = 0; row<r; row++){
            for(int col =0; col<c; col++){
                    //if dead do nothing
                if(grid[row][col] == DEAD)
                continue;
                
                if(grid[(row+1 + grid.length) % grid.length][(col+grid[0].length)%grid[0].length] == ALIVE)
                    uf.union((row + grid.length)%grid.length,(col+grid[0].length)%grid[0].length,(row+1 + grid.length) % grid.length,(col+grid[0].length)%grid[0].length);
                if(grid[(row-1 + grid.length)% grid.length][(col+grid[0].length)%grid[0].length] == ALIVE)
                    uf.union((row + grid.length)%grid.length,(col+grid[0].length)%grid[0].length,(row-1 + grid.length)% grid.length,(col+grid[0].length)%grid[0].length);
                if(grid[(row + grid.length) % grid.length][(col+1 + grid[0].length)%grid[0].length] == ALIVE)
                   uf.union((row + grid.length)%grid.length ,(col+grid[0].length)%grid[0].length,(row + grid.length) % grid.length ,(col+1 + grid[0].length)%grid[0].length);
                if(grid[(row + grid.length) % grid.length][(col-1 + grid[0].length)% grid[0].length] == ALIVE)
                    uf.union((row + grid.length)%grid.length ,(col+grid[0].length)%grid[0].length,(row + grid.length) % grid.length ,(col-1 + grid[0].length)% grid[0].length);
                if(grid[(row+1 + grid.length)  % grid.length][(col+1 +grid[0].length) %grid[0].length] == ALIVE)
                    uf.union((row + grid.length)%grid.length ,(col+grid[0].length)%grid[0].length,(row+1 + grid.length)  % grid.length ,(col+1 +grid[0].length) %grid[0].length);
                if(grid[(row+1 + grid.length)  % grid.length][(col-1 +grid[0].length) % grid[0].length] == ALIVE)
                    uf.union((row + grid.length)%grid.length ,(col+grid[0].length)%grid[0].length,(row+1 + grid.length)  % grid.length ,(col-1 +grid[0].length) % grid[0].length);
                if(grid[(row-1 + grid.length)% grid.length][(col+1 + grid[0].length) % grid[0].length] == ALIVE)
                    uf.union((row + grid.length)%grid.length ,(col+grid[0].length)%grid[0].length,(row-1 + grid.length)% grid.length,(col+1 + grid[0].length) % grid[0].length);
                if(grid[(row-1 + grid.length) % grid.length][(col-1 + grid[0].length) %grid[0].length] == ALIVE)
                    uf.union((row + grid.length)%grid.length ,(col+grid[0].length)%grid[0].length,((row-1) + grid.length) % grid.length,((col-1) + grid[0].length) % grid[0].length);
            }
        }

            int[] x = new int[r*c];
            int communities = 0;

            for(int j = 0; j<r; j++){
                for(int k =0; k<c; k++){
               if(grid[j][k] == ALIVE){
                    int z = uf.find(j, k);
                    if (x[z] == 0){
                        communities++;
                        x[z]++;
                    }  else 
                        x[z]++;

                       }
                    
                }
            }




        return communities; // update this line, provided so that code compiles

    }
}
