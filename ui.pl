	
/* User Interface */	
clue :- 
	writeln('enter player number'),
	read(X),
	X > 2 ->
	assert(playerNumber(X)),nl,
	setPlayers(X);
	writeln('minimum player number is 3'),
	clue.

/*
 Options:
 1. Set Current Room 
 2. My suggestion
 3. Other player suggestion
 4. View Record
	-> a. view knowncard
		-> a. sort by player 
		-> b. sort by cardtype
	-> b. view doesNotHave		
		-> all. display all doesNotHave
		-> X. display cards player X does not have 
		-> Card. display players who do not have Card
	-> c. view couldHave
		-> all. display all couldHave
		-> X. display all cards player X might have
		-> Card. display all players who might have Card
	-> d. view goal card(s)	
 5. Set Values (Admin)
	-> a. assert knowncard
	-> b. assert doesNotHave
	-> c. assert couldHave
	-> d. assert goal card
 6. Delete Values (Admin)
	-> a. retract knowncard
	-> b. retract doesNotHave
	-> c. retract couldHave
	-> d. retract goal card 
*/ 
gameOption :-
	writeln('Choose from following options:'),
	writeln('1. Set Current Room'),
	writeln('2. Make Suggestion'),
	writeln('3. Other player Suggestion'),
	writeln('4. View Record'),
	writeln('5. Set Values (Admin)'),
	writeln('6. Delete Values (Admin)'),
	read(X),
 	(X = 1 -> setCurrentLocation;
 		X = 2 -> makeSuggestion;
		X = 3 -> setSuggestion;
		X = 4 -> subOption4;
		X = 5 -> subOption5;
		X = 6 -> subOption6).

subOption4 :-
	writeln('a. view knowncard'),
	writeln('b. view doesNotHave'),
	writeln('c. view couldHave'),
	writeln('d. return to main menu'),
	read(X),
 	(X = a -> subOption4a, nl, subOption4;
 		X = b -> subOption4b, nl, subOption4;
		X = c -> subOption4c, nl, subOption4;
 		X = d -> gameOption).

subOption4a :-
	writeln('a. view knowncard by player'),
	writeln('b. view knowncard by cardtype'),
	writeln('c. return to Option 4'),
	read(X),
 	(X = a -> listing(knowncard), nl, subOption4a;
 		X = b -> listing(knowncard), nl, subOption4a;
 		X = c -> subOption4).

subOption4b :- 
	writeln('a. display all doesNotHave'),
	writeln('b. display cards player X does not have'),
	writeln('c. display players who do not have card'),
	writeln('d. return to Option 4'),
	read(X),
 	(X = a -> listing(doesNotHave), nl, subOption4b;
 		X = b -> writeln('enter players number: '),read(Y), findall(A,doesNotHave(A,Y),L), write(L), nl, subOption4b;
 		X = c -> read(Y), findall(A,doesNotHave(Y,A),L), write(L), nl, subOption4b;
 		X = d -> subOption4).

subOption4c :-
	writeln('a. display all couldHave'),
	writeln('b. display all cards player X might have'),
	writeln('c. display all players who might have Card'),
	writeln('d. return to Option 4'),
	read(X),
 	(X = a -> listing(couldHave), nl, subOption4c;
 		X = b -> read(Y), findall(A,couldHave(Y,A),L), write(L), nl, subOption4c;
 		X = c -> read(Y), findall(A,couldHave(A,Y),L), write(L), nl, subOption4c;
 		X = d -> subOption4).

subOption5 :-
	writeln('a. assert knowncard'),
	writeln('b. assert doesNotHave'),
	writeln('c. assert couldHave'),
	writeln('d. assert goal card'),
	writeln('e. return to main menu'),
	read(X),
 	(X = a -> writeln('enter card name'), read(X), 
 		writeln('enter player name'), read(Y),
 		checkassert(knowncard(X,Y)), nl, subOption5;
 	X = b -> writeln('enter card name'), read(X), 
 		writeln('enter player name'), read(Y),
 		checkassert(doesNotHave(X,Y)), nl, subOption5;
	X = c -> writeln('enter player name'), read(X),
		writeln('enter card1 name'), read(Y1),
		writeln('enter card2 name'), read(Y2),
		writeln('enter card3 name'), read(Y3),
		checkassert(couldHave(X,Y1,Y2,Y3)), nl, subOption5;
	X = d -> writeln('enter card name'), read(X),
		checkassert(goalCard(X)), nl, subOption5;
	X = e -> gameOption).

subOption6 :-
	writeln('a. retract knowncard'),
	writeln('b. retract doesNotHave'),
	writeln('c. retract couldHave'),
	writeln('d. retract goal card'),
	writeln('e. return to main menu'),
	read(X),
 	(X = a -> writeln('which card do you want to delete'), read(X), retract(knowncard(X,_)), nl, subOption5;
 		X = b -> retract(doesNotHave(_,_)), nl, subOption5;
		X = c -> retract(couldHave(_,_,_,_)), nl, subOption5;
		X = d -> retract(goalCard(_)), nl, subOption5;
		X = e -> gameOption).
	

makeSuggestion :-
	get_most_could_have_p(Person),
	get_most_could_have_w(Weapon),
	get_most_could_have_r(Room),
	
	writeln('This is what your loyal assistant recommends:'), 
	write(' person: '),writeln(Person),
	write(' weapon: '),writeln(Weapon),
	write(' room: '),writeln(Room),
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

/* 
 *  Setters
 */
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