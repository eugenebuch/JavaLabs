package com.company;

import com.company.common.Coordinate;

public abstract class Figure {
    protected Coordinate pointA, pointB;

    public Figure(Coordinate pointA, Coordinate pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
    }

    protected double countSide(Coordinate pointA, Coordinate pointB) {
        return Math.sqrt(Math.pow(pointA.x - pointB.x, 2)
                + Math.pow(pointA.y - pointB.y, 2));
    }

    public abstract double countSquare();
    public abstract double countPerimeter();
}
