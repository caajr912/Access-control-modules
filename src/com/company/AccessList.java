import java.util.*;
package com.company;
public class AccessList {
    private final int N; // domains
    private final int M; // files
    private final Random rand = new Random();

    // File ACLs: for each fileId -> (domainId -> "R"/"W"/"R/W")
    private final Map<Integer, Map<Integer, String>> fileACLs = new HashMap<Integer, Map<Integer, String>>();
    // Domain switch ACLs (TARGET-based): for each targetDomain -> set of source domains allowed to switch INTO it
    private final Map<Integer, Set<Integer>> domainTargetACLs = new HashMap<Integer, Set<Integer>>();

    public AccessList(int N, int M) {
        this.N = N;
        this.M = M;
        init();
    }

    private void init() {
        // Files
        for (int f = 0; f < M; f++) {
            Map<Integer, String> perFile = new HashMap<Integer, String>();
            for (int d = 0; d < N; d++) {
                int r = rand.nextInt(4); // 0:none, 1:R, 2:W, 3:RW
                if (r == 1) perFile.put(d, "R");
                else if (r == 2) perFile.put(d, "W");
                else if (r == 3) perFile.put(d, "R/W");
            }
            fileACLs.put(f, perFile);
        }
        // Target-domain ACLs (who may switch INTO this domain)
        for (int target = 0; target < N; target++) {
            Set<Integer> allowedFrom = new HashSet<Integer>();
            for (int from = 0; from < N; from++) {
                if (from == target) continue;
                if (rand.nextBoolean()) allowedFrom.add(from);
            }
            domainTargetACLs.put(target, allowedFrom);
        }
    }

    // === Methods Arbitrator expects for Task 2 ===

    // Return "", "R", "W", or "R/W" for (domain, file)
    public String getFilePermission(int domainId, int fileId) {
        Map<Integer, String> perFile = fileACLs.get(fileId);
        if (perFile == null) return "";
        String p = perFile.get(domainId);
        return p == null ? "" : p;
    }

    // Return true iff 'fromDomain' may switch INTO 'toDomain' (target-based)
    public boolean canSwitchDomain(int fromDomain, int toDomain) {
        Set<Integer> allowedFrom = domainTargetACLs.get(toDomain);
        return allowedFrom != null && allowedFrom.contains(fromDomain);
    }

    // Pretty-prints for your logs
    public void printFileACLs() {
        System.out.println("==== File Access Lists (object-based) ====");
        for (int f = 0; f < M; f++) {
            Map<Integer, String> perFile = fileACLs.get(f);
            StringBuilder sb = new StringBuilder();
            sb.append("F").append(f).append(": ");
            if (perFile == null || perFile.isEmpty()) {
                sb.append("[no entries]");
            } else {
                ArrayList<Integer> keys = new ArrayList<Integer>(perFile.keySet());
                Collections.sort(keys);
                for (int d : keys) {
                    sb.append("D").append(d).append("(").append(perFile.get(d)).append(") ");
                }
            }
            System.out.println(sb.toString().trim());
        }
        System.out.println();
    }

    public void printDomainSwitchACLs() {
        System.out.println("==== Domain Switch Access Lists (target-based) ====");
        for (int target = 0; target < N; target++) {
            Set<Integer> s = domainTargetACLs.get(target);
            if (s == null || s.isEmpty()) {
                System.out.println("D" + target + " accepts from: [none]");
            } else {
                ArrayList<Integer> list = new ArrayList<Integer>(s);
                Collections.sort(list);
                StringBuilder sb = new StringBuilder();
                sb.append("D").append(target).append(" accepts from: ");
                for (int d : list) sb.append("D").append(d).append(" ");
                System.out.println(sb.toString().trim());
            }
        }
        System.out.println();
    }
}
