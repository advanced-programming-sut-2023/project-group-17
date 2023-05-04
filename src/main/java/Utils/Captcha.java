package Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Captcha {
    static Random random = new Random();
    public static final int ART_SIZE = 18;

    public enum ASCIIArtFont {
        ART_FONT_DIALOG("Dialog");

        private String value;

        public String getValue() {
            return value;
        }

        private ASCIIArtFont(String value) {
            this.value = value;
        }
    }

    public static void printTextArt(String artText, int textHeight, ASCIIArtFont fontType) {
        String artsSymbols = "@#$*";
        String currentArtSymbol = String.valueOf(artsSymbols.charAt(random.nextInt(4)));
        String fontName = fontType.getValue();
        int imageWidth = findImageWidth(textHeight, artText, fontName);

        BufferedImage image = new BufferedImage(imageWidth, textHeight, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Font font = new Font(fontName, Font.BOLD, textHeight);
        g.setFont(font);

        Graphics2D graphics = (Graphics2D) g;
        graphics.drawString(artText, 0, getBaselinePosition(g, font));

        for (int y = 0; y < textHeight; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < imageWidth; x++) {
                int number = random.nextInt(11);
                if (number == 0 || number == 5) {
                    sb.append(".");
                    continue;
                }
                sb.append(image.getRGB(x, y) == Color.WHITE.getRGB() ? currentArtSymbol : " ");
            }
            if (sb.toString().trim().isEmpty())
                continue;
            System.out.println(sb);

        }
    }

    public static int generateRandomNumber() {
        return (int) (((int)(Math.random()*9000) +1000) * Math.pow(10, random.nextInt(4)) + random.nextInt(10));
    }

    public static void printTextArt(int verifyingNumber) {
        printTextArt(Integer.toString(verifyingNumber), ART_SIZE, ASCIIArtFont.ART_FONT_DIALOG);
    }

    private static int findImageWidth(int textHeight, String artText, String fontName) {
        BufferedImage im = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics g = im.getGraphics();
        g.setFont(new Font(fontName, Font.BOLD, textHeight));
        return g.getFontMetrics().stringWidth(artText);
    }

    private static int getBaselinePosition(Graphics g, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int y = metrics.getAscent() - metrics.getDescent();
        return y;
    }
}

