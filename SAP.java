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
        ST<Integer, Integer> aofv = getAllAncestors(v);
        ST<Integer, Integer> aofw = getAllAncestors(w);
        int ans = ancestor(v, w);
        return aofv.get(ans) + aofw.get(ans);
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        ST<Integer, Integer> aofv = getAllAncestors(v);
        ST<Integer, Integer> aofw = getAllAncestors(w);
        int ans = -1;
        int len = 0;
        for (int a : aofw.keys()) {
            if (aofv.get(a) != null
                && (ans == -1 || (aofv.get(a) + aofw.get(a)) < len)) {
                len = aofv.get(a) + aofw.get(a);
                ans = a;
            }
        }
        
        return ans;
    }

    private ST<Integer, Integer> getAllAncestors(Iterable<Integer> v) {
        ST<Integer, Integer> aofv = new ST<Integer, Integer>();
        for (int x : v) {
            ST<Integer, Integer> aofx = cbfs.ancestors(x);
            for (int a : aofx.keys())
                if (aofv.get(a) == null || aofx.get(a) < aofv.get(a))
                    aofv.put(a, aofx.get(a));
        }
        return aofv;
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
