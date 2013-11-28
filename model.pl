% Stores the number of players
:- dynamic playerNumber/1.  

% Stores the name of a player and his/her number
:- dynamic player/2.

% Stores the name of the player the program is assisting
:- dynamic mainPlayer/1.

% knowncard(C,P) means player P has the card C, maybe should rename to something like has_card
:- dynamic knowncard/2.

% doesNotHave(C,P) means Player P does not have the card C 
:- dynamic doesNotHave/2. % card player

% accusation(P,C1,C2,C3) means player P made the accusation C1,C2,C3
:- dynamic accusation/4. % player,card,card,card

% couldHave(P,C1,C2) means player P could have either C1 or C2
:- dynamic couldHave/3. % player,card,card

% couldHave(P,C1,C2,C3) means player P could have either C1,C2 or C3
:- dynamic couldHave/4. % player,card,card,card

% currentLocation(R) means the current room the main player is in is room R.
:- dynamic currentLocation/1. % room , only 1 instance.

% goalCard(C) means that C is a card in the envelope.
:- dynamic goalCard/1. % card
	
/* 
 * Checks if the clause is already stored before asserting
 * Avoids having duplicate clauses
 */	
checkassert(X):-
	not(X) ->
	assert(X);
	true.

/*
 * for debugging purposes, lists all the stored dynamic clauses
 */
listingall:-
	listing(playerNumber),
	listing(player),
	listing(mainPlayer),
	listing(knowncard),
	listing(doesNotHave),
	listing(accusation),
	listing(couldHave),
	listing(currentLocation),
	listing(goalCard).

/*
 * for debugging purposes, lists all stored clauses but the doesNotHave clause
 */
listingsome:-	
	listing(playerNumber),
	listing(player),
	listing(mainPlayer),
	listing(knowncard),
	listing(accusation),
	listing(couldHave),
	listing(currentLocation),
	listing(goalCard).
	
/*
 * For testing and debugging purposes, initializes the database.
 */ 
setup :-
 checkassert(playerNumber(4)),
 checkassert(player(1,1)),
 checkassert(player(2,2)),
 checkassert(player(3,3)),
 checkassert(player(4,4)),
 checkassert(mainPlayer(1)),
 
 checkassert(couldHave(1,scarlet,pistol,kitchen)),
 checkassert(couldHave(2,green,billiardroom)),
 checkassert(couldHave(3,green,wrench)),
 checkassert(knowncard(wrench,4)),
 checkassert(knowncard(scarlet,1)),
 checkassert(knowncard(white,1)),
 checkassert(knowncard(knife,2)),
 checkassert(knowncard(pistol,2)),
 checkassert(knowncard(kitchen,3)),
 checkassert(knowncard(ballroom,3)),
 checkassert(currentLocation(conservatory)),
 checkassert(doesNotHave(ax,2)), 
 checkassert(doesNotHave(ax,3)),
 checkassert(doesNotHave(ax,4)),
 
 checkassert(couldHave(2,plum,ax,ballroom)),
 checkassert(couldHave(3,plum,pistol,hall)),
 checkassert(couldHave(3,green,wrench)),
 checkassert(couldHave(2,plum,wrench)).
 
/*
 * deletes all stored data
 */  
flush :-
 	retractall(knowncard(_,_)),
 	retractall(player(_,_)),
 	retractall(mainPlayer(_)),
 	retractall(playerNumber(_)),
 	retractall(couldHave(_,_,_,_)),
 	retractall(doesNotHave(_,_)),
 	retractall(currentLocation(_)),
	retractall(goalCard(_)).