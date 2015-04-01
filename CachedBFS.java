public class CachedBFS
{
    private final int V;
    private ST<Integer, Integer>[] st;
    private boolean[] marked;
    private Digraph dg;
    
    public CachedBFS(Digraph dg) {
        V      = dg.V();
        this.dg = dg;
        marked = new boolean[V];
        st     = (ST<Integer, Integer>[]) new ST[V];
        for (int i = 0; i < V; i++)
            st[i] = new ST<Integer, Integer>();
        // for (int i = 0; i < V; i++)
        //     bfs(i);
    }

    public void bfs(int s) {
        // StdOut.printf("bfs on %d\n", s);
        boolean[] visited = new boolean[V];
        int dist          = 0;
        Queue<Integer> q  = new Queue<Integer>();
        visited[s] = true;
        q.enqueue(s);
        q.enqueue(dist);

        while (!q.isEmpty()) {
            int v = q.dequeue();
            dist  = q.dequeue();
            // StdOut.printf("%d with dist %d\n", v, dist);
            if (st[s].get(v) == null || dist < st[s].get(v))
                st[s].put(v, dist);

            PATH:
            for (int w : dg.adj(v)) {
                // StdOut.printf("  checking adjacent %d... ", w);
                if (!visited[w]) {
                    // StdOut.print("new vertex...");
                    if (marked[w]) {
                        // StdOut.printf(" but marked before\n");
                        for (int x : st[w].keys())
                            if (st[s].get(x) == null
                                || (dist + 1 + st[w].get(x)) < st[s].get(x)) {
                                // StdOut.printf("add ancestor %d with dist %d\n",
                                //               x, dist + 1 + st[w].get(x));
                                st[s].put(x, dist + 1 + st[w].get(x));
                            }
                        continue PATH;
                    }
                    // StdOut.printf("not marked, add to queue\n");
                    visited[w] = true;
                    q.enqueue(w);
                    q.enqueue(dist + 1);
                }
            }
        }
        marked[s] = true;
    }

    public ST<Integer, Integer> ancestors(int v) {
        if (!marked[v])
            bfs(v);
        return st[v];
    }
    public String toString() {
        String s = "";
        for (int i = 0; i < V; i++) {
            s = s + "Ancestors of " + i + "\n";
            for (int j : ancestors(i).keys())
                s = s + j + " : " + ancestors(i).get(j) + "\n";
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
