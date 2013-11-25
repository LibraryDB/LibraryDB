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

% prolog performs calculations to
% 1. removes redundant data
% 2. update data based on current knowledge
simplify:-

	% if we know player P has C1 , C2, or C3, then retract couldHave(P,C1,C2,C3)
	(couldHave(P,C1,C2,C3),knowncard(C1,P) -> retract(couldHave(P,C1,C2,C3));
	couldHave(P,C1,C2,C3),knowncard(C2,P) -> retract(couldHave(P,C1,C2,C3));
	couldHave(P,C1,C2,C3),knowncard(C3,P) -> retract(couldHave(P,C1,C2,C3));
	couldHave(P,C1,C2),knowncard(C1,P) -> retract(couldHave(P,C1,C2));
	couldHave(P,C1,C2),knowncard(C2,P) -> retract(couldHave(P,C1,C2));

	% if we know player 2 has a card that was one of the cards player 1 has, then we can reduce the cards player 1 could have.
	couldHave(P,C1,C2,C3),knowncard(C1,P2) -> retract(couldHave(P,C1,C2,C3)),checkassert(couldHave(P,C2,C3));
	couldHave(P,C1,C2,C3),knowncard(C2,P2) -> retract(couldHave(P,C1,C2,C3)),checkassert(couldHave(P,C1,C3));
	couldHave(P,C1,C2,C3),knowncard(C3,P2) -> retract(couldHave(P,C1,C2,C3)),checkassert(couldhave(P,C1,C2));
	couldHave(P,C1,C2),knowncard(C1,P2) -> retract(couldHave(P,C1,C2)),checkassert(knowncard(C2,P));
	couldHave(P,C1,C2),knowncard(C2,P2) -> retract(couldHave(P,C1,C2)),checkassert(knowncard(C1,P));
	
	% if we know player 1 does not have a card, then we can reduce the couldHave cards.
	couldHave(P,C1,C2,C3),doesNotHave(C1,P) -> retract(couldHave(P,C1,C2,C3)),checkassert(couldHave(P,C2,C3));
	couldHave(P,C1,C2,C3),doesNotHave(C2,P) -> retract(couldHave(P,C1,C2,C3)),checkassert(couldHave(P,C1,C3));
	couldHave(P,C1,C2,C3),doesNotHave(C3,P) -> retract(couldHave(P,C1,C2,C3)),checkassert(couldhave(P,C1,C2));
	couldHave(P,C1,C2),doesNotHave(C1,P) -> retract(couldHave(P,C1,C2)),checkassert(knowncard(C2,P));
	couldHave(P,C1,C2),doesNotHave(C2,P) -> retract(couldHave(P,C1,C2)),checkassert(knowncard(C1,P))).

% updates goalCard
updateGoalCard:-
	updateGoalCard1,updateGoalCardP,updateGoalCardW,updateGoalCardR.

% updates goalCards by looking at the doesNotHave.
% if all the players do not have card C, then C is a goalCard.	
updateGoalCard1:-
	validcard(C),
	not(goalCard(C)),
	findall(P,doesNotHave(C,P),L),
	length(L,Length),
	playerNumber(PN),
	Length =:= PN -> checkassert(goalCard(C)),updateGoalCard1;
	true.	
	
% Updates by looking at suspects, if we know 5 of 6 suspects, then the missing one is a goalCard.	
updateGoalCardP:-
	findall(Card,knowncard(Card,_),L),
	findall(Card,person(Card),LP),
	filterP(L,FP),
	subtract(LP,FP,UP),
	length(UP,Length),
	head(UP,H),
	not(goalCard(H)),
	Length =:= 1 -> checkassert(goalCard(H)); true.
	
updateGoalCardW:-
	findall(Card,knowncard(Card,_),L),
	findall(Card,weapon(Card),LW),
	filterW(L,FW),
	subtract(LW,FW,UW),
	length(UW,Length),
	head(UW,H),
	not(goalCard(H)),
	Length =:= 1 -> checkassert(goalCard(H)); true.
	
updateGoalCardR:-
	findall(Card,knowncard(Card,_),L),
	findall(Card,room(Card),LR),
	filterR(L,FR),
	subtract(LR,FR,UR),
	length(UR,Length),
	head(UR,H),
	not(goalCard(H)),
	Length =:= 1 -> checkassert(goalCard(H)); true.


	

	
% updates doesNotHave clause.	
% if we know P has card C, then other players can't have card C	
updateDNH:-
	knowncard(C,P),player(P2,P2),P =\= P2,not(doesNotHave(C,P2)) -> checkassert(doesNotHave(C,P2)),updateDNH; true.
	
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
 checkassert(knowncard(scarlet,1)),
 checkassert(knowncard(white,1)),
 checkassert(knowncard(knife,2)),
 checkassert(knowncard(pistol,2)),
 checkassert(knowncard(kitchen,3)),
 checkassert(knowncard(ballroom,3)),
 checkassert(currentLocation(conservatory)),
 checkassert(doesNotHave(ax,2)), 
 checkassert(doesNotHave(ax,3)),
 checkassert(doesNotHave(ax,4)).
 
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
	
clue :- 
	writeln('enter player number'),
	read(X),
	X > 2 ->
	assert(playerNumber(X)),nl,
	setPlayers(X);
	writeln('minimum player number is 3'),
	clue.

gameOption :-
	writeln('Choose from following options:'),
	writeln('1. Set Current Location'),
	writeln('2. Make Suggestion'),
	writeln('3. Other player Suggestion'),
	writeln('4. View Known Cards'),
	writeln('5. Make Accusation'),
	read(X),
 	(X = 1 -> setCurrentLocation;
 		X = 2 -> makeSuggestion;
		X = 3 -> setSuggestion;
		X = 4 -> listing(knowncard), nl, gameOption;
		X = 5 -> makeAccusation).

makeSuggestion :-
	findall(X, person(X),Lperson), % all valid persons
	findall(X,weapon(X),Lweapon),
	findall(X,room(X),Lroom),
	
	findall(X, knowncard(X,_),Lknown),
	
	filterP(Lknown,KnownP),	
	filterW(Lknown,KnownW),
	
	filterR(Lknown,KnownR),
	
	subtract(Lperson,KnownP,UnknownP),
	subtract(Lweapon,KnownW,UnknownW),
	subtract(Lroom,KnownR,UnknownR),	
	
	head(UnknownP,HeadP),
	head(UnknownW,HeadW),
	head(UnknownR,HeadR),
	
	writeln('This is what your loyal advisor recommends:'), 
	write(' person: '),writeln(HeadP),
	write(' weapon: '),writeln(HeadW),
	write(' room: '),writeln(HeadR),
	setSuggestion.
	
makeAccusation :-
	findall(X,goalCard(X),L),
	length(L,N),
	N =:= 3,
	writeln(L);
	writeln('not yet'),nl, gameOption.

countKnownCards(N) :-
	findall(X, knowncard(X,_),L),
	length(L,N).

% ************************ Setters **************************	
set_does_not_have(I,F,_,_,_):- 
	F =:= 1, 
	playerNumber(PN),
 	I =:= PN,!.
 
set_does_not_have(I,F,_,_,_):-
	I1 is I+1,
	I1 =:= F,!.
 
set_does_not_have(I,F,Person,Weapon,Room):-
 	playerNumber(PN),
 	I =:= PN,!,
 	checkassert(doesNotHave(Person,1)),
 	checkassert(doesNotHave(Weapon,1)),
 	checkassert(doesNotHave(Room,1)),
 	set_does_not_have(1,F,Person,Weapon,Room). 
 
set_does_not_have(I,F,Person,Weapon,Room):-
 	I1 is I + 1,
 	checkassert(doesNotHave(Person,I1)),
 	checkassert(doesNotHave(Weapon,I1)),
 	checkassert(doesNotHave(Room,I1)),
 	set_does_not_have(I1,F,Person,Weapon,Room).

% sets doesNotHave for main player.	
setMainDNH:-
	mainPlayer(N),validcard(C),not(knowncard(C,N)),not(doesNotHave(C,N)) -> checkassert(doesNotHave(C,N)),setMainDNH; true.	
	
setPlayers(0) :- setMainPlayer.

setPlayers(N) :-
	N > 0,
	playerNumber(X),
	writeln('enter player name'),
	read(Y),
	W is X-N+1,
	checkassert(player(Y,W)),
	N1 is N-1,
	setPlayers(N1).

setMainPlayer :-
	writeln('which is my turn'),
	read(X),
	checkassert(mainPlayer(X)), nl,
	setMyCards.

setKnownCards :-
 	writeln('input known cards'),
 	read(X),
 	writeln('enter the player who shown a card again'),
 	read(Y),
 	(X = done -> nl, gameOption;
 	Y = done -> nl, gameOption;
 	validcard(X), player(Y,_) , not(knowncard(X,_))-> checkassert(knowncard(X,Y)),setKnownCards;
 	writeln('invalid card or player or known already'), setKnownCards).
 
setMyCards :- 
 	writeln('input my cards: '),
 	read(X),
 	(validcard(X) ,not(knowncard(X,_))-> mainPlayer(Y), checkassert(knowncard(X,Y)),setMyCards;
 	X = done -> setMainDNH,gameOption;
 	writeln('invalid or known'),setMyCards).

setAccusation :-
	writeln('enter player'),
	read(X),
	writeln('who?'),
	read(P),
	writeln('what?'),
	read(W),
	writeln('where'),
	read(R),
	player(X,_), validperson(P),validweapon(W),validroom(R) -> checkassert(accusation(X,P,W,R));
	writeln('invalid player or card').

% set suggestion and couldhave, simple version
setSuggestion :-
	writeln('enter player'),
	read(X),
	writeln('who?'),
	read(P),
	writeln('what?'),
	read(W),
	writeln('where'),
	read(R),
	(player(X,_),validperson(P),validweapon(W),validroom(R) -> 
	checkassert(suggestion(X,P,W,R)), 
	writeln('enter the player who shown a card'),
	read(A),
	player(A,_) -> checkassert(couldHave(A,P,W,R)), set_does_not_have(X,A,P,W,R),
	setKnownCards, nl,gameOption;
	writeln('something is invalid'), setSuggestion).

setCurrentLocation:-
	write('enter current room: '),
	read(X),
	retractall(currentLocation(_)),
	checkassert(currentLocation(X)).
 
head([],null).	
head([H|_],H).

% ************************ Filters **************************	
% filter person list
filterP([],[]).
filterP([L|List],[L|Filtered]) :-
    person(L),!,
    filterP(List,Filtered).
filterP([_|List],Filtered) :-
    filterP(List,Filtered).	
	
filterW([],[]).
filterW([L|List],[L|Filtered]) :-
    weapon(L),!,
    filterW(List,Filtered).
filterW([_|List],Filtered) :-
    filterW(List,Filtered).
	
filterR([],[]).
filterR([L|List],[L|Filtered]) :-
    room(L),!,
    filterR(List,Filtered).
filterR([_|List],Filtered) :-
    filterR(List,Filtered).

% *********************** Valic Cards ***************************
validperson(X) :- person(X).
validweapon(X) :- weapon(X).
validroom(X) :- room(X).

validcard(X) :- person(X).
validcard(X) :- weapon(X).
validcard(X) :- room(X).

person(scarlet).
person(white).
person(peacock).
person(green).
person(mustard).
person(plum).

weapon(knife). 
weapon(candlestick).
weapon(pistol).
weapon(rope).
weapon(bat).
weapon(ax).

room(kitchen).
room(ballroom).
room(conservatory).
room(billiardroom).
room(library).
room(study).
room(hall).
room(lounge).
room(diningroom).

% *********************** Board model ***************************

/* 	For distanceToAdjacentRoom(A,B,N)
	N is the shortest distance to get from room A to B, which is an adjacent room of A.
	for instance, distanceToAdjacentRoom(conservatory,ballroom,4) means that if we start in the conservatory
	and roll a sum of 4 or greater, then we can get to the ballroom in one step  */
	
distanceToAdjacentRoom(conservatory,ballroom,4).
distanceToAdjacentRoom(conservatory,billiardroom,7).
distanceToAdjacentRoom(conservatory,lounge,1).
distanceToAdjacentRoom(billiardroom,conservatory,7).
distanceToAdjacentRoom(billiardroom,ballroom,6).
distanceToAdjacentRoom(billiardroom,library,4).
distanceToAdjacentRoom(library,billiardroom,4).
distanceToAdjacentRoom(library,study,7).
distanceToAdjacentRoom(library,hall,7).
distanceToAdjacentRoom(study,library,7).
distanceToAdjacentRoom(study,hall,4).
distanceToAdjacentRoom(study,kitchen,1).
distanceToAdjacentRoom(hall,study,4).
distanceToAdjacentRoom(hall,library,7).
distanceToAdjacentRoom(hall,lounge,8).
distanceToAdjacentRoom(hall,diningroom,8).
distanceToAdjacentRoom(lounge,hall,8).
distanceToAdjacentRoom(lounge,diningroom,4).
distanceToAdjacentRoom(lounge,conservatory,1).
distanceToAdjacentRoom(diningroom,loungh,4).
distanceToAdjacentRoom(diningroom,hall,8).
distanceToAdjacentRoom(diningroom,kitchen,11).
distanceToAdjacentRoom(diningroom,ballroom,7).
distanceToAdjacentRoom(kitchen,diningroom,11).
distanceToAdjacentRoom(kitchen,ballroom,7).
distanceToAdjacentRoom(kitchen,study,1).
distanceToAdjacentRoom(ballroom,kitchen,7).
distanceToAdjacentRoom(ballroom,diningroom,7).
distanceToAdjacentRoom(ballroom,conservatory,4).
distanceToAdjacentRoom(ballroom,billiardroom,6).