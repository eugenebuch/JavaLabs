package com.company.concreteFigures;

import com.company.Figure;
import com.company.common.Coordinate;
import com.company.common.HeightCountable;

public class Triangle extends Figure implements HeightCountable {
    protected final Coordinate pointC;
    protected final double sideA, sideB, sideC;

    public Triangle(Coordinate pointA, Coordinate pointB,
                    Coordinate pointC) {
        super(pointA, pointB);
        this.pointC = pointC;

        sideA = countSide(pointA, pointB);
        sideB = countSide(pointB, pointC);
        sideC = countSide(pointC, pointA);
    }


    @Override
    public double countSquare() {
        var p = countPerimeter() / 2;
        return Math.sqrt(p * (p - sideA) * (p - sideB)
                * (p - sideC));
    }

    @Override
    public double countPerimeter() {
        return sideA + sideB + sideC;
    }

    @Override
    public double countHeight() {
        // optional, change side to change height
        return 2 * countSquare() / sideA;
    }

    public double countMedian() {
        return Math.sqrt(2 * Math.pow(sideA, 2)
                + Math.pow(sideB, 2) - Math.pow(sideC, 2)) / 2;
    }

    public double countBisector() {
        return Math.sqrt(sideA * sideB * (sideA + sideB + sideC)
                * (sideA + sideB - sideC)) / (sideA + sideB);
    }

    public double inscribedCircleRadius() {
        var p = countPerimeter() / 2;
        return countSquare() / p;
    }

    public double circumscribedCircleRadius() {
        return sideA * sideB * sideC / (4 * countSquare());
    }
}
