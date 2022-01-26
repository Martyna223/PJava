import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RenderedImage;

/**
 * Reprezentacja filtrów Gabora - do detekcji krawędzi
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
     * @param waveLength długość fali
     * @param orientations kierunek działania filtru
     * @param phaseOffset przesunięcie fazowe - symetria
     * @param aspectRatio współczynnik aspektu - eliptyczność
     * @param bandwidth szerokość pasma
     * @param width szerokość
     * @param height wysokość
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
     * Pobiera tablicę orientacji
     *
     * @return tablica orientacji
     */
    public double[] getOrientations() {
        return orientations;
    }

    /**
     * Ustawia tablicę orientacji
     *
     * @param orientations nowa tablica orientacji
     */
    public void setOrientations(double[] orientations) {
        this.orientations = orientations;
    }

    /**
     * Pobiera długość fali
     *
     * @return długość fali
     */
    public double getWaveLength() {
        return waveLength;
    }

    /**
     * Ustawia długość fali
     *
     * @param waveLength nowa długość fali
     */
    public void setWaveLength(double waveLength) {
        if(waveLength > 0) {
            this.waveLength = waveLength;
        } else {
            System.out.println("The Wave Length should be a positive number");
        }
    }

    /**
     * Pobiera przesunięcie fazowe
     *
     * @return przesunięcie fazowe
     */
    public double getPhaseOffset() {
        return phaseOffset;
    }

    /**
     * Ustawia przesunięcie fazowe
     *
     * @param phaseOffset nowe przesunięcie fazowe
     */
    public void setPhaseOffset(double phaseOffset) {
        this.phaseOffset = phaseOffset;
    }

    /**
     * Pobiera współczynnik aspektu
     *
     * @return współczynnik aspektu
     */
    public double getAspectRatio() {
        return aspectRatio;
    }

    /**
     * Ustawia współczynnik aspektu
     *
     * @param aspectRatio nowy współczynnik aspektu
     */
    public void setAspectRatio(double aspectRatio) {
        if(aspectRatio <= MAX_ASPECT_RATIO && aspectRatio >= MIN_ASPECT_RATIO) {
            this.aspectRatio = aspectRatio;

        } else {
            System.out.println("The Aspect Ratio should be in the range [" + MIN_ASPECT_RATIO + "; " + MAX_ASPECT_RATIO +"]");
        }
    }

    /**
     * Pobiera szerokość pasma
     *
     * @return szerokość pasma
     */
    public double getBandwidth() {
        return bandwidth;
    }

    /**
     * Ustawia szerokość pasma
     *
     * @param bandwidth nowa szerokość pasma
     */
    public void setBandwidth(double bandwidth) {
        this.bandwidth = bandwidth;
    }

    /**
     * Oblicza sigmę dla podanej długości fali oraz szerokości pasma
     *
     * @param waveLength długość fali
     * @param bandwidth szerokość pasma
     * @return sigma (odchylenie standardowe)
     */

    private static double calculateSigma(double waveLength, double bandwidth) {
        return waveLength*Math.sqrt(Math.log(2)/2)*(Math.pow(2, bandwidth) + 1)/((Math.pow(2, bandwidth) - 1)*Math.PI);
    }

    /**
     * Oblicza wartości funkcji Gabora dla podanych danych
     *
     * @param x X
     * @param y Y
     * @param sigma sigma
     * @param aspectRatio współczynnik aspektu
     * @param waveLength długość fali
     * @param phaseOffset przesunięcie fazowe
     * @return wartość funkcji Gabora
     */

    private static double gaborFunction(double x, double y, double sigma, double aspectRatio, double waveLength, double phaseOffset) {
        double gaborReal = Math.exp(-(Math.pow(x/sigma, 2) + Math.pow(y*aspectRatio/sigma, 2))/2)*Math.cos(2*Math.PI*x/waveLength + phaseOffset);
        double gaborImage = Math.exp(-(Math.pow(x/sigma, 2) + Math.pow(y*aspectRatio/sigma, 2))/2)*Math.sin(2*Math.PI*x/waveLength + phaseOffset);
        return Math.sqrt(Math.pow(gaborReal, 2) + Math.pow(gaborImage, 2));
    }

    /**
     * Zwraca obiekt ConvolveOp (implementację splotu)
     *
     * @return ConvolveOp (implementacja splotu)
     */
    public ConvolveOp getConvolveOp() {
        return new ConvolveOp(getKernel(), ConvolveOp.EDGE_NO_OP, null);
    }

    /**
     * Pobiera jądro filtru Gabora
     *
     * @return jądro
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
     * Pobiera szerokość
     *
     * @return szerokość
     */
    public int getWidth() {
        return width;
    }

    /**
     * Ustawia szerokość
     *
     * @param width nowa szerokość
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Pobiera wysokość
     *
     * @return wysokość
     */
    public int getHeight() {
        return height;
    }

    /**
     * Ustawia wysokość
     *
     * @param height nowa wysokość
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Filtruje obiekt bufferedImage używając filtru Gabora. Jeśli bufferedImageDestination nie jest
     * null, bufferedImage używany jest jako cel
     *
     * @param bufferedImage źródłowy bufferedImage
     * @param bufferedImageDestination docelowy bufferedImage
     * @return przetworzony obraz
     */
    public RenderedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImageDestination) {
        return getConvolveOp().filter(bufferedImage, bufferedImageDestination);
    }
}