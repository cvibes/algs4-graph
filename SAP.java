public class SAP {
    private final int V;
    private ST<Integer, Integer>[] st;
    private boolean[]  marked;

    public SAP(Digraph G) {
        V      = G.V();
        st     = ((ST<Integer, Integer>[]) new ST[V]);
        marked = new boolean[V];

        for (int i = 0; i < V; i++) {
            st[i] = new ST<Integer, Integer>();
            for (int a : G.adj(i)) {
                st[i].put(a, 1);
            }
        }
    }

    private void bfs(Digraph dg, int s) {
        
    }

    public int length(int v, int w) {
        return 0;
    }

    public int ancestor(int v, int w) {
        return 0;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return 0;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return 0;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph g = new Digraph(in);
        SAP s = new SAP(g);
        return;
    }

}
