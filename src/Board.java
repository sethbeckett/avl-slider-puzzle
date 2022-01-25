import java.util.Arrays;
import java.util.Random;

public class Board implements Comparable<Board>{

    private static final int SIZE = 3;  // Number of rows and columns of board
    private int[][] board;  // Values of board.  0 represents the blank value.
    private int blankRow;   // Row location of blank
    private int blankCol;   // Column location of blank
    private int costSoFar; //how much it cost to get to that point
    private int estimatedCost; //estimate of the cost to get to solution board
    int priority; // Priority of Board: how close it is to a perfect board
    public String label;    //Name of Board
    public String moveHistory;// a history of the moves a board has made, added to by solver class
    public char lastMove; //move to keep track of the last move a board made (also check solver)

    /**
     * Construct a empty board.
     */
    public Board() {
        lastMove = ' ';
        moveHistory = "";
        board = new int[SIZE][SIZE];
        costSoFar = 0;
    }

    /**
     *   b: input board to copy
     *   A Deep Copy constructor (so original board in separate
     */
    Board(Board b) {
        board = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = b.board[i][j];
            }
        this.blankRow = b.blankRow;
        this.blankCol = b.blankCol;
        this.moveHistory = b.moveHistory;
        this.label = b.label;
        this.lastMove = b.lastMove;
        this.priority = b.priority;
        this.costSoFar = b.costSoFar;
        this.estimatedCost = b.estimatedCost;
    }

    /**
     *
     * @return String representing board
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
            sb.append(this.label + " " + moveHistory + "\n");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                sb.append(board[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    /**used to put the board into a string
     * @param sb: stringbuilder object
     * @return board filled stringbuilder object
     */
//    private String getString(StringBuilder sb) {
//        for (int i = 0; i < SIZE; i++) {
//            for (int j = 0; j < SIZE; j++) {
//                sb.append(board[i][j]);
//                sb.append(" ");
//            }
//            sb.append("\n");
//        }
//        return sb.toString();
//    }

    ;

    /**
     *
     * @param b board to check for equality
     * @return true if provided board and class board are identical
     */
    boolean equals(Board b) {

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (board[i][j] != b.board[i][j]) return false;
        return true;

    }


    /**
     * Create a board by performing legal moves on a board
     * @param jumbleCt indicates the number of moves to make
     *   if jumbleCt ==0, create the winning board
     * @param label name of board
     */
    void makeBoard(int jumbleCt, String label) {
        int val = 1;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                board[i][j] = val++;
        blankRow = SIZE - 1;
        blankCol = SIZE - 1;
        board[blankRow][blankCol] = 0;
        jumble(jumbleCt);
        this.label = label;
    }

    /**
     *
     * @param values create a board from a given set of values given by row
     * @param label label to attach to board
     */
    void makeBoard(int[] values, String label) {
        this.label = label;
        int c = 0;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                if (values[c] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
                board[i][j] = values[c++];
            }
    }

    /**
     *  // If possible, slides a tile up into the blank position.
     * @return  success of operation.
     */
    boolean slideUp()
    {
        if (blankRow == SIZE - 1) return false;
        board[blankRow][blankCol] = board[blankRow + 1][blankCol];
        board[blankRow + 1][blankCol] = 0;
        blankRow += 1;
        return true;
    }
    /**
     *  // If possible, slides a tile down into the blank position.
     * @return  success of operation.
     */
    boolean slideDown()
    {
        if (blankRow == 0) return false;
        board[blankRow][blankCol] = board[blankRow - 1][blankCol];
        board[blankRow - 1][blankCol] = 0;
        blankRow -= 1;
        return true;
    }
    /**
     *  // If possible, slides a tile left  into the blank position.
     * @return  success of operation.
     */
    boolean slideLeft()
    {
        if (blankCol == SIZE - 1) return false;
        board[blankRow][blankCol] = board[blankRow][blankCol + 1];
        board[blankRow][blankCol + 1] = 0;
        blankCol += 1;
        return true;
    }
    /**
     *  // If possible, slides a tile right into the blank position.
     * @return  success of operation.
     */
    boolean slideRight()
    {
        if (blankCol == 0) return false;
        board[blankRow][blankCol] = board[blankRow][blankCol - 1];
        board[blankRow][blankCol - 1] = 0;
        blankCol -= 1;
        return true;
    }

    /**
     * Randomly apply ct moves to the board, makingsure they are legal and don't undo the previous move
     * @param ct Number of random moves to make
     */
    void jumble(int ct) {
        Random rand = new Random();
        String moveStr = "UDLR";  // Moves representing Up, Down, Left, Right
        char lastMove = ' ';
        char thisMove = ' ';
        for (int i = 0; i < ct; i++) {
            thisMove = ' ';
            while (thisMove == ' ') {
                thisMove = moveStr.charAt(rand.nextInt(4));
                thisMove = makeMove(thisMove, lastMove);
            }
            lastMove = thisMove;
            //System.out.println("JUMBLE Moves" + thisMove + '\n');
        }
    }

    /**
     *
     * @param m symbol representing which move to make  (L Left, R Right, U Up, D Down)
     * @param lastMove Symbol representing last move to be made
     * @return if it is legal and if it doesn't undo the move specified by lastMove
     * // Return a blank if the move could not be made, otherwise return the move
     */
    char makeMove(char m, char lastMove) {
        //System.out.println( "makeMove " + m + lastMove +"\n");
        boolean moved = false;
        switch (m) {
            case 'R':
                if (lastMove != 'L') {
                    moved = slideRight();
                }
                break;
            case 'L':
                if (lastMove != 'R') {
                    moved = slideLeft();
                }
                break;
            case 'D':
                if (lastMove != 'U') {
                    moved = slideDown();
                }
                break;
            case 'U':
                if (lastMove != 'D') {
                    moved = slideUp();
                }
                break;
        }
        if (!moved)
            return ' ';
        this.costSoFar++;
        this.updateEstimatedCost();
        this.updatePriority();
        return m;
    }

    /**takes moves to make and presents the board at each step
     * @param moves: a string of letters signifying which slides to make
     */
    public void showMe(String moves) {
        System.out.println("Original Board\n" + this.toString());
        int movesLength = moves.length(); //number of moves to be made
        for (int i = 0; i < movesLength; i++) {
            char currentMove = moves.charAt(i);
            this.makeMove(currentMove, ' '); //using built-in makeMove function
            System.out.println(currentMove + "==>\n" + this.toString());
        }
    }

    /**
     * if the priority of one board is lower than another, than that board is < the other
     * @param b board to compare this board to
     * @return -1,0,1 depending on the board comparison
     */
    @Override
    public int compareTo(Board b) {
        if (this.priority < b.priority) return -1;
        else if (this.priority > b.priority) return 1;
        else return 0;
    }

    /**
     * updates estimatedCost based on how many tiles are out of place
     */
    private void updateEstimatedCost() {
        this.estimatedCost = 0;
//        System.out.println(this.toString());
        int val = 1;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
//                System.out.println("This is what the board at i j is: " + this.board[i][j]);
                if (this.board[i][j] != val) estimatedCost++;
                val++;
            }

    }

    private void updatePriority() {
        this.priority = costSoFar + estimatedCost;
    }

    public static void main(String[] args) {
        Board b = new Board();
        int[] values = {1,2,3,4,5,6,7,8,0};
        Board c = new Board();
        c.makeBoard(values, "Original Board");
        b.makeBoard(2, "Random Board 2");
        System.out.println(c);
        System.out.println(b);

        System.out.println("Random Board Stats\nEstimated Cost: " + b.estimatedCost);
        System.out.println("Current Cost: " + b.costSoFar);
        System.out.println("Priority: " + b.priority);


        //testing showMe
//        System.out.println("Testing showMe method on Original board with moves DDRR");
//        c.showMe("DDRRULUR");

    }
}
