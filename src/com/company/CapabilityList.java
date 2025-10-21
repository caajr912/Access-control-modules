package com.company;

import java.util.*;

public class CapabilityList {
    private List<Capability>[] capabilityLists;  // One list per domain
    private int N;  // domains
    private int M;  // files
    private Random rand;
    
    // Inner class to represent a capability (permission to access something)
    class Capability {
        int objectId;      // Which object (0 to M-1 for files, M to M+N-1 for domains)
        boolean isFile;    // true if file, false if domain
        String permission; // "R", "W", "R/W" for files, "allow" for domains
        
        Capability(int objectId, boolean isFile, String permission) {
            this.objectId = objectId;
            this.isFile = isFile;
            this.permission = permission;
        }
        
        @Override
        public String toString() {
            if (isFile) {
                return "F" + objectId + ":" + permission;
            } else {
                return "D" + objectId + ":" + permission;
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public CapabilityList(int N, int M) {
        this.N = N;
        this.M = M;
        this.rand = new Random();
        this.capabilityLists = new ArrayList[N];
        
        for (int i = 0; i < N; i++) {
            capabilityLists[i] = new ArrayList<>();
        }
        
        initializeLists();
        printLists();
    }
    
    private void initializeLists() {
        // For each domain, randomly give it capabilities to access files and other domains
        for (int domain = 0; domain < N; domain++) {
            // Add file capabilities
            for (int file = 0; file < M; file++) {
                String perm = generateFilePermission();
                if (!perm.isEmpty()) {
                    capabilityLists[domain].add(new Capability(file, true, perm));
                }
            }
            
            // Add domain switch capabilities
            for (int targetDomain = 0; targetDomain < N; targetDomain++) {
                if (targetDomain != domain) {  // Can't switch to self
                    if (rand.nextBoolean()) {  // 50% chance
                        capabilityLists[domain].add(
                            new Capability(targetDomain, false, "allow")
                        );
                    }
                }
            }
        }
    }
    
    private String generateFilePermission() {
        // Randomly generate R, W, R/W, or empty (no permission)
        int choice = rand.nextInt(4);
        switch (choice) {
            case 0: return "R";
            case 1: return "W";
            case 2: return "R/W";
            default: return "";  // no permission
        }
    }
    
    // Check if domain has permission to access a file
    public boolean hasFilePermission(int domain, int fileId, boolean isWrite) {
        for (Capability cap : capabilityLists[domain]) {
            if (cap.isFile && cap.objectId == fileId) {
                if (isWrite) {
                    return cap.permission.equals("W") || cap.permission.equals("R/W");
                } else {
                    return cap.permission.equals("R") || cap.permission.equals("R/W");
                }
            }
        }
        return false;  // No capability found
    }
    
    // Check if domain can switch to another domain
    public boolean canSwitchDomain(int fromDomain, int toDomain) {
        for (Capability cap : capabilityLists[fromDomain]) {
            if (!cap.isFile && cap.objectId == toDomain && cap.permission.equals("allow")) {
                return true;
            }
        }
        return false;
    }
    
    public void printLists() {
        System.out.println("\n========== CAPABILITY LISTS ==========");
        for (int domain = 0; domain < N; domain++) {
            System.out.print("Domain " + domain + " capabilities: ");
            if (capabilityLists[domain].isEmpty()) {
                System.out.print("(none)");
            } else {
                for (Capability cap : capabilityLists[domain]) {
                    System.out.print(cap + " ");
                }
            }
            System.out.println();
        }
        System.out.println("======================================\n");
    }
}
