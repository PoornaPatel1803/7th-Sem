import java.util.*;

class Node {
    int[][] state;
    Node parent;
    int cost;
    int heuristic;

    public Node(int[][] state, Node parent, int cost, int heuristic) {
        this.state = state;
        this.parent = parent;
        this.cost = cost;
        this.heuristic = heuristic;
    }
}

public class EightPuzzle {
    static int[][] initialState = {
        {1, 3, 4},
        {8, 2, 5},
        {7, 6, 0}
    };

    static int[][] goalState = {
        {1, 2, 3},
        {8, 0, 4},
        {7, 6, 5}
    };

    static List<Node> queue = new ArrayList<>();
    static Set<String> visited = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Initial State:");
        printState(initialState);
        System.out.println();
        System.out.println("Goal State:");
        printState(goalState);
        System.out.println();

        Node root = new Node(initialState, null, 0, heuristic(initialState));
        queue.add(root);
        visited.add(stateToString(initialState));

        while (!queue.isEmpty()) {
            Node node = queue.remove(0);
            if (Arrays.deepEquals(node.state, goalState)) {
                printPath(node);
                return;
            }

            List<Node> children = generateChildren(node);
            for (Node child : children) {
                if (!visited.contains(stateToString(child.state))) {
                    queue.add(child);
                    visited.add(stateToString(child.state));
                }
            }
        }
    }

    static List<Node> generateChildren(Node node) {
        List<Node> children = new ArrayList<>();
        int[][] state = node.state;
        int x = -1, y = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == 0) {
                    x = i;
                    y = j;
                    break;
                }
            }
            if (x != -1) break;
        }

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];
            if (newX >= 0 && newX < 3 && newY >= 0 && newY < 3) {
                int[][] newState = copyState(state);
                newState[x][y] = newState[newX][newY];
                newState[newX][newY] = 0;
                children.add(new Node(newState, node, node.cost + 1, heuristic(newState)));
            }
        }

        return children;
    }

    static int heuristic(int[][] state) {
        int heuristic = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] != goalState[i][j] && state[i][j] != 0) {
                    heuristic++;
                }
            }
        }
        return heuristic;
    }

    static int[][] copyState(int[][] state) {
        int[][] newState = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newState[i][j] = state[i][j];
            }
        }
        return newState;
    }

    static String stateToString(int[][] state) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(state[i][j]).append(",");
            }
        }
        return sb.toString();
    }

    static void printPath(Node node) {
        if (node == null) return;
        printPath(node.parent);
        printState(node.state);
        System.out.println();
    }

    static void printState(int[][] state) {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("| " + state[i][j] + " ");
            }
            System.out.println("|");
            System.out.println("-------------");
        }
    }
}