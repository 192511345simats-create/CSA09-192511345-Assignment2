import java.io.PrintWriter;
import java.util.*;

public class CollectionBenchmark {
    private static volatile long sink;
    private static ArrayList<Integer> array(int n) { ArrayList<Integer> a = new ArrayList<>(n); for (int i=0;i<n;i++) a.add(i); return a; }
    private static LinkedList<Integer> linked(int n) { LinkedList<Integer> a = new LinkedList<>(); for (int i=0;i<n;i++) a.add(i); return a; }
    public static void main(String[] args) throws Exception {
        int[] sizes = {1000, 10000, 50000};
        try (PrintWriter out = new PrintWriter("q2_benchmark.csv")) {
            out.println("size,structure,operation,trial,time_ns");
            for (int n : sizes) for (int trial=1; trial<=5; trial++) {
                ArrayList<Integer> a1 = array(n); long start=System.nanoTime();
                for(int i=0;i<1000;i++) a1.add(i); sink=a1.size(); long t=System.nanoTime()-start;
                out.printf("%d,ArrayList,add_end_1000,%d,%d%n",n,trial,t);
                LinkedList<Integer> l1 = linked(n); start=System.nanoTime();
                for(int i=0;i<1000;i++) l1.add(i); sink=l1.size(); t=System.nanoTime()-start;
                out.printf("%d,LinkedList,add_end_1000,%d,%d%n",n,trial,t);
                ArrayList<Integer> a2=array(n); start=System.nanoTime();
                for(int i=0;i<200;i++) a2.add(0,-i); sink=a2.size(); t=System.nanoTime()-start;
                out.printf("%d,ArrayList,insert_front_200,%d,%d%n",n,trial,t);
                LinkedList<Integer> l2=linked(n); start=System.nanoTime();
                for(int i=0;i<200;i++) l2.add(0,-i); sink=l2.size(); t=System.nanoTime()-start;
                out.printf("%d,LinkedList,insert_front_200,%d,%d%n",n,trial,t);
                ArrayList<Integer> a3=array(n); int index=n/2; start=System.nanoTime();
                for(int i=0;i<200;i++) a3.add(index,i); sink=a3.size(); t=System.nanoTime()-start;
                out.printf("%d,ArrayList,insert_middle_200,%d,%d%n",n,trial,t);
                LinkedList<Integer> l3=linked(n); start=System.nanoTime();
                for(int i=0;i<200;i++) l3.add(index,i); sink=l3.size(); t=System.nanoTime()-start;
                out.printf("%d,LinkedList,insert_middle_200,%d,%d%n",n,trial,t);
                ArrayList<Integer> aRemoveFront=array(n); start=System.nanoTime();
                for(int i=0;i<200;i++) aRemoveFront.remove(0); sink=aRemoveFront.size(); t=System.nanoTime()-start;
                out.printf("%d,ArrayList,remove_front_200,%d,%d%n",n,trial,t);
                LinkedList<Integer> lRemoveFront=linked(n); start=System.nanoTime();
                for(int i=0;i<200;i++) lRemoveFront.remove(0); sink=lRemoveFront.size(); t=System.nanoTime()-start;
                out.printf("%d,LinkedList,remove_front_200,%d,%d%n",n,trial,t);
                ArrayList<Integer> aRemoveMiddle=array(n); start=System.nanoTime();
                for(int i=0;i<200;i++) aRemoveMiddle.remove(aRemoveMiddle.size()/2); sink=aRemoveMiddle.size(); t=System.nanoTime()-start;
                out.printf("%d,ArrayList,remove_middle_200,%d,%d%n",n,trial,t);
                LinkedList<Integer> lRemoveMiddle=linked(n); start=System.nanoTime();
                for(int i=0;i<200;i++) lRemoveMiddle.remove(lRemoveMiddle.size()/2); sink=lRemoveMiddle.size(); t=System.nanoTime()-start;
                out.printf("%d,LinkedList,remove_middle_200,%d,%d%n",n,trial,t);
                ArrayList<Integer> a4=array(n); start=System.nanoTime(); long sum=0;
                for(int i=0;i<1000;i++) sum+=a4.get(n/2); sink=sum; t=System.nanoTime()-start;
                out.printf("%d,ArrayList,get_middle_1000,%d,%d%n",n,trial,t);
                LinkedList<Integer> l4=linked(n); start=System.nanoTime(); sum=0;
                for(int i=0;i<1000;i++) sum+=l4.get(n/2); sink=sum; t=System.nanoTime()-start;
                out.printf("%d,LinkedList,get_middle_1000,%d,%d%n",n,trial,t);
                start=System.nanoTime(); sum=0; for(int x:a4) sum+=x; sink=sum; t=System.nanoTime()-start;
                out.printf("%d,ArrayList,iterate,%d,%d%n",n,trial,t);
                start=System.nanoTime(); sum=0; for(int x:l4) sum+=x; sink=sum; t=System.nanoTime()-start;
                out.printf("%d,LinkedList,iterate,%d,%d%n",n,trial,t);
            }
        }
        ArrayDeque<Integer> upNext = new ArrayDeque<>(); upNext.addFirst(2); upNext.addLast(3); upNext.addFirst(1);
        System.out.println("Up Next (ArrayDeque): " + upNext + "; next = " + upNext.removeFirst());
        System.out.println("CSV written: q2_benchmark.csv (5 trials, three sizes)");
    }
}