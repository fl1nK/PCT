import mpi.*;

public class Main {

    static final int SIZE = 2000;

    public static void main(String[] args) throws MPIException {

        double[][] a = new double[SIZE][SIZE];
        double[][] b = new double[SIZE][SIZE];
        double[][] c = new double[SIZE][SIZE];

        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

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

        if (rank == 0) {
            double time = MPI.Wtime();

            int tag;
            int counter = 0;
            for (tag = 0; tag < SIZE / (size-1); tag++) {
                for (int i = 1; i < size; i++) {
                    int[] row = new int[]{counter};
                    MPI.COMM_WORLD.Send(row, 0, 1, MPI.INT, i, tag);
                    MPI.COMM_WORLD.Send(a[counter], 0, SIZE, MPI.DOUBLE, i, tag);
                    counter++;
                }

                for (int i = 1; i < size; i++) {
                    int[] row = new int[1];
                    double[] arrC = new double[SIZE];
                    MPI.COMM_WORLD.Recv(row, 0, 1, MPI.INT, i, tag);
                    MPI.COMM_WORLD.Recv(arrC, 0, SIZE, MPI.DOUBLE, i, tag);

                    System.arraycopy(arrC, 0, c[row[0]], 0, SIZE);
                }
            }

            if(SIZE % (size-1) != 0){
                for (int i = 1; i <= SIZE % (size-1); i++) {

                    int[] row = new int[]{counter};
                    MPI.COMM_WORLD.Send(row, 0, 1, MPI.INT, 1, tag + i);
                    MPI.COMM_WORLD.Send(a[counter], 0, SIZE, MPI.DOUBLE, 1, tag + i);
                    counter++;

                    int[] row2 = new int[1];
                    double[] arrC = new double[SIZE];
                    MPI.COMM_WORLD.Recv(row2, 0, 1, MPI.INT, 1, tag + i);
                    MPI.COMM_WORLD.Recv(arrC, 0, SIZE, MPI.DOUBLE, 1, tag + i);

                    System.arraycopy(arrC, 0, c[row2[0]], 0, SIZE);
                }
            }

            /* Print results */
            System.out.println("Result Matrix:");
            for (int i=0; i<SIZE; i++) {
                System.out.println();
                for (int j=0; j<SIZE; j++)
                    System.out.printf("%6.2f ", c[i][j]);
            }
                System.out.println("\n*****");
                System.out.println("Done.");

            time = MPI.Wtime() - time;
            System.out.println("Time: " + time);
        }

        if(rank != 0) {
            int tag;
            for (tag = 0; tag < SIZE / (size-1); tag++) {
                int[] row = new int[1];
                MPI.COMM_WORLD.Recv(row, 0, 1, MPI.INT, 0, tag);

                double[] arrA = new double[SIZE];
                double[] arrC = new double[SIZE];
                int[][] B = new int[SIZE][SIZE];
                MPI.COMM_WORLD.Recv(arrA, 0, SIZE, MPI.DOUBLE, 0, tag);

                for (int j = 0; j < SIZE; j++) {
                    for (int i = 0; i < SIZE; i++) {
                        arrC[j] += arrA[i] * b[i][j];
                    }
                }

                MPI.COMM_WORLD.Send(row, 0, 1, MPI.INT, 0, tag);
                MPI.COMM_WORLD.Send(arrC, 0, SIZE, MPI.DOUBLE, 0, tag);
            }

            if(rank == 1 && SIZE % (size-1) != 0 ){
                for (int i_ost = 1; i_ost <= SIZE % (size-1); i_ost++) {
                    int[] row = new int[1];
                    MPI.COMM_WORLD.Recv(row, 0, 1, MPI.INT, 0, tag + i_ost);

                    double[] arrA = new double[SIZE];
                    double[] arrC = new double[SIZE];
                    int[][] B = new int[SIZE][SIZE];
                    MPI.COMM_WORLD.Recv(arrA, 0, SIZE, MPI.DOUBLE, 0, tag + i_ost);

                    for (int j = 0; j < SIZE; j++) {
                        for (int i = 0; i < SIZE; i++) {
                            arrC[j] += arrA[i] * b[i][j];
                        }
                    }

                    MPI.COMM_WORLD.Send(row, 0, 1, MPI.INT, 0, tag + i_ost);
                    MPI.COMM_WORLD.Send(arrC, 0, SIZE, MPI.DOUBLE, 0, tag + i_ost);
                }
            }
        }

        MPI.Finalize();
    }
}

