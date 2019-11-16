import java.util.ArrayList;
import java.util.Collections;

class Node {

    private Board board;
    private Node parent = null;
    private int path_cost;
    private int manhattanDistance;

    Node(Board board) {
        this.board = board;
    }

    Node(Board board, Node parent) {
        this.board = board;
        this.parent = parent;
        this.path_cost = parent.path_cost + 1;
    }

    ArrayList<Node> path(){
        ArrayList<Node> path = new ArrayList<Node>();
        Node node = this;

        while(node != null){
            path.add(node);
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }

    Board getBoard() {
        return board;
    }

    int getPath_cost(){
        return path_cost;
    }

    void setManhattanDistance(int manhattanDistance){
        this.manhattanDistance = manhattanDistance;
    }

    int getManhattanDistance(){
        return manhattanDistance;
    }
}
