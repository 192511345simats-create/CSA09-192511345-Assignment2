import java.util.*;
import java.io.PrintWriter;

class Patient {
    final String name; final int severity; final long arrival;
    Patient(String name,int severity,long arrival){this.name=name;this.severity=severity;this.arrival=arrival;}
    @Override public String toString(){return name+"(S"+severity+")";}
}
public class Triage {
    private long counter=0;
    private final PriorityQueue<Patient> queue=new PriorityQueue<>(
        Comparator.comparingInt((Patient p)->p.severity).thenComparingLong(p->p.arrival));
    void admit(String name,int severity){if(severity<1||severity>5)throw new IllegalArgumentException("Severity must be 1 to 5"); queue.add(new Patient(name,severity,counter++));}
    Patient next(){return queue.poll();}
    public static void main(String[] args)throws Exception{
        Triage er=new Triage();
        er.admit("Ravi",3); er.admit("Meena",1); er.admit("John",3); er.admit("Asha",5); er.admit("Kumar",1); er.admit("Lata",2);
        StringBuilder order=new StringBuilder(); Patient p;
        while((p=er.next())!=null)order.append(p).append(' ');
        System.out.println(order.toString().trim());
        System.out.println("Tie rule: lower severity number first; arrival counter breaks equal-severity ties.");
        try(PrintWriter out=new PrintWriter("q6_benchmark.csv")) {
            out.println("queue_size,trial,add_all_ns,remove_all_ns");
            for(int size:new int[]{1000,10000,50000}) for(int trial=1;trial<=5;trial++) {
                PriorityQueue<Patient> test=new PriorityQueue<>(Comparator.comparingInt((Patient x)->x.severity).thenComparingLong(x->x.arrival));
                long start=System.nanoTime();
                for(int i=0;i<size;i++) test.add(new Patient("P"+i,1+(i%5),i));
                long addNs=System.nanoTime()-start; start=System.nanoTime();
                while(!test.isEmpty()) test.poll();
                long removeNs=System.nanoTime()-start;
                out.printf(Locale.ROOT,"%d,%d,%d,%d%n",size,trial,addNs,removeNs);
            }
        }
        System.out.println("CSV written: q6_benchmark.csv (5 trials, three queue sizes)");
    }
}