import java.util.*;

public class Tree {

    private ArrayList<Node> visited;

    public int MinimumSteps(int boardHeight, int boardWidth, int startX, int startY, int endX, int endY) {
        Board board = new Board(boardHeight, boardWidth, startX, startY, endX, endY);
        ArrayList<Node> path = search(board);
        if (path != null) {
            return path.get(path.size() - 1).getPath_cost();
        }
        return -1; //If no solution was found
    }

    private ArrayList<Node> search(Board board) {
        visited = new ArrayList<>();
        Node node = new Node(board);

        Comparator<Node> comparator = new NodeDistanceToGoalComparator();
        PriorityQueue<Node> fringe = new PriorityQueue<Node>(comparator);
        fringe.add(node);
        visited.add(node);

        while (!fringe.isEmpty()) {
            node = fringe.poll();
            if (goal_reached(node.getBoard())) {
                return node.path();
            }
            ArrayList<Node> children = expand(node);
            fringe.addAll(children);
        }

        return null; //No solution found
    }

    private ArrayList<Node> expand(Node parent) {
        ArrayList<Node> children = new ArrayList<>();

        for (Board move : allowed_moves(parent.getBoard())) {
            Node node = new Node(move, parent);
            node.setManhattanDistance(calculateManhattanDistance(node));
            if (!isVisited(node)) {
                children.add(node);
            }
        }
        return children;
    }

    private int calculateManhattanDistance(Node node) {
        return (Math.abs(node.getBoard().getKnightXPos() - node.getBoard().getKnightXGoalPos()) + Math.abs(node.getBoard().getKnightYPos() - node.getBoard().getKnightYGoalPos())) + node.getPath_cost();
    }

    private boolean isVisited(Node node) {
        for (Node visitedNode : visited) {
            if (visitedNode.getBoard().getKnightXPos() == node.getBoard().getKnightXPos() && visitedNode.getBoard().getKnightYPos() == node.getBoard().getKnightYPos()) {
                return true;
            }
        }
        visited.add(node);
        return false;
    }

    private boolean goal_reached(Board board) {
        return board.getKnightXPos() == board.getKnightXGoalPos() && board.getKnightYPos() == board.getKnightYGoalPos();
    }

    private ArrayList<Board> allowed_moves(Board board) {
        ArrayList<Board> moves = new ArrayList<>();
        if (board.getKnightXPos() - 2 >= 0 && board.getKnightYPos() - 1 >= 0) {
            moves.add(new Board(
                    board.getHeight(),
                    board.getWidth(),
                    board.getKnightXPos() - 2,
                    board.getKnightYPos() - 1,
                    board.getKnightXGoalPos(),
                    board.getKnightYGoalPos()));
        }
        if (board.getKnightXPos() - 1 >= 0 && board.getKnightYPos() - 2 >= 0) {
            moves.add(new Board(
                    board.getHeight(),
                    board.getWidth(),
                    board.getKnightXPos() - 1,
                    board.getKnightYPos() - 2,
                    board.getKnightXGoalPos(),
                    board.getKnightYGoalPos()));
        }
        if (board.getKnightXPos() + 1 < board.getWidth() && board.getKnightYPos() - 2 >= 0) {
            moves.add(new Board(
                    board.getHeight(),
                    board.getWidth(),
                    board.getKnightXPos() + 1,
                    board.getKnightYPos() - 2,
                    board.getKnightXGoalPos(),
                    board.getKnightYGoalPos()));
        }
        if (board.getKnightXPos() + 2 < board.getWidth() && board.getKnightYPos() - 1 >= 0) {
            moves.add(new Board(
                    board.getHeight(),
                    board.getWidth(),
                    board.getKnightXPos() + 2,
                    board.getKnightYPos() - 1,
                    board.getKnightXGoalPos(),
                    board.getKnightYGoalPos()));
        }
        if (board.getKnightXPos() + 2 < board.getWidth() && board.getKnightYPos() + 1 < board.getHeight()) {
            moves.add(new Board(
                    board.getHeight(),
                    board.getWidth(),
                    board.getKnightXPos() + 2,
                    board.getKnightYPos() + 1,
                    board.getKnightXGoalPos(),
                    board.getKnightYGoalPos()));
        }
        if (board.getKnightXPos() + 1 < board.getWidth() && board.getKnightYPos() + 2 < board.getHeight()) {
            moves.add(new Board(
                    board.getHeight(),
                    board.getWidth(),
                    board.getKnightXPos() + 1,
                    board.getKnightYPos() + 2,
                    board.getKnightXGoalPos(),
                    board.getKnightYGoalPos()));
        }
        if (board.getKnightXPos() - 1 >= 0 && board.getKnightYPos() + 2 < board.getHeight()) {
            moves.add(new Board(
                    board.getHeight(),
                    board.getWidth(),
                    board.getKnightXPos() - 1,
                    board.getKnightYPos() + 2,
                    board.getKnightXGoalPos(),
                    board.getKnightYGoalPos()));
        }
        if (board.getKnightXPos() - 2 >= 0 && board.getKnightYPos() + 1 < board.getHeight()) {
            moves.add(new Board(
                    board.getHeight(),
                    board.getWidth(),
                    board.getKnightXPos() - 2,
                    board.getKnightYPos() + 1,
                    board.getKnightXGoalPos(),
                    board.getKnightYGoalPos()));
        }

        return moves;
    }

}

class NodeDistanceToGoalComparator implements Comparator<Node>{
    @Override
    public int compare(Node o1, Node o2) {
        return o1.getManhattanDistance() - o2.getManhattanDistance();
    }
}

class Board {

    private int height;
    private int width;
    private int knightXPos;
    private int knightYPos;
    private int knightXGoalPos;
    private int knightYGoalPos;

    Board(int height, int width, int knightXPos, int knightYPos, int knightXGoalPos, int knightYGoalPos) {
        this.height = height;
        this.width = width;
        this.knightXPos = knightXPos;
        this.knightYPos = knightYPos;
        this.knightXGoalPos = knightXGoalPos;
        this.knightYGoalPos = knightYGoalPos;
    }

    int getHeight() {
        return height;
    }

    int getWidth() {
        return width;
    }

    int getKnightXPos() {
        return knightXPos;
    }

    int getKnightYPos() {
        return knightYPos;
    }

    int getKnightXGoalPos() {
        return knightXGoalPos;
    }

    int getKnightYGoalPos() {
        return knightYGoalPos;
    }
}
