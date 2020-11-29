# Сравнение GC

#### -XX:+UseSerialGC

Время работы: 18 сек.

Количество сборок: 22

Время на сборки:\
    Name:Copy - 89-104мс\
    Name:MarkSweepCompact - 315-441мс

#### -XX:+UseParallelGC

Время работы: 17 сек.

Количество сборок: 14

Время на сборки:\
    Name:PS Scavenge - 48-53мс\
    Name:PS MarkSweep - 335-382мс

#### -XX:+UseConcMarkSweepGC

error: 
Unrecognized VM option 'UseConcMarkSweepGC'\
Error: Could not create the Java Virtual Machine.\
Error: A fatal exception has occurred. Program will exit.\

Could not create the Java Virtual Machine.

#### -XX:+UseG1GC 

Время работы: 9 сек.

Количество сборок: 40

Время на сборки:\
    Name:G1 Young Generation - 8-38мс\
    Name:G1 Old Generation - 270-299мс

#### -XX:+UnlockExperimentalVMOptions -XX:+UseZGC

error: Failed to lookup symbol: VirtualAlloc2\
       Error occurred during initialization of VM\
       ZGC requires Windows version 1803 or later

### Итог:

Из протестированных вариантов дольше всего проработал SerialGC. При ParallelGC наблюдается самое минимальное  вмешательство в процесс выполнения приложения.
 Однако, если необходимо чтобы сборки происходили незаметно для пользователя, то лучше выбрать G1GC, так как он имеет минимальное вермя вмешательства в работу приложения.