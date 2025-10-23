package com.company;

public class Arbitrator {
    private AccessMatrix accessMatrix;      // For Task 1
    private AccessList accessList;          // For Task 2
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
        
        String permission = "";
        
        // Get permission based on which task
        if (taskNum == 1) {
            permission = accessMatrix.getFilePermission(domainId, fileId);
        } else if (taskNum == 2) {
            permission = accessList.getFilePermission(domainId, fileId);
        } else if (taskNum == 3) {
            permission = capabilityList.getFilePermission(domainId, fileId);
        }
        
        // Check if permission allows the operation
        boolean granted = false;
        if (isWrite) {
            granted = permission.equals("W") || permission.equals("R/W");
        } else {
            granted = permission.equals("R") || permission.equals("R/W");
        }

        System.out.println("[Arbitrator] " + "D" + domainId + " Access " + (granted ? "GRANTED" : "DENIED"));
        return granted;
        }
    
    public boolean checkDomainSwitch(int fromDomain, int toDomain) {
        System.out.println("[Arbitrator] Domain " + fromDomain + 
                          " requesting switch to Domain " + toDomain);
        
        boolean granted = false;
        
        if (taskNum == 1) {
            String perm = accessMatrix.getDomainPermission(fromDomain, toDomain);
            granted = perm.equals("allow");
        } else if (taskNum == 2) {
            granted = accessList.canSwitchDomain(fromDomain, toDomain);
        } else if (taskNum == 3) {
            granted = capabilityList.canSwitchDomain(fromDomain, toDomain);
        }
        
         System.out.println("[Arbitrator] Switch From D" + fromDomain + " to D" + toDomain + " " + (granted ? "GRANTED" : "DENIED"));
         return granted;
        }
}
