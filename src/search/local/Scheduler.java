package search.local;

public class Scheduler {
    private final int k, limit;
    private final double lambda;

    public Scheduler(int k, double lambda, int limit) {
        this.k = k;
        this.lambda = lambda;
        this.limit = limit;
    }

    public Scheduler() {
        this(20, 0.045, 100);
    }

    public double getTemperature(int t) {
        if (t < limit) {
            return k * Math.exp(-1.0 * lambda * t);
        } else {
            return 0.0;
        }
    }
}
