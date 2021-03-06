package com.company.algo;

import java.util.*;
import static java.lang.Runtime.*;


public class AlgoLib {
    static int MAX_PARALLEL_NUM = getRuntime().availableProcessors(); // max thread number, probably running more threads will not improve the result

    public static double[][] calcDistanceMatrix(List<Point> points, int parallelDegree)
    {
        if(parallelDegree < 1)
            parallelDegree = 1; // we can throw an exception as well
        if(parallelDegree > MAX_PARALLEL_NUM)
            parallelDegree = MAX_PARALLEL_NUM; // we have to put some limit

        if(points == null || points.size() == 0)
            throw new IllegalArgumentException("List of points should not be empty");

        // we have to made (n-1)+(n-2)+(n-3)+..+1 calculations in order to fill upper part of the matrix
        int n = points.size();
        int numOfCalculations = n*(n-1)/2;

        if(parallelDegree > numOfCalculations) // no need to run more threads than calculations
            parallelDegree = numOfCalculations;

        var retVal = runCalculations(points, parallelDegree, numOfCalculations);
        return retVal;
    }

    private static double[][] runCalculations(List<Point> points, int parallelDegree, int numOfCalculations) {
        int n = points.size();

        double[][] destMatrix = new double[n][n];

        var threads = new ArrayList<Thread>();

        // for each thread we will calculate start point in the matrix,
        // and how many calculations should be done when iterating sequentially
        // Last run will complete also errors of rounds and may calculate more points
        int currX = 0, currY = 1, totalCount = 0;
        for(int run = 0; run < parallelDegree; ++run) {
            final int count = (run == parallelDegree - 1) ?
                    numOfCalculations - totalCount : numOfCalculations/parallelDegree;
            final int fromX = currX;
            final int fromY = currY;

            var t = new Thread(() -> calcMatrixPart(fromX, fromY, count, points, destMatrix));
            t.start();
            threads.add(t);

            // this is very lazy way to calculate next x and y
            totalCount += count;
            for(int c = 0; c < count; ++c) {
                currY++;
                if(currY == n) {
                    currX++;
                    currY = currX+1;
                }
            }
        }
        try {
            for(Thread t: threads)
                t.join();
        }
        catch (InterruptedException e) {
            // TODO - what to do in case of interrupt?
        }

        return destMatrix;
    }

    private static void calcMatrixPart(int currX, int currY, int count, List<Point> points, double[][] destMatrix) {
        var xIter = points.listIterator(currX);
        var xPoint = xIter.next();
        var yIter = points.listIterator(currY);
        while(count-- > 0) {
            if(!yIter.hasNext()) { // next line
                currX++;
                currY = currX+1;
                yIter = points.listIterator(currY);
                xPoint = xIter.next();
            }
            var p = yIter.next();
            var distance = xPoint.findDistance(p);
            destMatrix[currX][currY] = distance;
            destMatrix[currY][currX] = distance;
            ++currY;
        }
    }
}
