import java.util.ArrayList;

public class Filter {
    int[][] mask = new int[3][3];
    int[][] sums = new int[3][3];

    public Filter() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.mask[i][j] = 0;
            }
        }
        this.mask[1][1] = 1;
        setSums();
    }

    public Filter(int[][] mask) {
        setFilter(mask);
    }

    public MyImage filterImage(MyImage image) {
        return image;
    }

    public int[][] getMask() {
        return mask;
    }

    public void setFilter(int[][] mask) {
        this.mask = mask;
        setSums();
    }

    public int makeSum(int start_row, int end_row, int start_column, int end_column) {
        int sum = 0;
        for (int i = start_row; i <= end_row; i++) {
            for (int j = start_column; j <= end_column; j++) {
                sum += this.mask[i][j];
            }
        }
        return sum;
    }

    public int correctSum(int sum) {
        if (sum < 1) {
            return 1;
        }
        return sum;
    }

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

    public void setCenterSum() {
        this.sums[1][1] = this.makeSum(0, 2, 0, 2);
        this.sums[1][1] = this.correctSum(this.sums[1][1]);
    }

    public void setRightUpSum() {
        this.sums[0][2] = this.makeSum(0, 1, 1, 2);
        this.sums[0][2] = this.correctSum(this.sums[0][2]);
    }

    public void setLeftUpSum() {
        this.sums[0][0] = this.makeSum(0, 1, 0, 1);
        this.sums[0][0] = this.correctSum(this.sums[0][0]);
    }


    public void setRightDownSum() {
        this.sums[2][2] = this.makeSum(1, 2, 1, 2);
        this.sums[2][2] = this.correctSum(this.sums[2][2]);
    }

    public void setLeftDownSum() {
        this.sums[2][0] = this.makeSum(1, 2, 0, 1);
        this.sums[2][0] = this.correctSum(this.sums[2][0]);
    }

    public void setUpSum() {
        this.sums[0][1] = this.makeSum(1, 2, 0, 2);
        this.sums[0][1] = this.correctSum(this.sums[0][1]);
    }


    public void setLeftSum() {
        this.sums[1][0] = this.makeSum(0, 2, 1, 2);
        this.sums[1][0] = this.correctSum(this.sums[0][1]);
    }

    public void setRightSum() {
        this.sums[1][2] = this.makeSum(0, 2, 0, 1);
        this.sums[1][2] = this.correctSum(this.sums[1][2]);
    }

    public void setDownSum() {
        this.sums[2][1] = this.makeSum(0, 1, 0, 2);
        this.sums[2][1] = this.correctSum(this.sums[2][1]);
    }

    //Setting filters with different parameters

    public void standardHighPassFilter() {
        int[][] maskHP = {{-1, -1, -1}, {-1, 14, -1}, {-1, -1, -1}};
        setFilter(maskHP);
    }

    public void standardLowPassFilter() {
        int[][] maskLP = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
        setFilter(maskLP);
    }

    public void gaussFilter() {
        int[][] gaussMask = {{1, 2, 1}, {2, 4, 2}, {1, 2, 1}};
        setFilter(gaussMask);
    }

    public void horizontalFilter() {
        int[][] horizontalMask = {{0, 0, 0}, {-1, 1, 0}, {0, 0, 0}};
        setFilter(horizontalMask);
    }

    public void verticalFilter() {
        int[][] verticalMask = {{0, -1, 0}, {0, 1, 0}, {0, 0, 0}};
        setFilter(verticalMask);
    }

    public void diagonalFilter() {
        int[][] diagonalMask = {{-1, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        setFilter(diagonalMask);
    }

    public void laplaceFilter() {
        int[][] laplaceMask = {{0, -1, 0}, {-1, 4, -1}, {0, -1, 0}};
        setFilter(laplaceMask);
    }

    public void sobelHorizontalFilter() {
        int[][] sobelMask = {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};
        setFilter(sobelMask);
    }

    public void sobelVerticalFilter() {
        int[][] sobelMask = {{1, 0, -1}, {2, 0, -2}, {1, 0, -1}};
        setFilter(sobelMask);
    }

    public void prewitteHorizontalFilter() {
        int[][] prewitteMask = {{-1, -1, -1}, {0, 0, 0}, {1, 1, 1}};
        setFilter(prewitteMask);
    }

    public void prewitteVerticalFilter() {
        int[][] prewitte = {{1, 0, -1}, {1, 0, -1}, {1, 0, -1}};
        setFilter(prewitte);
    }

    public void noEffectFilter() {
        int[][] doingNothing = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        setFilter(doingNothing);
    }

}