import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RenderedImage;

/**
 * Reprezentacja filtrow Gabora - do detekcji krawedzi
 */
public class GaborFilter {

    private static final double MIN_ASPECT_RATIO = 0;
    private static final double MAX_ASPECT_RATIO = 1;

    private double[] orientations;
    private double waveLength;
    private double phaseOffset;
    private double aspectRatio;
    private double bandwidth;
    private int width;
    private int height;

    /**
     * Podstawowy konstruktor
     *
     * @param waveLength dlugosc fali
     * @param orientations kierunek dzialania filtru
     * @param phaseOffset przesuniecie fazowe - symetria
     * @param aspectRatio wspolczynnik aspektu - eliptycznosc
     * @param bandwidth szerokosc pasma
     * @param width szerokosc
     * @param height wysokosc
     */
    public GaborFilter(double waveLength, double[] orientations, double phaseOffset, double aspectRatio, double bandwidth, int width, int height) {
        this.waveLength = waveLength;
        this.orientations = orientations;
        this.phaseOffset = phaseOffset;
        this.aspectRatio = aspectRatio;
        this.bandwidth = bandwidth;
        this.width = width;
        this.height = height;
    }

    /**
     * Pobiera tablice orientacji
     *
     * @return tablica orientacji
     */
    public double[] getOrientations() {
        return orientations;
    }

    /**
     * Ustawia tablice orientacji
     *
     * @param orientations nowa tablica orientacji
     */
    public void setOrientations(double[] orientations) {
        this.orientations = orientations;
    }

    /**
     * Pobiera dlugosc fali
     *
     * @return dlugosc fali
     */
    public double getWaveLength() {
        return waveLength;
    }

    /**
     * Ustawia dlugosc fali
     *
     * @param waveLength nowa dlugosc fali
     */
    public void setWaveLength(double waveLength) {
        if(waveLength > 0) {
            this.waveLength = waveLength;
        } else {
            System.out.println("The Wave Length should be a positive number");
        }
    }

    /**
     * Pobiera przesuniecie fazowe
     *
     * @return przesuniecie fazowe
     */
    public double getPhaseOffset() {
        return phaseOffset;
    }

    /**
     * Ustawia przesuniecie fazowe
     *
     * @param phaseOffset nowe przesuniecie fazowe
     */
    public void setPhaseOffset(double phaseOffset) {
        this.phaseOffset = phaseOffset;
    }

    /**
     * Pobiera wspolczynnik aspektu
     *
     * @return wspolczynnik aspektu
     */
    public double getAspectRatio() {
        return aspectRatio;
    }

    /**
     * Ustawia wspolczynnik aspektu
     *
     * @param aspectRatio nowy wspolczynnik aspektu
     */
    public void setAspectRatio(double aspectRatio) {
        if(aspectRatio <= MAX_ASPECT_RATIO && aspectRatio >= MIN_ASPECT_RATIO) {
            this.aspectRatio = aspectRatio;

        } else {
            System.out.println("The Aspect Ratio should be in the range [" + MIN_ASPECT_RATIO + "; " + MAX_ASPECT_RATIO +"]");
        }
    }

    /**
     * Pobiera szerokosc pasma
     *
     * @return szerokosc pasma
     */
    public double getBandwidth() {
        return bandwidth;
    }

    /**
     * Ustawia szerokosc pasma
     *
     * @param bandwidth nowa szerokosc pasma
     */
    public void setBandwidth(double bandwidth) {
        this.bandwidth = bandwidth;
    }

    /**
     * Oblicza sigme dla podanej dlugosci fali oraz szerokosci pasma
     *
     * @param waveLength dlugosc fali
     * @param bandwidth szerokosc pasma
     * @return sigma (odchylenie standardowe)
     */

    private static double calculateSigma(double waveLength, double bandwidth) {
        return waveLength*Math.sqrt(Math.log(2)/2)*(Math.pow(2, bandwidth) + 1)/((Math.pow(2, bandwidth) - 1)*Math.PI);
    }

    /**
     * Oblicza wartosci funkcji Gabora dla podanych danych
     *
     * @param x X
     * @param y Y
     * @param sigma sigma
     * @param aspectRatio wspolczynnik aspektu
     * @param waveLength dlugosc fali
     * @param phaseOffset przesuniecie fazowe
     * @return wartosc funkcji Gabora
     */

    private static double gaborFunction(double x, double y, double sigma, double aspectRatio, double waveLength, double phaseOffset) {
        double gaborReal = Math.exp(-(Math.pow(x/sigma, 2) + Math.pow(y*aspectRatio/sigma, 2))/2)*Math.cos(2*Math.PI*x/waveLength + phaseOffset);
        double gaborImage = Math.exp(-(Math.pow(x/sigma, 2) + Math.pow(y*aspectRatio/sigma, 2))/2)*Math.sin(2*Math.PI*x/waveLength + phaseOffset);
        return Math.sqrt(Math.pow(gaborReal, 2) + Math.pow(gaborImage, 2));
    }

    /**
     * Zwraca obiekt ConvolveOp (implementacje splotu)
     *
     * @return ConvolveOp (implementacja splotu)
     */
    public ConvolveOp getConvolveOp() {
        return new ConvolveOp(getKernel(), ConvolveOp.EDGE_NO_OP, null);
    }

    /**
     * Pobiera jadro filtru Gabora
     *
     * @return jadro
     */
    public Kernel getKernel() {
        double sigma = calculateSigma(waveLength, bandwidth);
        float[] data = new float[width*height];
        for(int k = 0, x = -width/2; x <= width/2; x++) {
            for(int y = -height/2; y <= height/2; y++) {
                for(double orientation : orientations) {
                    double x1 = x*Math.cos(orientation) + y*Math.sin(orientation);
                    double y1 = -x*Math.sin(orientation) + y*Math.cos(orientation);
                    data[k] += (float)(gaborFunction(x1, y1, sigma, aspectRatio, waveLength, phaseOffset));
                }
                k++;
            }
        }
        float sum = 0f;
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                sum += data[i*j + j];
            }
        }
        sum /= width*height;
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                data[i*j + j] -= sum;
            }
        }
        return new Kernel(width, height, data);
    }

    /**
     * Pobiera szerokosc
     *
     * @return szerokosc
     */
    public int getWidth() {
        return width;
    }

    /**
     * Ustawia szerokosc
     *
     * @param width nowa szerokosc
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Pobiera wysokosc
     *
     * @return wysokosc
     */
    public int getHeight() {
        return height;
    }

    /**
     * Ustawia wysokosc
     *
     * @param height nowa wysokosc
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Filtruje obiekt bufferedImage uzywajac filtru Gabora. Jesli bufferedImageDestination nie jest
     * null, bufferedImage uzywany jest jako cel
     *
     * @param bufferedImage zrodlowy bufferedImage
     * @param bufferedImageDestination docelowy bufferedImage
     * @return przetworzony obraz
     */
    public RenderedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImageDestination) {
        return getConvolveOp().filter(bufferedImage, bufferedImageDestination);
    }
}