import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main2 {
    public static void main(String[] args) {
        int size = 2000;
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
        //SerialAlgorithm(A,B,C, size);
        currTime = System.nanoTime() - currTime;

        System.out.println("Time for Serial Algorithm: " + currTime / 1_000_000);

        currTime = System.nanoTime();
        //StripedAlgorithm(A,B,C,2);
        currTime = System.nanoTime() - currTime;

        System.out.println("Time for Striped Algorithm: " + currTime / 1_000_000);

        currTime = System.nanoTime();
        //StripedAlgorithm(A,B,C,4);
        currTime = System.nanoTime() - currTime;

        System.out.println("Time for Striped Algorithm: " + currTime / 1_000_000);

        currTime = System.nanoTime();
        //StripedAlgorithm(A,B,C,6);
        currTime = System.nanoTime() - currTime;

        System.out.println("Time for Striped Algorithm: " + currTime / 1_000_000);

        C.matrix = new double[size][size];

        currTime = System.nanoTime();
        FoxAlgorithm(A,B,C, size);
        currTime = System.nanoTime() - currTime;

        System.out.println("Time for Fox Algorithm: " + currTime / 1_000_000);

        //C.print();

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

    public static void StripedAlgorithm(Matrix A, Matrix B, Matrix C, int nThreads){
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);

        for (int i = 0; i < A.getSizeAxis0(); i++) {
            StripedAlgorithmThread t = new StripedAlgorithmThread(A.getRow(i), i, B, C);
            executor.submit(t);
        }
        executor.shutdown();

        while (!executor.isTerminated()){
//            System.out.println(executor.isTerminated());
        }
    }

    public static void FoxAlgorithm(Matrix A, Matrix B, Matrix C, int size){

        int step = size / 2;

        ExecutorService executor = Executors.newFixedThreadPool(4);

        executor.submit(new FoxAlgorithmThread(copyBlock(A,0,0 ,step), copyBlock(B,0,0 ,step), C, 0, 0));
        executor.submit(new FoxAlgorithmThread(copyBlock(A,0,0 ,step), copyBlock(B,0,step ,step), C, 0, step));
        executor.submit(new FoxAlgorithmThread(copyBlock(A,step,step ,step), copyBlock(B,step,0 ,step), C, step, 0));
        executor.submit(new FoxAlgorithmThread(copyBlock(A,step,step ,step), copyBlock(B,step,step ,step), C, step, step));
        executor.submit(new FoxAlgorithmThread(copyBlock(A,0,step ,step), copyBlock(B,step,0 ,step), C, 0, 0));
        executor.submit(new FoxAlgorithmThread(copyBlock(A,0,step ,step), copyBlock(B,step,step ,step), C, 0, step));
        executor.submit(new FoxAlgorithmThread(copyBlock(A,step,0 ,step), copyBlock(B,0,0 ,step), C, step, 0));
        executor.submit(new FoxAlgorithmThread(copyBlock(A,step,0 ,step), copyBlock(B,0,step ,step), C, step, step));

        executor.shutdown();

        while (!executor.isTerminated()){
//            System.out.println(executor.isTerminated());
        }

    }

    private static Matrix copyBlock(Matrix matrix, int i, int j, int size) {
        Matrix block = new Matrix(size, size);
        for (int k = 0; k < size; k++) {
            System.arraycopy(matrix.matrix[k + i], j, block.matrix[k], 0, size);
        }
        return block;
    }
}
