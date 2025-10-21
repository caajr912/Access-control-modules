public class Main {
    public static void main(String[] args) {
        if (args.length < 2 || !args[0].equals("-S")) {
            System.err.println("Usage: java Main -S <1|2|3>");
            return;
        }
        
        int taskNum = Integer.parseInt(args[1]);
        int N = 3 + (int)(Math.random() * 5); // [3,7]
        int M = 3 + (int)(Math.random() * 5); // [3,7]
        
        System.out.println("N=" + N + " domains, M=" + M + " files");
        
        switch(taskNum) {
            case 1: runTask1(N, M); break;
            case 2: runTask2(N, M); break;
            case 3: runTask3(N, M); break;
            default: System.err.println("Invalid task number");
        }
    }
    
    static void runTask1(int N, int M) {
        // TODO: Create AccessMatrix and start simulation
    }

    static void runTask3(int N, int M) {
    System.out.println("\n========== TASK 3: CAPABILITY LISTS ==========\n");
    
    // Create capability lists
    CapabilityList capList = new CapabilityList(N, M);
    
    // Create arbitrator
    Arbitrator arbitrator = new Arbitrator(capList, M);
    
    // Create file objects
    FileObject[] files = new FileObject[M];
    for (int i = 0; i < M; i++) {
        files[i] = new FileObject("Initial content of File " + i);
    }
    
    // Create and start domain threads
    DomainThread[] domains = new DomainThread[N];
    for (int i = 0; i < N; i++) {
        domains[i] = new DomainThread(i, N, M, arbitrator, files);
        domains[i].start();
    }
    
    // Wait for all threads to finish
    for (int i = 0; i < N; i++) {
        try {
            domains[i].join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    System.out.println("\n========== TASK 3 COMPLETE ==========\n");
}
    
}
