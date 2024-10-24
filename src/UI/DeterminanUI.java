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
            "\n1. Metode reduksi baris" + 
            "\n2. Metode kofaktor\n");
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
        switch (subchoice) {
            case 1:
                System.out.println("Determinan matriks adalah: " + DeterminanReduksi.detRB(mtx));
                break;
            case 2:
                System.out.println("Determinan matriks adalah: " + DeterminanMK.detMK(mtx));
                break;
            default:
                break;
        }
        String saveFile = Main.getSaveFileName(scanner);
         if (saveFile != null) {
         }
    }
}
