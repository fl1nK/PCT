import mpi.MPI;
import mpi.MPIException;

import java.util.Arrays;

public class Test {
    private static float[] convertToOneDimention(float[][] matr, int size){
        float[] tmp = new float[size * size];
        for(int i = 0 ; i < size; i++)
            System.arraycopy(matr[i], 0, tmp, i * size, size);
        return tmp;
    }
    public static void main(String[] args) {
        final int SIZE = 500;
        int size, rank, sendCount, recvCount, source;
        float[] sendBuf = new float[SIZE * SIZE];


        MPI.Init(args);
        rank = MPI.COMM_WORLD.Rank();
        size = MPI.COMM_WORLD.Size();

        int sizeBuf = SIZE * (SIZE/size);
        float[] recvBuf = new float[sizeBuf];
        float[][] sendMatr = new float[SIZE][SIZE];
        int count = 1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                sendMatr[i][j] = count;
                count++;
            }
        }
        sendBuf = convertToOneDimention(sendMatr, SIZE);

        sendCount = sizeBuf;
        recvCount = sizeBuf;
        MPI.COMM_WORLD.Scatter(sendBuf, 0, sendCount, MPI.FLOAT, recvBuf, 0, recvCount, MPI.FLOAT, 0);
        System.out.printf(" %nПроцес = %d одержав: %s", rank, Arrays.toString(recvBuf));

        MPI.Finalize();
    }

}
