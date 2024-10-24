package Matrix;
public class Regresi {

    public static MatrixADT regresiLinierBerganda(MatrixADT data){
        MatrixADT regressionXs = new MatrixADT(data.nCols, data.nCols);
        MatrixADT regressionY = new MatrixADT(data.nCols, 1);
        regressionXs.setElmt(0, 0, data.nRows);
        for (int i = 1; i < regressionXs.nCols; i++){
            double sum = 0;
            for (int j= 0; j < data.nRows; j++){
                sum += data.getElmt(j, i);
            }
            regressionXs.setElmt(0, i, sum);
            regressionXs.setElmt(i, 0, sum);
        }
        for (int i = 1; i < regressionXs.nRows; i++){
            for (int j = i; j < regressionXs.nCols; j++){
                double sum = 0;
                for(int k = 0; k < data.nRows; k++){
                    sum += data.getElmt(k, j-1) * data.getElmt(k, i-1);
                }
                regressionXs.setElmt(j, i, sum);
                regressionXs.setElmt(i, j, sum);
            }
        }
        for (int i = 0; i < regressionY.nRows; i++){
            double sum = 0;
            for(int k = 0; k < data.nRows; k++){
                if (i == 0) sum += data.getElmt(k, data.nCols-1);
                else sum += data.getElmt(k, data.nCols-1) * data.getElmt(k, i-1);
            }
            regressionY.setElmt(i, 0, sum);
        }
        MatrixADT inverse = InverseGaussJ.inverseGaussJ(regressionXs);
        if (inverse == null) return null;
        MatrixADT coefs = inverse.multiply(regressionY);
        return coefs;
    }

    public static MatrixADT predict(MatrixADT coefs, MatrixADT queries){
        if (coefs == null) return null;
        MatrixADT res = new MatrixADT(queries.nRows, 1);
        for (int i = 0; i < res.nRows; i++){
            double resY = coefs.getElmt(0, 0);
            for (int j = 0; j < queries.nCols; j++){
                resY += queries.getElmt(i, j) * coefs.getElmt(j+1, 0);
            }
            res.setElmt(i, 0, resY);
        }
        return res;
    }

    public static void printLinearSolution(MatrixADT coefs){
        if (coefs == null) System.out.println("Regresi gagal.");
        System.out.printf("Y = %.3f ", coefs.getElmt(0,0));
        for(int i = 1; i < coefs.getRows(); i++){
            System.out.printf("+ %.3f X%d ", coefs.getElmt(i, 0), i);
        }
    }
    public static String LinearSolution2Str(MatrixADT coefs){
        if (coefs == null) return ("Regresi gagal.");
        String result = "";
        result += String.format("Y = %.3f ", coefs.getElmt(0,0));
        for(int i = 1; i < coefs.getRows(); i++){
            result += String.format("+ %.3f X%d ", coefs.getElmt(i, 0), i);
        }
        return result;
    }

    private static MatrixADT multipleQuadraticEquation(MatrixADT coefs, MatrixADT queries){
        if (coefs == null) return null;
        MatrixADT res = new MatrixADT(queries.nRows, 1);
        for (int i = 0; i < queries.nRows; i++){
            double sum = 0;
            for(int j = 0; j < queries.nCols; j++){
                for(int k = i; k < queries.nCols; k++){
                    double a, b;
                    if (j == 0) a = 1;
                    else a = queries.getElmt(i, j-1);
                    if (k == 0) b = 1;
                    else b = queries.getElmt(i, k-1);
                    sum += a + b;
                }
            }
            res.setElmt(i, 0, sum);
        }
        return res;
    }

    public static MatrixADT regresiKuadratikBerganda(MatrixADT data){
        MatrixADT linear = new MatrixADT(data.nCols * (data.nCols+1) / 2, data.nCols * (data.nCols+1) / 2);
        MatrixADT lineary = new MatrixADT(data.nCols * (data.nCols + 1) /2, 1);
        for(int i = 0; i < data.nCols; i++){
            for(int j = i; j < data.nCols; j++){
                for(int k = 0; k < data.nCols; k++){
                    for(int l = k; l < data.nCols; l++){
                        double sum = 0;
                        for (int m = 0; m < data.nRows; m++){
                            double a;
                            if (i == 0){
                                if (j == 0) a = 1;
                                else a = data.getElmt(m, j-1);
                            } else {
                                a = data.getElmt(m, i-1) * data.getElmt(m, j-1);
                            }
                            double b;
                            if (k == 0){
                                if (l == 0) b = 1;
                                else b = data.getElmt(m, l-1);
                            } else {
                                b = data.getElmt(m, k-1) * data.getElmt(m, l-1);
                            }
                            sum += a * b;
                        }
                        linear.setElmt(i * (2 * data.nCols - i - 1) / 2 + j, k * (2 * data.nCols - k - 1) / 2 + l, sum);
                        linear.setElmt(k * (2 * data.nCols - k - 1) / 2 + l, i * (2 * data.nCols - i - 1) / 2 + j, sum);
                    }
                }
            }
        }
        for(int i = 0; i < data.nCols; i++){
            for(int j = i; j < data.nCols; j++){
                double sum = 0;
                for (int k = 0; k < data.nRows; k++){
                    double a;
                    if (i == 0){
                        if (j == 0) a = 1;
                        else a = data.getElmt(k, j-1);
                    } else {
                        a = data.getElmt(k, i-1) * data.getElmt(k, j-1);
                    }
                    sum += data.getElmt(k, data.nCols-1) * a;
                }
                lineary.setElmt(i * (2 * data.nCols - i - 1) / 2 + j, 0, sum);
            }
        }
        MatrixADT inverse = InverseGaussJ.inverseGaussJ(linear);
        if (inverse == null) return null;
        MatrixADT coefs = inverse.multiply(lineary);
        linear.printMatrix();
        lineary.printMatrix();
        inverse.printMatrix();
        coefs.printMatrix();
        return coefs;
    }

    public static void printQuadraticSolution(MatrixADT coefs, int nVars){
        if (coefs == null) {
            System.out.println("Regresi gagal.");
            return;
        }
        System.out.printf("Y = %.3f ", coefs.getElmt(0,0));
        for(int i=1; i < nVars+1; i++){
            System.out.printf("+ %.3fX%d ", coefs.getElmt(i,0), i);
        }
        for (int i = 1; i < nVars+1; i++){
            for(int j = i; j < nVars+1; j++){
                int idx = (nVars + 1) * i - (i * (i-1) / 2) + (j-i);
                if (i != j){
                    System.out.printf(" + %.3fX%dX%d ", coefs.getElmt(idx, 0), i, j);
                } else {
                    System.out.printf(" + %.3fX%d^2d ", coefs.getElmt(idx, 0), i);
                }
            }
        }
    }

    public static String QuadraticSolution2Str(MatrixADT coefs, int nVars){
        if (coefs == null) {
            return ("Regresi gagal.");
        }
        String res = "";
        res += String.format("Y = %.3f ", coefs.getElmt(0,0));
        for(int i=1; i < nVars+1; i++){
            res += String.format("+ %.3fX%d ", coefs.getElmt(i,0), i);
        }
        for (int i = 1; i < nVars+1; i++){
            for(int j = i; j < nVars+1; j++){
                int idx = (nVars + 1) * i - (i * (i-1) / 2) + (j-i);
                if (i != j){
                    res += String.format(" + %.3fX%dX%d ", coefs.getElmt(idx, 0), i, j);
                } else {
                    res += String.format(" + %.3fX%d^2d ", coefs.getElmt(idx, 0), i);
                }
            }
        }
    return res;
    }
    //     return multipleQuadraticEquation(coefs, queries);
    // } 
}