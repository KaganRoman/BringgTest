package com.company.tests;

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

    @org.junit.jupiter.api.Test
    void calcDistanceMatrix() {
    }
}