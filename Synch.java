// This file defines class "Synch".  This class contains all the semaphores
// and variables needed to coordinate the instances of the Reader and Writer
// classes.  

import java.util.concurrent.*;

public class Synch {

  public static Semaphore mutex;
  public static Semaphore ReaderSem;
  public static Semaphore WriterSem;
  
  public static int wantToRead = 0;
  public static int wantToWrite = 0;
  public static int readingNow = 0;
  public static int writingNow = 0;
  
  
}  // end of class "Synch"

