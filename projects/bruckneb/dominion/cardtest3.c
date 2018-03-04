/*******************************************************************************
* Student: Brian Bruckner
* Course:  CS_362_400_W2018
*
* File: cardtest3.c
* 
* This file tests the Gardens card
*
* basic requirements of gardens (not an action card):
* 1. No state change for current player.
* 2. No state change should occur for other players.
* 3. No state change should occur to the victory card piles and kingdom card piles.
*
*******************************************************************************/
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>
#include "dominion.h"
#include "dominion_helpers.h"
#include "rngs.h"

#define TESTCARD "Gardens"
#define TESTCARDVAL gardens

int main() {
	int newCards = 0;
	int playedCards = 1;
	int usedActions = 1;
	int discardedCards = 0;
	int shuffledCards = 0;
	int extraCoins = 0;
	int iPlayer = 0, iCard = 0;
	int handpos = 0, choice1 = 0, choice2 = 0, choice3 = 0, bonus = 0;
	// int remove1, remove2;
	int seed = 1;
	int numPlayers = 2;
	// int thisPlayer = 0;
	struct gameState G, testG;
	int k[10] = {adventurer, gardens, embargo, village, minion, mine, council_room, sea_hag, tribute, smithy};
	int retval;
	
	// Setup and test initialization
	printf("---------- Testing Card: %s ----------\n", TESTCARD);
	retval = initializeGame(numPlayers, k, seed, &G);
	// thisPlayer = G.whoseTurn;
	assert(retval == 0);
	
	// Print out gameState summary info
	printf("  prior to playCard\n");
	printf("    whoseTurn = %d\n", G.whoseTurn);
	printf("    numActions = %d\n", G.numActions);
	printf("    deckCount = %d\n", G.deckCount[testG.whoseTurn]);
	printf("    handCount = %d\n", G.handCount[testG.whoseTurn]);
	printf("    discardCount = %d\n", G.discardCount[testG.whoseTurn]);
	printf("    coins = %d\n", G.coins);

	// setup and call playCard (choice1, choice2, choice3 are irrelevant for smithy)
	handpos = 0;
	choice1 = 0;
	choice2 = 0;
	choice3 = 0;
	bonus = 0;
	// Force card being tested into the hand of player 0
	G.hand[G.whoseTurn][handpos] = TESTCARDVAL;
	// Just before calling the test function, copy gameState to a test instance
	memcpy(&testG, &G, sizeof(struct gameState));
	//cardEffect(TESTCARDVAL, choice1, choice2, choice3, &testG, handpos, &bonus);
	playCard(handpos, choice1, choice2, choice3, &testG);
	
	// Gardens does nothing
	newCards = 0;
	usedActions = 0;
	playedCards = 0;
	discardedCards = 0; // playCard results in a played, not discarded, card
	shuffledCards = 0;
	extraCoins = 0;
	printf("  Test results:\n", newCards);
	printf("    \n");
	printf("    whoseTurn = %d, expected %d\n", testG.whoseTurn, G.whoseTurn);
	printf("    numActions = %d, expected %d\n", testG.numActions, G.numActions - usedActions);
	printf("    deckCount = %d, expected %d\n", testG.deckCount[testG.whoseTurn], G.deckCount[G.whoseTurn] - newCards + shuffledCards);
	printf("    handCount = %d, expected %d\n", testG.handCount[testG.whoseTurn], G.handCount[G.whoseTurn] + newCards - playedCards - discardedCards);
	printf("    playedCardCount = %d, expected %d\n", testG.playedCardCount, G.playedCardCount + playedCards);
	// printf("    playedCards[last] = %d, expected %d\n", testG.playedCards[testG.playedCardCount - 1], TESTCARDVAL);
	printf("    discardCount = %d, expected %d\n", testG.discardCount[testG.whoseTurn], G.discardCount[G.whoseTurn] + discardedCards);
	/*
	TO DO: add coin validation; the following is NOT correct
	printf("    coins = %d, expected %d\n", testG.coins, G.coins + extraCoins);
	*/
	// Assert same player's turn
	assert(testG.whoseTurn == G.whoseTurn);
	// Assert numActions has gone down by 1
	assert(testG.numActions == G.numActions - usedActions);
	// Assert that deckCount has gone down by newCards
	assert(testG.deckCount[testG.whoseTurn] == G.deckCount[G.whoseTurn] - newCards + shuffledCards);
	// Assert that handCount has gone up by newCards
	assert(testG.handCount[testG.whoseTurn] == G.handCount[G.whoseTurn] + newCards - playedCards - discardedCards);
	// Assert that playedCardCount has gone up by playedCards
	assert(testG.playedCardCount == G.playedCardCount + playedCards);
	// Assert that playedCard is correct
	if (usedActions != 0)
		assert(testG.playedCards[testG.playedCardCount - 1] == TESTCARDVAL);
	// Assert that discardCount has gone down by discardedCards
	assert(testG.discardCount[testG.whoseTurn] == G.discardCount[G.whoseTurn] + discardedCards);
	// Assert no state change for other players
	for (iPlayer = 0; iPlayer < testG.numPlayers; iPlayer++)
		if (iPlayer != testG.whoseTurn) {
			assert(testG.handCount[iPlayer] == G.handCount[iPlayer]);
			for (iCard = 0; iCard < G.handCount[iPlayer]; iCard++)
				assert(testG.hand[iPlayer][iCard] == G.hand[iPlayer][iCard]);
			assert(testG.deckCount[iPlayer] == G.deckCount[iPlayer]);
			for (iCard = 0; iCard < G.deckCount[iPlayer]; iCard++)
				assert(testG.deck[iPlayer][iCard] == G.deck[iPlayer][iCard]);
			assert(testG.discardCount[iPlayer] == G.discardCount[iPlayer]);
			for (iCard = 0; iCard < G.discardCount[iPlayer]; iCard++)
				assert(testG.discard[iPlayer][iCard] == G.discard[iPlayer][iCard]);
		}
	
}
