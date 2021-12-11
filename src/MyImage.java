import com.pixelmed.display.SourceImage;
import reader.OverriddenSingleImagePanelForDemo;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MyImage {
    private Filter filter= new Filter();
    private static BufferedImage jpgImage;

    public MyImage(String fileName) {
        String dicomInputFile = fileName;
        try {
            SourceImage sImg = new SourceImage(dicomInputFile);
            OverriddenSingleImagePanelForDemo singleImagePanel = new OverriddenSingleImagePanelForDemo(sImg);
            jpgImage = sImg.getBufferedImage();

        } catch (Exception e) {
            e.printStackTrace(); //in real life, do something about this exception
        }
    }

    public void setJpgImage(String fileName){
        String dicomInputFile = fileName;
        try {
            SourceImage sImg = new SourceImage(dicomInputFile);
            OverriddenSingleImagePanelForDemo singleImagePanel = new OverriddenSingleImagePanelForDemo(sImg);
            jpgImage = sImg.getBufferedImage();

        } catch (Exception e) {
            e.printStackTrace(); //in real life, do something about this exception
        }
    }

    public void setJpgImage(BufferedImage image){
        jpgImage=image;
    }

    public int getWidth(){
        return jpgImage.getWidth();
    }

    public int getHeight(){
        return jpgImage.getHeight();
    }

    public BufferedImage getJpgImage(){
        return jpgImage;
    }

    public void getPixelColor(int x, int y){
        int clr = jpgImage.getRGB(x, y);
        int red =   (clr & 0x00ff0000) >> 16;
        int green = (clr & 0x0000ff00) >> 8;
        int blue =   clr & 0x000000ff;
        System.out.println("Red Color value = " + red);
        System.out.println("Green Color value = " + green);
        System.out.println("Blue Color value = " + blue);
    }

    public int getRedPixelComponent(int x, int y){
        int clr = jpgImage.getRGB(x, y);
        int red =   (clr & 0x00ff0000) >> 16;
        return red;
    }


    public int getGreenPixelComponent(int x, int y){
        int clr = jpgImage.getRGB(x, y);
        int green = (clr & 0x0000ff00) >> 8;
        return green;
    }


    public int getBluePixelComponent(int x, int y){
        int clr = jpgImage.getRGB(x, y);
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

    public void addFilter(Filter newfilter) {
        int newColorRed;
        int newColorGreen;
        int newColorBlue;
        int newColor;
        filter.setFilter(newfilter);
        for (int j = 1; j < jpgImage.getHeight() - 1; j++) {
            for (int i = 1; i < jpgImage.getWidth() - 1; i++) {

                newColorRed = filter.mask[0][0] * getRedPixelComponent(i - 1, j - 1) + filter.mask[0][1] * getRedPixelComponent(i - 1, j) + filter.mask[0][2] * getRedPixelComponent(i - 1, j + 1) + filter.mask[1][0] * getRedPixelComponent(i, j - 1) + filter.mask[1][1] * getRedPixelComponent(i, j) + filter.mask[1][2] * getRedPixelComponent(i, j + 1) + filter.mask[2][0] * getRedPixelComponent(i + 1, j - 1) + filter.mask[2][1] * getRedPixelComponent(i + 1, j) + filter.mask[2][2] * getRedPixelComponent(i + 1, j + 1);
                newColorRed = newColorRed / filter.sum;
                newColorRed=checkColor(newColorRed);

                newColorGreen = filter.mask[0][0] * getGreenPixelComponent(i - 1, j - 1) + filter.mask[0][1] * getGreenPixelComponent(i - 1, j) + filter.mask[0][2] * getGreenPixelComponent(i - 1, j + 1) + filter.mask[1][0] * getGreenPixelComponent(i, j - 1) + filter.mask[1][1] * getGreenPixelComponent(i, j) + filter.mask[1][2] * getGreenPixelComponent(i, j + 1) + filter.mask[2][0] * getGreenPixelComponent(i + 1, j - 1) + filter.mask[2][1] * getGreenPixelComponent(i + 1, j) + filter.mask[2][2] * getGreenPixelComponent(i + 1, j + 1);
                newColorGreen = newColorGreen / filter.sum;
                newColorGreen=checkColor(newColorGreen);

                newColorBlue = filter.mask[0][0] * getBluePixelComponent(i - 1, j - 1) + filter.mask[0][1] * getBluePixelComponent(i - 1, j) + filter.mask[0][2] * getBluePixelComponent(i - 1, j + 1) + filter.mask[1][0] * getBluePixelComponent(i, j - 1) + filter.mask[1][1] * getBluePixelComponent(i, j) + filter.mask[1][2] * getBluePixelComponent(i, j + 1) + filter.mask[2][0] * getBluePixelComponent(i + 1, j - 1) + filter.mask[2][1] * getBluePixelComponent(i + 1, j) + filter.mask[2][2] * getBluePixelComponent(i + 1, j + 1);
                newColorBlue = newColorBlue / filter.sum;
                newColorBlue=checkColor(newColorBlue);

                Color color = new Color(newColorRed, newColorGreen, newColorBlue);
                newColor = color.getRGB();
                jpgImage.setRGB(i, j, newColor);
            }
        }
        filterInCorners(newfilter);
        filterOnEdge(newfilter);
    }


    public void filterInCorners(Filter newfilter) {
        int newColorRed;
        int newColorGreen;
        int newColorBlue;
        int newColor;
        filter.setFilter(newfilter);

        // left-up corner
        newColorRed= filter.mask[1][1]* getRedPixelComponent(0, 0)+filter.mask[1][2]* getRedPixelComponent(0,1)+filter.mask[2][1]* getRedPixelComponent(1, 0)+filter.mask[2][2]* getRedPixelComponent(1, 1);
        newColorRed=newColorRed/filter.sumLU;
        newColorRed=checkColor(newColorRed);

        newColorGreen= filter.mask[1][1]* getGreenPixelComponent(0, 0)+filter.mask[1][2]* getGreenPixelComponent(0,1)+filter.mask[2][1]* getGreenPixelComponent(1, 0)+filter.mask[2][2]* getGreenPixelComponent(1, 1);
        newColorGreen=newColorGreen/filter.sumLU;
        newColorGreen=checkColor(newColorGreen);

        newColorBlue= filter.mask[1][1]* getBluePixelComponent(0, 0)+filter.mask[1][2]* getBluePixelComponent(0,1)+filter.mask[2][1]* getBluePixelComponent(1, 0)+filter.mask[2][2]* getBluePixelComponent(1, 1);
        newColorBlue=newColorBlue/filter.sumLU;
        newColorBlue=checkColor(newColorBlue);

        Color colorLU=new Color(newColorRed,newColorGreen,newColorBlue);
        newColor= colorLU.getRGB();
        jpgImage.setRGB(0,0,newColor);

        //right-up corner
        newColorRed= filter.mask[1][1]* getRedPixelComponent(jpgImage.getWidth()-1, 0)+filter.mask[1][0]* getRedPixelComponent(jpgImage.getWidth()-2,0)+filter.mask[2][0]* getRedPixelComponent(jpgImage.getWidth()-2, 1 )+filter.mask[2][1]* getRedPixelComponent(jpgImage.getWidth()-1, 1);
        newColorRed=newColorRed/ filter.sumRU;
        newColorRed=checkColor(newColorRed);

        newColorGreen= filter.mask[1][1]* getGreenPixelComponent(jpgImage.getWidth()-1, 0)+filter.mask[1][0]* getGreenPixelComponent(jpgImage.getWidth()-2,0)+filter.mask[2][0]* getGreenPixelComponent(jpgImage.getWidth()-2, 1 )+filter.mask[2][1]* getGreenPixelComponent(jpgImage.getWidth()-1, 1);
        newColorGreen=newColorGreen/ filter.sumRU;
        newColorGreen=checkColor(newColorGreen);

        newColorBlue= filter.mask[1][1]* getBluePixelComponent(jpgImage.getWidth()-1, 0)+filter.mask[1][0]* getBluePixelComponent(jpgImage.getWidth()-2,0)+filter.mask[2][0]* getBluePixelComponent(jpgImage.getWidth()-2, 1 )+filter.mask[2][1]* getBluePixelComponent(jpgImage.getWidth()-1, 1);
        newColorBlue=newColorBlue/ filter.sumRU;
        newColorBlue=checkColor(newColorBlue);

        Color colorRU=new Color(newColorRed,newColorGreen,newColorBlue);
        newColor= colorRU.getRGB();
        jpgImage.setRGB(jpgImage.getWidth()-1,0,newColor);

        //left-down corner
        newColorRed= filter.mask[1][1]* getRedPixelComponent(0, jpgImage.getHeight()-1)+filter.mask[0][1]* getRedPixelComponent(0,jpgImage.getHeight()-2)+filter.mask[0][2]* getRedPixelComponent(1, jpgImage.getHeight()-2)+filter.mask[2][2]* getRedPixelComponent(1, jpgImage.getHeight()-1);
        newColorRed=newColorRed/ filter.sumLD;
        newColorRed=checkColor(newColorRed);

        newColorGreen= filter.mask[1][1]* getGreenPixelComponent(0, jpgImage.getHeight()-1)+filter.mask[0][1]* getGreenPixelComponent(0,jpgImage.getHeight()-2)+filter.mask[0][2]* getGreenPixelComponent(1, jpgImage.getHeight()-2)+filter.mask[2][2]* getGreenPixelComponent(1, jpgImage.getHeight()-1);
        newColorGreen=newColorGreen/ filter.sumLD;
        newColorGreen=checkColor(newColorGreen);

        newColorBlue= filter.mask[1][1]* getBluePixelComponent(0, jpgImage.getHeight()-1)+filter.mask[0][1]* getBluePixelComponent(0,jpgImage.getHeight()-2)+filter.mask[0][2]* getBluePixelComponent(1, jpgImage.getHeight()-2)+filter.mask[2][2]* getBluePixelComponent(1, jpgImage.getHeight()-1);
        newColorBlue=newColorBlue/ filter.sumLD;
        newColorBlue=checkColor(newColorBlue);

        Color colorLD=new Color(newColorRed,newColorGreen,newColorBlue);
        newColor= colorLD.getRGB();
        jpgImage.setRGB(0,jpgImage.getHeight()-1,newColor);

        //right-down corner
        newColorRed= filter.mask[1][1]* getRedPixelComponent(jpgImage.getWidth()-1, jpgImage.getHeight()-1)+filter.mask[1][0]* getRedPixelComponent(jpgImage.getWidth()-2,jpgImage.getHeight()-1)+filter.mask[0][0]* getRedPixelComponent(jpgImage.getWidth()-2, jpgImage.getHeight()-2)+filter.mask[0][1]* getRedPixelComponent(jpgImage.getWidth()-1, jpgImage.getHeight()-2);
        newColorRed=newColorRed/ filter.sumRD;
        newColorRed=checkColor(newColorRed);

        newColorGreen= filter.mask[1][1]* getGreenPixelComponent(jpgImage.getWidth()-1, jpgImage.getHeight()-1)+filter.mask[1][0]* getGreenPixelComponent(jpgImage.getWidth()-2,jpgImage.getHeight()-1)+filter.mask[0][0]* getGreenPixelComponent(jpgImage.getWidth()-2, jpgImage.getHeight()-2 )+filter.mask[0][1]* getGreenPixelComponent(jpgImage.getWidth()-1, jpgImage.getHeight()-2);
        newColorGreen=newColorGreen/ filter.sumRD;
        newColorGreen=checkColor(newColorGreen);

        newColorBlue= filter.mask[1][1]* getBluePixelComponent(jpgImage.getWidth()-1, jpgImage.getHeight()-1)+filter.mask[1][0]* getBluePixelComponent(jpgImage.getWidth()-2,jpgImage.getHeight()-1)+filter.mask[0][0]* getBluePixelComponent(jpgImage.getWidth()-2, jpgImage.getHeight()-2)+filter.mask[0][1]* getBluePixelComponent(jpgImage.getWidth()-1, jpgImage.getHeight()-2);
        newColorBlue=newColorBlue/ filter.sumRD;
        newColorBlue=checkColor(newColorBlue);

        Color colorRD=new Color(newColorRed,newColorGreen,newColorBlue);
        newColor= colorRD.getRGB();
        jpgImage.setRGB(jpgImage.getWidth()-1,jpgImage.getHeight()-1,newColor);
    }

    public void filterOnEdge(Filter newfilter) {
        int newColorRed;
        int newColorGreen;
        int newColorBlue;
        int newColor;
        filter.setFilter(newfilter);

        // up
        for (int i = 1; i < jpgImage.getWidth() - 1; i++) {
            newColorRed = filter.mask[1][0] * getRedPixelComponent(i-1, 0) + filter.mask[1][1] * getRedPixelComponent(i, 0) + filter.mask[1][2] * getRedPixelComponent(i+1, 0) + filter.mask[2][0] * getRedPixelComponent(i-1, 1) + filter.mask[2][1] * getRedPixelComponent(i, 1) + filter.mask[2][2] * getRedPixelComponent( i+1, 1);
            newColorRed = newColorRed / filter.sumU;
            newColorRed = checkColor(newColorRed);

            newColorGreen = filter.mask[1][0] * getGreenPixelComponent(i-1, 0) + filter.mask[1][1] * getGreenPixelComponent(i, 0) + filter.mask[1][2] * getGreenPixelComponent(i+1, 0) + filter.mask[2][0] * getGreenPixelComponent(i-1, 1) + filter.mask[2][1] * getGreenPixelComponent(i, 1) + filter.mask[2][2] * getGreenPixelComponent( i+1, 1);
            newColorGreen = newColorGreen / filter.sumU;
            newColorGreen = checkColor(newColorGreen);

            newColorBlue = filter.mask[1][0] * getBluePixelComponent(i-1, 0) + filter.mask[1][1] * getBluePixelComponent(i, 0) + filter.mask[1][2] * getBluePixelComponent(i+1, 0) + filter.mask[2][0] * getBluePixelComponent(i-1, 1) + filter.mask[2][1] * getBluePixelComponent(i, 1) + filter.mask[2][2] * getBluePixelComponent( i+1, 1);
            newColorBlue = newColorBlue / filter.sumU;
            newColorBlue = checkColor(newColorBlue);

            Color color = new Color(newColorRed, newColorGreen, newColorBlue);
            newColor = color.getRGB();
            jpgImage.setRGB(i, 0, newColor);
        }

        //left
        for (int j = 1; j < jpgImage.getHeight() - 1; j++) {
            newColorRed = filter.mask[0][1] * getRedPixelComponent(0, j-1) + filter.mask[0][2] * getRedPixelComponent( 1, j + 1) + filter.mask[1][1] * getRedPixelComponent(0, j) + filter.mask[1][2] * getRedPixelComponent(0, j + 1) + filter.mask[2][1] * getRedPixelComponent(1, j) + filter.mask[2][2] * getRedPixelComponent(1, j + 1);
            newColorRed = newColorRed / filter.sumL;
            newColorRed=checkColor(newColorRed);

            newColorGreen = filter.mask[0][1] * getGreenPixelComponent(0, j-1) + filter.mask[0][2] * getGreenPixelComponent( 1, j + 1) + filter.mask[1][1] * getGreenPixelComponent(0, j) + filter.mask[1][2] * getGreenPixelComponent(0, j + 1) + filter.mask[2][1] * getGreenPixelComponent(1, j) + filter.mask[2][2] * getGreenPixelComponent(1, j + 1);
            newColorGreen = newColorGreen / filter.sumL;
            newColorGreen=checkColor(newColorGreen);

            newColorBlue = filter.mask[0][1] * getBluePixelComponent(0, j-1) + filter.mask[0][2] * getBluePixelComponent( 1, j + 1) + filter.mask[1][1] * getBluePixelComponent(0, j) + filter.mask[1][2] * getBluePixelComponent(0, j + 1) + filter.mask[2][1] * getBluePixelComponent(1, j) + filter.mask[2][2] * getBluePixelComponent(1, j + 1);
            newColorBlue = newColorBlue / filter.sumL;
            newColorBlue=checkColor(newColorBlue);

            Color color = new Color(newColorRed, newColorGreen, newColorBlue);
            newColor = color.getRGB();
            jpgImage.setRGB(0, j, newColor);
        }

        //right
        for (int j = 1; j < jpgImage.getHeight() - 1; j++) {
            newColorRed = filter.mask[0][0] * getRedPixelComponent(jpgImage.getWidth()-2, j-1) + filter.mask[0][1] * getRedPixelComponent( jpgImage.getWidth()-1, j - 1) + filter.mask[1][0] * getRedPixelComponent(jpgImage.getWidth()-2, j) + filter.mask[1][1] * getRedPixelComponent(jpgImage.getWidth()-1, j) + filter.mask[2][0] * getRedPixelComponent(jpgImage.getWidth()-2, j+1) + filter.mask[2][1] * getRedPixelComponent(jpgImage.getWidth()-1, j + 1);
            newColorRed = newColorRed / filter.sumL;
            newColorRed=checkColor(newColorRed);

            newColorGreen = filter.mask[0][0] * getGreenPixelComponent(jpgImage.getWidth()-2, j-1) + filter.mask[0][1] * getGreenPixelComponent( jpgImage.getWidth()-1, j - 1) + filter.mask[1][0] * getGreenPixelComponent(jpgImage.getWidth()-2, j) + filter.mask[1][1] * getGreenPixelComponent(jpgImage.getWidth()-1, j) + filter.mask[2][0] * getGreenPixelComponent(jpgImage.getWidth()-2, j+1) + filter.mask[2][1] * getGreenPixelComponent(jpgImage.getWidth()-1, j + 1);
            newColorGreen = newColorGreen / filter.sumL;
            newColorGreen=checkColor(newColorGreen);

            newColorBlue = filter.mask[0][0] * getBluePixelComponent(jpgImage.getWidth()-2, j-1) + filter.mask[0][1] * getBluePixelComponent( jpgImage.getWidth()-1, j - 1) + filter.mask[1][0] * getBluePixelComponent(jpgImage.getWidth()-2, j) + filter.mask[1][1] * getBluePixelComponent(jpgImage.getWidth()-1, j) + filter.mask[2][0] * getBluePixelComponent(jpgImage.getWidth()-2, j+1) + filter.mask[2][1] * getBluePixelComponent(jpgImage.getWidth()-1, j + 1);
            newColorBlue = newColorBlue / filter.sumL;
            newColorBlue=checkColor(newColorBlue);

            Color color = new Color(newColorRed, newColorGreen, newColorBlue);
            newColor = color.getRGB();
            jpgImage.setRGB(jpgImage.getWidth()-1, j, newColor);
        }

        //down
        for (int i = 1; i < jpgImage.getWidth() - 1; i++) {
            newColorRed = filter.mask[0][0] * getRedPixelComponent(i-1, jpgImage.getHeight()-2) + filter.mask[0][1] * getRedPixelComponent( i, jpgImage.getHeight()-2) + filter.mask[0][2] * getRedPixelComponent(i+1, jpgImage.getHeight()-2) + filter.mask[1][0] * getRedPixelComponent(i-1, jpgImage.getHeight()-1) + filter.mask[1][1] * getRedPixelComponent(i, jpgImage.getHeight()-1) + filter.mask[1][2] * getRedPixelComponent(i+1, jpgImage.getHeight()-1);
            newColorRed = newColorRed / filter.sumL;
            newColorRed=checkColor(newColorRed);

            newColorGreen = filter.mask[0][0] * getGreenPixelComponent(i-1, jpgImage.getHeight()-2) + filter.mask[0][1] * getGreenPixelComponent( i, jpgImage.getHeight()-2) + filter.mask[0][2] * getGreenPixelComponent(i+1, jpgImage.getHeight()-2) + filter.mask[1][0] * getGreenPixelComponent(i-1, jpgImage.getHeight()-1) + filter.mask[1][1] * getGreenPixelComponent(i, jpgImage.getHeight()-1) + filter.mask[1][2] * getGreenPixelComponent(i+1, jpgImage.getHeight()-1);
            newColorGreen = newColorGreen / filter.sumL;
            newColorGreen=checkColor(newColorGreen);

            newColorBlue = filter.mask[0][0] * getBluePixelComponent(i-1, jpgImage.getHeight()-2) + filter.mask[0][1] * getBluePixelComponent( i, jpgImage.getHeight()-2) + filter.mask[0][2] * getBluePixelComponent(i+1, jpgImage.getHeight()-2) + filter.mask[1][0] * getBluePixelComponent(i-1, jpgImage.getHeight()-1) + filter.mask[1][1] * getBluePixelComponent(i, jpgImage.getHeight()-1) + filter.mask[1][2] * getBluePixelComponent(i+1, jpgImage.getHeight()-1);
            newColorBlue = newColorBlue / filter.sumL;
            newColorBlue=checkColor(newColorBlue);

            Color color = new Color(newColorRed, newColorGreen, newColorBlue);
            newColor = color.getRGB();
            jpgImage.setRGB(i, jpgImage.getHeight()-1, newColor);
        }
    }

}