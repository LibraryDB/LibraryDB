:- dynamic playerNumber/1.
:- dynamic player/2.
:- dynamic mainPlayer/1.
:- dynamic knowncard/2. % card , player
:- dynamic doesNotHave/2. % card player
:- dynamic suggestion/4. % player,card,card,card
:- dynamic accusation/4. % player,card,card,card
:- dynamic couldHave/4. % player,card,card,card
:- dynamic currentLocation/1. % room , only 1 instance.

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
	listing(currentLocation).

setup :-
 assert(knowncard(scarlet,1)),
 assert(knowncard(white,1)),
 assert(knowncard(knife,2)),
 assert(knowncard(pistol,2)),
 assert(knowncard(kitchen,3)),
 assert(knowncard(ballroom,3)),
 assert(currentLocation(conservatory)). 
 
flush :-
 	retractall(knowncard(_,_)),
 	retractall(player(_,_)),
 	retractall(mainPlayer(_)),
 	retractall(playerNumber(_)),
 	retractall(suggestion(_,_,_,_)),
 	retractall(accusation(_,_,_,_)),
 	retractall(couldHave(_,_,_,_)),
 	retractall(doesNotHave(_,_)),
 	retractall(currentLocation(_)).
	
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
	
	findall(X, knowncard(X,Y),Lknown),
	
	filterP(Lknown,KnownP),	
	filterW(Lknown,KnownW),
	
	filterR(Lknown,KnownR),
	
	subtract(Lperson,KnownP,UnknownP),
	subtract(Lweapon,KnownW,UnknownW),
	subtract(Lroom,KnownR,UnknownR),	
	
	head(UnknownP,HeadP),
	head(UnknownW,HeadW),
	head(UnknownR,HeadR),
	
	writeln(HeadP),writeln(HeadW),writeln(HeadR),nl,
	setSuggestion.
	
makeAccusation :-
	countKnownCards(N),
	N =:= 18 ->
	findall(X, validcard(X),L1),
	findall(X, knowncard(X,Y),L2),
	subtract(L1,L2,L),
	writeln(L);
	writeln('not enough cards to make accusation'),nl, gameOption.

countKnownCards(N) :-
	findall(X, knowncard(X,Y),L),
	length(L,N).

% ************************ Setters **************************	
set_does_not_have(I,F,Person,Weapon,Room):- 
	F =:= 1, 
	playerNumber(PN),
 	I =:= PN,!.
 
set_does_not_have(I,F,Person,Weapon,Room):-
	I1 is I+1,
	I1 =:= F,!.
 
set_does_not_have(I,F,Person,Weapon,Room):-
 	playerNumber(PN),
 	I =:= PN,!,
 	assert(doesNotHave(Person,1)),
 	assert(doesNotHave(Weapon,1)),
 	assert(doesNotHave(Room,1)),
 	set_does_not_have(1,F,Person,Weapon,Room). 
 
set_does_not_have(I,F,Person,Weapon,Room):-
 	I1 is I + 1,
 	assert(doesNotHave(Person,I1)),
 	assert(doesNotHave(Weapon,I1)),
 	assert(doesNotHave(Room,I1)),
 	set_does_not_have(I1,F,Person,Weapon,Room).

setPlayers(0) :- setMainPlayer.

setPlayers(N) :-
	N > 0,
	playerNumber(X),
	writeln('enter player name'),
	read(Y),
	W is X-N+1,
	assert(player(Y,W)),
	N1 is N-1,
	setPlayers(N1).

setMainPlayer :-
	writeln('which is my turn'),
	read(X),
	assert(mainPlayer(X)), nl,
	setMyCards.

setKnownCards :-
 	writeln('input known cards'),
 	read(X),
 	writeln('enter the player who shown a card again'),
 	read(Y),
 	(X = done -> nl, gameOption;
 	Y = done -> nl, gameOption;
 	validcard(X), player(Y,_) , not(knowncard(X,_))-> assert(knowncard(X,Y)),setKnownCards;
 	writeln('invalid card or player or known already'), setKnownCards).
 
setMyCards :- 
 	writeln('input my cards: '),
 	read(X),
 	(validcard(X) ,not(knowncard(X,_))-> mainPlayer(Y), assert(knowncard(X,Y)),setMyCards;
 	X = done -> gameOption;
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
	player(X,_), validperson(P),validweapon(W),validroom(R) -> assert(accusation(X,P,W,R));
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
	assert(suggestion(X,P,W,R)), 
	writeln('enter the player who shown a card'),
	read(A),
	player(A,_) -> assert(couldHave(A,P,W,R)), set_does_not_have(X,A,P,W,R),
	setKnownCards, nl,gameOption;
	writeln('something is invalid'), setSuggestion).

setCurrentLocation:-
	write('enter current room: '),
	read(X),
	retractall(currentLocation(_)),
	assert(currentLocation(X)).
 
head([],null).	
head([H|L],H).

% ************************ Filters **************************	
% filter person list
filterP([],[]).
filterP([L|List],[L|Filtered]) :-
    person(L),!,
    filterP(List,Filtered).
filterP([L|List],Filtered) :-
    filterP(List,Filtered).	
	
filterW([],[]).
filterW([L|List],[L|Filtered]) :-
    weapon(L),!,
    filterW(List,Filtered).
filterW([L|List],Filtered) :-
    filterW(List,Filtered).
	
filterR([],[]).
filterR([L|List],[L|Filtered]) :-
    room(L),!,
    filterR(List,Filtered).
filterR([L|List],Filtered) :-
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