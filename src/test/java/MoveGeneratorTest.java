import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoveGeneratorTest {

    /* ---------- position1 ---------- */
    @Test
    void movesFromFen_position1() {
        List<Move> generated = Fen.movesFromFen(
                "b36/3b12r3/7/7/1r2RG4/2BG4/6r1 b");

        List<Move> expected = List.of(
                new Move(0, 0, 1, 0, 1),
                new Move(0, 0, 2, 0, 2),
                new Move(0, 0, 3, 0, 3),
                new Move(0, 0, 0, 1, 1),
                new Move(0, 0, 0, 2, 2),
                new Move(0, 0, 0, 3, 3),
                new Move(1, 3, 0, 3, 1),
                new Move(1, 3, 2, 3, 1),
                new Move(1, 3, 1, 2, 1),
                new Move(1, 3, 1, 4, 1),
                new Move(5, 2, 4, 2, 1),
                new Move(5, 2, 6, 2, 1),
                new Move(5, 2, 5, 1, 1),
                new Move(5, 2, 5, 3, 1)
        );

        assertContainsAll(generated, expected);
    }

    /* ---------- position2 ---------- */
    @Test
    void movesFromFen_position2() {
        List<Move> generated = Fen.movesFromFen(
                "7/6r3/1RG5/3b43/1r25/7/2BG3r1 r");

        List<Move> expected = List.of(
                new Move(1, 6, 0, 6, 1),
                new Move(1, 6, 2, 6, 1),
                new Move(1, 6, 3, 6, 2),
                new Move(1, 6, 4, 6, 3),
                new Move(1, 6, 1, 5, 1),
                new Move(1, 6, 1, 4, 2),
                new Move(1, 6, 1, 3, 3),
                new Move(2, 1, 1, 1, 1),
                new Move(2, 1, 3, 1, 1),
                new Move(2, 1, 2, 0, 1),
                new Move(2, 1, 2, 2, 1),
                new Move(4, 1, 3, 1, 1),
                new Move(4, 1, 5, 1, 1),
                new Move(4, 1, 6, 1, 2),
                new Move(4, 1, 4, 0, 1),
                new Move(4, 1, 4, 2, 1),
                new Move(4, 1, 4, 3, 2),
                new Move(6, 6, 5, 6, 1),
                new Move(6, 6, 6, 5, 1)
        );

        assertContainsAll(generated, expected);
    }

    /* ---------- position3 ---------- */
    @Test
    void movesFromFen_position3() {
        List<Move> generated = Fen.movesFromFen(
                "3RG3/4r12/3r43/7/3b33/3b23/3BG3 r");

        List<Move> expected = List.of(
                new Move(0, 3, 0, 2, 1),
                new Move(0, 3, 1, 3, 1),
                new Move(0, 3, 0, 4, 1),
                new Move(1, 4, 0, 4, 1),
                new Move(1, 4, 1, 3, 1),
                new Move(1, 4, 2, 4, 1),
                new Move(1, 4, 1, 5, 1),
                new Move(2, 3, 1, 3, 1),
                new Move(2, 3, 2, 2, 1),
                new Move(2, 3, 2, 1, 2),
                new Move(2, 3, 2, 0, 3),
                new Move(2, 3, 3, 3, 1),
                new Move(2, 3, 2, 4, 1),
                new Move(2, 3, 2, 5, 2),
                new Move(2, 3, 2, 6, 3)
        );

        assertContainsAll(generated, expected);
    }

    /* ---------- position4 ---------- */
    @Test
    void movesFromFen_position4() {
        List<Move> generated = Fen.movesFromFen(
                "3RG1r1r1/2r14/3r43/7/7/2b34/1b21BG1b21 r");

        List<Move> expected = List.of(
                new Move(2, 3, 6, 3, 4),
                new Move(2, 3, 5, 3, 3),
                new Move(2, 3, 4, 3, 2),
                new Move(2, 3, 3, 3, 1),
                new Move(2, 3, 2, 4, 1),
                new Move(2, 3, 2, 5, 2),
                new Move(2, 3, 2, 6, 3),
                new Move(2, 3, 2, 2, 1),
                new Move(2, 3, 2, 1, 2),
                new Move(2, 3, 2, 0, 3),
                new Move(2, 3, 1, 3, 1),
                new Move(1, 2, 2, 2, 1),
                new Move(1, 2, 1, 1, 1),
                new Move(1, 2, 0, 2, 1),
                new Move(1, 2, 1, 3, 1),
                new Move(0, 3, 1, 3, 1),
                new Move(0, 3, 0, 2, 1),
                new Move(0, 3, 0, 4, 1),
                new Move(0, 5, 0, 4, 1),
                new Move(0, 5, 1, 5, 1),
                new Move(0, 5, 0, 6, 1),
                new Move(0, 6, 0, 5, 1),
                new Move(0, 6, 1, 6, 1)
        );

        assertContainsAll(generated, expected);
    }

    /* ---------- position5 ---------- */
    @Test
    void movesFromFen_position5() {
        List<Move> generated = Fen.movesFromFen(
                "3RG3/7/3r73/7/3b73/7/3BG3 r");

        List<Move> expected = List.of(
                new Move(2, 3, 2, 2, 1),
                new Move(2, 3, 2, 1, 2),
                new Move(2, 3, 2, 0, 3),
                new Move(2, 3, 1, 3, 1),
                new Move(2, 3, 2, 4, 1),
                new Move(2, 3, 2, 5, 2),
                new Move(2, 3, 2, 6, 3),
                new Move(2, 3, 3, 3, 1),
                new Move(0, 3, 0, 2, 1),
                new Move(0, 3, 0, 4, 1),
                new Move(0, 3, 1, 3, 1)
        );

        assertContainsAll(generated, expected);
    }

    /* ---------- helper ---------- */
    private static void assertContainsAll(List<Move> generated, List<Move> expected) {
        assertTrue(
                generated.containsAll(expected),
                "Generated move list is missing at least one expected move");
    }
}
