package br.com.upf.pool01;

public class ThreadWorking implements Runnable {

    private String command;
    private static Integer conta = 100;

    public ThreadWorking(String s){
        this.command=s;
    }

    @Override
    public  void run() {
        System.out.println(Thread.currentThread().getName()+" Start. Command = "+command);
        processCommand();        
        System.out.println(Thread.currentThread().getName()+" End.\n");
        System.out.println("Valor conta: " + conta);
    }

    private void processCommand() {
        try {    
        	conta -= 10;
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return this.command;
    }


}