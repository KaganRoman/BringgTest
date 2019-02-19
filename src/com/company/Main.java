package com.company;

import com.company.algo.AlgoLib;
import com.company.algo.Point;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        var points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(3, 4));
        var m = AlgoLib.calcDistanceMatrix(points, 1);

        for(int i = 0; i < points.size(); ++i) {
            for(int j = 0; j < points.size(); ++j) {
                System.out.print(m[i][j] + " \t");
            }
            System.out.println();
        }
    }

}
