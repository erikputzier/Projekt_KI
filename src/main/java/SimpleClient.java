import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public final class SimpleClient implements Runnable {

    private final String host;
    private final int    port;
    private final Thread worker;

    public SimpleClient(String host, int port, char ignored) {
        this.host   = Objects.requireNonNull(host);
        this.port   = port;
        this.worker = new Thread(this, "Client-connecting…");
    }

    public void start() { worker.start(); }

    @Override public void run() {
        try (Socket sock = new Socket(host, port)) {
            play(sock);
        } catch (Exception ex) {
            System.err.printf("[%s] %s%n",
                    Thread.currentThread().getName(), ex.getMessage());
            ex.printStackTrace(System.err);
        }
    }

    /* ------------------------------------------------------------------ */
    /*  network protocol                                                  */
    /* ------------------------------------------------------------------ */
    private void play(Socket sock) throws IOException {

        InputStream  inRaw  = sock.getInputStream();
        OutputStream outRaw = sock.getOutputStream();
        BufferedWriter out  = new BufferedWriter(
                new OutputStreamWriter(
                        outRaw, StandardCharsets.UTF_8));

        /* player-ID byte: '0' = red, '1' = blue ----------------------- */
        int id = inRaw.read();
        if (id < 0) throw new EOFException("server closed immediately");

        boolean iAmRed = id == '0';
        char    myTurn = iAmRed ? 'r' : 'b';

        Thread.currentThread().setName(iAmRed ? "RedClient" : "BlueClient");
        System.out.printf("[%s] Connected as player %c (%s)%n",
                Thread.currentThread().getName(),
                (char) id, iAmRed ? "RED" : "BLUE");

        sendJson(out, "\"get\"");                     // wake the server

        Gson       gson   = new Gson();
        JsonReader reader = new JsonReader(
                new InputStreamReader(inRaw,
                        StandardCharsets.UTF_8));
        reader.setLenient(true);                     // warning: deprecated but fine

        while (true) {

            GameState st = gson.fromJson(reader, GameState.class);
            if (st == null || st.end) break;         // closed or finished
            try {
                Thread.sleep(500);  // sleep for 500 ms
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                break;
            }
            System.out.println(BoardInitializer.toString(Fen.parseBoard(st.board.substring(0, st.board.length() - 2))));


            if (st.turn != null && st.turn.charAt(0) == myTurn) {
                /* -------------------- our move ---------------------- */
                String move = chooseMove(st.board);
                System.out.printf("[%s] → %s  (time %d ms)%n",
                        Thread.currentThread().getName(),
                        move, st.time);
                sendJson(out, "\"" + move + "\"");
            } else {
                /* -------------------- poll -------------------------- */
                sendJson(out, "\"get\"");
            }
        }
        System.out.printf("[%s] Session ended%n",
                Thread.currentThread().getName());
    }

    private static void sendJson(BufferedWriter out, String json)
            throws IOException {
        out.write(json);
        out.flush();
    }

    /* ------------------------------------------------------------------ */
    /*  chooseMove: first string returned by Fen.movesFromFen             */
    /* ------------------------------------------------------------------ */
    private static String chooseMove(String fenWithTurn) {
        List<Move> moves = Fen.movesFromFen(fenWithTurn);
        if (moves.isEmpty())
            throw new IllegalStateException("No legal moves returned");
        // pick a random index in [0, moves.size())
        int idx = ThreadLocalRandom.current().nextInt(moves.size());
        return moves.get(idx).toAlgebraic();
    }

    /* lightweight DTO for Gson                                           */
    private static final class GameState {
        String  board;
        String  turn;
        boolean bothConnected;
        long    time;
        boolean end;
    }
}