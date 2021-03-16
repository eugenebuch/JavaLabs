package com.company.concreteFigures;

import com.company.common.Coordinate;

public class Rectangle extends Parallelogram {
    public Rectangle(Coordinate pointA, Coordinate pointB,
                         Coordinate pointC, Coordinate pointD) {
        super(pointA, pointB, pointC, pointD);
    }

    @Override
    public double countHeight() {
        return sideA;
    }
}
