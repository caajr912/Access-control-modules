import java.util.concurrent.Semaphore;
package com.company
public class FileObject {
    private String content;
    private Semaphore lock;
    
    public FileObject(String initialContent) {
        this.content = initialContent;
        this.lock = new Semaphore(1);
    }
    
    public void read(int domainId) throws InterruptedException {
        lock.acquire();
        System.out.println("Domain " + domainId + " reading: " + content);
        // Yield 3-7 times before releasing
        int yields = 3 + (int)(Math.random() * 5);
        for (int i = 0; i < yields; i++) {
            Thread.yield();
        }
        lock.release();
    }
    
    public void write(int domainId, String newContent) throws InterruptedException {
        lock.acquire();
        System.out.println("Domain " + domainId + " writing: " + newContent);
        this.content = newContent;
        // Yield 3-7 times before releasing
        int yields = 3 + (int)(Math.random() * 5);
        for (int i = 0; i < yields; i++) {
            Thread.yield();
        }
        lock.release();
    }
}
