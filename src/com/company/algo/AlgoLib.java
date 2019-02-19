package com.company.algo;

import java.util.List;
import static java.lang.Runtime.*;


public class AlgoLib {
    public static double[][] calcDistanceMatrix(List<Point> points, int parallelDegree) {

        double[][] destMatrix = new double[points.size()][points.size()];

        // naive
        var xIter = points.listIterator();
        while(xIter.hasNext()) {
            int x = xIter.nextIndex();
            var p1 = xIter.next();
            var yIter = points.listIterator(x+1);
            while(yIter.hasNext())
            {
                int y = yIter.nextIndex();
                var p2 = yIter.next();
                var distance = p1.findDistance(p2);
                destMatrix[x][y] = distance;
                destMatrix[y][x] = distance;
            }
        }

        return destMatrix;
    }
}
