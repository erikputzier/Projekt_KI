import java.util.List;

public class GenerateMovesBenchmark {

    private static final String FEN =
            "7/6r3/1RG5/3b43/1r25/7/2BG3r1 r";   // ← position to test

    public static void main(String[] args) {
        /* -----------------------------------------------------------
         * 1.  Re‑create the board and side‑to‑move from the FEN
         * ----------------------------------------------------------- */
        String[] parts  = FEN.split("\\s+");        // board + “r|b”
        Piece[][] board = Fen.parseBoard(parts[0]); // your helper from earlier
        boolean redTurn = parts.length < 2 || parts[1].equals("r");

        /* -----------------------------------------------------------
         * 2.  Warm‑up (gives the JIT a chance to optimise the method)
         * ----------------------------------------------------------- */
        long checksum = 0;                          // stops dead‑code elimination
        for (int i = 0; i < 2_000; i++) {
            checksum += GuardTowersMoveGenerator.generateMoves(board, redTurn).size();
        }

        /* -----------------------------------------------------------
         * 3.  Timed run: 10 000 calls
         * ----------------------------------------------------------- */
        final int ITERATIONS = 10_000;
        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            /*  The method must not modify the board. If it does,
             *  re‑create board inside the loop instead. */
            List<Move> moves = GuardTowersMoveGenerator.generateMoves(board, redTurn);
            checksum       += moves.size();         // again, keep the result “alive”
        }
        long elapsed = System.nanoTime() - start;

        /* -----------------------------------------------------------
         * 4.  Report
         * ----------------------------------------------------------- */
        double millis = elapsed / 1_000_000.0;
        System.out.printf(
                "generateMoves() × %,d took %.3fms  (%.3fµs per call)%n",
                ITERATIONS, millis, (elapsed / 1_000.0) / ITERATIONS);
        System.out.println("checksum = " + checksum); // prevents optimisation
    }
}

