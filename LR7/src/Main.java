import mpi.MPI;
import mpi.MPIException;

import java.util.Arrays;

public class Main {

    private static float[] convertToOneDimention(float[][] matr, int size){
        float[] tmp = new float[size * size];
        for(int i = 0 ; i < size; i++)
            System.arraycopy(matr[i], 0, tmp, i * size, size);
        return tmp;
    }

    public static void main(String[] args) throws MPIException {
        final int SIZE = 2000;
        float[] aSendBuf = new float[SIZE * SIZE];
        float[] cSendBuf = new float[SIZE * SIZE];


        float[][] a = new float[SIZE][SIZE];
        float[][] b = new float[SIZE][SIZE];
        float[][] c = new float[SIZE][SIZE];

        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int root = 0;

        int sizeBuf = SIZE * (SIZE/size);
        float[] aRecvBuf = new float[sizeBuf];
        float[] cRecvBuf = new float[sizeBuf];

        int count = 1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                a[i][j] = count;
                count++;
            }
        }

        count = 1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                b[i][j] = count;
                count++;
            }
        }

        aSendBuf = convertToOneDimention(a, SIZE);
        cSendBuf = convertToOneDimention(c, SIZE);

        double time = MPI.Wtime();

        MPI.COMM_WORLD.Scatter(aSendBuf, 0, sizeBuf, MPI.FLOAT, aRecvBuf,0, sizeBuf, MPI.FLOAT, root);
        MPI.COMM_WORLD.Scatter(cSendBuf, 0, sizeBuf, MPI.FLOAT, cRecvBuf,0, sizeBuf, MPI.FLOAT, root);

        int offset = 0;
        for (int j = 0; j < sizeBuf; j++) {
            for (int i = 0; i < SIZE; i++) {
                if (j % SIZE == 0){
                    offset = j;
                }
                cRecvBuf[j] += aRecvBuf[i + offset] * b[i][j - offset];
            }
        }

//        System.out.printf(" %n aRecvBuf Процес = %d одержав: %s", rank, Arrays.toString(aRecvBuf));
//        System.out.printf(" %n cRecvBuf Процес = %d одержав: %s", rank, Arrays.toString(cRecvBuf));


        MPI.COMM_WORLD.Gather(aRecvBuf, 0, sizeBuf, MPI.FLOAT, aSendBuf,0, sizeBuf, MPI.FLOAT, root);
        MPI.COMM_WORLD.Gather(cRecvBuf, 0, sizeBuf, MPI.FLOAT, cSendBuf,0, sizeBuf, MPI.FLOAT, root);



        if (rank == root){
            time = MPI.Wtime() - time;
            System.out.println("Time: " + time);
            //            for (int i = 0; i < SIZE * SIZE; i++) {
//                if (i % SIZE == 0)
//                    System.out.println();
//                System.out.print(cSendBuf[i] + " ");
//            }
        }


        MPI.Finalize();
    }
}

