import java.util.*;

class Node implements Comparable<Node> {
    int x, y;
    double cost;
    Node parent;

    Node(int x, int y, double cost, Node parent) {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.parent = parent;
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(this.cost, other.cost);
    }
}

public class Astar {
    static final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    static final int[][] GRID = {
            {0, 0, 0, 0, 0, 0},
            {0, 1, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0}
    };

    public static List<Node> astar(int startX, int startY, int endX, int endY) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        boolean[][] closedList = new boolean[GRID.length][GRID[0].length];

        Node startNode = new Node(startX, startY, 0, null);
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();

            if (currentNode.x == endX && currentNode.y == endY) {
                List<Node> path = new ArrayList<>();
                while (currentNode != null) {
                    path.add(0, currentNode);
                    currentNode = currentNode.parent;
                }
                return path;
            }

            closedList[currentNode.x][currentNode.y] = true;

            for (int[] direction : DIRECTIONS) {
                int newX = currentNode.x + direction[0];
                int newY = currentNode.y + direction[1];

                if (newX >= 0 && newX < GRID.length && newY >= 0 && newY < GRID[0].length
                        && GRID[newX][newY] == 0 && !closedList[newX][newY]) {
                    double newCost = currentNode.cost + 1 + heuristic(newX, newY, endX, endY);
                    Node newNode = new Node(newX, newY, newCost, currentNode);
                    openList.add(newNode);
                }
            }
        }

        return Collections.emptyList();
    }

    public static double heuristic(int x, int y, int endX, int endY) {
        return Math.sqrt(Math.pow(endX - x, 2) + Math.pow(endY - y, 2));
    }

    public static void main(String[] args) {
        List<Node> path = astar(0, 0, 4, 5);
        if (!path.isEmpty()) {
            System.out.println("Path found:");
            for (Node node : path) {
                System.out.println(node.x + ", " + node.y);
            }
        } else {
            System.out.println("No path found");
        }
    }
}