package com.company.concreteFigures;

import com.company.Figure;
import com.company.common.Coordinate;
import com.company.common.HeightCountable;

public class Trapezoid extends Figure implements HeightCountable {
    private final Coordinate pointC, pointD;
    private final double sideA, sideB, sideC, sideD;


    public Trapezoid(Coordinate pointA, Coordinate pointB,
                     Coordinate pointC, Coordinate pointD) {
        super(pointA, pointB);
        this.pointC = pointC;
        this.pointD = pointD;

        sideA = countSide(pointA, pointB);
        sideB = countSide(pointB, pointC);
        sideC = countSide(pointC, pointD);
        sideD = countSide(pointD, pointA);
    }

    @Override
    public double countSquare() {
        return sideD * countHeight();
    }

    @Override
    public double countPerimeter() {
        return sideA + sideB + sideC + sideD;
    }

    @Override
    public double countHeight() {
        var angle = Math.acos(Math.pow(sideA, 2) + Math.pow(sideB, 2)
                - Math.pow(countSide(pointB, pointD), 2)/(2 * sideA * sideB));
        return sideA * Math.sin(angle);
    }
}
