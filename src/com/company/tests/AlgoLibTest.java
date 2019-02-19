package com.company.tests;

import com.company.algo.AlgoLib;
import com.company.algo.Point;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AlgoLibTest {

    static double[][] calcSimpleMatrix(ArrayList<Point> points) {
        double[][] result = new double[points.size()][points.size()];
        for(int i = 0; i < points.size(); ++i) {
            for (int j = 0; j < points.size(); ++j) {
                var dist = points.get(i).findDistance(points.get(j));
                result[i][j] = result[j][i] = dist;
            }
        }
        return result;
    }

    static boolean checkEqual(double[][] p1, double[][] p2) {
        for(int i = 0; i < p1.length; ++i)
            for(int j = 0; j < p1.length; ++j)
                if(p1[i][j] != p2[i][j]) return false;
        return true;
    }

    @org.junit.jupiter.api.Test
    void calcDistanceMatrix2x2() {
        var points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(3, 4));
        var m = AlgoLib.calcDistanceMatrix(points, 1);
        var r = calcSimpleMatrix(points);

        assertTrue(checkEqual(m, r), "2x2 failed");
    }

    @org.junit.jupiter.api.Test
    void calcDistanceMatrix3x3() {
        var points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(3, 4));
        points.add(new Point(2, 2));
        var m = AlgoLib.calcDistanceMatrix(points, 1);
        var r = calcSimpleMatrix(points);

        assertTrue(checkEqual(m, r), "3x3 failed");
    }

    @org.junit.jupiter.api.Test
    void calcDistanceMatrix4x4() {
        var points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(3, 4));
        points.add(new Point(2, 2));
        points.add(new Point(1.5, 12.2));
        var m = AlgoLib.calcDistanceMatrix(points, 1);
        var r = calcSimpleMatrix(points);

        assertTrue(checkEqual(m, r), "4x4 failed");
    }
}