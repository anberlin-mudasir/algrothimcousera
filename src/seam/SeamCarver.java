import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {
    private static final double Inf = Double.POSITIVE_INFINITY;
    private static final double BORDER_ENERGY = 1000.0;
    private Picture __picture;
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException("Null picture!");
        __picture = new Picture(picture);
    }
    // current picture
    public Picture picture() {
        return new Picture(__picture);
    }
    // width of current picture
    public int width() {
        return __picture.width();
    }
    // height of current picture
    public int height() {
        return __picture.height();
    }
    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height())
            throw new IllegalArgumentException("Pixel out of range!");
        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1)
            return BORDER_ENERGY;
        Color xu = __picture.get(x - 1, y);
        Color xd = __picture.get(x + 1, y);
        Color yu = __picture.get(x, y - 1);
        Color yd = __picture.get(x, y + 1);
        double xdr = (double) xu.getRed() - xd.getRed();
        xdr = xdr * xdr;
        double xdg = (double) xu.getGreen() - xd.getGreen();
        xdg = xdg * xdg;
        double xdb = (double) xu.getBlue() - xd.getBlue();
        xdb = xdb * xdb;
        double ydr = (double) yu.getRed() - yd.getRed();
        ydr = ydr * ydr;
        double ydg = (double) yu.getGreen() - yd.getGreen();
        ydg = ydg * ydg;
        double ydb = (double) yu.getBlue() - yd.getBlue();
        ydb = ydb * ydb;
        return Math.sqrt(xdr + xdg + xdb + ydr + ydg + ydb);
    }

    private void transposePicture() {
        int w = width();
        int h = height();
        Picture p = new Picture(h, w);
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++)
                p.set(i, j, __picture.get(j, i));
        __picture = p;
    }
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        transposePicture();
        int[] seam = findVerticalSeam();
        transposePicture();
        return seam;
    }
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int w = width();
        int h = height();
        if (w == 1 || h == 1) {
            return new int[h];
        }
        // System.out.println(w);
        // System.out.println(h);
        double[][] energyMatrix = new double[h][w];
        double[][] distToMatrix = new double[h][w];
        int[][] pathToMatrix = new int[h][w];

        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++) {
                energyMatrix[i][j] = energy(j, i);
                distToMatrix[i][j] = Inf;
            }
        for (int i = 0; i < h - 1; i++) {
            for (int j = 0; j < w; j++) {
                int[] adj;
                if (i == 0) {
                    pathToMatrix[i][j] = -1;
                    distToMatrix[i][j] = BORDER_ENERGY;
                }
                if (j == 0) {
                    // Relax j == 0
                    adj = new int[]{j, j + 1};
                } else if (j == w - 1) {
                    // Relax j == w - 1
                    adj = new int[]{j, j - 1};
                } else {
                    adj = new int[]{j, j - 1, j + 1};
                }
                for (int v : adj) {
                    if (distToMatrix[i+1][v] > distToMatrix[i][j] + energyMatrix[i+1][v]) {
                        distToMatrix[i+1][v] = distToMatrix[i][j] + energyMatrix[i+1][v];
                        pathToMatrix[i+1][v] = j;
                    }
                }
            }
        }
        double minDist = Inf;
        int minPath = -1;
        for (int j = 0; j < w; j++) {
            if (distToMatrix[h - 1][j] < minDist) {
                minDist = distToMatrix[h - 1][j];
                minPath = j;
            }
        }
        int[] seam = new int[h];
        seam[h - 1] = minPath;
        for (int i = h - 2; i >= 0; i--) {
            seam[i] = pathToMatrix[i+1][seam[i+1]];
        }
        return seam;
    }
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        transposePicture();
        removeVerticalSeam(seam);
        transposePicture();
    }
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        int w = width();
        int h = height();
        if (seam == null)
            throw new IllegalArgumentException("Null seam!");
        if (w <= 1)
            throw new IllegalArgumentException("Picture width <= 1!");
        if (seam.length != h)
            throw new IllegalArgumentException("Seam wrong length");
        Picture p = new Picture(w - 1, h);
        for (int i = 0; i < h; i++) {
            int s = seam[i];
            if ((s < 0 || s >= w) || (i > 0 && Math.abs(seam[i] - seam[i - 1]) > 1))
                throw new IllegalArgumentException("Seam not valid!");
            for (int j = 0; j < s; j++)
                p.set(j, i, __picture.get(j, i));
            for (int j = s; j < w - 1; j++)
                p.set(j, i, __picture.get(j+1, i));
        }
        __picture = p;
    }

    public static void main(String[] args) {
        Picture p = new Picture("input/seam/10x10.png");
        SeamCarver sc = new SeamCarver(p);
    }
}