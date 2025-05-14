import java.util.List;

/** Perft / iterative Tiefensuche für Guard & Towers. */
public final class Perft {

    /** Tiefe-für-Tiefe die Knotenzahl ausgeben. */
    public static void perft(String fen, int maxDepth) {
        // --- FEN in Board + SideToMove auflösen --------------------------
        String[] parts   = fen.trim().split("\\s+");
        Piece[][] board  = Fen.parseBoard(parts[0]);               // :contentReference[oaicite:0]{index=0}:contentReference[oaicite:1]{index=1}
        boolean redTurn = parts.length < 2 || parts[1].equalsIgnoreCase("r");

        for (int depth = 1; depth <= maxDepth; depth++) {
            long nodes = perft(board, redTurn, depth);
            System.out.printf("Depth %d: %,d nodes%n", depth, nodes);
        }
    }

    /* ==================================================================== */

    /** Rekursive Tiefensuche (DFS). Gibt die Blattknoten-Anzahl zurück. */
    private static long perft(Piece[][] board, boolean redToMove, int depth) {
        if (depth == 0 || VictoryChecker.isGameOver(board))
            return 1;

        List<Move> moves = GuardTowersMoveGenerator.generateMoves(board, redToMove);   // :contentReference[oaicite:2]{index=2}:contentReference[oaicite:3]{index=3}
        if (moves.isEmpty()) return 1;           // Patt / Game-Over als 1 Blatt zählen

        long nodes = 0;
        for (Move m : moves) {
            Piece[][] next = copyBoard(board);   // billige Deep-Copy
            applyMove(next, m);                  // eigene Version, s.u.
            nodes += perft(next, !redToMove, depth - 1);
        }
        return nodes;
    }

    /* ---------- Hilfsfunktionen ---------------------------------------- */

    /** Tiefe Kopie des 7×7-Arrays – nur 49 Einträge, daher schnell genug. */
    private static Piece[][] copyBoard(Piece[][] original) {
        Piece[][] copy = new Piece[original.length][original[0].length];
        for (int r = 0; r < original.length; r++) {
            for (int c = 0; c < original[r].length; c++) {
                Piece p = original[r][c];
                copy[r][c] = (p == null) ? null : p.copy();
            }
        }
        return copy;
    }

    /** Minimaler Zugausführer (abgekupfert aus Main.applyMove). */
    private static void applyMove(Piece[][] board, Move m) {      // :contentReference[oaicite:4]{index=4}:contentReference[oaicite:5]{index=5}
        Piece origin = board[m.fromRow][m.fromCol];
        if (origin.getType() == Piece.Type.TOWER && origin.getHeight() > m.moveHeight) {
            board[m.fromRow][m.fromCol] =
                    new Piece(Piece.Type.TOWER, origin.getHeight() - m.moveHeight, origin.isRed());
            origin = new Piece(Piece.Type.TOWER, m.moveHeight, origin.isRed());
        } else {
            board[m.fromRow][m.fromCol] = null;
        }

        Piece target = board[m.toRow][m.toCol];
        if (target == null) {
            board[m.toRow][m.toCol] = origin;                // freies Feld
        } else if (target.isRed() == origin.isRed()) {       // Stapeln
            target.increaseHeight(origin.getHeight());
        } else {                                             // Schlag
            board[m.toRow][m.toCol] = origin;
        }
    }

    // keine Instanz erlaubt
    private Perft() { }
}