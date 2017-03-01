import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.Comparator;

// A data type that provides autocomplete functionality for a given set of 
// string and weights, using Term and BinarySearchDeluxe. 
public class Autocomplete {
    private Term[] terms;

    // Initialize the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) {
            throw new java.lang.NullPointerException();
        }
        this.terms = terms;
        Arrays.sort(terms);
    }

    // All terms that start with the given prefix, in descending order of
    // weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) {
            throw new java.lang.NullPointerException();
        }
        Term list = new Term(prefix, 0);

        int i = BinarySearchDeluxe.firstIndexOf(terms, list, Term.byPrefixOrder(prefix.length()));
        int j = BinarySearchDeluxe.lastIndexOf(terms, list, Term.byPrefixOrder(prefix.length()));

        if (i == -1 || j == -1) {
            throw new java.lang.NullPointerException();
        }
        
        Term[] discovery = new Term[j - i + 1];
        discovery = Arrays.copyOfRange(terms, i, j);
        Arrays.sort(discovery, Term.byReverseWeightOrder());
        return discovery;
    }

    // The number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix.isEmpty()) {
            throw new java.lang.NullPointerException();
        }
        
        Term list = new Term(prefix, 0);
        int i = BinarySearchDeluxe.firstIndexOf(terms, list, Term.byPrefixOrder(prefix.length()));
        int j = BinarySearchDeluxe.lastIndexOf(terms, list, Term.byPrefixOrder(prefix.length()));
        return j - i + 1;
    }

    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong(); 
            in.readChar(); 
            String query = in.readLine(); 
            terms[i] = new Term(query, weight); 
        }
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println(results[i]);
            }
        }
    }
}
