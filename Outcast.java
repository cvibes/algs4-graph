public class Outcast {
    private WordNet wn;
    public Outcast(WordNet wordnet) {
        wn = wordnet;
    }

    public String outcast(String[] nouns) {
        int size = nouns.length;
        int[][] dist = new int[size][size];
        int[] sum    = new int[size];
        int max      = 0;
        for (int i = 0; i < size; i++) {
            sum[i] = 0;
            for (int j = 1; j < size; j++) {
                if (i == j) continue;
                int d;
                if (dist[i][j] != 0) {
                    d = dist[i][j];
                    // StdOut.printf("%s *> %s: %d\n", nouns[i], nouns[j], d);
                }
                else {
                    d = wn.distance(nouns[i], nouns[j]);
                    dist[i][j] = d;
                    dist[j][i] = d;
                    // StdOut.printf("%s -> %s: %d\n", nouns[i], nouns[j], d);
                }
                sum[i] += d;
                // StdOut.printf("sum is now %d\n", sum[i]);
            }
            // StdOut.printf("total distance for %d is %d\n", i, sum[i]);
            if (sum[i] > sum[max]) max = i;
        }
        return nouns[max];
    }

    public static void main(String[] args) {
        if (args.length < 3)
            throw new NullPointerException();
        WordNet wn = new WordNet(args[0], args[1]);
        Outcast oc = new Outcast(wn);
        for (int i = 2; i < args.length; i++) {
            In in = new In(args[i]);
            Queue<String> q = new Queue<String>();
            while (in.hasNextLine())
                q.enqueue(in.readLine());
            String[] words = new String[q.size()];
            int j = 0;
            while (!q.isEmpty())
                words[j++] = q.dequeue();
            String outcast = oc.outcast(words);
            StdOut.printf("%s: %s\n", args[i], outcast);
        }
    }
}
