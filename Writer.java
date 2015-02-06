// This file defines class "writer".

// This code uses
//      class Semaphore, from the java.util.concurrent package in Java 5.0 which defines the behaviour of a 
//                           semaphore, including acquire and release operations.
//      class Synch, which defines the semaphores and variables
//                   needed for synchronizing the readers and writers.
//      class RandomSleep, which defines the doSleep method.


public class Writer extends Thread {
  int myName;  // The variable myName stores the name of this thread.
               // It is initialized in the constructor for class Reader.

  RandomSleep rSleep;  // rSleep can hold an instance of class RandomSleep.



  // This is the constructor for class Writer.  It has an integer parameter,
  // which is the name that is given to this thread.
  public Writer(int name) {
    myName = name;  // copy the parameter value to local variable "MyName"
    rSleep = new RandomSleep();   // Create and instance of RandomSleep.
  }  // end of the constructor for class "Writer"



  public void run () {
    for (int I = 0;  I < 5; I++) {

      try{
      	Synch.mutex.acquire();
      }
      catch(Exception e){}
      
      if(Synch.writingNow > 0 || Synch.wantToWrite > 0 || Synch.readingNow > 0){
    	  Synch.wantToWrite++;
    	  System.out.println("Writer " + myName + " is wanting to write.");
    	  System.out.println("Writers Writing: " + Synch.writingNow + " Writers Waiting: " + Synch.wantToWrite + " Readers Reading: " + Synch.readingNow + " Readers Waiting: " + Synch.wantToRead);
    	  Synch.mutex.release();
    	  try{
    		  Synch.WriterSem.acquire();
    	  }
    	  catch(Exception e){}
    	  try{
    		  Synch.mutex.acquire();
    	  }
    	  catch(Exception e){}
    	  Synch.wantToWrite--;
      }
      Synch.writingNow++;
      System.out.println("Writer " + myName + " is writing");
      System.out.println("Writers Writing: " + Synch.writingNow + " Writers Waiting: " + Synch.wantToWrite + " Readers Reading: " + Synch.readingNow + " Readers Waiting: " + Synch.wantToRead);
      Synch.mutex.release();
      
      
      // Simulate the time taken by writing.
      rSleep.doSleep(1, 200);
      
      try{
    	  Synch.mutex.acquire();
      }
      catch(Exception e){}
      Synch.writingNow--;
      System.out.println("Writer " + myName + " is done writing");
      System.out.println("Writers Writing: " + Synch.writingNow + " Writers Waiting: " + Synch.wantToWrite + " Readers Reading: " + Synch.readingNow + " Readers Waiting: " + Synch.wantToRead);
      if (Synch.wantToWrite > 0){
    	  Synch.WriterSem.release();
      } else if (Synch.wantToRead > 0){
    	  for(int i=0; i < Synch.wantToRead; i++){
    		  Synch.ReaderSem.release();
    	  }
      } else{}
      
      Synch.mutex.release();
      

      // Simulate "doing something else"
      rSleep.doSleep(1, 1000);
    } // end of "for" loop
  }  // end of "run" method
}  // end of class "Writer"

