package util;

/* 
 used to count the messages exchanged by the agents 
 so that their order is easier to follow
 */
public class GlobalCounter {

    private static int val = 0;

    public static void Reset() {
        val = 0;
    }

    public static void Increment() {
        val++;
    }

    public static int Get() {
        return val;
    }
}
