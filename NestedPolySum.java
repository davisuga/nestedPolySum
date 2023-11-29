public class NestedPolySum {
    static final int iterLim = 10000;
    static final int kLim = 10000;

    static double[][] MEM = new double[iterLim][kLim];

    static double func(long x) {
        return 6e-40 + 2e-40 * x - 3e-40 * x * x - 3e-40 * x * x * x - 3e-40 * x * x * x * x + 1e-40 * x * x * x * x * x;
    }

    static void memoization(int a, int x, int k) {
        if (k == 0) {
            MEM[x][0] = func(x);
        } else if (x == a) {
            MEM[a][k] = MEM[a][k - 1];
        } else {
            MEM[x][k] = MEM[x - 1][k] + MEM[x][k - 1];
        }
    }

    public static void main(String[] args) {
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);
        for (int x = 0; x < iterLim; x++) {
            for (int y = 0; y < kLim; y++) {
                MEM[x][y] = 0.0;
            }
        }

        for (int j = 0; j <= k; j++) {
            for (int i = a; i <= b; i++) {
                memoization(a, i, j);
            }
        }
        long start = System.currentTimeMillis();
        for (int x = 0; x < iterLim; x++) {
            for (int y = 0; y < kLim; y++) {
                MEM[x][y] = 0.0;
            }
        }

        for (int j = 0; j <= k; j++) {
            for (int i = a; i <= b; i++) {
                memoization(a, i, j);
            }
        }
        long end = System.currentTimeMillis();
        
        System.out.println(MEM[b][k]);
        System.out.println("Time: " + (end - start) + "ms");
    }
}