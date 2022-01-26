/**
* Reprezentacja filtru macierzowego używajacego masek
 */
public class Filter {
    /**
     * macierz zawierająca maskę
     */
    int[][] mask = new int[3][3];
    /**
     * macierz zawierająca sumy znormalizowane
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
     * Konstruktor z podaną maską
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
     * Pobiera maskę
     *
     * @return maska
     */
    public int[][] getMask() {
        return mask;
    }

    /**
     * Ustawia maskę filtru
     *
     * @param mask maska
     */
    public void setFilter(int[][] mask) {
        this.mask = mask;
        setSums();
    }

    /**
     * Oblicza współczynniki
     *
     * @param start_row początkowy rząd
     * @param end_row końcowy rząd
     * @param start_column początkowa kolumna
     * @param end_column końcowa kolumna
     * @return macierz współczynników
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
     * Normalizuje współczynniki
     *
     * @param sum współczynniki do znormalizowania
     * @return macierz znormalizowanych współczynników
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
     * Liczy sumy dla poszczególnych miejsc macierzy
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
     * Oblicza sumę w środkowu
     */
    public void setCenterSum() {
        this.sums[1][1] = this.makeSum(0, 2, 0, 2);
        this.sums[1][1] = this.correctSum(this.sums[1][1]);
    }

    /**
     * Oblicza sumę w prawym górnym narożniku
     */
    public void setRightUpSum() {
        this.sums[0][2] = this.makeSum(0, 1, 1, 2);
        this.sums[0][2] = this.correctSum(this.sums[0][2]);
    }

    /**
     * Oblicza sumę w lewym górnym narożniku
     */
    public void setLeftUpSum() {
        this.sums[0][0] = this.makeSum(0, 1, 0, 1);
        this.sums[0][0] = this.correctSum(this.sums[0][0]);
    }

    /**
     * Oblicza sumę w prawym dolnym narożniku
     */
    public void setRightDownSum() {
        this.sums[2][2] = this.makeSum(1, 2, 1, 2);
        this.sums[2][2] = this.correctSum(this.sums[2][2]);
    }

    /**
     * Oblicza sumę w lewym dolnym narożniku
     */
    public void setLeftDownSum() {
        this.sums[2][0] = this.makeSum(1, 2, 0, 1);
        this.sums[2][0] = this.correctSum(this.sums[2][0]);
    }

    /**
     * Oblicza sumę w górnym środkowym polu
     */
    public void setUpSum() {
        this.sums[0][1] = this.makeSum(1, 2, 0, 2);
        this.sums[0][1] = this.correctSum(this.sums[0][1]);
    }

    /**
     * Oblicza sumę w lewym środkowym polu
     */
    public void setLeftSum() {
        this.sums[1][0] = this.makeSum(0, 2, 1, 2);
        this.sums[1][0] = this.correctSum(this.sums[0][1]);
    }

    /**
     * Oblicza sumę w prawym środkowym polu
     */
    public void setRightSum() {
        this.sums[1][2] = this.makeSum(0, 2, 0, 1);
        this.sums[1][2] = this.correctSum(this.sums[1][2]);
    }

    /**
     * Oblicza sumę w dolnym środkowym polu
     */
    public void setDownSum() {
        this.sums[2][1] = this.makeSum(0, 1, 0, 2);
        this.sums[2][1] = this.correctSum(this.sums[2][1]);
    }

    /**
     * Ustawia standardowy górnoprzepustowy filtr
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
     * Ustawia filtr ukośny
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
     * Ustawia filtr "nic-nie-robiący"
     */
    public void noEffectFilter() {
        int[][] doingNothing = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        setFilter(doingNothing);
    }
}