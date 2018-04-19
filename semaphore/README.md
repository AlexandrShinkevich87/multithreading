Синхронизатор Semaphore реализует шаблон синхронизации Семафор. 
Чаще всего, семафоры необходимы, когда нужно ограничить доступ к некоторому общему ресурсу. 
В конструктор этого класса (Semaphore(int permits) или Semaphore(int permits, boolean fair)) обязательно передается 
количество потоков, которому семафор будет разрешать одновременно использовать заданный ресурс.
![alt text](./multithread/semaphore/src/main/resources/semaphore.gif)
Доступ управляется с помощью счётчика: изначально значение счётчика равно int permits, 
когда поток заходит в заданный блок кода, то значение счётчика уменьшается на единицу, 
когда поток его покидает, то увеличивается. 
Если значение счётчика равно нулю, то текущий поток блокируется, пока кто-нибудь не выйдет из блока 
(в качестве примера из жизни с permits = 1, можно привести очередь в кабинет в поликлинике: 
когда пациент покидает кабинет, мигает лампа, и заходит следующий пациент).

Semaphore can be used to create a set of Children Threads even when the size of the Threads to be created is not known fore hand. 
This is because a Semaphore can wait until a number of releases have been made but that number is not required to initialize the Semaphore. 
Semaphores can be used in other scenarios such as Synchronizing between different threads such as Publisher, Subscriber scenario. 

Practical Example : Traversing through a folder with sub folders within sub folders 
and if  JPEG files are found, move them to a destination directory and then zip them. 
In this scenario the folder traversing is done recursively until a JPEG file is found. 
And then a Thread is invoked to move it to destination directory. 
But zipping needs to wait until all JPEG files are moved to the destination directory. 
In this scenario no of JPEG files available in the folder structure is not known 
but the zipping needs to wait till all files are successfully moved. 
Ideal scenario for a Semaphore based waiting.