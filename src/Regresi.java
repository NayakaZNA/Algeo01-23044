public class Regresi {
    static MatrixADT BSIDefaultCoordinates = new MatrixADT(new double[][]{ {0,0}, {1,0}, {0,1}, {1,1} });
    public static MatrixADT RegresiLinierBerganda(MatrixADT data, MatrixADT queries){
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
        MatrixADT coefs = InverseGaussJ.inverseGaussJ(regressionXs).multiply(regressionY);
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
    public static MatrixADT RegresiKuadratikBerganda(MatrixADT data, MatrixADT queries){

    } 
    private static MatrixADT BicubicSplineMatrixGenerator(MatrixADT coordinates){
        MatrixADT res = new MatrixADT(16, 16) ;
        for (int i = 0; i < 4; i++){
            // for (int j = 0; j < 4; j++){
            //     res[i][j] = Math.pow(coordinates[i][0], j);
            // }
            res.setElmt(i, 0, 1);
            res.setElmt(i, 1, coordinates.getElmt(i, 0));
            res.setElmt(i, 2, res.getElmt(i, 1) * coordinates.getElmt(i, 0));
            res.setElmt(i, 3, res.getElmt(i,2) * coordinates.getElmt(i, 0));
            for(int j = 4; j < 16; j++) {
                res.setElmt(i, j, res.getElmt(i, j-4) * coordinates.getElmt(i, 1));
            }
            res.setElmt(i+4, 0, 0);
            for (int j = 1; j < 4; j++){
                res.setElmt(i+4, j, res.getElmt(i, j-1) * j);
            }
            for (int j = 4; j < 16; j++){
                res.setElmt(i+4, j, res.getElmt(i+4, j-4) * coordinates.getElmt(i, 1));
            }
            for (int j = 0; j < 4; j++){
                res.setElmt(i+8, j, 0);
                res.setElmt(i+12, j, 0);
            }
            for (int j = 1; j < 4; j++){
                for (int k = 0; k < 4; k++){
                    res.setElmt(i+8, 4*j + k, res.getElmt(i, k) * j * res.getElmt(i, 4*(j-1)));
                }
            }
            for (int j = 1; j < 4; j++){
                res.setElmt(i+12, 4*j, 0);
                for (int k = 1; k < 4; k++){
                    res.setElmt(i+12, 4*j + k, res.getElmt(i, k-1 + (j-1) * 4) * k * j);
                }
            }
        }
        return res;
    }
    private static MatrixADT BicubicSplineCoefs(MatrixADT coordinates, MatrixADT values){
        return BicubicSplineMatrixGenerator(coordinates).multiply(values);
    }
    private static double BicubicEquation(MatrixADT coefs, double x, double y){
        double x2 = x * x, x3 = x2 * x, y2 = y * y, y3 = y2 * y;
        return (
            coefs.getElmt(0, 0)
            + coefs.getElmt(1, 0) * x
            + coefs.getElmt(2, 0) * x2
            + coefs.getElmt(3, 0) * x3
            + coefs.getElmt(4, 0) * y
            + coefs.getElmt(5, 0) * x * y
            + coefs.getElmt(6, 0) * x2 * y
            + coefs.getElmt(7, 0) * x3 * y
            + coefs.getElmt(8, 0) * y2
            + coefs.getElmt(9, 0) * x * y2
            + coefs.getElmt(10, 0) * x2 * y2
            + coefs.getElmt(11, 0) * x3 * y2
            + coefs.getElmt(12, 0) * y3
            + coefs.getElmt(13, 0) * x * y3
            + coefs.getElmt(14, 0) * x2 * y3
            + coefs.getElmt(15, 0) * x3*y3);
    }
    public static MatrixADT BicubicSplineInterpolation(MatrixADT values, MatrixADT queries){
        MatrixADT coefs = BicubicSplineCoefs(BSIDefaultCoordinates, values);
        MatrixADT res = new MatrixADT(queries.nRows, 1);
        for (int i = 0; i < res.nRows; i++){
            res.setElmt(i, 0, BicubicEquation(coefs, queries.getElmt(i, 0), queries.getElmt(i, 1)));
        }
        return res;
    } 
    public static double BicubicSplineInterpolation(MatrixADT coordinates, MatrixADT values, double x, double y){
        MatrixADT mtx = InverseGaussJ.inverseGaussJ(BicubicSplineMatrixGenerator(BSIDefaultCoordinates));
        MatrixADT coefs = mtx.multiply(values);
        double x2 = x * x, x3 = x2 * x, y2 = y * y, y3 = y2 * y;
        return (
            coefs.getElmt(0, 0)
            + coefs.getElmt(1, 0) * x
            + coefs.getElmt(2, 0) * x2
            + coefs.getElmt(3, 0) * x3
            + coefs.getElmt(4, 0) * y
            + coefs.getElmt(5, 0) * x * y
            + coefs.getElmt(6, 0) * x2 * y
            + coefs.getElmt(7, 0) * x3 * y
            + coefs.getElmt(8, 0) * y2
            + coefs.getElmt(9, 0) * x * y2
            + coefs.getElmt(10, 0) * x2 * y2
            + coefs.getElmt(11, 0) * x3 * y2
            + coefs.getElmt(12, 0) * y3
            + coefs.getElmt(13, 0) * x * y3
            + coefs.getElmt(14, 0) * x2 * y3
            + coefs.getElmt(15, 0) * x3*y3);
    }
    public static void main(String[] args) {
        MatrixADT values = new MatrixADT(
            new double[][] {
                {69}, {96}, {420}, {},
                {1}, {1}, {1}, {1},
                {0}, {0}, {0}, {0},
                {0}, {0}, {0}, {0},
            });
        
        System.out.println(BicubicSplineInterpolation(values, 2, 1));
        MatrixADT data = new MatrixADT( new double[][] {{0, 2}, {2, 0}});
        MatrixADT q = new MatrixADT( new double[][] {{1}});
        RegresiLinierBerganda(data, q).printMatrix();
    }
}