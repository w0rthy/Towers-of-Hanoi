package towers.of.hanoi;

import java.awt.Point;
import java.util.Stack;
import javax.swing.JOptionPane;

public class TowersOfHanoi {

    //NUMBER OF PIECES
    static int PIECES = 0;
    static Stack<Integer>[] towers = new Stack[3];
    
    static boolean SLEEP = true;
    static int SLEEPAMT = Integer.MIN_VALUE;
    
    public static void main(String[] args) {
        while(PIECES == 0)
            try{PIECES = Integer.parseInt(JOptionPane.showInputDialog("Number of pieces"));}catch(Throwable t){}
        while(SLEEPAMT == Integer.MIN_VALUE)
            try{SLEEPAMT = Integer.parseInt(JOptionPane.showInputDialog(null, "ms of time between swaps", PIECES <= 6 ? 300 : PIECES <= 15 ? 50 : 0));}catch(Throwable t) {}
        if(SLEEPAMT <= 0)
            SLEEP = false;
        
        towers[0] = new Stack<Integer>();
        towers[1] = new Stack<Integer>();
        towers[2] = new Stack<Integer>();
        
        for(int i = PIECES; i>0; i--)
            towers[0].push(i);
        
        HanoiPane.open();
        
        solve();
    }
    
    public static void solve(){
        moveStack(0,2);
    }
    
    public static void moveStack(int from, int to){
        int size = stackSize(from);
        for(int i = 0; i < size; i++){
            int dest = hanoiSwap(from, to);
            int adj = getAdjacent(from, dest);
            if(!towers[adj].empty())
                if(towers[adj].peek()<towers[dest].peek()){
                    int amt = stackSize(adj);
                    moveStack(adj, dest);
                }
        }
        //HanoiPane.setLastSwap(new Point(getAdjacent(from, to), to));
    }
    
    public static void swap(int from, int to){
        towers[to].add(towers[from].pop());
        HanoiPane.setLastSwap(new Point(from, to));
    }
    
    public static int hanoiSwap(int from, int dest){
        if(towers[from].empty())
            return -1;
        
        int adj = getAdjacent(from, dest);
        
        if(SLEEP)
            try{Thread.sleep(SLEEPAMT);}catch(Throwable t){}
        
        if(stackSize(from)%2==0){
            swap(from, adj);
            return adj;
        }
        else
            swap(from, dest);

        return dest;
    }

    static int getAdjacent(int from, int dest) {
        int adj = 0;
        if(!(from==0||dest==0))
            adj = 0;
        else if(!(from==1||dest==1))
            adj = 1;
        else
            adj = 2;
        return adj;
    }

    private static int stackSize(int from) {
        for(int i = towers[from].size()-1; i > 0; i--){
            if(Math.abs(towers[from].get(i)-towers[from].get(i-1)) <= 1){
                continue;
            }
            return towers[from].size()-i;
        }
        return towers[from].size();
    }
}
