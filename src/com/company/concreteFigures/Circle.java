package com.company.concreteFigures;

import com.company.Figure;
import com.company.common.Coordinate;

public class Circle extends Figure {
    private final double radius, diameter;
    private static final double pi = 3.14;

    public Circle(Coordinate center, Coordinate pointOnCircle) {
        super(center, pointOnCircle);
        radius = countSide(pointA, pointB);
        diameter = radius * 2;
    }

    @Override
    public double countSquare() {
        return pi * Math.pow(radius, 2);

    }

    @Override
    public double countPerimeter() {
        return pi * diameter;
    }
}
