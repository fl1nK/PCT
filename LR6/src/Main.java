import mpi.*;

public class Main {

    static final int SIZE = 2000;

    public static void main(String[] args) throws MPIException {

        int[][] a = new int[SIZE][SIZE];
        int[][] b = new int[SIZE][SIZE];
        int[][] c = new int[SIZE][SIZE];

        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        if (rank == 0) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    a[i][j] = i + j;
                }
            }

            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    b[i][j] = i + j;
                }
            }

            double time = MPI.Wtime();
            int tag;
            int counter = 0;
            for (tag = 0; tag < SIZE / (size-1); tag++) {
                for (int i = 1; i < size; i++) {
                    int[] row = new int[]{counter};
                    MPI.COMM_WORLD.Send(row, 0, 1, MPI.INT, i, tag);
                    MPI.COMM_WORLD.Send(a[counter], 0, SIZE, MPI.INT, i, tag);
                    MPI.COMM_WORLD.Send(b, 0, SIZE, MPI.OBJECT, i, tag);
                    counter++;

                }

                for (int i = 1; i < size; i++) {
                    int[] row = new int[1];
                    int[] arrC = new int[SIZE];
                    MPI.COMM_WORLD.Recv(row, 0, 1, MPI.INT, i, tag);
                    MPI.COMM_WORLD.Recv(arrC, 0, SIZE, MPI.INT, i, tag);

                    System.arraycopy(arrC, 0, c[row[0]], 0, SIZE);
                }
            }

            if(SIZE % (size-1) != 0){
                for (int i = 1; i <= SIZE % (size-1); i++) {

                    int[] row = new int[]{counter};
                    MPI.COMM_WORLD.Send(row, 0, 1, MPI.INT, 1, tag + i);
                    MPI.COMM_WORLD.Send(a[counter], 0, SIZE, MPI.INT, 1, tag + i);
                    MPI.COMM_WORLD.Send(b, 0, SIZE, MPI.OBJECT, 1, tag + i);
                    counter++;

                    int[] row2 = new int[1];
                    int[] arrC = new int[SIZE];
                    MPI.COMM_WORLD.Recv(row2, 0, 1, MPI.INT, 1, tag + i);
                    MPI.COMM_WORLD.Recv(arrC, 0, SIZE, MPI.INT, 1, tag + i);

                    System.arraycopy(arrC, 0, c[row2[0]], 0, SIZE);
                }
            }

            /* Print results */
//            System.out.println("Result Matrix:");
//            for (int i=0; i<SIZE; i++) {
//                System.out.println();
//                for (int j=0; j<SIZE; j++)
//                    System.out.printf("%6d ", c[i][j]);
//            }
//                System.out.println("\n*****");
//                System.out.println("Done.");

            time = MPI.Wtime() - time;
            System.out.println("Time: " + time);
        }

        if(rank != 0) {
            int tag;
            for (tag = 0; tag < SIZE / (size-1); tag++) {
                int[] row = new int[1];
                MPI.COMM_WORLD.Recv(row, 0, 1, MPI.INT, 0, tag);

                int[] arrA = new int[SIZE];
                int[] arrC = new int[SIZE];
                int[][] B = new int[SIZE][SIZE];
                MPI.COMM_WORLD.Recv(arrA, 0, SIZE, MPI.INT, 0, tag);
                MPI.COMM_WORLD.Recv(B, 0, SIZE, MPI.OBJECT, 0, tag);

                for (int j = 0; j < SIZE; j++) {
                    for (int i = 0; i < SIZE; i++) {
                        arrC[j] += arrA[i] * B[i][j];
                    }
                }

                MPI.COMM_WORLD.Send(row, 0, 1, MPI.INT, 0, tag);
                MPI.COMM_WORLD.Send(arrC, 0, SIZE, MPI.INT, 0, tag);
            }

            if(rank == 1 && SIZE % (size-1) != 0 ){
                for (int i_ost = 1; i_ost <= SIZE % (size-1); i_ost++) {
                    int[] row = new int[1];
                    MPI.COMM_WORLD.Recv(row, 0, 1, MPI.INT, 0, tag + i_ost);

                    int[] arrA = new int[SIZE];
                    int[] arrC = new int[SIZE];
                    int[][] B = new int[SIZE][SIZE];
                    MPI.COMM_WORLD.Recv(arrA, 0, SIZE, MPI.INT, 0, tag + i_ost);
                    MPI.COMM_WORLD.Recv(B, 0, SIZE, MPI.OBJECT, 0, tag + i_ost);

                    for (int j = 0; j < SIZE; j++) {
                        for (int i = 0; i < SIZE; i++) {
                            arrC[j] += arrA[i] * B[i][j];
                        }
                    }

                    MPI.COMM_WORLD.Send(row, 0, 1, MPI.INT, 0, tag + i_ost);
                    MPI.COMM_WORLD.Send(arrC, 0, SIZE, MPI.INT, 0, tag + i_ost);
                }
            }
        }

        MPI.Finalize();
    }
}

