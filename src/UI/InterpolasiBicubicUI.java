package UI;
import Matrix.*;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.InputMismatchException;

public class InterpolasiBicubicUI {
    public static void interpolasiBicubic(int subchoice, MatrixADT mtx, MatrixADT result, Scanner scanner){
        MatrixADT coefs;
        if (mtx == null){
            System.out.println("Format masukan: "+
            "\nbaris pertama: f(x, y)" +
            "\nbaris kedua: f_x(x, y)" +
            "\nbaris ketiga: f_y(x, y)" +
            "\nbaris keempat: f_xy(x, y)" +
            "\nbaris kelima: x y"
            );
            mtx = new MatrixADT(4, 4);
            mtx.readMatrix(4, 4, scanner);
            double q[][] = new double[1][2];
            q[0][0] = scanner.nextDouble();
            q[0][1] = scanner.nextDouble();
            MatrixADT m = BicubicSplineInterpolation.straighten(mtx);
            coefs = BicubicSplineInterpolation.bicubicSplineCoefs(m);
            MatrixADT res = BicubicSplineInterpolation.bicubicSplineInterpolation(m, new MatrixADT(q));
            System.out.println("Persamaan interpolasi adalah: ");
            BicubicSplineInterpolation.printEquation(coefs);
            System.out.println("Hasil interpolasi: " + res.getElmt(0, 0));
        } else {
            MatrixADT m = BicubicSplineInterpolation.straighten(mtx);
            coefs = BicubicSplineInterpolation.bicubicSplineCoefs(m);
            System.out.println("Persamaan interpolasi adalah: ");
            System.out.println(BicubicSplineInterpolation.toString(mtx, coefs));
        }
        String saveFile = Main.getSaveFileName(scanner);
        if (saveFile != null) {
            try (FileWriter wr = new FileWriter(saveFile, false)){
                wr.write(BicubicSplineInterpolation.toString(mtx, coefs));
                wr.close();
            } catch (Exception e) {
                System.out.println("Gagal :(");
            }
        }
        // String saveFile = Main.getSaveFileName(scanner);
        // if (saveFile != null) {
        //     txtIO.writeTXT(saveFile, inv);
        // }
    }
}
