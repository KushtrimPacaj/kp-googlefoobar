import java.util.*;

/**
 * Created by kushtrimpacaj on 14/01/2017.
 * <p>
 * Don't Get Volunteered!
 * ======================
 * <p>
 * As a henchman on Commander Lambda's space station, you're expected to be resourceful, smart, and a quick thinker.
 * It's not easy building a doomsday device and capturing bunnies at the same time, after all!
 * In order to make sure that everyone working for her is sufficiently quick-witted,
 * Commander Lambda has installed new flooring outside the henchman dormitories. It looks like a chessboard,
 * and every morning and evening you have to solve a new movement puzzle in order to cross the floor.
 * <p>
 * That would be fine if you got to be the rook or the queen, but instead, you have to be the knight.
 * Worse, if you take too much time solving the puzzle, you get "volunteered" as a test subject for the LAMBCHOP doomsday device!
 * <p>
 * To help yourself get to and from your bunk every day, write a function called answer(src, dest) which takes in two parameters:
 * the source square, on which you start, and the destination square, which is where you need to land to solve the puzzle.
 * The function should return an integer representing the smallest number of moves it will take for you to travel
 * from the source square to the destination square using a chess knight's moves
 * (that is, two squares in any direction immediately followed by one square perpendicular to that direction,
 * or vice versa, in an "L" shape).
 * <p>
 * Both the source and destination squares will be an integer between 0 and 63,
 * inclusive, and are numbered like the example chessboard below:
 * <p>
 * ------------------------- <br/>
 * | 0| 1| 2| 3| 4| 5| 6| 7| <br/>
 * ------------------------- <br/>
 * | 8| 9|10|11|12|13|14|15| <br/>
 * ------------------------- <br/>
 * |16|17|18|19|20|21|22|23| <br/>
 * ------------------------- <br/>
 * |24|25|26|27|28|29|30|31| <br/>
 * ------------------------- <br/>
 * |32|33|34|35|36|37|38|39| <br/>
 * ------------------------- <br/>
 * |40|41|42|43|44|45|46|47| <br/>
 * ------------------------- <br/>
 * |48|49|50|51|52|53|54|55| <br/>
 * ------------------------- <br/>
 * |56|57|58|59|60|61|62|63| <br/>
 * ------------------------- <br/>
 * <p>
 * Languages
 * =========
 * <p>
 * To provide a Python solution, edit solution.py
 * To provide a Java solution, edit solution.java
 * <p>
 * Test cases
 * ==========
 * <p>
 * Inputs:
 * (int) src = 19
 * (int) dest = 36
 * Output:
 * (int) 1
 * <p>
 * Inputs:
 * (int) src = 0
 * (int) dest = 1
 * Output:
 * (int) 3
 */
public class Challenge3 {

    public static final int BOARD_LENGTH = 8;

    public static void main(String[] args) {
        //let's test our implementation with two test cases

        int answer1 = answer(19, 36);
        int answer2 = answer(0, 1);

        if (answer1 != 1 && answer2 != 3) {
            System.out.println("Bad results");
        } else {
            System.out.println("Correct");
        }
    }


    /**
     * Returns the minimal number of moves a knight has to make to go from src position to dest position
     * @param src the starting position
     * @param dest the destination position
     * @return the minimum number of moves that the knight has to make when jumping from src to dest.
     */
    public static int answer(int src, int dest) {
        List<Node> nodes = new ArrayList<>();
        List<Lines> lines = new ArrayList<>();


        Node[][] chessBoard = new Node[BOARD_LENGTH][BOARD_LENGTH];
        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                Node node = new Node(i, j);
                chessBoard[i][j] = node; // chessboard is used only when calculating
                nodes.add(node);
            }
        }


        for (Node thisNode : nodes) {
            List<Node> nodesConnectedWithBelow = thisNode.getNodesKnightCanJumpFromHere(chessBoard);
            for (Node otherNode : nodesConnectedWithBelow) {
                Lines line = new Lines(thisNode, otherNode, 1);
                lines.add(line);
            }
        }


        Node srcNode = getNodeFromFoobarPos(chessBoard, src);
        Node destNode = getNodeFromFoobarPos(chessBoard, dest);

        Graph graph = new Graph(nodes, lines);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.execute(srcNode);
        LinkedList<Node> path = dijkstra.getPath(destNode);

        System.out.println("Shortest path is : " + (path.size() - 1));
        for (Node node : path) {
            System.out.println(node);
        }

        return path.size() - 1;
    }

    private static Node getNodeFromFoobarPos(Node[][] chessBoard, int foobarPos) {
        return chessBoard[foobarPos % 8][foobarPos / 8];
    }

    /**
     * A model for lines ( edges of a graph). It contains the two nodes which it connects
     */
    static class Lines {
        private final Node source;
        private final Node destination;
        private final int weight;

        public Lines(Node source, Node destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        public Node getDestination() {
            return destination;
        }

        public Node getSource() {
            return source;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return source + " " + destination;
        }

    }


    static class Node {

        private int x;
        private int y;
        private int foobarPos;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
            foobarPos = y * 8 + x;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (x != node.x) return false;
            return y == node.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }

        /**
         * @param chessBoard The 2D array containing all the nodes (vertices) of the graph.
         *                   These are actually all the 64 places in the chess board.
         * @return The vertices that the knight can go to from this places.
         */
        public List<Node> getNodesKnightCanJumpFromHere(Node[][] chessBoard) {
            List<Node> placesKnightCanJumpTo = new ArrayList<>();

            addToListIfNodeExists(chessBoard, placesKnightCanJumpTo, x - 2, y + 1);
            addToListIfNodeExists(chessBoard, placesKnightCanJumpTo, x - 1, y + 2);
            addToListIfNodeExists(chessBoard, placesKnightCanJumpTo, x + 1, y + 2);
            addToListIfNodeExists(chessBoard, placesKnightCanJumpTo, x + 2, y + 1);

            addToListIfNodeExists(chessBoard, placesKnightCanJumpTo, x - 2, y - 1);
            addToListIfNodeExists(chessBoard, placesKnightCanJumpTo, x - 1, y - 2);
            addToListIfNodeExists(chessBoard, placesKnightCanJumpTo, x + 1, y - 2);
            addToListIfNodeExists(chessBoard, placesKnightCanJumpTo, x + 2, y - 1);

            return placesKnightCanJumpTo;
        }

        /**
         * Checks if the node at these positions exists, and adds it to the #placesKnightCanJumpTo if it does
         */
        private void addToListIfNodeExists(Node[][] chessBoard, List<Node> placesKnightCanJumpTo, int newX, int newY) {
            if (isInsideBoard(newX, newY)) {
                placesKnightCanJumpTo.add(chessBoard[newX][newY]);
            }
        }

        private boolean isInsideBoard(int newX, int newY) {
            return isInside(newX) && isInside(newY);
        }

        private boolean isInside(int pos) {
            return pos >= 0 && pos < BOARD_LENGTH;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "x=" + x +
                    ", y=" + y +
                    ", foobarPos=" + foobarPos +
                    '}';
        }
    }

    /**
     * Models a graph which has nodes (vertices) and lines (edges)
     */
    static class Graph {

        private final List<Node> nodes;
        private final List<Lines> lines;

        public Graph(List<Node> nodes, List<Lines> lines) {
            this.nodes = nodes;
            this.lines = lines;
        }

        public List<Node> getNodes() {
            return nodes;
        }

        public List<Lines> getLines() {
            return lines;
        }


    }

    /**
     * Models an implementations of the DijkstraAlgorithm, for calculating the shortest path between two nodes.
     */
    static class DijkstraAlgorithm {

        private final List<Node> nodes;
        private final List<Lines> lines;
        private Set<Node> settledNodes;
        private Set<Node> unSettledNodes;
        private Map<Node, Node> predecessors;
        private Map<Node, Integer> distance;

        public DijkstraAlgorithm(Graph graph) {
            // create a copy of the array so that we can operate on this array
            this.nodes = new ArrayList<>(graph.getNodes());
            this.lines = new ArrayList<>(graph.getLines());
        }

        public void execute(Node source) {
            settledNodes = new HashSet<>();
            unSettledNodes = new HashSet<>();
            distance = new HashMap<>();
            predecessors = new HashMap<>();
            distance.put(source, 0);
            unSettledNodes.add(source);
            while (unSettledNodes.size() > 0) {
                Node node = getMinimum(unSettledNodes);
                settledNodes.add(node);
                unSettledNodes.remove(node);
                findMinimalDistances(node);
            }
        }

        private void findMinimalDistances(Node node) {
            List<Node> adjacentNodes = getNeighbors(node);
            for (Node target : adjacentNodes) {
                if (getShortestDistance(target) > getShortestDistance(node)
                        + getDistance(node, target)) {
                    distance.put(target, getShortestDistance(node)
                            + getDistance(node, target));
                    predecessors.put(target, node);
                    unSettledNodes.add(target);
                }
            }

        }

        private int getDistance(Node node, Node target) {
            for (Lines lines : this.lines) {
                if (lines.getSource().equals(node)
                        && lines.getDestination().equals(target)) {
                    return lines.getWeight();
                }
            }
            throw new RuntimeException("Should not happen");
        }

        private List<Node> getNeighbors(Node node) {
            List<Node> neighbors = new ArrayList<>();
            for (Lines lines : this.lines) {
                if (lines.getSource().equals(node)
                        && !isSettled(lines.getDestination())) {
                    neighbors.add(lines.getDestination());
                }
            }
            return neighbors;
        }

        private Node getMinimum(Set<Node> nodes) {
            Node minimum = null;
            for (Node node : nodes) {
                if (minimum == null) {
                    minimum = node;
                } else {
                    if (getShortestDistance(node) < getShortestDistance(minimum)) {
                        minimum = node;
                    }
                }
            }
            return minimum;
        }

        private boolean isSettled(Node node) {
            return settledNodes.contains(node);
        }

        private int getShortestDistance(Node destination) {
            Integer d = distance.get(destination);
            if (d == null) {
                return Integer.MAX_VALUE;
            } else {
                return d;
            }
        }

        /*
         * This method returns the path from the source to the selected target and
         * NULL if no path exists
         */
        public LinkedList<Node> getPath(Node target) {
            LinkedList<Node> path = new LinkedList<>();
            Node step = target;
            // check if a path exists
            if (predecessors.get(step) == null) {
                return null;
            }
            path.add(step);
            while (predecessors.get(step) != null) {
                step = predecessors.get(step);
                path.add(step);
            }
            // Put it into the correct order
            Collections.reverse(path);
            return path;
        }

    }

}
