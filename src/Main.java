// Source code is decompiled from a .class file using FernFlower decompiler.
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
import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Main {
   public Main() {
   }

    private static void spl(int subchoice, MatrixADT mtx, Scanner scanner){
        subchoice = -1;
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
            System.out.println("Masukkan persamaan dalam bentuk matriks");
            mtx = new MatrixADT(vars, vars+1);
            mtx.readMatrix(vars, vars+1, scanner);
        }
        switch (subchoice) {
            case 1:
                break;
            case 2:
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
    
    private static void determinan(int subchoice, MatrixADT mtx, Scanner scanner){
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
    }

private static void balikan(int subchoice, MatrixADT mtx, Scanner scanner){
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

    private static void interpolasiPolinom(int subchoice, MatrixADT mtx, Scanner scanner){
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
            InterpolasiPolinom.displaySolution(coefs);
        }
        System.out.println();
        return;
    }

    private static void interpolasiBicubic(int subchoice, MatrixADT mtx, Scanner scanner){
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
            MatrixADT coefs = BicubicSplineInterpolation.bicubicSplineCoefs(m);
            MatrixADT res = BicubicSplineInterpolation.bicubicSplineInterpolation(m, new MatrixADT(q));
            System.out.println("Persamaan interpolasi adalah: ");
            BicubicSplineInterpolation.printEquation(coefs);
            System.out.println("Hasil interpolasi: " + res.getElmt(0, 0));
        }
    }

    private static void regresi(int subchoice, MatrixADT mtx, Scanner scanner){
        subchoice = -1;
        while(subchoice < 1 || subchoice > 7) {
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
                break;

            case 2:
                coefs = Regresi.regresiKuadratikBerganda(mtx);
                System.out.println("Persamaan regresi adalah:");
                Regresi.printQuadraticSolution(coefs, vars);
        
            default:
                break;
        }
    }
    public static void main(String[] var0) {
        Scanner scanner = new Scanner(System.in);
        String filename = null;
        MatrixADT mtx = null;
        while (mtx == null){
            System.out.print("Masukkan nama file input (kosongkan untuk menggunakan input keyboard): ");
            filename = scanner.nextLine();
            if (filename == ""){
                break;
            }
            mtx = txtIO.readTXT(filename);
        }
        while(true){
            int choice = -69420;
            int subchoice = -1;
            int err = 0;
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
                    spl(subchoice, mtx, scanner);
                case 2: //determinan
                    determinan(subchoice, mtx, scanner);
                    break;
                case 3: //balikan
                    balikan(subchoice, mtx, scanner);
                    break;
                case 4: //interpolasi polinom
                    interpolasiPolinom(subchoice, mtx, scanner);
                    break;
                case 5: //interpolasi bicubic
                    interpolasiBicubic(subchoice, mtx, scanner);
                    break;
                case 6: //regresi
                    regresi(subchoice, mtx, scanner);
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