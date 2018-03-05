/*******************************************************************************
* Student: Brian Bruckner
* Course:  CS_362_400_W2018
*
* File: cardtest2.c
* 
* This file tests the Adventurer card
*
* basic requirements of adventurer:
* 1. Current player should draw cards until 2 Treasure cards are drawn
* 1a. if after running out of cards, less than 2 Treasure cards revealed, 
*     then <2 Treasure added to hand (non-Treasure cards are not placed in 
*     Discard till after revealing)
* 2. 2 Treasure cards go into current player's hand
* 2a. no treasure cards go into discard
* 3. Remaining revealed cards are discarded
* 4. No state change should occur for other players.
* 5. No state change should occur to the victory card piles and kingdom card piles.
*
*******************************************************************************/
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>
#include "dominion.h"
#include "dominion_helpers.h"
#include "rngs.h"

#define TESTCARD "adventurer"
#define TESTCARDVAL adventurer

int main() {
	int newCards = 0;
	int curCard;
	int playedCards = 1;
	int discardedCards = 0;
	int shuffledCards = 0;
	int extraCoins = 0;
	int iPlayer = 0, iCard = 0;
	int handpos = 0, choice1 = 0, choice2 = 0, choice3 = 0, bonus = 0;
	// int remove1, remove2;
	int seed = 2;
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
	
	// Adventurer: draw cards until 2 Treasure cards are drawn
	// newCards = max(2, number of treasure in deck and discard)
	newCards = 0;
	shuffledCards = 0;
	discardedCards = 0;
printf("deck: %d %d %d %d %d\n", G.deck[G.whoseTurn][0], G.deck[G.whoseTurn][1], G.deck[G.whoseTurn][2], G.deck[G.whoseTurn][3], G.deck[G.whoseTurn][4]);
printf("hand: %d %d %d %d %d\n", G.hand[G.whoseTurn][0], G.hand[G.whoseTurn][1], G.hand[G.whoseTurn][2], G.hand[G.whoseTurn][3], G.hand[G.whoseTurn][4]); 
	for (iCard = G.deckCount[G.whoseTurn] - 1; iCard >=0; iCard--) {
		curCard = G.deck[G.whoseTurn][iCard];
		if ( curCard == copper || curCard == silver || curCard == gold) {
			newCards++;
			if (newCards == 2)
				break;
		}
		else {
			discardedCards++;
		}
	}

	if (newCards < 2) {
		shuffledCards += G.discardCount[G.whoseTurn];
		for (iCard = 0; iCard < G.discardCount[G.whoseTurn]; iCard++) {
			curCard = G.discard[G.whoseTurn][iCard];
			if (curCard == copper || curCard == silver || curCard == gold) {
				newCards++;
				if (newCards == 2)
					break;
			}
		}
	}
	
	if (newCards > 2)
		newCards = 2;
	
	playedCards = 1;
	
	extraCoins = 0;
	printf("  Testing for +%d cards\n", newCards);
	printf("    \n");
	printf("    whoseTurn = %d, expected %d\n", testG.whoseTurn, G.whoseTurn);
	printf("    numActions = %d, expected %d\n", testG.numActions, G.numActions - 1);
	printf("    deckCount = %d, expected <= %d\n", testG.deckCount[testG.whoseTurn], G.deckCount[G.whoseTurn] - newCards + shuffledCards);
	printf("    handCount = %d, expected %d\n", testG.handCount[testG.whoseTurn], G.handCount[G.whoseTurn] + newCards - playedCards);
	printf("    playedCardCount = %d, expected %d\n", testG.playedCardCount, G.playedCardCount + playedCards);
	printf("    playedCards[last] = %d, expected %d\n", testG.playedCards[testG.playedCardCount - 1], TESTCARDVAL);
	printf("    discardCount = %d, expected %d\n", testG.discardCount[testG.whoseTurn], G.discardCount[G.whoseTurn] + discardedCards);
	/*
	TO DO: add coin validation; the following is NOT correct
	printf("    coins = %d, expected %d\n", testG.coins, G.coins + extraCoins);
	*/
	// Assert same player's turn
	assert(testG.whoseTurn == G.whoseTurn);
	// Assert numActions has gone down by 1
	assert(testG.numActions == G.numActions - 1);
	// 1. Current player should draw cards until 2 Treasure cards are drawn
	// (or all treasure if < 2 in deck and discard). Assert that handCount 
	// has gone up by newCards
	assert(testG.handCount[testG.whoseTurn] == G.handCount[G.whoseTurn] + newCards - playedCards);
	
	// 2. check to ensure that new cards in hand are treasure
	for (iCard = G.handCount[G.whoseTurn]; iCard < testG.handCount[testG.whoseTurn]; iCard++) {
		curCard = testG.hand[testG.whoseTurn][iCard];
		assert(curCard == copper || curCard == silver || curCard == gold);
	}
	
	// 2.a check to ensure no treasure in discard
	for (iCard = G.discardCount[G.whoseTurn]; iCard < testG.discardCount[testG.whoseTurn]; iCard++) {
		curCard = testG.discard[testG.whoseTurn][iCard];
		assert(curCard != copper && curCard != silver && curCard != gold);
	}

	// TO DO: Fix the following
	// Assert that deckCount has gone down by newCards
	// assert(testG.deckCount[testG.whoseTurn] == G.deckCount[G.whoseTurn] - newCards + shuffledCards);

	// Assert that playedCardCount has gone up by playedCards
	// assert(testG.playedCardCount == G.playedCardCount + playedCards);
	
	// Assert that discardCount has gone down by discardedCards
	// assert(testG.discardCount[testG.whoseTurn] == G.discardCount[G.whoseTurn] + discardedCards);
	// TO DO: Fix the above
	
	// Assert that playedCard is correct
	assert(testG.playedCards[testG.playedCardCount - 1] == TESTCARDVAL);
	
	// Assert no state change for other players
	for (iPlayer = 0; iPlayer < testG.numPlayers; iPlayer++) {
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
}
