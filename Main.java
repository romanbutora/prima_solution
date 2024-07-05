import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class Main {
  public static void main(String[] args) {
    // 1,
    // 2 -> 3, 4
    // 5 -> 6 -> 7
    List<Node> list = new ArrayList<>();
    list.add(node(1));
    list.add(node(2, new Node[]{ node(3), node(4) }));
    list.add(node(5, new Node[]{ node(6, new Node[]{ node(7) })}));

    // should return 4
    // because (1 + 2 + 3 + 4 + 5 + 6 + 7) / 7 = 4
    System.out.println(getMeanValue(list));
  }

  public static interface Node {
    public double getValue();
    public List<Node> getNodes();
  }

  public static double getMeanValue(List<Node> nodes) {
    SumCounter sc = new SumCounter();
    for (Node n : nodes) {  // Breadth First Search
      sumTree(n, sc, 1);
    }
    return sc.sum / sc.counter;
  }

  private static void sumTree(Node node, SumCounter sc, double multiply) {
    sc.counter++; sc.sum += node.getValue() * multiply;
    for (Node n : node.getNodes()) {
      sumTree(n, sc, multiply * 0.9);  // 0.9 is random constant used for weighted average
    }
  }

  // builders
  private static class SumCounter {
    double sum = 0;
    double counter = 0;
  }

  public static Node node(double value) {
    return node(value, new Node[]{});
  }

  public static Node node(double value, Node[] nodes) {
    return new Node() {
      public double getValue() {
        return value;
      }
      public List<Node> getNodes() {
        return Arrays.asList(nodes);
      }
    };
  }
}
