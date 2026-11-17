import java.io.PrintWriter;
import java.util.*;

public class PharmacyInventory {
    private static volatile int sink;
    private static long lookupTime(Map<String,Integer> map, List<String> keys) {
        long start=System.nanoTime(); int total=0;
        for (int i=0;i<100000;i++) total += map.getOrDefault(keys.get(i % keys.size()),0);
        sink=total; return System.nanoTime()-start;
    }
    public static void main(String[] args) throws Exception {
        int[] sizes={1000,10000,50000};
        try(PrintWriter out=new PrintWriter("q4_benchmark.csv")) {
            out.println("medicine_count,map,trial,lookup_100000_ns,code_order_report_ns,insertion_order_report_ns,code_order_correct,insertion_order_correct");
            for(int n:sizes) for(int trial=1;trial<=5;trial++) {
                Map<String,Integer> hash=new HashMap<>(), tree=new TreeMap<>(), linked=new LinkedHashMap<>();
                List<String> keys=new ArrayList<>();
                for(int i=0;i<n;i++){ String k=String.format(Locale.ROOT,"MED%06d",(i*37)%n); keys.add(k); int stock=(i*13)%500; hash.put(k,stock); tree.put(k,stock); linked.put(k,stock); }
                long ht=lookupTime(hash,keys), tt=lookupTime(tree,keys), lt=lookupTime(linked,keys);
                long start=System.nanoTime(); List<String> hashCode=new ArrayList<>(hash.keySet()); Collections.sort(hashCode); long hashCodeNs=System.nanoTime()-start;
                start=System.nanoTime(); List<String> hashInsertion=new ArrayList<>(keys); for(String k:hashInsertion) hash.get(k); long hashInsertionNs=System.nanoTime()-start;
                start=System.nanoTime(); List<String> treeCode=new ArrayList<>(tree.keySet()); long treeCodeNs=System.nanoTime()-start;
                start=System.nanoTime(); List<String> treeInsertion=new ArrayList<>(keys); for(String k:treeInsertion) tree.get(k); long treeInsertionNs=System.nanoTime()-start;
                start=System.nanoTime(); List<String> linkedCode=new ArrayList<>(linked.keySet()); Collections.sort(linkedCode); long linkedCodeNs=System.nanoTime()-start;
                start=System.nanoTime(); List<String> insertion=new ArrayList<>(linked.keySet()); long linkedInsertionNs=System.nanoTime()-start;
                List<String> expected=new ArrayList<>(new TreeSet<>(keys));
                out.printf(Locale.ROOT,"%d,HashMap,%d,%d,%d,%d,%b,%b%n",n,trial,ht,hashCodeNs,hashInsertionNs,hashCode.equals(expected),false);
                out.printf(Locale.ROOT,"%d,TreeMap,%d,%d,%d,%d,%b,%b%n",n,trial,tt,treeCodeNs,treeInsertionNs,treeCode.equals(expected),false);
                out.printf(Locale.ROOT,"%d,LinkedHashMap,%d,%d,%d,%d,%b,%b%n",n,trial,lt,linkedCodeNs,linkedInsertionNs,linkedCode.equals(expected),insertion.equals(keys));
            }
        }
        Map<String,Integer> demo=new LinkedHashMap<>(); demo.put("MED003",20); demo.put("MED001",50); demo.put("MED002",30);
        System.out.println("Insertion order: "+demo.keySet());
        System.out.println("Code order: "+new TreeSet<>(demo.keySet()));
        System.out.println("CSV written: q4_benchmark.csv (5 trials, three sizes)");
    }
}