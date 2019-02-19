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

        int n = points.size();
        double[][] destMatrix = new double[n][n];

        // we have to made (n-1)+(n-2)+(n-3)+..+1 calculations in order to fill upper part of the matrix
        int numOfCalculations = n*(n-1)/2;

        if(parallelDegree > numOfCalculations)
            parallelDegree = numOfCalculations;

        var threads = new ArrayList<Thread>();

        int fromX = 0, fromY = 1, count = numOfCalculations/parallelDegree;
        for(int run = 0; run < parallelDegree; ++run) {
            var t = new Thread(() -> calcMatrixPart(fromX, fromY, count, points, destMatrix));
            t.start();
            threads.add(t);

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
