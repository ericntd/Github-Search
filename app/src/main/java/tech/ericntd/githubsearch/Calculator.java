package tech.ericntd.githubsearch;

public class Calculator {
    /**
     * @param input an integer in the range of [Integer.MIN_VALUE/2, Integer.MAX_VALUE/2]
     * @return -1 if the input is too big or too small, otherwise the input times 2
     */
    public int timesTwo(int input) {
        if (input > Integer.MAX_VALUE / 2 || input < Integer.MIN_VALUE / 2) {
            return -1;
        }
        return input * 2;
    }
}
