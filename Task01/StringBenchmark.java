import java.io.PrintWriter;
import java.util.Locale;

public class StringBenchmark {
    private static volatile int sink;
    private static String[] fields(int count) {
        String[] values = new String[count];
        for (int i = 0; i < count; i++) values[i] = "FIELD" + i + "=Chennai-Train-" + (1000 + i);
        return values;
    }
    private static String plus(String[] fields) {
        String result = "";
        for (String field : fields) result += field + "\n";
        return result;
    }
    private static String builder(String[] fields) {
        StringBuilder result = new StringBuilder();
        for (String field : fields) result.append(field).append('\n');
        return result.toString();
    }
    private static String buffer(String[] fields) {
        StringBuffer result = new StringBuffer();
        for (String field : fields) result.append(field).append('\n');
        return result.toString();
    }
    private static long measure(Runnable action) {
        long start = System.nanoTime(); action.run(); return System.nanoTime() - start;
    }
    public static void main(String[] args) throws Exception {
        int[][] workloads = {{1, 6}, {10000, 50}, {500, 200}};
        try (PrintWriter out = new PrintWriter("q1_benchmark.csv")) {
            out.println("workload,approach,tickets,fields_per_ticket,trial,time_ns");
            for (int w = 0; w < workloads.length; w++) {
                int tickets = workloads[w][0], fieldCount = workloads[w][1];
                String[] data = fields(fieldCount);
                for (int trial = 1; trial <= 5; trial++) {
                    for (String approach : new String[]{"StringPlus", "StringBuilder", "StringBuffer"}) {
                        long elapsed = measure(() -> {
                            int length = 0;
                            for (int i = 0; i < tickets; i++) {
                                String s = approach.equals("StringPlus") ? plus(data) :
                                    approach.equals("StringBuilder") ? builder(data) : buffer(data);
                                length += s.length();
                            }
                            sink = length;
                        });
                        out.printf(Locale.ROOT, "%d,%s,%d,%d,%d,%d%n", w + 1, approach, tickets, fieldCount, trial, elapsed);
                    }
                }
            }
        }
        String[] sample = fields(6);
        System.out.println("String + length: " + plus(sample).length());
        System.out.println("StringBuilder length: " + builder(sample).length());
        System.out.println("StringBuffer length: " + buffer(sample).length());
        System.out.println("CSV written: q1_benchmark.csv (5 trials per approach/workload)");
    }
}