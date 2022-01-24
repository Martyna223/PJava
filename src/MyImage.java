import com.pixelmed.display.SourceImage;
import reader.OverriddenSingleImagePanelForDemo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class MyImage {

    public BufferedImage pngImage;


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

    public MyImage(BufferedImage img){
        this.pngImage=img;
    }


    public void setPngImage(BufferedImage image){
        this.pngImage =image;
    }

    public int getWidth(){
        return pngImage.getWidth();
    }

    public int getHeight(){
        return pngImage.getHeight();
    }

    public BufferedImage getPngImage(){
        return this.pngImage;
    }

    public void getPixelColor(int x, int y, BufferedImage image){
        int clr = image.getRGB(x, y);
        int red =   (clr & 0x00ff0000) >> 16;
        int green = (clr & 0x0000ff00) >> 8;
        int blue =   clr & 0x000000ff;
        System.out.println("Red Color value = " + red);
        System.out.println("Green Color value = " + green);
        System.out.println("Blue Color value = " + blue);
    }

    public int getRedPixelComponent(int x, int y, BufferedImage image){
        int clr = image.getRGB(x, y);
        int red =   (clr & 0x00ff0000) >> 16;
        return red;
    }


    public int getGreenPixelComponent(int x, int y, BufferedImage image){
        int clr = image.getRGB(x, y);
        int green = (clr & 0x0000ff00) >> 8;
        return green;
    }

    public int getBluePixelComponent(int x, int y, BufferedImage image){
        int clr = image.getRGB(x, y);
        int blue =   clr & 0x000000ff;
        return blue;
    }

    public int checkColor(int newColor){
        if(newColor<0){
            newColor=0;
        }
        if(newColor>255){
            newColor=255;
        }

        return newColor;
    }

    static BufferedImage copyImage(BufferedImage image) {
        ColorModel cm = image.getColorModel();
        boolean isRasterPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        return new BufferedImage(cm, raster, isRasterPremultiplied, null);
    }

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

    public void addFilterNormalization(Filter filter){
        BufferedImage imageBefore= this.copyImage(pngImage);
        int[][] newColorRed= new int[pngImage.getWidth()][pngImage.getHeight()];
        int[][] newColorGreen= new int[pngImage.getWidth()][pngImage.getHeight()];
        int[][] newColorBlue= new int[pngImage.getWidth()][pngImage.getHeight()];
        int newColor;
        int redMax=0;
        int redMin=255;
        int greenMax=0;
        int greenMin=255;
        int blueMax=0;
        int blueMin=255;

        for (int j = 1; j < pngImage.getHeight() - 1; j++) {
            for (int i = 1; i < pngImage.getWidth() - 1; i++) {

                newColorRed[i][j] = filter.mask[0][0] * getRedPixelComponent(i - 1, j - 1, imageBefore) + filter.mask[0][1] * getRedPixelComponent(i - 1, j, imageBefore) + filter.mask[0][2] * getRedPixelComponent(i - 1, j + 1, imageBefore) + filter.mask[1][0] * getRedPixelComponent(i, j - 1, imageBefore) + filter.mask[1][1] * getRedPixelComponent(i, j, imageBefore) + filter.mask[1][2] * getRedPixelComponent(i, j + 1, imageBefore) + filter.mask[2][0] * getRedPixelComponent(i + 1, j - 1, imageBefore) + filter.mask[2][1] * getRedPixelComponent(i + 1, j, imageBefore) + filter.mask[2][2] * getRedPixelComponent(i + 1, j + 1, imageBefore);
                newColorRed[i][j] = newColorRed[i][j]/filter.sums[1][1];
                //newColorRed[i][j]=checkColor(newColorRed[i][j]);
                if(newColorRed[i][j]>redMax){
                    redMax= newColorRed[i][j];
                    //redMax= checkColor(redMax);
                }
                if(newColorRed[i][j]<redMin){
                    redMin= newColorRed[i][j];
                    //redMin= checkColor(redMin);
                }

                newColorGreen[i][j] = filter.mask[0][0] * getGreenPixelComponent(i - 1, j - 1, imageBefore) + filter.mask[0][1] * getGreenPixelComponent(i - 1, j, imageBefore) + filter.mask[0][2] * getGreenPixelComponent(i - 1, j + 1, imageBefore) + filter.mask[1][0] * getGreenPixelComponent(i, j - 1, imageBefore) + filter.mask[1][1] * getGreenPixelComponent(i, j, imageBefore) + filter.mask[1][2] * getGreenPixelComponent(i, j + 1, imageBefore) + filter.mask[2][0] * getGreenPixelComponent(i + 1, j - 1, imageBefore) + filter.mask[2][1] * getGreenPixelComponent(i + 1, j, imageBefore) + filter.mask[2][2] * getGreenPixelComponent(i + 1, j + 1, imageBefore);
                newColorGreen[i][j] = newColorGreen[i][j]/filter.sums[1][1];
                //newColorGreen[i][j]=checkColor(newColorGreen[i][j]);
                if(newColorGreen[i][j]>greenMax){
                    greenMax= newColorGreen[i][j];
                   // greenMax= checkColor(greenMax);
                }
                if(newColorGreen[i][j]<greenMin){
                    greenMin= newColorRed[i][j];
                   // greenMin= checkColor(greenMin);
                }

                newColorBlue[i][j] = filter.mask[0][0] * getBluePixelComponent(i - 1, j - 1, imageBefore) + filter.mask[0][1] * getBluePixelComponent(i - 1, j,imageBefore) + filter.mask[0][2] * getBluePixelComponent(i - 1, j + 1, imageBefore) + filter.mask[1][0] * getBluePixelComponent(i, j - 1, imageBefore) + filter.mask[1][1] * getBluePixelComponent(i, j, imageBefore) + filter.mask[1][2] * getBluePixelComponent(i, j + 1, imageBefore) + filter.mask[2][0] * getBluePixelComponent(i + 1, j - 1, imageBefore) + filter.mask[2][1] * getBluePixelComponent(i + 1, j, imageBefore) + filter.mask[2][2] * getBluePixelComponent(i + 1, j + 1, imageBefore);
                newColorBlue[i][j] = newColorBlue[i][j]/filter.sums[1][1];
                //newColorBlue[i][j]= checkColor(newColorBlue[i][j]);
                if(newColorBlue[i][j]>blueMax){
                    blueMax= newColorBlue[i][j];
                    //blueMax= checkColor(blueMax);
                }
                if(newColorBlue[i][j]<blueMin){
                    blueMin= newColorBlue[i][j];
                   // blueMin= checkColor(blueMin);
                }

            }
        }
        System.out.println("Red Max: "+ redMax + ", Red Min: " + redMin);
            for (int j = 1; j < pngImage.getHeight() - 1; j++) {
                for (int i = 1; i < pngImage.getWidth() - 1; i++) {
                    newColorRed[i][j]= (newColorRed[i][j]-redMin)/(redMax-redMin);
                    newColorRed[i][j]=checkColor(newColorRed[i][j]);
                    newColorGreen[i][j]= (newColorGreen[i][j]-greenMin)/(greenMax-greenMin);
                    newColorGreen[i][j]=checkColor(newColorGreen[i][j]);
                    newColorBlue[i][j]= (newColorBlue[i][j]-blueMin)/(blueMax-blueMin);
                    newColorBlue[i][j]= checkColor(newColorBlue[i][j]);

                    Color color = new Color(newColorRed[i][j], newColorGreen[i][j], newColorBlue[i][j]);
                    newColor = color.getRGB();
                    pngImage.setRGB(i, j, newColor);
                }
            }

        filterInCorners(filter);
        filterOnEdge(filter);
    }

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

    void savePngImage(File file) throws IOException {
        //File file = new File(fileName);
        ImageIO.write(this.getPngImage(), "png", file);
    }

}