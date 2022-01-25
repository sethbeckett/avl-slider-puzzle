import java.util.Scanner;
public class Game {

    /**
     * Solves using the brute force method
     * @param b : The input board to solve
     */
    public void playByBruteForce(  Board b){
        BruteForceSolver s = new BruteForceSolver();
        s.solveBoard(b); //solves the input board and prints each move out
    }

    /**
     * Solves using A* method
     * @param b board to solve
     */
    public void playByAStar(  Board b){
        AStarSolver a = new AStarSolver();
        a.solveBoard(b); //solves the input board and prints each move out
    }

    /**
     *
     * @param jumbleCount: number of random moves on initial board to create a solvable board.d
     */
    public void playRandom( int jumbleCount){
        Board b = new Board();
        b.makeBoard(jumbleCount, "Random Board " + jumbleCount);
        System.out.println("\n" + b);

    }


    public static void main(String[] args) {
       Game g = new Game();
        Scanner in = new Scanner(System.in);

        //testing
//        int[] game0 = {1,2,3,4,5,6,0,7,8};
        int [] game0 = { 1, 2, 3, 7, 4, 0, 6, 5, 8 };
        Board b = new Board();
        b.makeBoard(game0, "game0");

        Board bruteForceCopy = new Board(b); //makes copy of board to use with brute force method
        Board aStarCopy = new Board(b); //makes copy of b board to use with A* method

        System.out.println("Original Board " + "\n" + b);
        g.playByBruteForce(bruteForceCopy);
        System.out.println();
        g.playByAStar(aStarCopy);
        System.out.println("Click any key to continue\n");
        String resp;
        resp= in.nextLine();

        int []game1 = { 1, 3, 2, 4, 5, 6, 8, 7, 0 };
        bruteForceCopy.makeBoard(game1,"game 1");
        System.out.println("Original Board " + "\n" + bruteForceCopy);

        g.playByBruteForce( bruteForceCopy);
        aStarCopy.makeBoard(game1, "game 1");
        g.playByAStar(aStarCopy);
        System.out.println("Click any key to continue\n");
        resp= in.nextLine();

        int []game2 = { 1, 3, 8, 6, 2, 0, 5, 4, 7 };
        bruteForceCopy.makeBoard(game1,"game 2");
        g.playByBruteForce( bruteForceCopy);
        aStarCopy.makeBoard(game1, "game 2");
        g.playByAStar(aStarCopy);
        System.out.println("Click any key to continue\n");
        resp= in.nextLine();

        int []game3 = { 4, 0, 1, 3, 5, 2, 6, 8, 7 };
        bruteForceCopy.makeBoard(game3,"game 3");
        g.playByBruteForce( bruteForceCopy);
        aStarCopy.makeBoard(game3, "game 3");
        g.playByAStar(aStarCopy);
        System.out.println("Click any key to continue\n");
        resp= in.nextLine();

        int []game4 = { 7, 6, 4, 0, 8, 1, 2, 3, 5 };  // Warning slow to solve
        bruteForceCopy.makeBoard(game4,"game 4");
        g.playByBruteForce( bruteForceCopy);
        aStarCopy.makeBoard(game4, "game 4");
        g.playByAStar(aStarCopy);
        System.out.println("Click any key to continue\n");
        resp= in.nextLine();

//        int []game5 = { 1, 2, 3, 4, 5, 6, 8, 7, 0 };   // Warning unsolvable
//        bruteForceCopy.makeBoard(game5,"game 5");
//        g.playByBruteForce( bruteForceCopy);
//        aStarCopy.makeBoard(game5, "game 5");
//        g.playByAStar(aStarCopy);
//        System.out.println("Click any key to continue\n");
//        resp= in.nextLine();

       boolean playAgain = true;

	   int JUMBLECT = 4;  // how much jumbling to to in random board
        while (playAgain)
        {
            g.playRandom( JUMBLECT);

            System.out.println("Play Again?  Answer Y for yes\n");
            resp= in.nextLine().toUpperCase();
            playAgain = (resp.charAt(0) == 'Y');
        }


    }


}
