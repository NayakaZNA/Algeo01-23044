package UI;
import Matrix.*;
import java.util.Scanner;
import java.util.InputMismatchException;
public class SPLUI {
    public static void spl(int subchoice, MatrixADT mtx, MatrixADT result, Scanner scanner){
        subchoice = -1;
        MatrixADT mtxSPL, mtxy, mtxBalikan;
        while(subchoice < 0 || subchoice > 4) {
            System.out.println(
            "\n0. Kembali" +
            "\n1. Metode Eliminasi Gauss" + 
            "\n2. Metode Eliminasi Gauss-Jordan" + 
            "\n3. Metode matriks balikan" + 
            "\n4. Metode Cramer\n");
            try {
                subchoice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\nMasukan Anda salah!\nMasukkan angka pada menu.\n");
                scanner.nextLine();
            }
        }
        if (subchoice == 0) return;
        int rows = -1;
        int cols = -1;
        if (mtx == null){
            try {
                System.out.println("Masukkan banyak baris matriks transformasi");
                rows = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\nMasukan Anda salah!\n");
                scanner.nextLine();
            }
            try {
                System.out.println("Masukkan banyak kolom matriks transformasi");
                cols = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\nMasukan Anda salah!\n");
                scanner.nextLine();
            }
            System.out.println("Masukkan matriks transformasi");
            mtx = new MatrixADT(rows, cols);
            mtx.readMatrix(rows, cols, scanner);
            System.out.println("Masukkan vektor hasil");
            scanner.nextLine();
            mtxy = new MatrixADT(rows, 1);
            mtxy.readMatrix(rows, 1, scanner);
            mtxSPL = new MatrixADT(rows, cols+1);
            for(int i = 0; i < rows; i++){
                for(int j = 0; j < cols; j++){
                    mtxSPL.setElmt(i, j, mtx.getElmt(i, j));
                }
            }
            for(int i = 0; i < rows; i++){
                mtxSPL.setElmt(i, cols, mtxy.getElmt(i, 0));
            }
        } else {
            mtxSPL = new MatrixADT(mtx.getRows(), mtx.getCols());
            mtxSPL = mtx;
        };
        switch (subchoice) {
            case 1:
                System.out.println();
                System.out.println("================== PENYELESAIAN SPL METODE GAUSS ==================");
                SPLGauss mGauss = new SPLGauss(mtxSPL);
                mGauss.gaussReduction();
                break;
            case 2:
                System.out.println();
                System.out.println("================== PENYELESAIAN SPL METODE GAUSS JORDAN ==================");
                SPLGaussJ mGaussJ = new SPLGaussJ(mtxSPL);
                mGaussJ.gaussJordanElimination();
                break;
            case 3:
                System.out.println();
                System.out.println("================== PENYELESAIAN SPL METODE BALIKAN ==================");
                SPLBalikan.solve(mtxSPL);
                // SPLBalikan.displaySolution(SPLBalikan.solve(mtx));
                // System.out.println("");
                break;
            case 4:
                System.out.println();
                System.out.println("================== PENYELESAIAN SPL METODE CRAMER ==================");
                SPLCramer.solve(mtxSPL);
                // SPLBalikan.displaySolution(SPLCramer.solve(mtx));
                // System.out.println("");
                break;
            default:
                break;
        }
    }
}
