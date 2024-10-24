import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import Matrix.MatrixADT;

import java.util.Scanner;

public class ImageResizing {

    public static Matrix.MatrixADT generateDMatrix() {
        Matrix.MatrixADT D = new Matrix.MatrixADT(16, 16);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                D.setElmt(i, j, 0);
            }
        }
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 2; i++) {
                D.setElmt(i + 2*j, 4*j+i+5, 4);
                D.setElmt(i + 2*j + 4, 4*j+i+6, 2);
                D.setElmt(i + 2*j + 4, 4*j+i+4, -2);
                D.setElmt(i + 2*j + 8, 4*j+i+9, 1);
                D.setElmt(i + 2*j + 8, 4*j+i+1, -1);
                D.setElmt(i + 2*j + 12, 4*j+i+10, 1);
                D.setElmt(i + 2*j + 12, 4*j+i+5, 1);
                D.setElmt(i + 2*j + 12, 4*j+i+4, -1);
                D.setElmt(i + 2*j + 12, 4*j+i+1, -1);
            }
        }
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                D.setElmt(i, j, D.getElmt(i, j) * 0.25);
            }
        }
    return D;
    }

    // public static Matrix.MatrixADT ImageResizeCoefs(Matrix.MatrixADT Image) {
        
    //     return Image;
    // }

    public static MatrixADT loadImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        // Mendapatkan dimensi matriks
        int width = image.getWidth();
        int height = image.getHeight();
        // Membuat matriks
        MatrixADT matrix = new MatrixADT(height, width);
        // Mengisi matriks dengan ARGB
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = image.getRGB(x, y);
                matrix.setElmt(y, x, argb); 
            }
        }
        return matrix; 
    }

    public static void saveImage(MatrixADT matrix, String path, int length, int height) {
        BufferedImage image = new BufferedImage(length, height, BufferedImage.TYPE_INT_ARGB);

        // Mengisi pixel (x, y) dengan warna
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < length; x++) {
                int argb = (int) matrix.getElmt(y, x);
                image.setRGB(x, y, argb);
            }
        }
        try {
            ImageIO.write(image, "png", new File(path)); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String filename = scan.nextLine();
        MatrixADT mtx;
        mtx = loadImage(filename);
        saveImage(mtx, "nigga.png", mtx.nCols, mtx.nRows);
        scan.close();
    }
}
