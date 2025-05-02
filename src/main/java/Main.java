import java.util.List;
import java.util.Random;

/**
 * Very small demonstration program: two random‑move AIs
 * play Guard & Towers against each other until the game is over.
 */
public class Main {

    /** One shared RNG instance – good enough for a toy example. */
    private static final Random RNG = new Random();

    public static void main(String[] args) {
        /* ---------- initial position ---------- */
        Piece[][] board = BoardInitializer.createStartingPosition();
        boolean redToMove = true;               // red starts (top half)
        int fullMoveNumber = 1;                 // 1 … 2 … 3 … (like in chess)

        System.out.println("Initial position:\n" + BoardInitializer.toString(board));

        /* ---------- game loop ---------- */
        while (!VictoryChecker.isGameOver(board)) {
            // Generate every legal move for the side to move
            List<Move> moves = GuardTowersMoveGenerator.generateMoves(board, redToMove);

            // No legal move → stalemate (extremely rare with random play)
            if (moves.isEmpty()) {
                System.out.println((redToMove ? "Red" : "Blue") + " has no legal moves – stalemate.");
                break;
            }

            // Pick one at random and execute it
            Move chosen = moves.get(RNG.nextInt(moves.size()));
            applyMove(board, chosen);

            // Pretty print: 1. Red a7a6 … 1… Blue d1d2 …
            String plyPrefix = redToMove ? fullMoveNumber + ". " : fullMoveNumber + "… ";
            System.out.printf("%s%s %s%n", plyPrefix, redToMove ? "Red" : "Blue", chosen.toAlgebraic());

            // update counters & switch side
            if (!redToMove) fullMoveNumber++;
            redToMove = !redToMove;
        }

        /* ---------- finished ---------- */
        System.out.println("\nFinal board:\n" + BoardInitializer.toString(board));
        System.out.println("Game over – winner: " + VictoryChecker.checkWinner(board));
    }

    /**
     * Apply {@code m} to {@code board}. Minimalistic state update that supports
     *   – moving whole guards and towers,
     *   – unstacking (moving part of a tower),
     *   – stacking towers of the same colour, and
     *   – capturing legal enemy pieces.
     * It relies on the move generator to guarantee legality.
     */
    private static void applyMove(Piece[][] board, Move m) {
        Piece origin = board[m.fromRow][m.fromCol];
        if (origin == null) throw new IllegalStateException("No piece on the origin square!");

        /* --- split off the moving part (for tower unstacking) --- */
        Piece moving;
        if (origin.getType() == Piece.Type.TOWER && origin.getHeight() > m.moveHeight) {
            // leave the remainder behind as a new (smaller) tower
            int remaining = origin.getHeight() - m.moveHeight;
            board[m.fromRow][m.fromCol] = new Piece(Piece.Type.TOWER, remaining, origin.isRed());
            moving = new Piece(Piece.Type.TOWER, m.moveHeight, origin.isRed());
        } else {
            // guard move or full‑tower move: empty origin square afterwards
            board[m.fromRow][m.fromCol] = null;
            moving = origin;
        }

        /* --- destination square --- */
        Piece target = board[m.toRow][m.toCol];
        if (target == null) {
            // simple move
            board[m.toRow][m.toCol] = moving;

        } else if (target.isRed() == moving.isRed()) {
            // stacking onto a friendly tower (legality already checked)
            if (target.getType() != Piece.Type.TOWER)
                throw new IllegalStateException("Cannot stack onto a non‑tower");
            target.increaseHeight(moving.getHeight());
            board[m.toRow][m.toCol] = target; // height already updated

        } else {
            // capture → replace enemy piece with the moving one
            board[m.toRow][m.toCol] = moving;
        }
    }
}

