
package com.company;

public class Arbitrator {
    private AccessMatrix accessMatrix;  // For Task 1
    // Later Diego's AccessList stuff for Task 2
    private CapabilityList capabilityList;  // For Task 3
    private int M;
    private int taskNum;
    
    // Constructor for Task 1
    public Arbitrator(AccessMatrix matrix, int M) {
        this.accessMatrix = matrix;
        this.M = M;
        this.taskNum = 1;
    }
    
    // Constructor for Task 2 
    public Arbitrator(AccessList list, int M) {
        this.accessList = list;
        this.M = M;
        this.taskNum = 2;
    }

    
    // Constructor for Task 3 
    public Arbitrator(CapabilityList capList, int M) {
        this.capabilityList = capList;
        this.M = M;
        this.taskNum = 3;
    }
    
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
        } else if (taskNum == 3) {
        // Your capability list logic
        granted = capabilityList.hasFilePermission(domainId, fileId, isWrite);
    }
        
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
        //diego will put task 2
        } else if (taskNum == 3) {
        granted = capabilityList.canSwitchDomain(fromDomain, toDomain);
    }
    
        
        System.out.println("[Arbitrator] Switch " + (granted ? "GRANTED" : "DENIED"));
        return granted;
    }
}
