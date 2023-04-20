//import java.util.Arrays;
//
//public class Main {
//    public static void main(String[] args) {
//        Matrix B = new Matrix(3, 3);
//
//        double[][] A = {{1, 2}, {3, 4}, {6, 3}}; // матриця A
//        //double[][] B = {{5, 6, 3}, {7, 8, 5}}; // матриця B
//        int m = A.length; // кількість рядків матриці A
//        int n = A[0].length; // кількість стовпців матриці A, рядків матриці B
//        //int p = B[0].length; // кількість стовпців матриці B
//        int p = B.getSizeAxis1(); // кількість стовпців матриці B
//
//        double[][] C = new double[m][p]; // матриця результату
//        double[] row = new double[m];
//        double[] result = new double[p];
////        for (int i = 0; i < m; i++) {
////            for (int j = 0; j < p; j++) {
////                for (int k = 0; k < n; k++) {
////                    C[i][j] += A[i][k] * B[k][j];
////                    System.out.print(C[i][j] + " ");
////                }
////                System.out.println();
////            }
////        }
//
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < p; j++) {
//                result[j] += row[i] * B.matrix[i][j];
//                System.out.print(C[i][j] + " ");
//            }
//        }
//
//        // виведення матриці результату
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < p; j++) {
//                System.out.print(C[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }
//}