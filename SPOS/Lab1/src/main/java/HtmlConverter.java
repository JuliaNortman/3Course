import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlConverter {
    private static final Logger logger = LogManager.getLogger(HtmlConverter.class);
    private static final String html_begin = "" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "\n" +
            "    <title>Lexer Java </title>\n" +
            "\n" +
            "   <style>body{white-space: pre-wrap;}</style>\n" +
            "\n" +
            "</head>\n" +
            "<body>";
    private static final String html_end = "</body>\n" +
            "</html>" +
            "";

    private static StringBuilder html_body = new StringBuilder();

    public static void convert(List<Token> tokens) {
        for (Token token : tokens) {
            html_body.append("<font color=\"#")
                    .append(Long.toHexString(token.getTokenName().getColor()))
                    .append("\">")
                    .append(token.getValue())
                    .append("</font>");
        }
        String html = html_begin + html_body + html_end;

        try {
            FileWriter myWriter = new FileWriter("index.html");
            myWriter.write(html);
            myWriter.close();
            logger.info("Successfully wrote to the file.");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
