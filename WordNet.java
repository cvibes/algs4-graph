public class WordNet {
    private Bag<String> nouns;
    private ST<String, Boolean> isNoun;
    private ST<String, Bag<Integer>> idOf;
    private ST<Integer, String> syns;
    private SAP sap;

    public WordNet(String synsets, String hypernyms) {
        In sin = new In(synsets);
        In hin = new In(hypernyms);
        isNoun = new ST<String, Boolean>();
        idOf   = new ST<String, Bag<Integer>>();
        syns   = new ST<Integer, String>();
        nouns  = new Bag<String>();
        readSynsets(sin);
        readHypernyms(hin);
    }

    public Iterable<String> nouns() {
        return nouns;
    }

    public boolean isNoun(String word) {
        return isNoun.contains(word);
    }

    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new NullPointerException();

        return sap.length(idOf.get(nounA), idOf.get(nounB));
    }

    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new NullPointerException();
        int ancestor = sap.ancestor(idOf.get(nounA), idOf.get(nounB));
        if (ancestor == -1) return "";
        return syns.get(ancestor);
    }

    private void readSynsets(In in) {
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] fields = line.split(",");
            int id = Integer.parseInt(fields[0]);
            String[] newNouns = fields[1].split(" ");
            syns.put(id, fields[1]);
            for (int i = 0; i < newNouns.length; i++) {
                String noun = newNouns[i];
                nouns.add(noun);
                isNoun.put(noun, true);
                if (idOf.get(noun) == null)
                    idOf.put(noun, new Bag<Integer>());
                idOf.get(noun).add(id);
            }
        }
    }

    private void readHypernyms(In in) {
        Queue<Integer> q = new Queue<Integer>();
        int maxVertex = -1;
        while (in.hasNextLine()) {
            String[] hypers = in.readLine().split(",");
            int child = Integer.parseInt(hypers[0]);
            if (child > maxVertex) maxVertex = child;

            for (int i = 1; i < hypers.length; i++) {
                int ancestor = Integer.parseInt(hypers[i]);
                if (ancestor > maxVertex) maxVertex = ancestor;
                q.enqueue(child);
                q.enqueue(ancestor);
            }
        }

        Digraph dg = new Digraph(maxVertex + 1);
        while (!q.isEmpty())
            dg.addEdge(q.dequeue(), q.dequeue());
        sap = new SAP(dg);
    }

    public static void main(String[] args) {
        if (args.length < 2)
            throw new NullPointerException();
        if (args[0] == null || args[1] == null)
            throw new NullPointerException();
        WordNet wn = new WordNet(args[0], args[1]);

        while (!StdIn.isEmpty()) {
            String line = StdIn.readLine();
            String[] inputs = line.split(" ");
            int length = wn.distance(inputs[0], inputs[1]);
            String words = wn.sap(inputs[0], inputs[1]);
            StdOut.printf("distance is %d, ancestor is %s\n", length, words);
        }
    }
}
