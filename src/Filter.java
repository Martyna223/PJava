public class Filter {

    public Filter()  {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mask[i][j] = 0;
            }
        }
        mask[1][1] = 1;
        setSum();
    }

    public Filter(int a0, int a1, int a2, int b0, int b1, int b2, int c0, int c1, int c2) {
       setFilter(a0, a1, a2, b0, b1, b2, c0, c1, c2);
    }

    public void setFilter(Filter newfilter) {
        this.mask= newfilter.mask;
        setSum();
    }

    public void setFilter(int a0, int a1, int a2, int b0, int b1, int b2, int c0, int c1, int c2) {
        mask[0][0] = a0;
        mask[0][1] = a1;
        mask[0][2] = a2;

        mask[1][0] = b0;
        mask[1][1] = b1;
        mask[1][2] = b2;

        mask[2][0] = c0;
        mask[2][1] = c1;
        mask[2][2] = c2;

        setSum();
    }


    public void standardLowPassFilter() {
        setFilter(1, 1, 1, 1, 1, 1, 1, 1, 1);
    }

    public void standardHighPassFilter() {
        setFilter(0, -1, 0, -1, 5, -1, 0, -1, 0);
    }

    public void HPFilter(){ setFilter(-1, -1, -1, -1, 14, -1, -1, -1,-1);}


    public int[][] getMask(){
        return mask;
    }

    public void setSum() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sum = sum + mask[i][j];
            }
        }
        if (sum < 1) {
            sum = 1;
        }
        setLeftUpSum();
        setRightUpSum();
        setLeftDownSum();
        setRightDownSum();
        setUpSum();
        setLeftSum();
        setRightSum();
        setDownSum();
    }

    public void setRightUpSum() {
        sumRU= mask[0][2]+mask[0][1]+mask[1][2]+mask[1][1];
        if (sumRU < 1) {
            sumRU = 1;
        }
    }

    public void setLeftUpSum() {
        sumLU= mask[0][0]+mask[0][1]+mask[1][0]+mask[1][1];
        if (sumLU < 1) {
            sumLU = 1;
        }
    }

    public void setRightDownSum() {
        sumRD= mask[2][2]+mask[2][1]+mask[1][1]+mask[1][2];
        if (sumRD < 1) {
            sumRD = 1;
        }
    }

    public void setLeftDownSum() {
        sumLD= mask[2][0]+mask[1][0]+mask[2][1]+mask[1][1];
        if (sumLD < 1) {
            sumLD = 1;
        }
    }

    public void setUpSum() {
        for (int i = 1; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sumU = sumU + mask[i][j];
            }
        }
        if (sumU < 1) {
            sumU = 1;
        }
    }

    public void setLeftSum() {
        for (int i = 0; i < 3; i++) {
            for (int j = 1; j < 3; j++) {
                sumL = sumL + mask[i][j];
            }
        }
        if (sumL < 1) {
            sumL = 1;
        }
    }

    public void setRightSum() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                sumR = sumR + mask[i][j];
            }
        }
        if (sumR < 1) {
            sumR = 1;
        }
    }

    public void setDownSum() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                sumD = sumD + mask[i][j];
            }
        }
        if (sumD < 1) {
            sumD = 1;
        }
    }

    int sum=0;
    int sumRU=0;
    int sumLU=0;
    int sumRD=0;
    int sumLD=0;
    int sumU=0;
    int sumL=0;
    int sumR=0;
    int sumD=0;
    int mask[][] = new int[3][3];

}
