:- dynamic playerNumber/1.
:- dynamic player/2.
:- dynamic mainPlayer/1.
:- dynamic knowncard/2. % card , player
:- dynamic doesNotHave/2. % card player
:- dynamic suggestion/4. % player,card,card,card
:- dynamic accusation/4. % player,card,card,card
:- dynamic couldHave/3. % player,card,card
:- dynamic couldHave/4. % player,card,card,card
:- dynamic currentLocation/1. % room , only 1 instance.
:- dynamic goalCard/1. % card
		
checkassert(X):-
	not(X) ->
	assert(X);
	true.

% for debugging purposes, lists all the stored dynamic predicates
listingall:-
	listing(playerNumber),
	listing(player),
	listing(mainPlayer),
	listing(knowncard),
	listing(doesNotHave),
	listing(suggestion),
	listing(accusation),
	listing(couldHave),
	listing(currentLocation),
	listing(goalCard).

listingsome:-	
	listing(playerNumber),
	listing(player),
	listing(mainPlayer),
	listing(knowncard),
	listing(suggestion),
	listing(accusation),
	listing(couldHave),
	listing(currentLocation),
	listing(goalCard).
	
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
 
flush :-
 	retractall(knowncard(_,_)),
 	retractall(player(_,_)),
 	retractall(mainPlayer(_)),
 	retractall(playerNumber(_)),
 	retractall(suggestion(_,_,_,_)),
 	retractall(accusation(_,_,_,_)),
 	retractall(couldHave(_,_,_,_)),
 	retractall(doesNotHave(_,_)),
 	retractall(currentLocation(_)),
	retractall(goalCard(_)).