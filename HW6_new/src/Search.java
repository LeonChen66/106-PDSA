import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Search {
    private final static int VH_length = 10;
    private final static int Dia_length = 14;
    public final static int BAR = -1;
    public final static int PATH = -2;
//    private int road_length = 0;
    private MinPQ<Node> openList = new MinPQ<>();
    private List<Node> closeList = new ArrayList<>();
    private Stack<Coord> pointArray = new Stack<>();
//    private boolean[][] matrix_open;

    private boolean isEndNode(Coord end,Coord coord){
        return coord !=null &&end.equals(coord);
    }

    private boolean canAddNodeToOpen(MapInfo mapInfo, int row, int col){
//        StdOut.println("limit:"+mapInfo.hight+","+mapInfo.width+"==>"+(row)+","+(col));
        if (row<0 || row >= mapInfo.hight || col<0 || col>=mapInfo.width){
            return false;
        }
        if (mapInfo.maps[row][col]==BAR) return false;
        if (isCoordInClose(row,col)) return false;
        return true;
    }

    private boolean isCoordInClose(Coord coord){
        return coord!=null&&isCoordInClose(coord.row, coord.col);
    }
    private boolean isCoordInClose(int row, int col){
        if(closeList.isEmpty()) return false;
        for (Node node : closeList){
            if(node.coord.row == row && node.coord.col==col){
                return true;
            }
        }
        return false;
    }

    private int calcH(MapInfo mapInfo, Coord coord){
        return mapInfo.maps[coord.row][coord.col];
    }

    private Node findNodeInOpen(Coord coord){
        if(coord==null||openList.isEmpty()) return null;
        for (Node node : openList){
            if (node.coord.equals(coord)){
                return node;
            }
        }
        return null;
    }

    private void addNeighborNodeInOpen(MapInfo mapInfo,Node current){
        int row = current.coord.row;
        int col = current.coord.col;
        addNeighborNodeInOpen(mapInfo,current,row,col+1,VH_length);
        addNeighborNodeInOpen(mapInfo,current,row+1,col+1,Dia_length);
        addNeighborNodeInOpen(mapInfo,current,row+1,col,VH_length);
        addNeighborNodeInOpen(mapInfo,current,row+1,col-1,Dia_length);
        addNeighborNodeInOpen(mapInfo,current,row,col-1,VH_length);
        addNeighborNodeInOpen(mapInfo,current,row-1,col-1,Dia_length);
        addNeighborNodeInOpen(mapInfo,current,row-1,col,VH_length);
        addNeighborNodeInOpen(mapInfo,current,row-1,col+1,Dia_length);
    }

    private void addNeighborNodeInOpen(MapInfo mapinfo,Node current, int row, int col, int value){
        if (canAddNodeToOpen(mapinfo,row,col)){
            Node end = mapinfo.end;
            Coord coord = new Coord(row,col);
            int G = current.G + value;
            Node child = findNodeInOpen(coord);
            if(child==null){
                int H = calcH(mapinfo,coord);
                if(isEndNode(end.coord,coord)){
                    child=end;
                    child.parent = current;
                    child.G = G;
                    child.H = H;
                }
                else{
                    child = new Node(coord, current, G, H);
                }
                openList.insert(child);
            }
            else if (child.G > G){
                child.G = G;
                child.parent = current;
                openList.insert(child);
            }
        }
    }

    private void drawPath(int[][] maps, Node end){
        if (end==null||maps ==null) return;
        StdOut.println(end.G);
        while(end!=null){
            Coord c = end.coord;
            maps[c.row][c.col] = PATH;
            pointArray.push(c);
            end = end.parent;
        }
        while(!pointArray.isEmpty()){
            Coord min = pointArray.pop();
            int row_final = min.row+1;
            int col_final = min.col+1;
            StdOut.println(row_final+","+col_final);
        }
    }

    public void start(MapInfo mapInfo){
        if(mapInfo == null) return;
        openList = new MinPQ<>();
        closeList.clear();
        openList.insert(mapInfo.start);
        moveNodes(mapInfo);
    }

    private void moveNodes(MapInfo mapInfo){
        while(!openList.isEmpty()){
            if (isCoordInClose(mapInfo.end.coord)){
                drawPath(mapInfo.maps,mapInfo.end);
                break;
            }
            Node current = openList.delMin();
            closeList.add(current);
            addNeighborNodeInOpen(mapInfo,current);
        }
    }

    public static class Coord{
        public int row;
        public int col;
        public Coord(int row, int col){
            this.row = row;
            this.col = col;
        }
        @Override
        public boolean equals(Object obj){
            if (obj==null) return false;
            if (obj instanceof Coord){
                Coord c = (Coord) obj;
                return row==c.row && col==c.col;
            }
            return false;
        }
    }

    public static class Node implements Comparable<Node>{
        public Coord coord;
        public Node parent;
        public int G;
        public int H;

        public Node(int row, int col){
            this.coord = new Coord(row,col);
        }

        public Node(Coord coord, Node parent, int g,int h){
            this.coord = coord;
            this.parent = parent;
            G = g;
            H = h;
        }
        @Override
        public int compareTo(Node o){
            if(o==null) return -1;
            if(G+H>o.G+o.H) return 1;
            else if (G+H<o.G+o.H) return -1;
            else{
                if (G>o.G) return -1;
                else if (G<o.G) return 1;
                else return 0;
            }
        }
    }

    public static class MapInfo{
        public int[][] maps;
        public int width;
        public int hight;
        public Node start;
        public Node end;
        public MapInfo(int[][] maps, int width, int hight,Node start,Node end){
            this.maps = maps;
            this.width = width;
            this.hight = hight;
            this.start = start;
            this.end = end;
        }
    }

    public static void printMap(int[][] maps)
    {
        for (int i = 0; i < maps.length; i++)
        {
            for (int j = 0; j < maps[i].length; j++)
            {
                System.out.print(maps[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String agrv[]){
        String input = agrv[0];
        In br = new In(input);
        int[][] maps;
        int[] start;
        int[] goal;
        int row;
        int col;
        br.readString();
        row = br.readInt();
        br.readString();
        col = br.readInt();
        maps = new int[row][col];
        br.readString();
        start = new int[2];
        goal = new int[2];
        String vals;
        vals = br.readString();
        start[0] = Integer.parseInt(vals.split(",")[0]);
        start[1] = Integer.parseInt(vals.split(",")[1]);
        br.readString();
        vals = br.readString();
        goal[0] = Integer.parseInt(vals.split(",")[0]);
        goal[1] = Integer.parseInt(vals.split(",")[1]);
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                vals = br.readString();
                if(vals.equals("nn")){
                    maps[i][j] = -1;
                }
                else{
                    maps[i][j] = Integer.parseInt(vals);
                }
            }
        }
        MapInfo info=new MapInfo(maps,col, row,new Node(start[0]-1, start[1]-1), new Node(goal[0]-1, goal[1]-1));
        new Search().start(info);
//        printMap(maps);
    }

//    printMap(maps);
//        test.Search_min();
//        System.out.println(Arrays.deepToString(test.map));
    }

