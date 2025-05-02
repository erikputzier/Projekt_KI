import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
public class MoveGeneratorTest {

    @Test
    public void moveGeneratorTest() {

        List<Move> generated = Fen.movesFromFen("b36/3b12r3/7/7/1r2RG4/2BG4/6r1 b");
        List<Move> actual = List.of(
                new Move(0, 0, 1, 0, 1),  // A7‑A6‑1
                new Move(0, 0, 2, 0, 2),  // A7‑A5‑2
                new Move(0, 0, 3, 0, 3),  // A7‑A4‑3
                new Move(0, 0, 0, 1, 1),  // A7‑B7‑1
                new Move(0, 0, 0, 2, 2),  // A7‑C7‑2
                new Move(0, 0, 0, 3, 3),  // A7‑D7‑3
                new Move(1, 3, 0, 3, 1),  // D6‑D7‑1
                new Move(1, 3, 2, 3, 1),  // D6‑D5‑1
                new Move(1, 3, 1, 2, 1),  // D6‑C6‑1
                new Move(1, 3, 1, 4, 1),  // D6‑E6‑1
                new Move(5, 2, 4, 2, 1),  // C2‑C3‑1
                new Move(5, 2, 6, 2, 1),  // C2‑C1‑1
                new Move(5, 2, 5, 1, 1),  // C2‑B2‑1
                new Move(5, 2, 5, 3, 1)   // C2‑D2‑1
        );



        assertTrue(generated.containsAll(actual), "List b is missing at least one element from list a");
    }
}
