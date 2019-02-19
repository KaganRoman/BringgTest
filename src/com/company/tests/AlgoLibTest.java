package com.company.tests;

import com.company.algo.AlgoLib;
import com.company.algo.Point;

import java.util.ArrayList;
import java.util.Random;

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

    @org.junit.jupiter.api.Test
    void calcDistanceMatrix4x4_2threads() {
        var points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(3, 4));
        points.add(new Point(2, 2));
        points.add(new Point(1.5, 12.2));
        var m = AlgoLib.calcDistanceMatrix(points, 2);
        var r = calcSimpleMatrix(points);

        assertTrue(checkEqual(m, r), "4x4 two threads failed");
    }

    @org.junit.jupiter.api.Test
    void calcDistanceMatrix5x5_3threads() {
        var points = new ArrayList<Point>();
        points.add(new Point(3, 4));
        points.add(new Point(0, 0));
        points.add(new Point(2, 2));
        points.add(new Point(-19.2, 21));
        points.add(new Point(1.5, 12.2));
        var m = AlgoLib.calcDistanceMatrix(points, 3);
        var r = calcSimpleMatrix(points);

        assertTrue(checkEqual(m, r), "5x5 three threads failed");
    }

    @org.junit.jupiter.api.Test
    void calcDistanceMatrix5x5_20threads() {
        var points = new ArrayList<Point>();
        points.add(new Point(3, 4));
        points.add(new Point(0, 0));
        points.add(new Point(2, 2));
        points.add(new Point(-19.2, 21));
        points.add(new Point(1.5, 12.2));
        var m = AlgoLib.calcDistanceMatrix(points, 20);
        var r = calcSimpleMatrix(points);

        assertTrue(checkEqual(m, r), "5x5 twenty threads failed");
    }

    @org.junit.jupiter.api.Test
    void calcDistanceMatrixAllCoverage() {
        for(int threads = 0; threads < 4; ++threads) {
            for(int size = 1; size < 20; ++size) {
                var points = new ArrayList<Point>();
                for(int i = 0; i < size; ++i) {
                    points.add(new Point(Math.random()*100, Math.random()*100));
                }
                var m = AlgoLib.calcDistanceMatrix(points, threads);
                var r = calcSimpleMatrix(points);

                assertTrue(checkEqual(m, r), String.format("All coverage test failed, threads: %d, size: %d", threads, size));
            }
        }
    }
}