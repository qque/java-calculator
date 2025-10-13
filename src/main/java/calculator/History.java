package calculator;

import java.util.ArrayList;

public class History {
    
    private ArrayList<String> history;

    public History() {
        history = new ArrayList<>();
    }

    public void add(String entry) {
        history.add(entry);
    }

    public void get(int index) {
        history.get(index);
    }

    public void clear() {
        history.clear();
    }

}
