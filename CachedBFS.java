public class CachedBFS
{
    private final int V;
    private ST<Integer, Integer>[] st;
    private boolean[] marked;
    
    public CachedBFS(Digraph dg) {
        V      = dg.V();
        st     = (ST<Integer, Integer>[]) new ST[V];
        marked = new boolean[V];

        for (int i = 0; i < V; i++)
            st[i] = new ST<Integer, Integer>();

        for (int i = 0; i < V; i++)
            bfs(dg, i);
    }

    public void bfs(Digraph dg, int s) {
        Queue<Integer> q = new Queue<Integer>();
        marked[s] = true;
        q.enqueue(s);
        while (!q.isEmpty()) {
            int v = q.dequeue();
            st[v].put(v, 0);
            for (int w : dg.adj(v))
                if (!marked[w]) {
                    marked[w] = true;
                    st[w].put(v, 1);
                    q.enqueue(w);
                }
                else
                    for (int n : st[w].keys())
                        st[v].put(n, st[w].get(n) + 1);
        }
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < V; i++) {
            s = s + "Ancestors of " + i + "\n";
            for (int j : st[i].keys())
                s = s + j + " : " + st[i].get(j) + "\n";
            s = s + "\n";
        }
        return s;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph dg = new Digraph(in);
        CachedBFS bfs = new CachedBFS(dg);
        System.out.print(bfs.toString());
    }
}
