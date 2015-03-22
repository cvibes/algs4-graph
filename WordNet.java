public class WordNet {
    public WordNet(String synsets, String hypernyms) {
    }

    public Iterable<String> nouns() {
    }

    public boolean isNoun(String word) {
        return false;
    }

    public int distance(String nounA, String nounB) {
        return 1;
    }

    public String sap(String nounA, String nounB) {
        return "hello";
    }

    public static void main(String[] args) {
        System.out.println("bye");
    }
}
