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
    // ... etc
}
