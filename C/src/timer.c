#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <timer.h>

struct _timer {
    clock_t start,end;
};

typedef struct _timer* Timer;

Timer initTimer() {
    Timer timer = (Timer) malloc(sizeof(struct _timer));
    timer->start = clock();
    return timer;
}

void resetTimer(Timer timer){
    timer->start = clock();
    timer->end = 0;
}

void finishTimer(Timer timer) {
    timer->end = clock();
}

double calculateTime(Timer timer) {
    return ((double) ((timer->end)-(timer->start)))/CLOCKS_PER_SEC;
}

