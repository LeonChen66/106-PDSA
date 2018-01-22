import java.util.*;
import java.util.Queue;

public class Search {

    public static class AStar
    {
        public final static int BAR = -1;
        public final static int PATH = -2;
        public final static int DIRECT_VALUE = 10;
        public final static int OBLIQUE_VALUE = 14;
        private Stack<Coord> pointArray = new Stack<>();
        Queue<Node> openList = new PriorityQueue<Node>();
        List<Node> closeList = new ArrayList<Node>();

        public void start(MapInfo mapInfo)
        {
            if(mapInfo==null) return;
            // clean
            openList.clear();
            closeList.clear();
            openList.add(mapInfo.start);
            moveNodes(mapInfo);
        }

        private void moveNodes(MapInfo mapInfo)
        {
            while (!openList.isEmpty())
            {
                if (isCoordInClose(mapInfo.end.coord))
                {
                    drawPath(mapInfo.maps, mapInfo.end);
                    break;
                }
                Node current = openList.poll();
                closeList.add(current);
                addNeighborNodeInOpen(mapInfo,current);
            }
        }

        private void drawPath(int[][] maps, Node end)
        {
            if(end==null||maps==null) return;
            StdOut.println(end.G);
            while (end != null)
            {
                Coord c = end.coord;
                maps[c.y][c.x] = PATH;
                pointArray.push(c);
                end = end.parent;
            }
            while(!pointArray.isEmpty()){
                Coord min = pointArray.pop();
                int row_final = min.y+1;
                int col_final = min.x+1;
                StdOut.println(row_final+","+col_final);
            }
        }

        private void addNeighborNodeInOpen(MapInfo mapInfo,Node current)
        {
            int x = current.coord.x;
            int y = current.coord.y;

            // 右
            addNeighborNodeInOpen(mapInfo,current, x + 1, y, DIRECT_VALUE);
            // 右下
            addNeighborNodeInOpen(mapInfo,current, x + 1, y + 1, OBLIQUE_VALUE);
            // 下
            addNeighborNodeInOpen(mapInfo,current, x, y + 1, DIRECT_VALUE);
            // 左下
            addNeighborNodeInOpen(mapInfo,current, x - 1, y + 1, OBLIQUE_VALUE);
            // 左
            addNeighborNodeInOpen(mapInfo,current, x - 1, y, DIRECT_VALUE);
            // 左上
            addNeighborNodeInOpen(mapInfo,current, x - 1, y - 1, OBLIQUE_VALUE);
            // 上
            addNeighborNodeInOpen(mapInfo,current, x, y - 1, DIRECT_VALUE);
            // 右上
            addNeighborNodeInOpen(mapInfo,current, x + 1, y - 1, OBLIQUE_VALUE);
        }

        private void addNeighborNodeInOpen(MapInfo mapInfo,Node current, int x, int y, int value)
        {
            if (canAddNodeToOpen(mapInfo,x, y))
            {
                Node end=mapInfo.end;
                Coord coord = new Coord(x, y);
                int G = current.G + value;
                Node child = findNodeInOpen(coord);
                if (child == null)
                {
                    int H=calcH(mapInfo,coord);
                    if(isEndNode(end.coord,coord))
                    {
                        child=end;
                        child.parent=current;
                        child.G=G;
                        child.H=H;
                    }
                    else
                    {
                        child = new Node(coord, current, G, H);
                    }
                    openList.add(child);
                }
                else if (child.G > G)
                {
                    child.G = G;
                    child.parent = current;
                    openList.add(child);
                }
            }
        }

        private Node findNodeInOpen(Coord coord)
        {
            if (coord == null || openList.isEmpty()) return null;
            for (Node node : openList)
            {
                if (node.coord.equals(coord))
                {
                    return node;
                }
            }
            return null;
        }


        private int calcH(MapInfo mapInfo,Coord coord)
        {
            return mapInfo.maps[coord.y][coord.x];
        }

        private boolean isEndNode(Coord end,Coord coord)
        {
            return coord != null && end.equals(coord);
        }

        private boolean canAddNodeToOpen(MapInfo mapInfo,int x, int y)
        {
            if (x < 0 || x >= mapInfo.width || y < 0 || y >= mapInfo.hight) return false;
            if (mapInfo.maps[y][x] == BAR) return false;
            if (isCoordInClose(x, y)) return false;

            return true;
        }

        private boolean isCoordInClose(Coord coord)
        {
            return coord!=null&&isCoordInClose(coord.x, coord.y);
        }

        private boolean isCoordInClose(int x, int y)
        {
            if (closeList.isEmpty()) return false;
            for (Node node : closeList)
            {
                if (node.coord.x == x && node.coord.y == y)
                {
                    return true;
                }
            }
            return false;
        }
    }

    public static class MapInfo
    {
        public int[][] maps;
        public int width;
        public int hight;
        public Node start;
        public Node end;

        public MapInfo(int[][] maps, int width, int hight, Node start, Node end)
        {
            this.maps = maps;
            this.width = width;
            this.hight = hight;
            this.start = start;
            this.end = end;
        }
    }

    public static class Coord
    {

        public int x;
        public int y;

        public Coord(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj == null) return false;
            if (obj instanceof Coord)
            {
                Coord c = (Coord) obj;
                return x == c.x && y == c.y;
            }
            return false;
        }
    }

    public static class Node implements Comparable<Node>
    {

        public Coord coord;
        public Node parent;
        public int G;
        public int H;

        public Node(int x, int y)
        {
            this.coord = new Coord(x, y);
        }

        public Node(Coord coord, Node parent, int g, int h)
        {
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
    public static void main(String[] args) {
        String input = args[0];
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
        if(start[0]==goal[0]&&start[1]==goal[1]){
            StdOut.println(0);
            StdOut.print(start[0]+","+start[1]);
            return;
        }
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
        MapInfo info=new MapInfo(maps,maps[0].length, maps.length,new Node(start[1]-1, start[0]-1), new Node(goal[1]-1, goal[0]-1));
        new Search.AStar().start(info);
//        System.out.println(Arrays.deepToString(maps));
    }
}
