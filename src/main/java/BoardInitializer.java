public class BoardInitializer {

    /**
     * Build the Guard & Towers initial layout.
     * <p>
     * Coordinates:
     * columns 0-6 == A-G (left→right)
     * rows    0-6 == 7-1 (top→bottom)               ┌─ 7 ─┐
     * …middle…
     * red (true) starts at the top (row 0);         └─ 1 ─┘
     */
    public static Piece[][] createStartingPosition() {
        Piece[][] board = new Piece[7][7];

        /* ---------- red player (top, rows 0-2) ---------- */
        // baseline row 0 – A7 B7 D7 F7 G7
        board[0][0] = new Piece(Piece.Type.TOWER, 1, true);          // A7
        board[0][1] = new Piece(Piece.Type.TOWER, 1, true);          // B7
        board[0][3] = new Piece(Piece.Type.GUARD, 1, true);         // D7 (castle)
        board[0][5] = new Piece(Piece.Type.TOWER, 1, true);          // F7
        board[0][6] = new Piece(Piece.Type.TOWER, 1, true);          // G7

        // row 1 – C6 E6
        board[1][2] = new Piece(Piece.Type.TOWER, 1, true);          // C6
        board[1][4] = new Piece(Piece.Type.TOWER, 1, true);          // E6

        // row 2 – D5
        board[2][3] = new Piece(Piece.Type.TOWER, 1, true);          // D5


        /* ---------- black player (bottom, rows 4-6) ---------- */
        // row 4 – D3
        board[4][3] = new Piece(Piece.Type.TOWER, 1, false);         // D3

        // row 5 – C2 E2
        board[5][2] = new Piece(Piece.Type.TOWER, 1, false);         // C2
        board[5][4] = new Piece(Piece.Type.TOWER, 1, false);         // E2

        // baseline row 6 – A1 B1 D1 F1 G1
        board[6][0] = new Piece(Piece.Type.TOWER, 1, false);         // A1
        board[6][1] = new Piece(Piece.Type.TOWER, 1, false);         // B1
        board[6][3] = new Piece(Piece.Type.GUARD, 1, false);        // D1 (castle)
        board[6][5] = new Piece(Piece.Type.TOWER, 1, false);         // F1
        board[6][6] = new Piece(Piece.Type.TOWER, 1, false);         // G1

        return board;
    }


    public static String toString(Piece[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0)
            return "<empty board>";

        final int rows = board.length;
        final int cols = board[0].length;
        final int CELL_W = 3;                         // ← change here if you like

        /* build one horizontal separator, e.g. "  +---+---+---+" */
        StringBuilder sep = new StringBuilder("  +");
        for (int c = 0; c < cols; c++)
            sep.append("-".repeat(CELL_W)).append('+');
        sep.append(System.lineSeparator());
        final String HLINE = sep.toString();

        StringBuilder sb = new StringBuilder();

        sb.append(HLINE);
        for (int r = 0; r < rows; r++) {
            /* rank on the left (8 … 1) */
            sb.append(rows - r).append(' ');
            for (int c = 0; c < cols; c++) {
                String sym = cellSymbol(board[r][c]);
                /* pad / trim the symbol so it fits exactly CELL_W chars */
                if (sym.length() > CELL_W)           // too long → truncate
                    sym = sym.substring(0, CELL_W);
                sb.append('|')
                        .append(String.format("%-" + CELL_W + "s", sym));
            }
            sb.append('|').append(System.lineSeparator())
                    .append(HLINE);
        }

        /* file letters underneath */
        sb.append("    ");
        for (int c = 0; c < cols; c++)
            sb.append((char) ('a' + c)).append(" ".repeat(CELL_W));
        return sb.toString();
    }

    /** One– or two‑letter piece symbols:  .  R  R3  b2  … */
    /** Convert a Piece into a fixed‑width diagram symbol. */
    private static String cellSymbol(Piece p) {
        if (p == null) return ".";
        if (p.getType() == Piece.Type.GUARD)          // --- guards ---
            return p.isRed() ? "RG" : "BG";           //  ⇐ new names
        /* towers: colour letter (R / b) plus height */
        return (p.isRed() ? 'R' : 'B') + String.valueOf(p.getHeight());
    }

}