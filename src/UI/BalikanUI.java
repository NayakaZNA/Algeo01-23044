package UI;
import java.util.Scanner;

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
                inv = InverseGaussJ.inverseGaussJ(mtx);
                break;
             case 2:
                inv = InverseAdjoin.inverseAdj(mtx);
                if (inv != null) {
                    System.out.println("Balikan Matriks adalah: ");
                    inv.printMatrix();
                } else System.out.println("Matriks tidak memiliki invers");
                break;
            default:
                inv = null;
                break;
        }
        if (inv != null) {
            System.out.println("Balikan Matriks adalah: ");
            inv.printMatrix();
        } else System.out.println("Matriks tidak memiliki invers");
    }
}
