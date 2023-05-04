package ex2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Main {
    public static void main(String[] args) {
        int size = 1000;
        Matrix A = new Matrix(size, size);
        Matrix B = new Matrix(size, size);
        A.generateRandomMatrix();
        B.generateRandomMatrix();

//        A.matrix = new double[][]{{1, 2, 3,4}, {5, 6, 7,8}, {9, 10, 11,12}, {13, 14, 15,16}};
//        B.matrix = new double[][]{{1, 2, 3,4}, {5, 6, 7,8}, {9, 10, 11,12}, {13, 14, 15,16}};

//        A.generateOneMatrix();
//        B.generateOneMatrix();
//        A.print();
//        B.print();

        Matrix C = new Matrix(A.getSizeAxis0(), B.getSizeAxis1());

        int nThread = Runtime.getRuntime().availableProcessors();
        System.out.println(nThread);

        long currTime = System.nanoTime();
        SerialAlgorithm(A,B,C, size);
        currTime = System.nanoTime() - currTime;

        System.out.println("Time for Serial Algorithm: " + currTime / 1_000_000);

        //C.print();

        C.matrix = new double[size][size];

        long currTimeFox = System.nanoTime();
        ForkJoinPool forkJoinPool = new ForkJoinPool(nThread);
        C = forkJoinPool.invoke(new FoxAlgorithm(A, B, C, size));
        currTimeFox = System.nanoTime() - currTimeFox;

        //C.print();
        System.out.println("Time for Fox Algorithm: " + currTimeFox / 1_000_000);
    }

    public static void SerialAlgorithm(Matrix A, Matrix B, Matrix C, int size){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    C.matrix[i][j] += A.matrix[i][k] * B.matrix[k][j];
                }
            }
        }
    }

}
