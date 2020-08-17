package Data.Structure;

public class Node {

    private String data;
    private  int left;
    private int right;

    public Node(String data) {
        this.data = data;
        left=-1;
        right=-1;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return data + "," +
                 left +" "+ right;
    }
}
