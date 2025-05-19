public class SelfPlay {
    public static void main(String[] args) {
        String host = "localhost";
        int    port = 8000;

        new Thread(() -> new SimpleClient(host, port, 'r').start(),
                "RedClient").start();
        new Thread(() -> new SimpleClient(host, port, 'b').start(),
                "BlueClient").start();
    }
}

