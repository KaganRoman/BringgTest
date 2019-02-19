# BringgTest

Calculate distance matrix

## Solution

The solution is straightforward, according to the test requirement: split points to groups and run thread for each group. 

There are other ways to implement the calculation. I personally prefer to create a queue of jobs, and thread pool which runs the tasks to consume jobs. It will ensure balanced load of work in case one of the threads will complete its jobs earlier.

One of the challenges is to split points to equal groups according to thread numbers, and I could not find elegant math formula to do it, so I calculated it in iterative manner.

## Tests

In order to test it, I created naive implementation of algorithm, and run multiple JUnit tests to compare. One of the tests creates multiple permutations of threads and points to cover almost all cases.
Tests do not check if algorithm runs really distributed, and in equal way. It can be tested with profilers, or AOP injections to collect metrics, for example.