package UI;
import Matrix.InterpolasiPolinom;
import Matrix.MatrixADT;
import Matrix.SPLBalikan;
import Matrix.SPLCramer;
import Matrix.InverseAdjoin;
import Matrix.InverseGaussJ;
import Matrix.Regresi;
import Matrix.BicubicSplineInterpolation;
import Matrix.DeterminanMK;
import Matrix.DeterminanReduksi;
import Matrix.txtIO;
import Matrix.SPLGauss;
import Matrix.SPLGaussJ;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static String getSaveFileName(Scanner scanner){
        Boolean saveFile = null;
        String saveFileName = null;
        scanner.nextLine();
        do {
            System.out.println("Simpan hasil dalam file? (y/n)");
            String save = scanner.nextLine();
            if (save.toLowerCase().trim().equals("y")) {
                System.out.println("y");
                saveFile = true;
            } else if (save.toLowerCase().trim().equals("n")){
                System.out.println("n");
                saveFile = false;
                return null;
            } else {
                System.out.println("err");
                saveFile = null;
            }
        } while (saveFile == null);
        do {
            System.out.println("Masukkan nama file");
            String filenameTemp = scanner.nextLine().trim();
            if (filenameTemp != "") {
                saveFileName = filenameTemp;
            } else {
                saveFileName = null;
            }
        } while (saveFileName == null);
        return saveFileName;
    }

    public static void main(String[] var0) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String filename = null;
        MatrixADT mtx = null;
        MatrixADT result = new MatrixADT(1,1);
        while (mtx == null){
            System.out.print("Masukkan nama file input yang disimpan di ../test (kosongkan untuk menggunakan input keyboard): ");
            filename = scanner.nextLine();
            if (filename == ""){
                break;
            }
            mtx = txtIO.readTXT("../test/"+filename);
        }
        while(true){
            int choice = -69420;
            int subchoice = -1;
            Boolean saveFile = null;
            String saveFileName = null;
            while(choice < 1 || choice > 7) {
                if (choice != -69420)
                    System.out.println("\nMasukan Anda salah!\nMasukkan angka pada menu.");
                System.out.println("\nMENU" + 
                "\n1. Sistem Persamaan Linear" + 
                "\n2. Determinan" + 
                "\n3. Matriks Balikan" + 
                "\n4. Interpolasi Polinom" + 
                "\n5. Interpolasi Bicubic" + 
                "\n6. Regresi Linier dan Kuadratik Berganda" + 
                "\n7. Keluar\n");
                try {
                    choice = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("\nMasukan Anda salah!\nMasukkan angka pada menu.");
                    scanner.nextLine();
                    choice = -1;
                }
            }

            switch (choice) {
                case 1: // SPL
                    SPLUI.spl(subchoice, mtx, result, scanner);
                    break;
                case 2: //determinan
                    DeterminanUI.determinan(subchoice, mtx, result, scanner);
                    break;
                case 3: //balikan
                    BalikanUI.balikan(subchoice, mtx, result, scanner);
                    break;
                case 4: //interpolasi polinom
                    InterpolasiPolinomUI.interpolasiPolinom(subchoice, mtx, result, scanner);
                    break;
                case 5: //interpolasi bicubic
                    InterpolasiBicubicUI.interpolasiBicubic(subchoice, mtx, result, scanner);
                    break;
                case 6: //regresi
                    RegresiUI.regresi(subchoice, mtx, result, scanner);
                    break;
                case 7:
                    scanner.close();
                    return;
                default:
                    break;
            }
        }
    }

   public static void main1(String[] var0) {
      MatrixADT var1 = new MatrixADT(new double[][]{{0.0, 2.0}, {2.0, 4.0}, {3.0, 10.0}});
      InterpolasiPolinom.displaySolution(InterpolasiPolinom.PolynomialCoefficients(var1));
   }
}
