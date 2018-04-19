CyclicBarrier реализует шаблон синхронизации Барьер. 
Циклический барьер является точкой синхронизации, в которой указанное количество параллельных потоков встречается и блокируется.
Как только все потоки прибыли, выполняется опционное действие (или не выполняется, если барьер был инициализирован без него), 
и, после того, как оно выполнено, барьер ломается и ожидающие потоки «освобождаются». 
В конструктор барьера (CyclicBarrier(int parties) и CyclicBarrier(int parties, Runnable barrierAction)) обязательно передается 
количество сторон, которые должны «встретиться», и, опционально, действие, которое должно произойти, когда стороны встретились, 
но перед тем когда они будут «отпущены». 
![alt text](src/main/resources/cyclic-barrier.gif.gif)

Барьер похож на CountDownLatch, но главное различие между ними в том, что вы не можете заново использовать «замок» 
после того, как его счётчик достигнет нуля, а барьер вы можете использовать снова, даже после того, как он сломается. 
CyclicBarrier является альтернативой метода join(), который «собирает» потоки только после того, как они выполнились.

CyclicBarrier can be used to create a set of Children Threads if the size of the Threads created is known forehand. 
CyclicBarrier can be used to implement waiting amongst Children Threads until all of them finish. 
This is useful where parallel threads needs to perform a job which requires sequential execution. 
For example 10 Threads doing steps 1, 2, 3, but all 10 Threads should finish step one before any can do step 2. 
Cyclic barrier can be reset after all Threads are finished execution. 
This is a distinguishing feature from a CountDownLatch. A CountDownLatch can only be used for a single count down. Additionally a CyclicBarrier can be assigned an Additional Thread which executes each time all the Children Threads finish their respective tasks. 

Practical Example : 
Processing of a Image Pixels Matrix row by row in the first step 
and in the second step saving the Pixel values to file row by row. 
In this scenario if there are 10 Threads running simultaneously to process the matrix row by row 
then all 10 should wait until all are finished before they move on to the next step which is saving those rows to file. 