import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EuclidianDistance {
    /**
     * varianta 1-cu formula matematica
     */
    public static double distance1(int x1, int y1, int x2, int y2)
    {
        // Calculating distance
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) * 1.0);
    }

    /**
     * varianta 2-cu functia distance
     */
    public static double distance2(int x1, int y1, int x2, int y2){

        return Point2D.distance(x1, y1, x2, y2);
    }

}
