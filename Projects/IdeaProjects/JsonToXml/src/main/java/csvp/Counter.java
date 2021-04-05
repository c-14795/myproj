package csvp;

import java.io.Serializable;

public class Counter implements Serializable {
    private int count;


    private Counter() {
    }

    Counter(int a) {
        this.count = a;
    }

    @Override
    public String toString() {
        return "current counter is : "+count;
    }

    public int getCount() {
        return this.count;
    }
}
