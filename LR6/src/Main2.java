import mpi.MPI;
import mpi.MPIException;

public class Main2 {

    static final int SIZE = 8; /* number of rows in matrix A */
    static final int NCA = 15; /* number of columns in matrix A */
    static final int NCB = 7; /* number of columns in matrix B */
    static final int MASTER = 0; /* taskid of first task */
    static final int FROM_MASTER = 1; /* setting a message type */
    static final int FROM_WORKER = 10; /* setting a message type */

    public static void main(String[] args) throws MPIException {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
 //       System.out.println(rank + " " + size);
//        int destinationRank = (rank + 1) % size;
//        int sourceRank = (rank - 1 + size) % size;

        if (rank == 0) {
            int tag;
            for (tag = 0; tag < SIZE / (size-1); tag++) {
                for (int i = 1; i < size; i++) {
                    int[] message = new int[]{0};
                    MPI.COMM_WORLD.Send(message, 0, 1, MPI.INT, i, tag);
                }

                for (int i = 1; i < size; i++) {
                    int[] message = new int[1];
                    MPI.COMM_WORLD.Recv(message, 0, 1, MPI.INT, i, tag);
                    System.out.println("Recv" + message[0]);
                }
            }

            if(SIZE % (size-1) != 0){
                for (int i = 1; i <= SIZE % (size-1); i++) {
                    int[] message = new int[]{0};
                    MPI.COMM_WORLD.Send(message, 0, 1, MPI.INT, 1, tag + i);

                    int[] message1 = new int[1];
                    MPI.COMM_WORLD.Recv(message1, 0, 1, MPI.INT, 1, tag);
                    System.out.println("Recv" + message1[0]);
                }

//                for (int i = 1; i < SIZE % (size-1); i++) {
//
//                }
            }
        }

        if (rank != 0){
            int tag;
            for (tag = 0; tag < SIZE / (size-1); tag++) {
                int[] message = new int[1];
                MPI.COMM_WORLD.Recv(message, 0, 1, MPI.INT, 0, tag);
                System.out.println("Process " + rank + " received message " + message[0] + tag);

                message[0] = message[0] + 1;
                MPI.COMM_WORLD.Send(message, 0, 1, MPI.INT, 0, tag);
            }

            if(rank == 1 && SIZE % (size-1) != 0) {
                for (int i = 1; i <= SIZE % (size-1); i++) {
                    int[] message = new int[1];
                    MPI.COMM_WORLD.Recv(message, 0, 1, MPI.INT, 0, tag + i);
                    System.out.println("Process " + rank + " received message " + message[0] + tag + i);

                    message[0] = message[0] + 1;
                    MPI.COMM_WORLD.Send(message, 0, 1, MPI.INT, 0, tag);
                }
            }
        }

        MPI.Finalize();
    }
}

