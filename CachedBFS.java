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
    }

    public void bfs(int s) {
        boolean[] visited = new boolean[V];
        int dist          = 0;
        Queue<Integer> q  = new Queue<Integer>();

        visited[s] = true;
        q.enqueue(s);
        q.enqueue(dist);

        while (!q.isEmpty()) {
            int v = q.dequeue();
            dist  = q.dequeue();
            if (st[s].get(v) == null || dist < st[s].get(v))
                st[s].put(v, dist);
            PATH:
            for (int w : dg.adj(v))
                if (!visited[w]) {
                    if (marked[w]) {
                        for (int x : st[w].keys())
                            if (st[s].get(x) == null
                                || (dist + st[w].get(x)) < st[s].get(x))
                                st[s].put(x, dist + st[w].get(x));
                        break PATH;
                    }
                    visited[w] = true;
                    q.enqueue(w);
                    q.enqueue(dist + 1);
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
        for (int i = 0; i < dg.V(); i++) {
            bfs.bfs(i);
        }
        System.out.print(bfs.toString());
    }
}
