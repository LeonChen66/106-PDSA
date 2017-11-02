import java.awt.*;
import java.util.*;
import java.lang.*;
import java.util.List;
import java.util.Stack;

public class Onion {

    public static int[] ConvexHullVertex(Point2D[] pointSet){
        ArrayList<Point2D> array = new ArrayList<Point2D>();
        for (Point2D p:pointSet){
            array.add(p);
        }
        int min = findMinY(pointSet);
        Point2D start = pointSet[min];
        Arrays.sort(pointSet, start.ATAN2_ORDER);
        Stack<Point2D> convex = new Stack<Point2D>();
        convex.push(pointSet[0]);
        convex.push(pointSet[1]);
        for (int i=2;i<pointSet.length;i++){
            Point2D b = convex.pop();
            Point2D a = convex.pop();
            Point2D c = pointSet[i];
            while(Point2D.ccw(a,b,c)!=1){
                b = a;
                a = convex.pop();
            }
            convex.push(a);
            convex.push(b);
            convex.push(c);
        }

        Iterator<Point2D> iterator = convex.iterator();
        int count = 0;
        int[] index = new int[convex.size()];
        while(iterator.hasNext()){
            Point2D p = iterator.next();
            index[count++] = array.indexOf(p);
        }
        return index;
    }

    private static int countConvex(Point2D[] pointSet){
        int count = 0;
        while(pointSet.length>2){try{
        int min = findMinY(pointSet);
        Point2D start = pointSet[min];
        Arrays.sort(pointSet, start.ATAN2_ORDER);
        Stack<Point2D> convex = new Stack<Point2D>();
        convex.push(pointSet[0]);
        convex.push(pointSet[1]);
        for (int i=2;i<pointSet.length;i++){
            Point2D b = convex.pop();
            Point2D a = convex.pop();
            Point2D c = pointSet[i];
            while(Point2D.ccw(a,b,c)!=1){
                b = a;
                a = convex.pop();
            }
            convex.push(a);
            convex.push(b);
            convex.push(c);
        }
            int index;
            int run = convex.size();
        for (int i=0;i<run;i++){
            index = Arrays.asList(pointSet).indexOf(convex.pop());
            pointSet[index] = null;
        }
            List<Point2D> list = new ArrayList<Point2D>();

            for(Point2D s : pointSet) {
                if(s != null) {
                    list.add(s);
                }
            }

            pointSet = list.toArray(new Point2D[list.size()]);
        count++;
        }catch(Exception e){
            break;
        }
        }

        return count;
    }

    private static int findMinY(Point2D[] p){
        int min = 0;
        for (int i=0;i<p.length;i++){
            if(Point2D.Y_ORDER.compare(p[min],p[i])==1){
                min = i;
            }
        }
        return min;
    }

    public static void drawLine(Point2D[] p, int[] index){
        for (int i=0;i<index.length-1;i++){
//            StdOut.println(i);
            StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
            StdDraw.line(p[index[i]].x(),p[index[i]].y(),p[index[i+1]].x(),p[index[i+1]].y());
        }
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        StdDraw.line(p[index[0]].x(),p[index[0]].y(),p[index[index.length-1]].x(),p[index[index.length-1]].y());
    }

    public static void drawPoint(Point2D[] p){
        StdDraw.setCanvasSize(900,900);
        StdDraw.setScale(-2,+2);
        StdDraw.setPenRadius(0.01);
        Font font = new Font("Arial", Font.BOLD, 20);
        StdDraw.setFont(font);
        for (int i=0;i<p.length;i++){
            StdDraw.point(p[i].x(),p[i].y());
            StdDraw.textRight(p[i].x(),p[i].y(),String.valueOf(i));
        }
    }

    public static void quickSort(Point2D[] array , Comparator<Point2D> c) {
        quickSortKit(array , c , 0 , array.length-1);
    }

    private static void swap(Object[] data,int a,int b){
        Object temp = data[a];
        data[a] = data[b];
        data[b] = temp;
    }

    public static void quickSortKit(Point2D[] array , Comparator<Point2D> c , int start , int end) {

        if(start >= end) return;
        Point2D pivot = array[start];
        int left = start;
        int right = end+1;

        while (true){
            while (c.compare(pivot , array[++left]) == 1)
                if (left == end) break;
            while (c.compare(pivot , array[--right]) == -1)
                if (right == start) break;

            if(left >= right) break;

            Point2D temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            swap(array,left,right);
        }
        array[start] = array[right];
        array[right] = pivot;

        quickSortKit(array , c , start , right-1);
        quickSortKit(array , c , right+1 , end);

    }

    public static void printArray(int[] array){
        String sp = "";
        System.out.print("[");
        for(int i:array){
            System.out.print(sp + i);
            sp = ",";
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        /*
        //4-1 test
        long startTime = System.currentTimeMillis();
        int num = 1000000;
        Point2D[] points = new Point2D[num];
        for(int i = 0  ; i < num ; i++){
            points[i] = new Point2D(Math.random(),Math.random());
//            System.out.println(String.format("%d:(%.3f,%.3f)",i,points[i].x(),points[i].y()));
        }
//        drawPoint(points);
        int[] index = ConvexHullVertex(points);
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
//        drawLine(points, index);
        */
//        /* 4-2
        In br = new In(args[0]);
        int num = br.readInt();
        Point2D[] points = new Point2D[num];
        for(int i=0;i<num;i++) {
            double temp_x = br.readDouble();
            double temp_y = br.readDouble();
            points[i] = new Point2D(temp_x,temp_y);
//            System.out.println(String.format("%d:(%.3f,%.3f)",i,points[i].x(),points[i].y()));
        }
        StdOut.print(countConvex(points));
        /*
        Point2D[] oriPoints = Arrays.copyOf(points,points.length);
        int nowLength = oriPoints.length;
        int count = 0;
        int[] index = ConvexHullVertex(points);
//        StdOut.println(Arrays.toString(index));
        ArrayList<Integer> index_array = new ArrayList<Integer>();
        while (index.length >2 ){
            try{
                count++;
                Collections.addAll(index_array, Arrays.stream(index).boxed().toArray(Integer[]::new));
                nowLength = nowLength - index.length;
                Point2D[] array = new Point2D[nowLength];
                int cn = 0;
                for (int i=0;i<num;i++){
                    if (!index_array.contains(i)){
                        array[cn] = oriPoints[i];
                        cn++;
                    }
                }
                Point2D[] temp = Arrays.copyOf(array,array.length);
                int[] test = ConvexHullVertex(temp);
                index = test;
                for (int i=0;i<test.length;i++){
                    index[i] = Arrays.asList(oriPoints).indexOf(array[test[i]]);
                }
            }catch (Exception e){
                break;
            }
        }
        StdOut.print(count);
        */
    }
}
