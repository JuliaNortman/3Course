import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static public int findRight(ArrayList<Point> points){
        int temp = 0;
        for (int i = 0; i < points.size(); i++){
            if(points.get(temp).x < points.get(i).x) temp = i;
        }
        return temp;
    }

    static public ArrayList<Point> build(ArrayList<Point> points){
        ArrayList<Point> Obl1 = new ArrayList<>();
        ArrayList<Point> Obl2 = new ArrayList<>();
        if(points.size()<3) return points;

        int index = findRight(points);
        for (int i = 0; i < index; i++) {
            Point p = points.get(i);
            Obl1.add(p);
        }
        for (int i = index; i < points.size(); i++) {
            Point p = points.get(i);
            Obl2.add(p);
        }
        Obl1.add(points.get(index));
        Obl2.add(points.get(0));

        Obl1 = halfBuild(Obl1, true);
        Obl1.remove(Obl1.size()-1);
        Obl2 = halfBuild(Obl2, false);
        Obl2.remove(Obl2.size()-1);
        Obl1.addAll(Obl2);
        return Obl1;
    }

    static public ArrayList<Point> halfBuild(ArrayList<Point> points, boolean flag){
        if(points.size()<3) return points;
        ArrayList<Point> Stack = new ArrayList<>();
        Point p = new Point();
        p.setLocation(points.get(0));
        if(flag) --p.y;
        else ++p.y;

        Stack.add(p);
        Stack.add(points.get(0));

        int last = Stack.size()-1;
        int cur = 1;
        while (cur < points.size()) {
            Stack.add(points.get(cur++));
            last++;
            if (pointLocation(Stack.get(last - 2), Stack.get(last - 1), Stack.get(last)) == 1) {
                Stack.remove(last-1);
                last--;
                continue;
            }
            if (pointLocation(Stack.get(last - 1),Stack.get(last),points.get(points.size()-1)) == 1){
                Stack.remove(last);
                last--;
                continue;
            }
        }
        Stack.remove(0);
        return Stack;
    }
    static public int pointLocation(Point A, Point B, Point P)
    {
        int cp1 = (B.x - A.x) * (P.y - A.y) - (B.y - A.y) * (P.x - A.x);
        if (cp1 > 0) return 1;
        else if (cp1 == 0) return 0;
        else return -1;
    }

    public static ArrayList<Point> inputDatas(String filename) throws FileNotFoundException {
        ArrayList<Point> arr = new ArrayList<>();

        Scanner scFile = new Scanner(new File(filename));
        while (scFile.hasNextInt()) {
            Point tmp = new Point();
            tmp.x = scFile.nextInt();
            tmp.y = scFile.nextInt();
            arr.add(tmp);
        }
        return arr;
    }

    public static void main(String args[]) throws FileNotFoundException
    {
        try (Scanner sc = new Scanner(System.in)) {
            ArrayList<Point> points = inputDatas("input.txt");

            ArrayList<Point> p = build(points);
            System.out.println("The points in the hull are: ");
            for (int i = 0; i < p.size(); i++)
                System.out.println(p.get(i).x + " " + p.get(i).y);
        }
    }
}