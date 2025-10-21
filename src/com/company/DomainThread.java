public class DomainThread extends Thread {
    private final int domainId;
    private int currentDomain;
    private final int N, M;
    private final Arbitrator arbitrator;
    private final FileObject[] files;

    public DomainThread(int id, int N, int M, Arbitrator arbitrator, FileObject[] files) {
        this.domainId = id;
        this.currentDomain = id;
        this.N = N;
        this.M = M;
        this.arbitrator = arbitrator;
        this.files = files;
    }

    @Override
    public void run() {
        System.out.println("D" + domainId + " thread started.");
        for (int i = 0; i < 5; i++) {
            int X = (int) (Math.random() * (M + N));
            if (X < M) {
                boolean isWrite = Math.random() < 0.5;
                boolean ok = arbitrator.checkFileAccess(currentDomain, X, isWrite);
                if (ok) {
                    try {
                        if (isWrite) {
                            String payload = "Data_from_D" + currentDomain + "_iter" + i;
                            files[X].write(currentDomain, payload);
                        } else {
                            files[X].read(currentDomain);
                        }
                    } catch (InterruptedException e) {
                        // keep going; spec says handle gracefully
                    }
                } else {
                    System.out.println("D" + domainId + " denied " + (isWrite ? "WRITE" : "READ") + " on F" + X);
                    yield3to7();
                }
            } else {
                int targetDomain = X - M;
                if (targetDomain == currentDomain) {
                    i--; // don't count self-switch attempts
                    continue;
                }
                boolean ok = arbitrator.checkDomainSwitch(currentDomain, targetDomain);
                if (ok) {
                    currentDomain = targetDomain;
                    System.out.println("D" + domainId + " switched to D" + targetDomain);
                }
                yield3to7();
            }
        }
        System.out.println("D" + domainId + " thread finished.");
    }

    private void yield3to7() {
        int yields = 3 + (int) (Math.random() * 5); // 3..7
        System.out.println("[Yield] D" + domainId + " yielding " + yields + " times");
        for (int j = 0; j < yields; j++) Thread.yield();
    }
}
