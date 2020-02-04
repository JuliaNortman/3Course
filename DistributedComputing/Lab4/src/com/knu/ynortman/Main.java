package com.knu.ynortman;

import java.util.concurrent.*;

class PCSem {

    public static void main() throws InterruptedException {

        Q q = new Q();
        new Writer(q,1);
        new Writer(q,2);
        new Reader(q,1);
        new Reader(q,2);
        new Reader(q,3);
        new Reader(q,4);
        try {
            Thread.sleep(2000);
        }
        catch(InterruptedException {
            System.out.println("Прерывание главного потока");
        } ;
        q.work = false;}}

class Q {

    Semaphore sema = new Semaphore(4, true);
    String n;
    boolean work = true;
    String read() {
        int numerOfPerm = sema.availablePermits();

        if (numerOfPerm < 0){ sema.release(3); }

        try { sema.acquire(); } catch (InterruptedException e) {}

        Thread t = Thread.currentThread();

        System.out.println(t.getName()+" try...");

        System.out.println(t.getName()+" reading...");

        try { Thread.sleep(50); } catch (InterruptedException e) {}

        System.out.println(t.getName()+":"+Minimum(n));

        sema.release();

        return n;}

    void write(String str) {

        int numerOfPerm = sema.availablePermits();

        if (numerOfPerm >= 4) {

            try {sema.acquire();sema.acquire();sema.acquire();sema.acquire(); } catch (InterruptedException e) {}

            Thread t = Thread.currentThread();

            System.out.println(t.getName()+" try...");

            System.out.println(t.getName()+" writing...");

            try { Thread.sleep(10); } catch (InterruptedException e) {}

            n = str;

            System.out.println(t.getName()+":"+n);

            sema.release();sema.release();sema.release();sema.release();}}

    static int Minimum (String str) {

        int [] n=null;

        try{String []nn=str.split(" "); n=new int[nn.length];

            for(int i=0;i<nn.length;i++) n[i]=Integer.parseInt(nn[i]+"");} catch(Exception r){}

        if (n == null) {return 0;}

        int min=n[0];

        for (int i=1; i<n.length; i++) {

            if (min > n[i]) min = n[i]; }

        return min;}}

class Writer implements Runnable {

    Q q;

    Writer(Q q,int num) {

        this.q = q;

        new Thread(this, "Writer "+num).start();}

    public void run() {

        while (q.work) { q.write(NumberToString(GenerNatural(10))); }}

    int [] GenerNatural (int n) {

        int [] k = new int [n];

        for (int i=0; i<k.length; i++) {

            k[i] = (int) (Math.random()*100); }

        return k; }

    String NumberToString(int []n){ String str="";

        for (int i=0; i<n.length; i++) {

            str+=n[i]+" "; }

        return str;}}

class Reader implements Runnable {

this.q=q;

new Thread(this, "Reader "+num).start(); }

    public void run() {while (q.work) { q.read(); } }

/*public class Main {

    public static void main(String[] args) {
	// write your code here
    }
}*/
