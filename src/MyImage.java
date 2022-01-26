import com.pixelmed.display.SourceImage;
import reader.OverriddenSingleImagePanelForDemo;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

/**
 * Przechowuje parametry obrazów
 * @param pngImage przetwarzane zdjęcie
 */
public class MyImage {

    public BufferedImage pngImage;

    /**
     * Podstawowy konstruktor
     *
     * @param fileName nazwa pliku
     */
    public MyImage(String fileName) {
        String dicomInputFile = fileName;
        System.out.println(dicomInputFile);
        try {
            SourceImage sImg = new SourceImage(dicomInputFile);
            OverriddenSingleImagePanelForDemo singleImagePanel = new OverriddenSingleImagePanelForDemo(sImg);
            this.pngImage = sImg.getBufferedImage();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ustawia obraz
     *
     * @param img źródłowy obraz
     */
    public MyImage(BufferedImage img){
        this.pngImage=img;
    }

    /**
     * Ustawia obraz w formacie png
     *
     * @param image źródłowy obraz
     */
    public void setPngImage(BufferedImage image){
        this.pngImage =image;
    }

    /**
     * Pobiera szerokość obrazu
     *
     * @return szerokość obrazu
     */
    public int getWidth(){
        return pngImage.getWidth();
    }

    /**
     * Pobiera wysokość obrazu
     *
     * @return wysokość obrazu
     */
    public int getHeight(){
        return pngImage.getHeight();
    }

    /**
     * Pobiera obraz w formacie png
     *
     * @return obraz w formacie png
     */
    public BufferedImage getPngImage(){
        return this.pngImage;
    }

    /**
     * Pobiera kolor piksela
     *
     * @param x współrzędna X piksela
     * @param y współrzędna Y piksela
     * @param image obraz, z którego pobierany jest kolor
     */
    public void getPixelColor(int x, int y, BufferedImage image){
        int clr = image.getRGB(x, y);
        int red =   (clr & 0x00ff0000) >> 16;
        int green = (clr & 0x0000ff00) >> 8;
        int blue =   clr & 0x000000ff;
        System.out.println("Red Color value = " + red);
        System.out.println("Green Color value = " + green);
        System.out.println("Blue Color value = " + blue);
    }

    /**
     * Pobiera składową czerwoną piksela
     *
     * @param x współrzędna X piksela
     * @param y współrzędna Y piksela
     * @param image obraz, z którego pobierany jest kolor
     * @return składowa czerwona
     */
    public int getRedPixelComponent(int x, int y, BufferedImage image){
        int clr = image.getRGB(x, y);
        int red =   (clr & 0x00ff0000) >> 16;
        return red;
    }


    /**
     * Pobiera składową zieloną piksela
     *
     * @param x współrzędna X piksela
     * @param y współrzędna Y piksela
     * @param image obraz, z którego pobierany jest kolor
     * @return składowa zielona
     */
    public int getGreenPixelComponent(int x, int y, BufferedImage image){
        int clr = image.getRGB(x, y);
        int green = (clr & 0x0000ff00) >> 8;
        return green;
    }

    /**
     * Pobiera składową niebieską piksela
     *
     * @param x współrzędna X piksela
     * @param y współrzędna Y piksela
     * @param image obraz, z którego pobierany jest kolor
     * @return składowa niebieska
     */
    public int getBluePixelComponent(int x, int y, BufferedImage image){
        int clr = image.getRGB(x, y);
        int blue =   clr & 0x000000ff;
        return blue;
    }

    /**
     * Sprawdza poprawność koloru
     *
     * @param newColor sprawdzany kolor
     * @return nowy, poprawiony kolor
     */
    public int checkColor(int newColor){
        if(newColor<0){
            newColor=0;
        }
        if(newColor>255){
            newColor=255;
        }

        return newColor;
    }

    /**
     * Kopiuje obraz
     *
     * @param image obraz
     * @return powielony obraz
     */
    static BufferedImage copyImage(BufferedImage image) {
        ColorModel cm = image.getColorModel();
        boolean isRasterPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        return new BufferedImage(cm, raster, isRasterPremultiplied, null);
    }

    /**
     * Kopiuje obraz klasy myImage
     * @param myImage obraz
     */
    void copy(MyImage myImage){
        this.pngImage= copyImage(myImage.getPngImage());
    }

    /**
     * Przeprowadza filtrację obrazu
     * @param filter wybrany filtr
     */
    public void addFilter(Filter filter){
        BufferedImage imageBefore= this.copyImage(pngImage);
        int newColorRed;
        int newColorGreen;
        int newColorBlue;
        int newColor;

        for (int j = 1; j < pngImage.getHeight() - 1; j++) {
            for (int i = 1; i < pngImage.getWidth() - 1; i++) {

                newColorRed = filter.mask[0][0] * getRedPixelComponent(i - 1, j - 1, imageBefore) + filter.mask[0][1] * getRedPixelComponent(i - 1, j, imageBefore) + filter.mask[0][2] * getRedPixelComponent(i - 1, j + 1, imageBefore) + filter.mask[1][0] * getRedPixelComponent(i, j - 1, imageBefore) + filter.mask[1][1] * getRedPixelComponent(i, j, imageBefore) + filter.mask[1][2] * getRedPixelComponent(i, j + 1, imageBefore) + filter.mask[2][0] * getRedPixelComponent(i + 1, j - 1, imageBefore) + filter.mask[2][1] * getRedPixelComponent(i + 1, j, imageBefore) + filter.mask[2][2] * getRedPixelComponent(i + 1, j + 1, imageBefore);
                newColorRed = newColorRed/filter.sums[1][1];
                newColorRed=checkColor(newColorRed);

                newColorGreen = filter.mask[0][0] * getGreenPixelComponent(i - 1, j - 1, imageBefore) + filter.mask[0][1] * getGreenPixelComponent(i - 1, j, imageBefore) + filter.mask[0][2] * getGreenPixelComponent(i - 1, j + 1, imageBefore) + filter.mask[1][0] * getGreenPixelComponent(i, j - 1, imageBefore) + filter.mask[1][1] * getGreenPixelComponent(i, j, imageBefore) + filter.mask[1][2] * getGreenPixelComponent(i, j + 1, imageBefore) + filter.mask[2][0] * getGreenPixelComponent(i + 1, j - 1, imageBefore) + filter.mask[2][1] * getGreenPixelComponent(i + 1, j, imageBefore) + filter.mask[2][2] * getGreenPixelComponent(i + 1, j + 1, imageBefore);
                newColorGreen = newColorGreen/filter.sums[1][1];
                newColorGreen=checkColor(newColorGreen);

                newColorBlue = filter.mask[0][0] * getBluePixelComponent(i - 1, j - 1, imageBefore) + filter.mask[0][1] * getBluePixelComponent(i - 1, j,imageBefore) + filter.mask[0][2] * getBluePixelComponent(i - 1, j + 1, imageBefore) + filter.mask[1][0] * getBluePixelComponent(i, j - 1, imageBefore) + filter.mask[1][1] * getBluePixelComponent(i, j, imageBefore) + filter.mask[1][2] * getBluePixelComponent(i, j + 1, imageBefore) + filter.mask[2][0] * getBluePixelComponent(i + 1, j - 1, imageBefore) + filter.mask[2][1] * getBluePixelComponent(i + 1, j, imageBefore) + filter.mask[2][2] * getBluePixelComponent(i + 1, j + 1, imageBefore);
                newColorBlue = newColorBlue/filter.sums[1][1];
                newColorBlue= checkColor(newColorBlue);

                Color color = new Color(newColorRed, newColorGreen, newColorBlue);
                newColor = color.getRGB();
                pngImage.setRGB(i, j, newColor);
            }
        }
        filterInCorners(filter);
        filterOnEdge(filter);
    }

    /**
     * Przeprowadza specjalną filtrację na rogach
     *
     * @param filter wybrany filtr
     */
    public void filterInCorners(Filter filter) {
        int newColorRed;
        int newColorGreen;
        int newColorBlue;
        int newColor;
        BufferedImage imageBefore= this.copyImage(pngImage);

        // left-up corner
        newColorRed= filter.mask[1][1]* getRedPixelComponent(0, 0, imageBefore)+filter.mask[1][2]* getRedPixelComponent(0,1, imageBefore)+filter.mask[2][1]* getRedPixelComponent(1, 0, imageBefore)+filter.mask[2][2]* getRedPixelComponent(1, 1, imageBefore);
        newColorRed=newColorRed/filter.sums[0][0];
        newColorRed=checkColor(newColorRed);

        newColorGreen= filter.mask[1][1]* getGreenPixelComponent(0, 0, imageBefore)+filter.mask[1][2]* getGreenPixelComponent(0,1, imageBefore)+filter.mask[2][1]* getGreenPixelComponent(1, 0, imageBefore)+filter.mask[2][2]* getGreenPixelComponent(1, 1, imageBefore);
        newColorGreen=newColorGreen/filter.sums[0][0];
        newColorGreen=checkColor(newColorGreen);

        newColorBlue= filter.mask[1][1]* getBluePixelComponent(0, 0, imageBefore)+filter.mask[1][2]* getBluePixelComponent(0,1, imageBefore)+filter.mask[2][1]* getBluePixelComponent(1, 0, imageBefore)+filter.mask[2][2]* getBluePixelComponent(1, 1, imageBefore);
        newColorBlue=newColorBlue/filter.sums[0][0];
        newColorBlue=checkColor(newColorBlue);

        Color colorLU=new Color(newColorRed,newColorGreen,newColorBlue);
        newColor= colorLU.getRGB();
        pngImage.setRGB(0,0,newColor);

        //right-up corner
        newColorRed= filter.mask[1][1]* getRedPixelComponent(pngImage.getWidth()-1, 0, imageBefore)+filter.mask[1][0]* getRedPixelComponent(pngImage.getWidth()-2,0, imageBefore)+filter.mask[2][0]* getRedPixelComponent(pngImage.getWidth()-2, 1, imageBefore)+filter.mask[2][1]* getRedPixelComponent(pngImage.getWidth()-1, 1, imageBefore);
        newColorRed=newColorRed/filter.sums[0][2];
        newColorRed=checkColor(newColorRed);

        newColorGreen= filter.mask[1][1]* getGreenPixelComponent(pngImage.getWidth()-1, 0, imageBefore)+filter.mask[1][0]* getGreenPixelComponent(pngImage.getWidth()-2,0, imageBefore)+filter.mask[2][0]* getGreenPixelComponent(pngImage.getWidth()-2, 1, imageBefore)+filter.mask[2][1]* getGreenPixelComponent(pngImage.getWidth()-1, 1, imageBefore);
        newColorGreen=newColorGreen/filter.sums[0][2];
        newColorGreen=checkColor(newColorGreen);

        newColorBlue= filter.mask[1][1]* getBluePixelComponent(pngImage.getWidth()-1, 0, imageBefore)+filter.mask[1][0]* getBluePixelComponent(pngImage.getWidth()-2,0,imageBefore)+filter.mask[2][0]* getBluePixelComponent(pngImage.getWidth()-2, 1, imageBefore)+filter.mask[2][1]* getBluePixelComponent(pngImage.getWidth()-1, 1, imageBefore);
        newColorBlue=newColorBlue/filter.sums[0][2];
        newColorBlue=checkColor(newColorBlue);

        Color colorRU=new Color(newColorRed,newColorGreen,newColorBlue);
        newColor= colorRU.getRGB();
        pngImage.setRGB(pngImage.getWidth()-1,0,newColor);

        //left-down corner
        newColorRed= filter.mask[1][1]* getRedPixelComponent(0, pngImage.getHeight()-1, imageBefore)+filter.mask[0][1]* getRedPixelComponent(0, pngImage.getHeight()-2, imageBefore)+filter.mask[0][2]* getRedPixelComponent(1, pngImage.getHeight()-2, imageBefore)+filter.mask[2][2]* getRedPixelComponent(1, pngImage.getHeight()-1, imageBefore);
        newColorRed=newColorRed/filter.sums[2][0];
        newColorRed=checkColor(newColorRed);

        newColorGreen= filter.mask[1][1]* getGreenPixelComponent(0, pngImage.getHeight()-1, imageBefore)+filter.mask[0][1]* getGreenPixelComponent(0, pngImage.getHeight()-2, imageBefore)+filter.mask[0][2]* getGreenPixelComponent(1, pngImage.getHeight()-2, imageBefore)+filter.mask[2][2]* getGreenPixelComponent(1, pngImage.getHeight()-1, imageBefore);
        newColorGreen=newColorGreen/filter.sums[2][0];
        newColorGreen=checkColor(newColorGreen);

        newColorBlue= filter.mask[1][1]* getBluePixelComponent(0, pngImage.getHeight()-1, imageBefore)+filter.mask[0][1]* getBluePixelComponent(0, pngImage.getHeight()-2, imageBefore)+filter.mask[0][2]* getBluePixelComponent(1, pngImage.getHeight()-2, imageBefore)+filter.mask[2][2]* getBluePixelComponent(1, pngImage.getHeight()-1, imageBefore);
        newColorBlue=newColorBlue/filter.sums[2][0];
        newColorBlue=checkColor(newColorBlue);

        Color colorLD=new Color(newColorRed,newColorGreen,newColorBlue);
        newColor= colorLD.getRGB();
        pngImage.setRGB(0, pngImage.getHeight()-1,newColor);

        //right-down corner
        newColorRed= filter.mask[1][1]* getRedPixelComponent(pngImage.getWidth()-1, pngImage.getHeight()-1, imageBefore)+filter.mask[1][0]* getRedPixelComponent(pngImage.getWidth()-2, pngImage.getHeight()-1, imageBefore)+filter.mask[0][0]* getRedPixelComponent(pngImage.getWidth()-2, pngImage.getHeight()-2, imageBefore)+filter.mask[0][1]* getRedPixelComponent(pngImage.getWidth()-1, pngImage.getHeight()-2, imageBefore);
        newColorRed=newColorRed/filter.sums[2][2];
        newColorRed=checkColor(newColorRed);

        newColorGreen= filter.mask[1][1]* getGreenPixelComponent(pngImage.getWidth()-1, pngImage.getHeight()-1, imageBefore)+filter.mask[1][0]* getGreenPixelComponent(pngImage.getWidth()-2, pngImage.getHeight()-1, imageBefore)+filter.mask[0][0]* getGreenPixelComponent(pngImage.getWidth()-2, pngImage.getHeight()-2, imageBefore)+filter.mask[0][1]* getGreenPixelComponent(pngImage.getWidth()-1, pngImage.getHeight()-2, imageBefore);
        newColorGreen=newColorGreen/filter.sums[2][2];
        newColorGreen=checkColor(newColorGreen);

        newColorBlue= filter.mask[1][1]* getBluePixelComponent(pngImage.getWidth()-1, pngImage.getHeight()-1, imageBefore)+filter.mask[1][0]* getBluePixelComponent(pngImage.getWidth()-2, pngImage.getHeight()-1, imageBefore)+filter.mask[0][0]* getBluePixelComponent(pngImage.getWidth()-2, pngImage.getHeight()-2, imageBefore)+filter.mask[0][1]* getBluePixelComponent(pngImage.getWidth()-1, pngImage.getHeight()-2, imageBefore);
        newColorBlue=newColorBlue/filter.sums[2][2];
        newColorBlue=checkColor(newColorBlue);

        Color colorRD=new Color(newColorRed,newColorGreen,newColorBlue);
        newColor= colorRD.getRGB();
        pngImage.setRGB(pngImage.getWidth()-1, pngImage.getHeight()-1,newColor);
    }

    /**
     * Przeprowadza specjalną filtrację na krawędziach
     *
     * @param filter wybrany filtr
     */
    public void filterOnEdge(Filter filter) {
        int newColorRed;
        int newColorGreen;
        int newColorBlue;
        int newColor;
        BufferedImage imageBefore= this.copyImage(pngImage);

        // up
        for (int i = 1; i < pngImage.getWidth() - 1; i++) {
            newColorRed = filter.mask[1][0] * getRedPixelComponent(i-1, 0, imageBefore) + filter.mask[1][1] * getRedPixelComponent(i, 0, imageBefore) + filter.mask[1][2] * getRedPixelComponent(i+1, 0, imageBefore) + filter.mask[2][0] * getRedPixelComponent(i-1, 1, imageBefore) + filter.mask[2][1] * getRedPixelComponent(i, 1, imageBefore) + filter.mask[2][2] * getRedPixelComponent( i+1, 1, imageBefore);
            newColorRed = newColorRed/filter.sums[0][1];
            newColorRed = checkColor(newColorRed);

            newColorGreen = filter.mask[1][0] * getGreenPixelComponent(i-1, 0, imageBefore) + filter.mask[1][1] * getGreenPixelComponent(i, 0, imageBefore) + filter.mask[1][2] * getGreenPixelComponent(i+1, 0, imageBefore) + filter.mask[2][0] * getGreenPixelComponent(i-1, 1, imageBefore) + filter.mask[2][1] * getGreenPixelComponent(i, 1, imageBefore) + filter.mask[2][2] * getGreenPixelComponent( i+1, 1, imageBefore);
            newColorGreen = newColorGreen/filter.sums[0][1];
            newColorGreen = checkColor(newColorGreen);

            newColorBlue = filter.mask[1][0] * getBluePixelComponent(i-1, 0, imageBefore) + filter.mask[1][1] * getBluePixelComponent(i, 0, imageBefore) + filter.mask[1][2] * getBluePixelComponent(i+1, 0, imageBefore) + filter.mask[2][0] * getBluePixelComponent(i-1, 1, imageBefore) + filter.mask[2][1] * getBluePixelComponent(i, 1, imageBefore) + filter.mask[2][2] * getBluePixelComponent( i+1, 1, imageBefore);
            newColorBlue = newColorBlue/filter.sums[0][1];
            newColorBlue = checkColor(newColorBlue);

            Color color = new Color(newColorRed, newColorGreen, newColorBlue);
            newColor = color.getRGB();
            pngImage.setRGB(i, 0, newColor);
        }

        //left
        for (int j = 1; j < pngImage.getHeight() - 1; j++) {
            newColorRed = filter.mask[0][1] * getRedPixelComponent(0, j-1, imageBefore) + filter.mask[0][2] * getRedPixelComponent( 1, j + 1, imageBefore) + filter.mask[1][1] * getRedPixelComponent(0, j, imageBefore) + filter.mask[1][2] * getRedPixelComponent(0, j + 1, imageBefore) + filter.mask[2][1] * getRedPixelComponent(1, j, imageBefore) + filter.mask[2][2] * getRedPixelComponent(1, j + 1, imageBefore);
            newColorRed = newColorRed/filter.sums[1][0];
            newColorRed=checkColor(newColorRed);

            newColorGreen = filter.mask[0][1] * getGreenPixelComponent(0, j-1, imageBefore) + filter.mask[0][2] * getGreenPixelComponent( 1, j + 1, imageBefore) + filter.mask[1][1] * getGreenPixelComponent(0, j, imageBefore) + filter.mask[1][2] * getGreenPixelComponent(0, j + 1, imageBefore) + filter.mask[2][1] * getGreenPixelComponent(1, j, imageBefore) + filter.mask[2][2] * getGreenPixelComponent(1, j + 1, imageBefore);
            newColorGreen = newColorGreen/filter.sums[1][0];
            newColorGreen=checkColor(newColorGreen);

            newColorBlue = filter.mask[0][1] * getBluePixelComponent(0, j-1, imageBefore) + filter.mask[0][2] * getBluePixelComponent( 1, j + 1, imageBefore) + filter.mask[1][1] * getBluePixelComponent(0, j, imageBefore) + filter.mask[1][2] * getBluePixelComponent(0, j + 1, imageBefore) + filter.mask[2][1] * getBluePixelComponent(1, j, imageBefore) + filter.mask[2][2] * getBluePixelComponent(1, j + 1, imageBefore);
            newColorBlue = newColorBlue/filter.sums[1][0];
            newColorBlue=checkColor(newColorBlue);

            Color color = new Color(newColorRed, newColorGreen, newColorBlue);
            newColor = color.getRGB();
            pngImage.setRGB(0, j, newColor);
        }

        //right
        for (int j = 1; j < pngImage.getHeight() - 1; j++) {
            newColorRed = filter.mask[0][0] * getRedPixelComponent(pngImage.getWidth()-2, j-1, imageBefore) + filter.mask[0][1] * getRedPixelComponent( pngImage.getWidth()-1, j - 1, imageBefore) + filter.mask[1][0] * getRedPixelComponent(pngImage.getWidth()-2, j, imageBefore) + filter.mask[1][1] * getRedPixelComponent(pngImage.getWidth()-1, j, imageBefore) + filter.mask[2][0] * getRedPixelComponent(pngImage.getWidth()-2, j+1, imageBefore) + filter.mask[2][1] * getRedPixelComponent(pngImage.getWidth()-1, j + 1, imageBefore);
            newColorRed = newColorRed/filter.sums[1][2];
            newColorRed=checkColor(newColorRed);

            newColorGreen = filter.mask[0][0] * getGreenPixelComponent(pngImage.getWidth()-2, j-1, imageBefore) + filter.mask[0][1] * getGreenPixelComponent( pngImage.getWidth()-1, j - 1, imageBefore) + filter.mask[1][0] * getGreenPixelComponent(pngImage.getWidth()-2, j, imageBefore) + filter.mask[1][1] * getGreenPixelComponent(pngImage.getWidth()-1, j, imageBefore) + filter.mask[2][0] * getGreenPixelComponent(pngImage.getWidth()-2, j+1, imageBefore) + filter.mask[2][1] * getGreenPixelComponent(pngImage.getWidth()-1, j + 1, imageBefore);
            newColorGreen = newColorGreen/filter.sums[1][2];
            newColorGreen=checkColor(newColorGreen);

            newColorBlue = filter.mask[0][0] * getBluePixelComponent(pngImage.getWidth()-2, j-1, imageBefore) + filter.mask[0][1] * getBluePixelComponent( pngImage.getWidth()-1, j - 1, imageBefore) + filter.mask[1][0] * getBluePixelComponent(pngImage.getWidth()-2, j, imageBefore) + filter.mask[1][1] * getBluePixelComponent(pngImage.getWidth()-1, j, imageBefore) + filter.mask[2][0] * getBluePixelComponent(pngImage.getWidth()-2, j+1, imageBefore) + filter.mask[2][1] * getBluePixelComponent(pngImage.getWidth()-1, j + 1, imageBefore);
            newColorBlue = newColorBlue/filter.sums[1][2];
            newColorBlue=checkColor(newColorBlue);

            Color color = new Color(newColorRed, newColorGreen, newColorBlue);
            newColor = color.getRGB();
            pngImage.setRGB(pngImage.getWidth()-1, j, newColor);
        }

        //down
        for (int i = 1; i < pngImage.getWidth() - 1; i++) {
            newColorRed = filter.mask[0][0] * getRedPixelComponent(i-1, pngImage.getHeight()-2, imageBefore) + filter.mask[0][1] * getRedPixelComponent( i, pngImage.getHeight()-2, imageBefore) + filter.mask[0][2] * getRedPixelComponent(i+1, pngImage.getHeight()-2, imageBefore) + filter.mask[1][0] * getRedPixelComponent(i-1, pngImage.getHeight()-1, imageBefore) + filter.mask[1][1] * getRedPixelComponent(i, pngImage.getHeight()-1, imageBefore) + filter.mask[1][2] * getRedPixelComponent(i+1, pngImage.getHeight()-1, imageBefore);
            newColorRed = newColorRed/filter.sums[2][1];
            newColorRed=checkColor(newColorRed);

            newColorGreen = filter.mask[0][0] * getGreenPixelComponent(i-1, pngImage.getHeight()-2, imageBefore) + filter.mask[0][1] * getGreenPixelComponent( i, pngImage.getHeight()-2, imageBefore) + filter.mask[0][2] * getGreenPixelComponent(i+1, pngImage.getHeight()-2, imageBefore) + filter.mask[1][0] * getGreenPixelComponent(i-1, pngImage.getHeight()-1, imageBefore) + filter.mask[1][1] * getGreenPixelComponent(i, pngImage.getHeight()-1, imageBefore) + filter.mask[1][2] * getGreenPixelComponent(i+1, pngImage.getHeight()-1, imageBefore);
            newColorGreen = newColorGreen/filter.sums[2][1];
            newColorGreen=checkColor(newColorGreen);

            newColorBlue = filter.mask[0][0] * getBluePixelComponent(i-1, pngImage.getHeight()-2, imageBefore) + filter.mask[0][1] * getBluePixelComponent( i, pngImage.getHeight()-2, imageBefore) + filter.mask[0][2] * getBluePixelComponent(i+1, pngImage.getHeight()-2, imageBefore) + filter.mask[1][0] * getBluePixelComponent(i-1, pngImage.getHeight()-1, imageBefore) + filter.mask[1][1] * getBluePixelComponent(i, pngImage.getHeight()-1, imageBefore) + filter.mask[1][2] * getBluePixelComponent(i+1, pngImage.getHeight()-1, imageBefore);
            newColorBlue = newColorBlue/filter.sums[2][1];
            newColorBlue=checkColor(newColorBlue);

            Color color = new Color(newColorRed, newColorGreen, newColorBlue);
            newColor = color.getRGB();
            pngImage.setRGB(i, pngImage.getHeight()-1, newColor);
        }
    }

    /**
     * Zapisuje obraz
     *
     * @param file plik docelowy
     * @throws IOException
     */
    void savePngImage(File file) throws IOException {
        //File file = new File(fileName);
        ImageIO.write(this.getPngImage(), "png", file);
    }

}