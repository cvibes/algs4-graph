public class SAP {
    private CachedBFS cbfs;

    public SAP(Digraph G) {
        cbfs = new CachedBFS(G);
    }

    public int length(int v, int w) {
        ST<Integer, Integer> aofv = cbfs.ancestors(v);
        ST<Integer, Integer> aofw = cbfs.ancestors(w);
        int minDist = -1;
        for (int x : aofv.keys())
            if (aofw.get(x) != null
                && (minDist == -1 || (aofv.get(x) + aofw.get(x)) < minDist))
                    minDist = aofv.get(x) + aofw.get(x);
        
        return minDist;
    }

    public int ancestor(int v, int w) {
        ST<Integer, Integer> aofv = cbfs.ancestors(v);
        ST<Integer, Integer> aofw = cbfs.ancestors(w);
        int minDist = -1;
        int ancestor = -1;
        for (int x : aofv.keys()) {
            if (aofw.get(x) != null
                && (minDist == -1 || (aofv.get(x) + aofw.get(x)) < minDist)) {
                    minDist = aofv.get(x) + aofw.get(x);
                    ancestor = x;
                }
        }
        return ancestor;
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
        SAP sap = new SAP(g);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

}
