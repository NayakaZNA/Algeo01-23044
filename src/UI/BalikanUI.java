package UI;
import Matrix.*;
import java.util.Scanner;
import java.util.InputMismatchException;

public class BalikanUI {
    public static void balikan(int subchoice, MatrixADT mtx, MatrixADT result, Scanner scanner){
        subchoice = -1;
        while(subchoice < 0 || subchoice > 2) {
            System.out.println(
            "\n0. Kembali" + 
            "\n1. Metode Gauss-Jordan" + 
            "\n2. Metode Adjoin\n");
            try {
                subchoice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\nMasukan Anda salah!\nMasukkan angka pada menu.");
                scanner.nextLine();
                subchoice = -1;
            }
        }
        if (subchoice == 0) return;
        if (mtx == null){
            int dimension = -1;
            while (true){
                try {
                    System.out.println("Masukkan dimensi matriks persegi");
                    dimension = scanner.nextInt();
                    if (dimension > 0) break;
                } catch (InputMismatchException e) {;}
                System.out.println("\nMasukan Anda salah!\n");
                scanner.nextLine();
                subchoice = -1;
            }
            System.out.println("Masukkan matriks");
            mtx = new MatrixADT(dimension, dimension);
            mtx.readMatrix(dimension, dimension, scanner);
        }
        MatrixADT inv;
        switch (subchoice) {
            case 1:
                System.out.println();
                System.out.println("================== PENYELESAIAN INVERSE METODE GAUSS JORDAN ==================");
                inv = InverseGaussJ.inverseGaussJ(mtx);
                if (inv != null) {
                    inv.printMatrix();
                } else {
                    System.out.println("Matriks tidak memiliki invers");
                }
                break;
             case 2:
                System.out.println();
                System.out.println("================== PENYELESAIAN INVERSE METODE ADJOIN ==================");
                inv = InverseAdjoin.inverseAdj(mtx);
                if (inv != null) {
                    inv.printMatrix();
                } else {
                    System.out.println("Matriks tidak memiliki invers");
                }
                break;
            default:
                inv = null;
                break;
        }
        String saveFile = Main.getSaveFileName(scanner);
        if (saveFile != null) {
           txtIO.writeTXT(saveFile, inv);
        }
    }
}
