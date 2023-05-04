package ex2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FoxAlgorithm extends RecursiveTask<Matrix>{
    Matrix A;
    Matrix B;
    Matrix C;
    int size;

    public FoxAlgorithm(Matrix a, Matrix b, Matrix c, int size) {
        A = a;
        B = b;
        C = c;
        this.size = size;
    }

    private static Matrix copyBlock(Matrix matrix, int i, int j, int size) {
        Matrix block = new Matrix(size, size);
        for (int k = 0; k < size; k++) {
            System.arraycopy(matrix.matrix[k + i], j, block.matrix[k], 0, size);
        }
        return block;
    }

    @Override
    protected Matrix compute() {
        int step = size / 2;

        List<RecursiveTask<HashMap<String, Object>>> tasks = new ArrayList<>();

        FoxAlgorithmForkJoin task1 = new FoxAlgorithmForkJoin(copyBlock(A,0,0 ,step), copyBlock(B,0,0 ,step), 0, 0);
        FoxAlgorithmForkJoin task2 = new FoxAlgorithmForkJoin(copyBlock(A,0,0 ,step), copyBlock(B,0,step ,step), 0, step);
        FoxAlgorithmForkJoin task3 = new FoxAlgorithmForkJoin(copyBlock(A,step,step ,step), copyBlock(B,step,0 ,step), step, 0);
        FoxAlgorithmForkJoin task4 = new FoxAlgorithmForkJoin(copyBlock(A,step,step ,step), copyBlock(B,step,step ,step), step, step);
        FoxAlgorithmForkJoin task5 = new FoxAlgorithmForkJoin(copyBlock(A,0,step ,step), copyBlock(B,step,0 ,step), 0, 0);
        FoxAlgorithmForkJoin task6 = new FoxAlgorithmForkJoin(copyBlock(A,0,step ,step), copyBlock(B,step,step ,step), 0, step);
        FoxAlgorithmForkJoin task7 = new FoxAlgorithmForkJoin(copyBlock(A,step,0 ,step), copyBlock(B,0,0 ,step), step, 0);
        FoxAlgorithmForkJoin task8 = new FoxAlgorithmForkJoin(copyBlock(A,step,0 ,step), copyBlock(B,0,step ,step), step, step);

        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
        tasks.add(task5);
        tasks.add(task6);
        tasks.add(task7);
        tasks.add(task8);

        task1.fork();
        task2.fork();
        task3.fork();
        task4.fork();
        task5.fork();
        task6.fork();
        task7.fork();
        task8.fork();

        for (RecursiveTask<HashMap<String, Object>> task : tasks) {
            HashMap<String, Object> r = task.join();

            Matrix blockRes = (Matrix) r.get("blockRes");
            int stepI = (int) r.get("stepI");
            int stepJ = (int) r.get("stepJ");

            for (int i = 0; i < blockRes.getSizeAxis0(); i++) {
                for (int j = 0; j < blockRes.getSizeAxis1(); j++) {
                    C.matrix[i + stepI][j + stepJ] += blockRes.matrix[i][j];
                }
            }
        }
        return C;
    }
}
