CFLAGS = -Wall -fpic -coverage -lm -std=c99

rngs.o: rngs.h rngs.c
	gcc -c rngs.c -g  $(CFLAGS)

dominion.o: dominion.h dominion.c rngs.o
	gcc -c dominion.c -g  $(CFLAGS)

playdom: dominion.o playdom.c
	gcc -o playdom playdom.c -g dominion.o rngs.o $(CFLAGS)
#To run playdom you need to entere: ./playdom <any integer number> like ./playdom 10*/

testDrawCard: testDrawCard.c dominion.o rngs.o
	gcc  -o testDrawCard -g  testDrawCard.c dominion.o rngs.o $(CFLAGS)

badTestDrawCard: badTestDrawCard.c dominion.o rngs.o
	gcc -o badTestDrawCard -g  badTestDrawCard.c dominion.o rngs.o $(CFLAGS)

testBuyCard: testDrawCard.c dominion.o rngs.o
	gcc -o testDrawCard -g  testDrawCard.c dominion.o rngs.o $(CFLAGS)

testAll: dominion.o testSuite.c
	gcc -o testSuite testSuite.c -g  dominion.o rngs.o $(CFLAGS)

interface.o: interface.h interface.c
	gcc -c interface.c -g  $(CFLAGS)

unittest1: unittest1.c dominion.o rngs.o
	gcc $(CFLAGS) -g -o unittest1 unittest1.c dominion.o rngs.o

unittest2: unittest2.c dominion.o rngs.o
	gcc $(CFLAGS) -g -o unittest2 unittest2.c dominion.o rngs.o

unittest3: unittest3.c dominion.o rngs.o
	gcc $(CFLAGS) -o unittest3 unittest3.c dominion.o rngs.o

unittest4: unittest4.c dominion.o rngs.o
	gcc $(CFLAGS) -o unittest4 unittest4.c dominion.o rngs.o

cardtest1: cardtest1.c dominion.o rngs.o
	gcc $(CFLAGS) -o cardtest1 cardtest1.c dominion.o rngs.o

cardtest2: cardtest2.c dominion.o rngs.o
	gcc $(CFLAGS) -o cardtest2 cardtest2.c dominion.o rngs.o

cardtest3: cardtest3.c dominion.o rngs.o
	gcc $(CFLAGS) -o cardtest3 cardtest3.c dominion.o rngs.o

cardtest4: cardtest4.c dominion.o rngs.o
	gcc $(CFLAGS) -o cardtest4 cardtest4.c dominion.o rngs.o

randomtest: randomtestdriver.h randomtestdriver.c randomtestadventurer.h randomtestadventurer.c randomtestcard1.h randomtestcard1.c randomtestcard2.h randomtestcard2.c dominion.o rngs.o
	gcc $(CFLAGS) -o randomtest randomtestdriver.c randomtestadventurer.c randomtestcard1.c randomtestcard2.c dominion.o rngs.o $(CFLAGS)

#the following is to run all tests for assignment 5
runalltests: unittest1.o unittest2.o unittest3.o unittest4.o cardtest1.o cardtest2.o cardtest3.o cardtest4.o randomtest.o randomtest1.o randomtest2.o
	./unittest1 > unittest1.out
	./unittest2 > unittest2.out
	./unittest3 > unittest3.out
	./unittest4 > unittest4.out
	./cardtest1 > cardtest1.out
	./cardtest2 > cardtest2.out
	./cardtest3 > cardtest3.out
	./cardtest4 > cardtest4.out
	./randomtest > randomtest.out

runtests: testDrawCard 
	./testDrawCard &> unittestresult.out
	gcov dominion.c >> unittestresult.out
	cat dominion.c.gcov >> unittestresult.out

player: player.c interface.o
	gcc -o player player.c -g  dominion.o rngs.o interface.o $(CFLAGS)

all: playdom player testDrawCard testBuyCard badTestDrawCard

clean:
	rm -f *.o playdom.exe playdom player player.exe  *.gcov *.gcda *.gcno *.so *.out testDrawCard testDrawCard.exe
