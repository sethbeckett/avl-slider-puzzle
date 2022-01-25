/**
 * @author Seth Beckett
 */
public class AStarSolver {
    public static Board SOLUTION_BOARD = new Board();
    public static final String MOVE_STRING = "UDLR";

    /**instantiates a solver object and instantiates KEY_BOARD correctly
     *
     */
    AStarSolver(){
        SOLUTION_BOARD.makeBoard(0, "Key Board");
    }

    /**solves input board originalBoard
     *
     * @param originalBoard:
     */
    public void solveBoard(Board originalBoard) {
        AVLTree<Board> avlTree = new AVLTree<>();
        boolean isSolved = originalBoard.equals(SOLUTION_BOARD);
        avlTree.insert(originalBoard);

        while (!avlTree.isEmpty() && !isSolved) {
            Board currentState = avlTree.findMin(); //assigns the min to the currentState
            avlTree.deleteMin(); //deletes the min

            //run through each possible move
            for (int k = 0; k < MOVE_STRING.length(); k++) {
                Board successor = new Board(currentState); //creates parent board to make moves on
                char move = MOVE_STRING.charAt(k);

                //See if successor board can make a move in direction k and add the move data if successful
                if (successor.makeMove(MOVE_STRING.charAt(k), currentState.lastMove) == move) {

                    //for testing
//                    System.out.println("Successor Priority: " + successor.priority);

                    successor.lastMove = move;
                    successor.moveHistory += move; //adds to history if move successful

                    if (successor.equals(SOLUTION_BOARD)) {
                        isSolved = true;
                        System.out.println("--------------------\nA* Method \n--------------------");

                        System.out.println(successor);
//                        originalBoard.showMe(successor.moveHistory); //print the winning board succession
                        System.out.printf("Moves Required: %d\n", successor.moveHistory.length());
                        avlTree.printQueueInfo();
                        break;
                    }
                    else {
                        avlTree.insert(successor);
                    }

                }
            }
        }

    }
}
