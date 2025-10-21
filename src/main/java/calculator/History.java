/*
 *  Manages history of computations, stored as a 2-d ArrayList
 *  history = {{"computation", "result"}, {"7!","5040"}, etc.}
 */

package calculator;

import java.util.ArrayList;
import java.util.List;

public class History {
    
    private ArrayList<ArrayList<String>> history;

    private static History instance;

    public History() {
        history = new ArrayList<>();
    }

    public static History getInstance() {
        if (instance == null) instance = new History();
        return instance;
    }

    public void add(ArrayList<String> entry) {
        history.add(entry); 
    }

    public void add(String computation, String result) {
        history.add(new ArrayList<>(List.of(computation,result))); 
    }

    public ArrayList<String> get(int index) {
        return history.get(index); 
    }

    public ArrayList<String> getLatest() {
        return history.get(history.size() - 1); 
    }

    public String getLatestToString() {
        ArrayList<String> latest = getLatest();
        return latest.get(0) + " = " + latest.get(1);
    }

    public boolean isEmpty() {
        return history.size() == 0; 
    }

    public void clear() {
        history.clear(); 
    }

    public void clearLatest() {
        history.remove(history.size() - 1); 
    }

}
