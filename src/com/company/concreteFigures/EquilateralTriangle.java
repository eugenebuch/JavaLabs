package com.company.concreteFigures;

import com.company.common.Coordinate;

public class EquilateralTriangle extends Triangle {
    public EquilateralTriangle(Coordinate pointA, Coordinate pointB, Coordinate pointC) {
        super(pointA, pointB, pointC);
    }

    @Override
    public double countSquare() {
        return Math.sqrt(3) / 4 * Math.pow(sideA, 2);
    }
}
