import java.io.PrintWriter;
import java.util.Locale;

public class RedesignBenchmark {
    private static volatile int sink;
    private static String original(int fields){String text="";for(int i=0;i<fields;i++) text+="FIELD"+i+"=VALUE"+i+"\n";return text;}
    private static String redesigned(int fields){StringBuilder text=new StringBuilder(fields*24);for(int i=0;i<fields;i++)text.append("FIELD").append(i).append("=VALUE").append(i).append('\n');return text.toString();}
    private static long run(boolean useBuilder,int fields,int repetitions){long start=System.nanoTime();int length=0;for(int r=0;r<repetitions;r++)length+=(useBuilder?redesigned(fields):original(fields)).length();sink=length;return System.nanoTime()-start;}
    public static void main(String[] args)throws Exception{
        int fields=Integer.getInteger("fields",300), repetitions=Integer.getInteger("repetitions",100);
        if(!original(fields).equals(redesigned(fields)))throw new IllegalStateException("Output mismatch");
        try(PrintWriter out=new PrintWriter("q7_redesign.csv")){
            out.println("solution,fields,repetitions,trial,time_ns");
            for(int trial=1;trial<=7;trial++){
                long a=run(false,fields,repetitions),b=run(true,fields,repetitions);
                out.printf(Locale.ROOT,"RepeatedStringPlus,%d,%d,%d,%d%n",fields,repetitions,trial,a);
                out.printf(Locale.ROOT,"StringBuilder,%d,%d,%d,%d%n",fields,repetitions,trial,b);
            }
        }
        System.out.println("Both approaches produce identical text.");
        System.out.println("Original: repeated String concatenation; redesigned: one StringBuilder per output.");
        System.out.println("CSV written: q7_redesign.csv (7 paired trials). Re-run on your computer for your evidence.");
    }
}