import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/*
(x,y) - point
x,y - point
*/

public class PointReader {
    private ArrayList<Point2D> pointList;

    public PointReader(String filepath){
        pointList = new ArrayList<>();
        String []xy;
        try{
            Scanner scanner = new Scanner(new File(filepath));
            while(scanner.hasNext()){
                String s = scanner.nextLine();
                xy = s.split("[)(,]");
                pointList.add(new Point2D.Double(Double.parseDouble(xy[1]), Double.parseDouble(xy[2])));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Point2D> getPoints(){
        return pointList;
    }
}