// This file defines class "Reader".

// This code uses
//      class Semaphore, from the java.util.concurrent package in Java 5.0 which defines the behaviour of a 
//                           semaphore, including acquire and release operations.
//      class Synch, which defines the semaphores and variables 
//                   needed for synchronizing the readers and writers.
//      class RandomSleep, which defines the doSleep method.


public class Reader extends Thread {
  int myName;  // The variable myName stores the name of this thread.
               // It is initialized in the constructor for class Reader.

  RandomSleep rSleep;  // rSleep can hold an instance of class RandomSleep.



  // This is the constructor for class Reader.  It has an integer parameter,
  // which is the name that is given to this thread.
  public Reader(int name) {
    myName = name;  // copy the parameter value to local variable "MyName"
    rSleep = new RandomSleep();  // Create an instance of RandomSleep.
  }  // end of the constructor for class "Reader"



  public void run () {
    for (int I = 0;  I < 5; I++) {
      // Do acquire on the "mutex" semaphore, to get exclusive access to the
      // variable "Synch.readcount". 
      try{
      	Synch.mutex.acquire();
      }
      catch(Exception e){}
      if (Synch.wantToWrite > 0 || Synch.writingNow > 0){
    	  Synch.wantToRead++;
    	  System.out.println("Reader " + myName + " is wanting to read");
    	  System.out.println("Writers Writing: " + Synch.writingNow + " Writers Waiting: " + Synch.wantToWrite + " Readers Reading: " + Synch.readingNow + " Readers Waiting: " + Synch.wantToRead);
    	  Synch.mutex.release();
    	  try{
        	  Synch.ReaderSem.acquire(); 
    	  }
    	  catch(Exception e){}
    	  try{
        	  Synch.mutex.acquire(); 
    	  }
    	  catch(Exception e){}
    	  Synch.wantToRead--;
      }
      Synch.readingNow++;
      System.out.println("Reader " + myName + " is reading");
      System.out.println("Writers Writing: " + Synch.writingNow + " Writers Waiting: " + Synch.wantToWrite + " Readers Reading: " + Synch.readingNow + " Readers Waiting: " + Synch.wantToRead);
	  Synch.mutex.release();

      // Simulate the time taken for reading
      rSleep.doSleep(1, 200);   

      // We're finished reading.  Decrement readcount.  If we are the last
      // reader, then signal "wrt".  The signal to "wrt" will either wake up
      // a waiting writer, or set the semaphore value to 1, so that a future
      // writer or reader can go without waiting.
      try{
      	Synch.mutex.acquire();
      }
      catch(Exception e){}
      Synch.readingNow--;
      System.out.println("Reader " + myName + " is done reading");
      System.out.println("Writers Writing: " + Synch.writingNow + " Writers Waiting: " + Synch.wantToWrite + " Readers Reading: " + Synch.readingNow + " Readers Waiting: " + Synch.wantToRead);
      if (Synch.readingNow==0 && Synch.wantToWrite>0){
    	  Synch.WriterSem.release();
      }
      Synch.mutex.release();


      // Simulate "doing something else".
      rSleep.doSleep(1, 1000);
    } // end of "for" loop
  }  // end of "run" method
}  // end of class "Reader"

