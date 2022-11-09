// Name: Kevin Kim
// Instructor: Professor Young
// Course: CS 3310.03
// Date: 11/03/2022
// Bronco ID: 015683070
// Project: Project #1

import java.util.Random;

public class Main
{
    public static void main(String[] args)
    {
        int n = 2;

        // perform different matrix multiplication methods with different matrix sizes.
        while (n <= 128)
        {
            System.out.println("\nMatrix Size: " + n + " x " + n);
            TestClassic(n);
            TestDivAndConq(n);
            TestStrassen(n);

            n *= 2;
        }

    }

    public static void TestClassic(int n)
    {
        // perform the classical matrix multiplication on the two matrices.
        // System.out.println("Performing the classical matrix multiplication...");
        int[][] matrixC;
        long start = 0;
        long duration = 0;
        double avgTime = 0.0;

        // generate 1000 sets of matrices with random values.
        for (int i = 0; i < 1000; i++)
        {
            int[][] matrixA = RandomMatrix(n);
            int[][] matrixB = RandomMatrix(n);

            // for each set of input data, run it 20 times before
            // getting the average time for this particular set.
            for (int j = 0; j < 20; j++)
            {
                // start timing.
                start = System.nanoTime();
                matrixC = ClassicMult(matrixA, matrixB, n);
                duration += (System.nanoTime() - start);

            }

            avgTime += (duration/20);
        }

        // calculate the average time spent performing this method.
        avgTime = avgTime / 1000;
        System.out.println("The average time spent performing the Classical "
                + "Matrix Multiplication in nanoseconds: " + avgTime);

    }

    public static void TestDivAndConq(int n)
    {
        // perform the divide-and-conquer matrix multiplication on the two matrices.
        // System.out.println("Performing the divide-and-conquer matrix multiplication...");
        int[][] matrixC;
        long start = 0;
        long duration = 0;
        double avgTime = 0.0;

        // generate 1000 sets of matrices with random values.
        for (int i = 0; i < 1000; i++)
        {
            int[][] matrixA = RandomMatrix(n);
            int[][] matrixB = RandomMatrix(n);

            // for each set of input data, run it 20 times before
            // getting the average time for this particular set.
            for (int j = 0; j < 20; j++)
            {
                // start timing.
                start = System.nanoTime();
                matrixC = DivAndConq(matrixA, matrixB);
                duration += (System.nanoTime() - start);

            }

            avgTime += (duration/20);
        }

        // calculate the average time spent performing this method.
        avgTime = avgTime / 1000;
        System.out.println("The average time spent performing the Divide-"
                + "and-Conquer Matrix Multiplication in nanoseconds: " + avgTime);

    }

    public static void TestStrassen(int n)
    {
        // perform the Strassen's matrix multiplication on the two matrices.
        // System.out.println("Performing the Strassen's matrix multiplication...");
        int[][] matrixC = new int[n][n];
        long start = 0;
        long duration = 0;
        double avgTime = 0.0;

        // generate 1000 sets of matrices with random values.
        for (int i = 0; i < 1000; i++)
        {
            int[][] matrixA = RandomMatrix(n);
            int[][] matrixB = RandomMatrix(n);

            // for each set of input data, run it 20 times before
            // getting the average time for this particular set.
            for (int j = 0; j < 20; j++)
            {
                // start timing.
                start = System.nanoTime();
                matrixC = Strassen(n, matrixA, matrixB, matrixC);
                duration += (System.nanoTime() - start);

            }

            avgTime += (duration/20);
        }

        // calculate the average time spent performing this method.
        avgTime = avgTime / 1000;
        System.out.println("The average time spent performing the Strassen's "
                + "Matrix Multiplication in nanoseconds: " + avgTime);

    }

    // this method uses the classical matrix multiplication on two matrices of
    // sizes of n x n in order to generate a new matrix by using three for-loops.
    public static int[][] ClassicMult(int[][] matrixA, int[][] matrixB, int n)
    {
        int[][] matrixC = new int[n][n];

        // perform the classical matrix multiplication on matrixA and
        // matrixB, then store the products in matrixC
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                // first, initialize the new matrix to 0.
                matrixC[i][j] = 0;

                for (int k = 0; k < n; k++)
                {
                    // then, start multiplying matrixA and matrixB before
                    // storing the products in the new matrix.
                    matrixC[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        // return the new matrix back to the caller method.
        return matrixC;
    }

    public static int[][] DivAndConq(int[][] matrixA, int[][] matrixB)
    {
        // get the length of the row/column of the matrix.
        // NOTE: we are assuming that the matrix is N x N.
        int n = matrixA.length;
        int[][] matrixC = new int[n][n];

        // check for the base case.
        if (2 == n)
        {
            matrixC = ClassicMult(matrixA, matrixB, n);
        }
        else
        {
            // perform the divide-and-conquer matrix multiplication.
            int [][] A11 = CreateSubMatrix(n / 2, matrixA, 0, (n / 2) - 1, 0, (n / 2) - 1);
            int [][] A12 = CreateSubMatrix(n / 2, matrixA, 0, (n / 2) - 1, n / 2, n);
            int [][] A21 = CreateSubMatrix(n / 2, matrixA, n / 2, n, 0, (n / 2) - 1);
            int [][] A22 = CreateSubMatrix(n / 2, matrixA, n / 2, n, n / 2, n);

            int [][] B11 = CreateSubMatrix(n / 2, matrixB, 0, (n / 2) - 1, 0, (n / 2) - 1);
            int [][] B12 = CreateSubMatrix(n / 2, matrixB, 0, (n / 2) - 1, n / 2, n);
            int [][] B21 = CreateSubMatrix(n / 2, matrixB, n / 2, n, 0, (n / 2) - 1);
            int [][] B22 = CreateSubMatrix(n / 2, matrixB, n / 2, n, n / 2, n);

            // perform addition on the sub-matrices.
            int [][] C11 = Add(DivAndConq(A11, B11), DivAndConq(A12, B21));
            int [][] C12 = Add(DivAndConq(A11, B12), DivAndConq(A12, B22));
            int [][] C21 = Add(DivAndConq(A21, B11), DivAndConq(A22, B21));
            int [][] C22 = Add(DivAndConq(A21, B12), DivAndConq(A22, B22));

            // combine all the sub-matrices.
            matrixC = CombineMatrix(C11, C12, C21, C22);
        }

        return matrixC;
    }

    public static int[][] Strassen(int n, int[][] matrixA, int[][] matrixB, int[][] matrixC)
    {
        // check for the base case.
        if (2 == n)
        {
            matrixC = ClassicMult(matrixA, matrixB, n);
        }
        // perform the Strassen's matrix multiplication.
        else
        {
            // Partition A into 4 sub-matrices: A11, A12, A21, and A22.
            int [][] A11 = CreateSubMatrix(n / 2, matrixA, 0, (n / 2) - 1, 0, (n / 2) - 1);
            int [][] A12 = CreateSubMatrix(n / 2, matrixA, 0, (n / 2) - 1, n / 2, n);
            int [][] A21 = CreateSubMatrix(n / 2, matrixA, n / 2, n, 0, (n / 2) - 1);
            int [][] A22 = CreateSubMatrix(n / 2, matrixA, n / 2, n, n / 2, n);

            // Partition B into 4 sub-matrices: B11, B12, B21, and B22.
            int [][] B11 = CreateSubMatrix(n / 2, matrixB, 0, (n / 2) - 1, 0, (n / 2) - 1);
            int [][] B12 = CreateSubMatrix(n / 2, matrixB, 0, (n / 2) - 1, n / 2, n);
            int [][] B21 = CreateSubMatrix(n / 2, matrixB, n / 2, n, 0, (n / 2) - 1);
            int [][] B22 = CreateSubMatrix(n / 2, matrixB, n / 2, n, n / 2, n);

            // P = (A11 + A22) X (B11 + B22)
            int[][] P = new int[n/2][n/2];

            // Q = (A21 + A22) X B11
            int[][] Q = new int[n/2][n/2];

            // R = A11 X (B12 - B22)
            int[][] R = new int[n/2][n/2];

            // S = A22 X (B21 - B11)
            int[][] S = new int[n/2][n/2];

            // T = (A11 + A12) X B22
            int[][] T = new int[n/2][n/2];

            // U = (A21 - A11) X (B11 + B12)
            int[][] U = new int[n/2][n/2];

            // V = (A12 - A22) X (B21 + B22)
            int[][] V = new int[n/2][n/2];

            Strassen(n / 2, Add(A11,A22), Add(B11,B22), P);
            Strassen(n / 2, Add(A21,A22), B11, Q);
            Strassen(n / 2, A11, Subtract(B12, B22), R);
            Strassen(n / 2, A22, Subtract(B21,B11), S);
            Strassen(n / 2, Add(A11, A12), B22, T);
            Strassen(n / 2, Subtract(A21, A11), Add(B11, B12), U);
            Strassen(n / 2, Subtract(A12, A22), Add(B21, B22), V);

            // perform addition on the sub-matrices.
            // C11 = P + S - T + V
            int [][] C11 = Add(Subtract(Add(P, S), T), V);
            // C12 = R + T
            int [][] C12 = Add(R, T);
            // C21 = Q + S
            int [][] C21 = Add(Q, S);
            // C22 = P + R - Q + U
            int [][] C22 = Add(Subtract(Add(P,R), Q), U);

            // combine all the sub-matrices.
            matrixC = CombineMatrix(C11, C12, C21, C22);
        }

        return matrixC;
    }

    // this function combines two matrices into one.
    protected static int[][] CombineMatrix(int[][] c11, int[][] c12, int[][] c21, int[][] c22)
    {
        int n1 = c11.length;
        int n2 = n1 * 2;
        int[][] result = new int[n2][n2];

        // copy c11 into the result matrix.
        for (int i = 0; i < n1; i++)
        {
            for (int j = 0; j < n1; j++)
            {
                result[i][j] = c11[i][j];
            }
        }

        // copy c12 into the result matrix.
        for (int i = 0; i < n1; i++)
        {
            for (int j1 = 0, j2 = n1; j2 < n2; j1++, j2++)
            {
                result[i][j2] = c12[i][j1];
            }
        }

        // copy c21 into the result matrix.
        for (int i1 = 0, i2 = n1; i2 < n2; i1++, i2++)
        {
            for (int j = 0; j < n1; j++)
            {
                result[i2][j] = c21[i1][j];
            }
        }

        // copy c22 into the result matrix.
        for (int i1 = 0, i2 = n1; i2 < n2; i1++, i2++)
        {
            for (int j1 = 0, j2 = n1; j2 < n2; j1++, j2++)
            {
                result[i2][j2] = c22[i1][j1];
            }
        }

        return result;
    }


    // this function performs matrix addition.
    protected static int[][] Add(int[][] matrix1, int[][] matrix2)
    {
        int n = matrix1.length;
        int[][] result = new int[n][n];

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                // add corresponding elements of matrices.
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return result;
    }


    // this function performs matrix subtraction.
    protected static int[][] Subtract(int[][] matrix, int[][] matrix2)
    {
        int n = matrix.length;
        int[][] result = new int[n][n];

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                // Subtracting corresponding elements of matrices
                result[i][j] = matrix[i][j] - matrix2[i][j];
            }
        }
        return result;
    }

    // this function creates sub-problems for the divide-and-conquer
    // method of matrix multiplication.
    protected static int[][] CreateSubMatrix(int n, int[][] matrix, int rowStart
                                                  , int rowEnd, int colStart, int colEnd)
    {
        int[][] subMatrix = new int[n][n];

        for (int i1 = 0, i2 = rowStart; i2 < rowEnd; i1++, i2++)
        {
            for (int j1 = 0, j2 = colStart; j2 < colEnd; j1++, j2++)
            {
                // copy the original matrix to the sub-matrix.
                subMatrix[i1][j1] = matrix[i2][j2];
            }
        }

        return subMatrix;
    }

    // this function generates an n x n matrix with random data.
    protected static int[][] RandomMatrix(int n)
    {
        int[][] matrix = new int[n][n];
        Random rand = new Random();

        // fill up the matrix with random values.
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                matrix[i][j] = rand.nextInt(10);;
            }
        }

        return matrix;
    }

}
