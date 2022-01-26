/**
* Reprezentacja filtru macierzowego uzywajacego masek
 */
public class Filter {
    /**
     * macierz zawierajaca maske
     */
    int[][] mask = new int[3][3];
    /**
     * macierz zawierajaca sumy znormalizowane
     */
    int[][] sums = new int[3][3];

    /**
     * Podstawowy konstruktor
     */
    public Filter() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.mask[i][j] = 0;
            }
        }
        this.mask[1][1] = 1;
        setSums();
    }

    /**
     * Konstruktor z podana maska
     *
     * @param mask maska
     */
    public Filter(int[][] mask) {
        setFilter(mask);
    }

    /**
     * Dokonuje filtracji na obrazie
     *
     * @param image podany obraz
     * @return przefiltrowany obraz
     */
    public MyImage filterImage(MyImage image) {
        return image;
    }

    /**
     * Pobiera maske
     *
     * @return maska
     */
    public int[][] getMask() {
        return mask;
    }

    /**
     * Ustawia maske filtru
     *
     * @param mask maska
     */
    public void setFilter(int[][] mask) {
        this.mask = mask;
        setSums();
    }

    /**
     * Oblicza wspolczynniki
     *
     * @param start_row poczatkowy rzad
     * @param end_row koncowy rzad
     * @param start_column poczatkowa kolumna
     * @param end_column koncowa kolumna
     * @return macierz wspolczynnikow
     */
    public int makeSum(int start_row, int end_row, int start_column, int end_column) {
        int sum = 0;
        for (int i = start_row; i <= end_row; i++) {
            for (int j = start_column; j <= end_column; j++) {
                sum += this.mask[i][j];
            }
        }
        return sum;
    }

    /**
     * Normalizuje wspolczynniki
     *
     * @param sum wspolczynniki do znormalizowania
     * @return macierz znormalizowanych wspolczynnikow
     */
    public int correctSum(int sum) {
        if (sum==0){
            return 1;
        }
        if (sum < 1) {
            sum= -sum;
        }
        return sum;
    }

    /**
     * Liczy sumy dla poszczegolnych miejsc macierzy
     */
    public void setSums() {
        setCenterSum();
        setLeftUpSum();
        setRightUpSum();
        setLeftDownSum();
        setDownSum();
        setRightDownSum();
        setUpSum();
        setLeftSum();
        setRightSum();
    }

    /**
     * Oblicza sume w srodku
     */
    public void setCenterSum() {
        this.sums[1][1] = this.makeSum(0, 2, 0, 2);
        this.sums[1][1] = this.correctSum(this.sums[1][1]);
    }

    /**
     * Oblicza sume w prawym gornym narozniku
     */
    public void setRightUpSum() {
        this.sums[0][2] = this.makeSum(0, 1, 1, 2);
        this.sums[0][2] = this.correctSum(this.sums[0][2]);
    }

    /**
     * Oblicza sume w lewym gornym narozniku
     */
    public void setLeftUpSum() {
        this.sums[0][0] = this.makeSum(0, 1, 0, 1);
        this.sums[0][0] = this.correctSum(this.sums[0][0]);
    }

    /**
     * Oblicza sume w prawym dolnym narozniku
     */
    public void setRightDownSum() {
        this.sums[2][2] = this.makeSum(1, 2, 1, 2);
        this.sums[2][2] = this.correctSum(this.sums[2][2]);
    }

    /**
     * Oblicza sume w lewym dolnym narozniku
     */
    public void setLeftDownSum() {
        this.sums[2][0] = this.makeSum(1, 2, 0, 1);
        this.sums[2][0] = this.correctSum(this.sums[2][0]);
    }

    /**
     * Oblicza sume w gornym srodkowym polu
     */
    public void setUpSum() {
        this.sums[0][1] = this.makeSum(1, 2, 0, 2);
        this.sums[0][1] = this.correctSum(this.sums[0][1]);
    }

    /**
     * Oblicza sume w lewym srodkowym polu
     */
    public void setLeftSum() {
        this.sums[1][0] = this.makeSum(0, 2, 1, 2);
        this.sums[1][0] = this.correctSum(this.sums[0][1]);
    }

    /**
     * Oblicza sume w prawym srodkowym polu
     */
    public void setRightSum() {
        this.sums[1][2] = this.makeSum(0, 2, 0, 1);
        this.sums[1][2] = this.correctSum(this.sums[1][2]);
    }

    /**
     * Oblicza sume w dolnym srodkowym polu
     */
    public void setDownSum() {
        this.sums[2][1] = this.makeSum(0, 1, 0, 2);
        this.sums[2][1] = this.correctSum(this.sums[2][1]);
    }

    /**
     * Ustawia standardowy gornoprzepustowy filtr
     */
    public void standardHighPassFilter() {
        int[][] maskHP = {{-1, -1, -1}, {-1, 14, -1}, {-1, -1, -1}};
        setFilter(maskHP);
    }

    /**
     * Ustawia standardowy dolnoprzepustowy filtr
     */
    public void standardLowPassFilter() {
        int[][] maskLP = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
        setFilter(maskLP);
    }

    /**
     * Ustawia filtr Gaussa
     */
    public void gaussFilter() {
        int[][] gaussMask = {{1, 2, 1}, {2, 4, 2}, {1, 2, 1}};
        setFilter(gaussMask);
    }

    /**
     * Ustawia filtr poziomy
     */
    public void horizontalFilter() {
        int[][] horizontalMask = {{0, 0, 0}, {-1, 1, 0}, {0, 0, 0}};
        setFilter(horizontalMask);
    }

    /**
     * Ustawia filtr pionowy
     */
    public void verticalFilter() {
        int[][] verticalMask = {{0, -1, 0}, {0, 1, 0}, {0, 0, 0}};
        setFilter(verticalMask);
    }

    /**
     * Ustawia filtr ukosny
     */
    public void diagonalFilter() {
        int[][] diagonalMask = {{-1, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        setFilter(diagonalMask);
    }

    /**
     * Ustawia filtr Laplace'a
     */
    public void laplaceFilter() {
        int[][] laplaceMask = {{0, -1, 0}, {-1, 4, -1}, {0, -1, 0}};
        setFilter(laplaceMask);
    }

    /**
     * Ustawia filtr poziomy Sobela
     */
    public void sobelHorizontalFilter() {
        int[][] sobelMask = {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};
        setFilter(sobelMask);
    }

    /**
     * Ustawia filtr pionowy Sobela
     */
    public void sobelVerticalFilter() {
        int[][] sobelMask = {{1, 0, -1}, {2, 0, -2}, {1, 0, -1}};
        setFilter(sobelMask);
    }

    /**
     * Ustawia filtr poziomy Prewitte'a
     */
    public void prewitteHorizontalFilter() {
        int[][] prewitteMask = {{-1, -1, -1}, {0, 0, 0}, {1, 1, 1}};
        setFilter(prewitteMask);
    }

    /**
     * Ustawia filtr pionowy Prewitte'a
     */
    public void prewitteVerticalFilter() {
        int[][] prewitte = {{1, 0, -1}, {1, 0, -1}, {1, 0, -1}};
        setFilter(prewitte);
    }

    /**
     * Ustawia filtr "nic-nie-robiacy"
     */
    public void noEffectFilter() {
        int[][] doingNothing = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        setFilter(doingNothing);
    }
}