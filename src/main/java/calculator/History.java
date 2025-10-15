package calculator;

import java.util.ArrayList;

public class History {
    
    private ArrayList<String> history;

    private static History instance;

    public History() {
        history = new ArrayList<>();
    }

    public static History getInstance() {
        if (instance == null) instance = new History();
        return instance;
    }

    public void add(String entry) {
        history.add(entry);
    }

    public String get(int index) {
        return history.get(index);
    }

    public String getLatest() {
        return history.get(history.size() - 1);
    }

    public void clear() {
        history.clear();
    }

    public void clearLatest() {
        history.remove(history.size() - 1);
    }

}
