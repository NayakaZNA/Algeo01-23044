package UI;
import Matrix.*;
import java.util.Scanner;
import java.util.InputMismatchException;
public class SPLUI {
    public static void spl(int subchoice, MatrixADT mtx, MatrixADT result, Scanner scanner){
        subchoice = -1;
        MatrixADT mtxSPL, mtxy;
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
        int vars = -1;
        if (mtx == null){
            try {
                System.out.println("Masukkan jumlah variabel");
                vars = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\nMasukan Anda salah!\n");
                scanner.nextLine();
            }
            System.out.println("Masukkan matriks transformasi");
            mtx = new MatrixADT(vars, vars);
            mtx.readMatrix(vars, vars, scanner);
            System.out.println("Masukkan vektor hasil");
            mtxy = new MatrixADT(vars, 1);
            mtxy.readMatrix(vars, 1, scanner);
            mtxSPL = new MatrixADT(vars, vars+1);
            for(int i = 0; i < vars; i++){
                for(int j = 0; j < vars; j++){
                    mtxSPL.setElmt(i, j, mtx.getElmt(i, j));
                }
            }
            for(int i = 0; i < vars; i++){
                mtxSPL.setElmt(i, vars, mtxy.getElmt(i, 0));
            }
        } else {
            mtxSPL = new MatrixADT(vars, vars+1);
            mtxSPL = mtx;
        };
        switch (subchoice) {
            case 1:
                SPLGauss mGauss = new SPLGauss(mtxSPL);
                mGauss.gaussReduction();
                break;
            case 2:
                SPLGaussJ mGaussJ = new SPLGaussJ(mtxSPL);
                mGaussJ.gaussJordanElimination();
                break;
            case 3:
                SPLBalikan.displaySolution(SPLBalikan.solve(mtx));
                System.out.println("");
                break;
            case 4:
                SPLBalikan.displaySolution(SPLCramer.solve(mtx));
                System.out.println("");
                break;
            default:
                break;
        }
    }
}
