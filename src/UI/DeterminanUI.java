package UI;
import Matrix.*;
import java.util.Scanner;
import java.util.InputMismatchException;

public class DeterminanUI{
    public static void determinan(int subchoice, MatrixADT mtx, MatrixADT result, Scanner scanner){
        subchoice = -1;
        while(subchoice < 0 || subchoice > 2) {
            System.out.println(
            "\n0. Kembali" +
            "\n1. Metode Reduksi Baris" + 
            "\n2. Metode Kofaktor\n");
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
            try {
                System.out.println("Masukkan dimensi matriks persegi");
                dimension = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\nMasukan Anda salah!\n");
                scanner.nextLine();
                subchoice = -1;
            }
            System.out.println("Masukkan matriks\n");
            mtx = new MatrixADT(dimension, dimension);
            mtx.readMatrix(dimension, dimension, scanner);
        } else if (mtx.getRows() != mtx.getCols()) {
            System.out.println("Dimensi matriks tidak seusai");
            return;
        }
        double DET = 0;
        switch (subchoice) {
            case 1:
                double det = DeterminanReduksi.detRB(mtx);
                DET = det;
                System.out.println();
                System.out.println("================== DETERMINAN METODE REDUKSI BARIS ==================");
                if (Math.abs(det) < 1e-9) {
                    System.out.printf("Determinan : %.6e\n", det);
                } else {
                    System.out.printf("Determinan : %.6f\n", det);
                }
                break;
            case 2:
                double det2 = DeterminanMK.detMK(mtx);
                DET = det2;
                System.out.println();
                System.out.println("================== DETERMINAN EKSPANSI KOFAKTOR ==================");
                if (Math.abs(det2) < 1e-9) {
                    System.out.printf("Determinan : %.6e\n", det2);
                } else {
                    System.out.printf("Determinan : %.6f\n", det2);
                }
                break;
            default:
                break;
        }
        String saveFile = Main.getSaveFileName(scanner);
         if (saveFile != null) {
            MatrixADT out = new MatrixADT(new double[][]{{DET}});
            txtIO.writeTXT(saveFile, out);
         }
    }
}
