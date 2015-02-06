# ReaderWriterSemaphores
A demo of how to use semaphores to allow Readers simultaneous access and Writers exclusive access.

This program allows several readers to access the shared data structure simultaneously, or one writer can have exclusive access.

Three type of solutions for the "Readers and Writers" programs exist; here is the 'starvation-free' solution where neither readers nor writers wait indefinitely.
