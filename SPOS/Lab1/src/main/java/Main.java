public class Main {
    public static void main(String[] args) {
        String filePath = "Game.java";
        String filePath2 = "Kernel.java";
        String filePath3 = "test1.java";
        String filePath4 = "Ripemd160.java";
        Lexer lexer = new Lexer(filePath3);
        lexer.parse();
    }
}
