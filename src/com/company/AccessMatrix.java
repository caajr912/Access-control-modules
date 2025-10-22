package com.company;

import java.util.*;
public class AccessMatrix {
    private final int N; // domains
    private final int M; // files
    private final Random rand = new Random();

    // Access matrix: rows are domains, columns are files + domains
    // matrix[domain][object] where object < M is file, object >= M is domain
    private final String[][] matrix;

    public AccessMatrix(int N, int M) {
        this.N = N;
        this.M = M;
        this.matrix = new String[N][M + N];
        init();
    }

    private void init() {
        // Initialize all cells to empty string (no permission)
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M + N; j++) {
                matrix[i][j] = "";
            }
        }

        // Populate file permissions (columns 0 to M-1)
        for (int domain = 0; domain < N; domain++) {
            for (int file = 0; file < M; file++) {
                int r = rand.nextInt(4); // 0:none, 1:R, 2:W, 3:R/W
                if (r == 1) matrix[domain][file] = "R";
                else if (r == 2) matrix[domain][file] = "W";
                else if (r == 3) matrix[domain][file] = "R/W";
                // else leave as "" (no permission)
            }
        }

        // Populate domain switch permissions (columns M to M+N-1)
        for (int fromDomain = 0; fromDomain < N; fromDomain++) {
            for (int toDomain = 0; toDomain < N; toDomain++) {
                int col = M + toDomain;
                if (fromDomain == toDomain) {
                    // N/A for switching to self
                    matrix[fromDomain][col] = "N/A";
                } else {
                    // 50% chance to allow switch
                    if (rand.nextBoolean()) {
                        matrix[fromDomain][col] = "allow";
                    }
                    // else leave as "" (not allowed)
                }
            }
        }
    }

    // === Methods Arbitrator expects for Task 1 ===

    // Return "", "R", "W", or "R/W" for (domain, file)
    public String getFilePermission(int domainId, int fileId) {
        if (domainId < 0 || domainId >= N || fileId < 0 || fileId >= M) {
            return "";
        }
        return matrix[domainId][fileId];
    }

    // Return "allow" or "" for domain switch permission
    public String getDomainPermission(int fromDomain, int toDomain) {
        if (fromDomain < 0 || fromDomain >= N || toDomain < 0 || toDomain >= N) {
            return "";
        }
        int col = M + toDomain;
        return matrix[fromDomain][col];
    }

    // Pretty-print the access matrix
    public void printMatrix() {
        System.out.println("==== Access Matrix ====");

        // Print header row
        System.out.print("        ");
        for (int f = 0; f < M; f++) {
            System.out.printf("%-8s", "F" + f);
        }
        for (int d = 0; d < N; d++) {
            System.out.printf("%-8s", "D" + d);
        }
        System.out.println();

        // Print separator
        System.out.print("        ");
        for (int i = 0; i < M + N; i++) {
            System.out.print("--------");
        }
        System.out.println();

        // Print each domain row
        for (int domain = 0; domain < N; domain++) {
            System.out.printf("D%-6d", domain);

            // File permissions
            for (int file = 0; file < M; file++) {
                String perm = matrix[domain][file];
                if (perm.isEmpty()) {
                    System.out.printf("%-8s", "-");
                } else {
                    System.out.printf("%-8s", perm);
                }
            }

            // Domain switch permissions
            for (int d = 0; d < N; d++) {
                String perm = matrix[domain][M + d];
                if (perm.isEmpty()) {
                    System.out.printf("%-8s", "-");
                } else {
                    System.out.printf("%-8s", perm);
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}