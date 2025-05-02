import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Static helpers for Guard & Towers FEN notation. */
public final class Fen {

    private static final int BOARD_SIZE = 7;                // 7 × 7 board
    private static final int ROW_COUNT  = BOARD_SIZE;

    private Fen() { }                                       // utility class

    /* ------------------------------------------------------------------ */
    /** Return every legal move for the side to move that the FEN encodes. */
    public static List<Move> movesFromFen(String fen) {
        Objects.requireNonNull(fen, "fen must not be null");

        String[] parts = fen.trim().split("\\s+");
        if (parts.length == 0) {
            throw new IllegalArgumentException("Empty FEN");
        }
        Piece[][] board = parseBoard(parts[0]);
        boolean redToMove = parts.length < 2 || parts[1].equalsIgnoreCase("r");

        return GuardTowersMoveGenerator.generateMoves(board, redToMove);
    }

    /* ------------------------------------------------------------------ */
    /** Parse only the board part (the text in front of the first blank). */
    public static Piece[][] parseBoard(String boardPart) {
        String[] rows = boardPart.split("/");
        if (rows.length != ROW_COUNT) {
            throw new IllegalArgumentException(
                    "Expected " + ROW_COUNT + " ranks but got " + rows.length);
        }

        Piece[][] board = new Piece[BOARD_SIZE][BOARD_SIZE];

        for (int r = 0; r < ROW_COUNT; r++) {
            String row = rows[r];
            int c = 0;                                      // current file
            int i = 0;                                      // position in row string

            while (i < row.length()) {
                char ch = row.charAt(i);

                /* ---------- red / blue tower ------------------------------------ */
                if (ch == 'r' || ch == 'b') {
                    boolean isRed = ch == 'r';
                    if (i + 1 >= row.length() || !Character.isDigit(row.charAt(i + 1)))
                        throw new IllegalArgumentException("Tower without height at row " + r);

                    int height = row.charAt(i + 1) - '0';   // one‑digit tower heights (1‑7)
                    board[r][c++] = new Piece(Piece.Type.TOWER, height, isRed);
                    i += 2;
                    continue;
                }

                /* ---------- red / blue guard ------------------------------------ */
                if (ch == 'R' || ch == 'B') {
                    if (i + 1 >= row.length() || row.charAt(i + 1) != 'G')
                        throw new IllegalArgumentException("Guard token malformed at row " + r);

                    boolean isRed = ch == 'R';
                    board[r][c++] = new Piece(Piece.Type.GUARD, 1, isRed);
                    i += 2;
                    continue;
                }

                /* ---------- empty squares --------------------------------------- */
                if (Character.isDigit(ch)) {
                    int empties = ch - '0';                 // 1–7 empty cells
                    c += empties;
                    i++;
                    continue;
                }

                throw new IllegalArgumentException("Unexpected character '" + ch + '\'');
            }

            if (c != BOARD_SIZE) {
                throw new IllegalArgumentException(
                        "Rank " + r + " has " + c + " squares instead of " + BOARD_SIZE);
            }
        }
        return board;
    }
}
