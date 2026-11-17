import java.util.*;

class Team {
    final String name; final int points; final int goalDiff;
    Team(String name,int points,int goalDiff){this.name=name;this.points=points;this.goalDiff=goalDiff;}
    @Override public String toString(){return name+"("+points+","+goalDiff+")";}
}
class Leaderboard<T> {
    private final List<T> items=new ArrayList<>();
    private final Comparator<? super T> order;
    Leaderboard(Comparator<? super T> order){this.order=order;}
    void add(T item){items.add(item);}
    List<T> ranking(){List<T> copy=new ArrayList<>(items); copy.sort(order); return copy;}
}
public class LeaderboardDemo {
    public static void main(String[] args){
        Comparator<Team> byRank=Comparator.comparingInt((Team t)->t.points).reversed()
            .thenComparing(Comparator.comparingInt((Team t)->t.goalDiff).reversed())
            .thenComparing(t->t.name);
        Leaderboard<Team> board=new Leaderboard<>(byRank);
        board.add(new Team("Panthers",9,4)); board.add(new Team("Eagles",9,6));
        board.add(new Team("Cobras",6,1)); board.add(new Team("Bisons",9,4));
        System.out.println(board.ranking());
        System.out.println("Original insertion list remains unchanged; ranking returns a sorted copy.");
        List<Team> ties=Arrays.asList(new Team("Zebras",7,2),new Team("Aces",7,2),new Team("Bears",7,1));
        List<Team> sorted=new ArrayList<>(ties); sorted.sort(byRank); System.out.println("Tie-break test: "+sorted);
        Comparator<Team> faulty=(x,y)->Integer.compare(y.points,x.points);
        List<Team> faultyOrder=new ArrayList<>(ties); faultyOrder.sort(faulty);
        System.out.println("Faulty points-only comparator (ignores goal difference/name): "+faultyOrder);
    }
}