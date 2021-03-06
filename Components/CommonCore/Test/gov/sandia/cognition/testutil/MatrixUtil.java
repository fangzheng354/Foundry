
package gov.sandia.cognition.testutil;

import gov.sandia.cognition.math.matrix.Matrix;
import gov.sandia.cognition.math.matrix.MatrixEntry;
import gov.sandia.cognition.math.matrix.Vector;
import gov.sandia.cognition.math.matrix.VectorEntry;

import static org.junit.Assert.*;

/**
 * Methods to test matrix values, size, iterators, etc.
 *
 * @author Jeremy D. Wendt
 */
public class MatrixUtil
{

    /**
     * Helper that tests that the input matrix is of the correct type, correct
     * dimensions, and contains the correct values.
     *
     * @param m The matrix to test
     * @param c The type the matrix should be
     * @param numRows The number of rows the matrix should have
     * @param numCols The number of columns the matrix should have
     * @param vals The values the matrix should have (vals.size must equal
     * numRows * numCols)
     */
    public static void testMatrixEquals(
        Matrix m,
        Class<?> c,
        int numRows,
        int numCols,
        double... vals)
    {
        assertTrue(c.isInstance(m));
        assertEquals(m.getNumRows(), numRows);
        assertEquals(m.getNumColumns(), numCols);
        
        int nonZeroCount = 0;
        for (int i = 0; i < numRows;
            ++i)
        {
            for (int j = 0; j < numCols;
                ++j)
            {
                double v = vals[i * numCols + j];
                double mij = m.getElement(i, j);
                AssertUtil.equalToNumDigits("At " + i + ", " + j + ": " + v
                    + " != " + mij, v, mij, 6);
                
                if (v != 0.0)
                {
                    nonZeroCount++;
                }
            }
        }
        
        int entryCount = 0;
        for (MatrixEntry entry : m)
        {
            int i = entry.getRowIndex();
            int j = entry.getColumnIndex();
            
            double v = vals[i * numCols + j];
            double mij = entry.getValue();
            AssertUtil.equalToNumDigits("At " + i + ", " + j + ": " + v
                + " != " + mij, v, mij, 6);
            assertEquals(m.getElement(i, j), mij, 0.0);
            entryCount++;
        }
        assertTrue(entryCount >= nonZeroCount);
    }

    /**
     * Helper that tests that the input vector is of the correct type, correct
     * size, and contains the correct values.
     *
     * @param v The vector to test
     * @param c The type that vector should be
     * @param dimensionality The size the vector should be
     * @param vals The values that the vector should have (vals.size must equal
     * dimensionality)
     */
    public static void testVectorEquals(
        Vector v,
        Class<?> c,
        int dimensionality,
        double... vals)
    {
        assertTrue(c.isInstance(v));
        assertEquals(v.getDimensionality(), dimensionality);
        int nonZeroCount = 0;
        for (int i = 0; i < dimensionality;
            ++i)
        {
            double val = vals[i];
            double vi = v.getElement(i);
            
            AssertUtil.equalToNumDigits("At " + i + ": " + val + " != " + vi,
                val, vi, 6);
            
            if (val != 0.0)
            {
                nonZeroCount++;
            }
                
        }
        
        int entryCount = 0;
        for (VectorEntry entry : v)
        {
            int i = entry.getIndex();
            double val = vals[i];
            double vi = entry.getValue();
            
            AssertUtil.equalToNumDigits("At " + i + ": " + val + " != " + vi,
                val, vi, 6);
            assertEquals(v.get(i), vi, 0.0);
            entryCount++;
        }
        
        assertTrue(entryCount >= nonZeroCount);
    }

    /**
     * Tests that the input matrix (mtrans) is the transpose of the other input
     * (m). They must be the same size (transposed), and the values must be
     * within 6 significant digits of each other.
     *
     * @param m The input matrix
     * @param mtrans The matrix that should be the transpose of m
     */
    public static void testIsTranspose(Matrix m,
        Matrix mtrans)
    {
        assertEquals(m.getNumRows(), mtrans.getNumColumns());
        assertEquals(m.getNumColumns(), mtrans.getNumRows());
        for (int i = 0; i < m.getNumRows();
            ++i)
        {
            for (int j = 0; j < m.getNumColumns();
                ++j)
            {
                double mij = m.getElement(i, j);
                double mtji = mtrans.getElement(j, i);
                AssertUtil.equalToNumDigits("At " + i + ", " + j + ": " + mij
                    + " != " + mtji, mij, mtji, 6);
            }
        }
    }

}
