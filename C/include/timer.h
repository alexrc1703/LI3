#ifndef __TIMER_H
#define __TIMER_H
typedef struct _timer* Timer;
Timer initTimer(); // inicializa a contagem do tempo
void finishTimer(Timer timer); // para a contagem do tempo
double calculateTime(Timer timer); // calula o tempo ente o inicial e o final
#endif
