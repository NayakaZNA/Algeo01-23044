package UI;
import java.util.Scanner;
import Matrix.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.InputMismatchException;
public class RegresiUI {
    public static void regresi(int subchoice, MatrixADT mtx, MatrixADT result, Scanner scanner) throws IOException{
        subchoice = -1;
        while(subchoice < 0 || subchoice > 2) {
            System.out.println(
            "\n0. Kembali" + 
            "\n1. Regresi Linear Berganda" + 
            "\n2. Regresi Kuadratik Berganda\n");
            try {
                subchoice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\nMasukan Anda salah!\nMasukkan angka pada menu.");
                scanner.nextLine();
                subchoice = -1;
            }
        }
        if (subchoice == 0) return; 
        int vars = -1;
        int points = -1;
        if (mtx == null){
            while (true){
                try {
                    System.out.println("Masukkan banyak variabel\n");
                    vars = scanner.nextInt();
                    if (vars > 0) break;
                } catch (InputMismatchException e) {;}
                System.out.println("\nMasukan Anda salah!\n");
                scanner.nextLine();
                subchoice = -1;
            }
            while (true){
                try {
                    System.out.println("Masukkan banyak titik yang diketahui\n");
                    points = scanner.nextInt();
                    if (points > 0) break;
                } catch (InputMismatchException e) {;}
                System.out.println("\nMasukan Anda salah!\n");
                scanner.nextLine();
                subchoice = -1;
            }
            System.out.println("Masukkan titik-titik yang diketahui");
            mtx = new MatrixADT(points, vars+1);
            mtx.readMatrix(points, vars+1, scanner);
        }
        switch (subchoice) {
            case 1:
                MatrixADT coefs = Regresi.regresiLinierBerganda(mtx);
                System.out.println("Persamaan regresi adalah:");
                Regresi.printLinearSolution(coefs);
                System.out.println();
                String saveFile = Main.getSaveFileName(scanner);
                if (saveFile != null) {
                    FileWriter wr = new FileWriter(saveFile, false);
                    wr.write(Regresi.LinearSolution2Str(coefs));
                    wr.close();
                }
                break;

            case 2:
                coefs = Regresi.regresiKuadratikBerganda(mtx);
                System.out.println("Persamaan regresi adalah:");
                Regresi.printQuadraticSolution(coefs, vars);
                System.out.println();
                saveFile = Main.getSaveFileName(scanner);
                if (saveFile != null) {
                    try (FileWriter wr = new FileWriter(saveFile, false)){
                        wr.write(Regresi.QuadraticSolution2Str(coefs, vars));
                        wr.close();
                    } catch (Exception e) {
                        System.out.println("Gagal :(");
                    }
                }
            default:
                break;
        }

    }
}
