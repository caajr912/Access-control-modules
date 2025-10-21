public class Domain extends Thread {
    private int domainId;
    private int currentDomain;
    private int N, M;
    // TODO: Add reference to arbitrator
    
    public Domain(int id, int N, int M) {
        this.domainId = id;
        this.currentDomain = id;
        this.N = N;
        this.M = M;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            int X = (int)(Math.random() * (M + N));
            
            if (X < M) {
                // File access request
                boolean isWrite = Math.random() < 0.5;
                // TODO: Call arbitrator
            } else {
                // Domain switch request
                int targetDomain = X - M;
                if (targetDomain == currentDomain) {
                    continue; // Skip switching to self
                }
                // TODO: Call arbitrator
            }
            
            // Yield 3-7 times
            int yields = 3 + (int)(Math.random() * 5);
            for (int j = 0; j < yields; j++) {
                Thread.yield();
            }
        }
    }
}
