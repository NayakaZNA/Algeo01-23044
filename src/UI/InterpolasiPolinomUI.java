package UI;
import java.util.Scanner;
import Matrix.*;

import java.io.FileWriter;
import java.util.InputMismatchException;

public class InterpolasiPolinomUI {
    public static void interpolasiPolinom(int subchoice, MatrixADT mtx, MatrixADT result, Scanner scanner){
        String s = "";
        if (mtx == null){
            int points = -1;
            while (true){
                try {
                    System.out.println("Masukkan banyak titik");
                    points = scanner.nextInt();
                    if (points > 0) break;
                } catch (InputMismatchException e) {;}
                System.out.println("\nMasukan Anda salah!\n");
                scanner.nextLine();
                subchoice = -1;
            }
            System.out.println("Masukkan titik-titik yang diketahui");
            mtx = new MatrixADT(points, 2);
            mtx.readMatrix(points, 2, scanner);
        }
        MatrixADT coefs = InterpolasiPolinom.PolynomialCoefficients(mtx);
        if (coefs == null) System.err.println("Tidak ada solusi");
        else {
            System.out.println("Persamaan interpolasi adalah: ");
            s = InterpolasiPolinom.displaySolution(coefs);
        }
        System.out.println();
        String saveFile = Main.getSaveFileName(scanner);
        if (saveFile != null) {
            try (FileWriter wr = new FileWriter(saveFile, false)){
                wr.write(s);
                wr.close();
            } catch (Exception e) {
                System.out.println("Gagal :(");
            }
        }
        return;
    }
}
