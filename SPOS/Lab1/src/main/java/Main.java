public class Main {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\HP250\\Documents\\3course\\SECOND_SEMESTER\\3Course\\OOP\\Lab1\\src\\main\\java\\hedgehoggame\\gamelogic\\Game.java";
        String filePath2 = "C:\\Users\\HP250\\Documents\\3course\\SECOND_SEMESTER\\3Course\\SPOS\\lab6\\src\\com\\knu\\ynortman\\Kernel.java";
        Lexer lexer = new Lexer(filePath2);
        lexer.parse();
    }
}
