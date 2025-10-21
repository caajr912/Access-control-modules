where your arbitrator logic will be.  I

package com.company;

public class Arbitrator {
    private AccessMatrix accessMatrix;  // For Task 1
    // Later Diego's AccessList stuff for Task 2
    // Later Task 3's CapabilityList
    private int M;
    private int taskNum;
    
    // Constructor for Task 1
    public Arbitrator(AccessMatrix matrix, int M) {
        this.accessMatrix = matrix;
        this.M = M;
        this.taskNum = 1;
    }
    
    // Constructor for Task 2 (Diego will add this)
    // public Arbitrator(AccessList list, int M) { ... }
    
    // Constructor for Task 3 (Christopher will add this)
    // public Arbitrator(CapabilityList capList, int M) { ... }
    
    public boolean checkFileAccess(int domainId, int fileId, boolean isWrite) {
        System.out.println("[Arbitrator] Domain " + domainId + 
                          " requesting " + (isWrite ? "WRITE" : "READ") + 
                          " to File " + fileId);
        
        boolean granted = false;
        
        if (taskNum == 1) {
            // Use access matrix
            String permission = accessMatrix.getFilePermission(domainId, fileId);
            if (isWrite) {
                granted = permission.equals("W") || permission.equals("R/W");
            } else {
                granted = permission.equals("R") || permission.equals("R/W");
            }
        } 
        // else if (taskNum == 2) { ... Diego's logic ... }
        // else if (taskNum == 3) { ... Task 3 logic ... }
        
        System.out.println("[Arbitrator] Access " + (granted ? "GRANTED" : "DENIED"));
        return granted;
    }
    
    public boolean checkDomainSwitch(int fromDomain, int toDomain) {
        System.out.println("[Arbitrator] Domain " + fromDomain + 
                          " requesting switch to Domain " + toDomain);
        
        boolean granted = false;
        
        if (taskNum == 1) {
            String permission = accessMatrix.getDomainPermission(fromDomain, toDomain);
            granted = permission.equals("allow");
        }
        // Add other task logic as needed
        
        System.out.println("[Arbitrator] Switch " + (granted ? "GRANTED" : "DENIED"));
        return granted;
    }
}
