	
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
	-> b. view doesNotHave		
		-> a. display all doesNotHave
		-> b. display cards player X does not have 
		-> c. display players who do not have Card
	-> c. view couldHave
		-> a. display all couldHave
		-> b. display all cards player X might have
		-> c. display all players who might have Card
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
	% at the start of each turn we perform backend logic computations
	simplify,  			% see clue.pl
	updateGoalCard, 	% see clue.pl
	makeAccusation,		% see below

	writeln('Choose from following options:'),
	writeln('1. Set Current Room'),
	writeln('2. Make Suggestion'),
	writeln('3. Other player Suggestion'),
	writeln('4. View Record'),
	writeln('5. Set Values (Admin)'),
	writeln('6. Delete Values (Admin)'),
	read(X),
 	(X = 1 -> setCurrentLocation,write('current room is now set to: '),currentLocation(R),write(R),nl,nl,gameOption;
 		X = 2 -> makeSuggestion,nl,setSuggestion;
		X = 3 -> setSuggestion;
		X = 4 -> nl,subOption4;
		X = 5 -> nl,subOption5;
		X = 6 -> nl,subOption6).

subOption4 :-
	writeln('a. view knowncard'),
	writeln('b. view doesNotHave'),
	writeln('c. view couldHave'),
	writeln('d. return to main menu'),
	read(X),
 	(X = a -> listing(knowncard), nl, subOption4;
 		X = b -> subOption4b, nl, subOption4;
		X = c -> subOption4c, nl, subOption4;
 		X = d -> gameOption).

subOption4b :- 
	writeln('a. display all doesNotHave'),
	writeln('b. display cards player X does not have'),
	writeln('c. display players who do not have card'),
	writeln('d. return to Option 4'),
	read(X),
 	(X = a -> listing(doesNotHave), nl,nl, subOption4b;
 		X = b -> doesNotHaveByPlayer, nl,nl, subOption4b;
 		X = c -> doesNotHaveByCard, nl, nl,subOption4b;
 		X = d -> subOption4).

 doesNotHaveByPlayer :-
 	read(X), findall(A,doesNotHave(A,X),L), write(L).

 doesNotHaveByCard :-
 	read(X), findall(A,doesNotHave(X,A),L), write(L).

subOption4c :-
	writeln('a. display all couldHave'),
	writeln('b. display all cards player X might have'),
	writeln('c. display all players who might have Card'),
	writeln('d. return to Option 4'),
	read(X),
 	(X = a -> listing(couldHave), nl, subOption4c;
 		X = b -> couldHaveByPlayer, nl, subOption4c;
 		X = c -> couldHaveByCard, nl, subOption4c;
 		X = d -> subOption4).

couldHaveByPlayer :-
	read(X),
	findall(A,couldHave(X,A,_,_),L1),
	findall(A,couldHave(X,_,A,_),L2),
	findall(A,couldHave(X,_,_,A),L3),
	append(L1,L2,L4),
	append(L4,L3,L), 
	write(L).

couldHaveByCard :- 
	read(X), 
	findall(A,couldHave(A,X,_,_),L1),
	findall(A,couldHave(A,_,X,_),L2),
	findall(A,couldHave(A,_,_,X),L3),
	append(L1,L2,L4),
	append(L4,L3,L), 
	write(L).

subOption5 :-
	writeln('a. assert knowncard'),
	writeln('b. assert doesNotHave'),
	writeln('c. assert couldHave'),
	writeln('d. assert goal card'),
	writeln('e. return to main menu'),
	read(X),
 	(X = a -> assertKnown, nl, subOption5;
 		X = b -> assertDoesNotHave, nl, subOption5;
		X = c -> assertCouldHave, nl, subOption5;
	X = d -> assertGoal, nl, subOption5;
	X = e -> gameOption).

assertKnown :-
	writeln('enter card name'), read(X), 
 		writeln('enter player name'), read(Y),
 		checkassert(knowncard(X,Y)).

assertDoesNotHave :-
	writeln('enter card name'), read(X), 
 		writeln('enter player name'), read(Y),
 		checkassert(doesNotHave(X,Y)).

assertCouldHave :-
	writeln('enter player name'), read(X),
		writeln('enter card1 name'), read(Y1),
		writeln('enter card2 name'), read(Y2),
		writeln('enter card3 name'), read(Y3),
		checkassert(couldHave(X,Y1,Y2,Y3)).

assertGoal :-
	writeln('enter card name'), read(X),
		checkassert(goalCard(X)).

subOption6 :-
	writeln('a. retract knowncard'),
	writeln('b. retract doesNotHave'),
	writeln('c. retract couldHave'),
	writeln('d. retract goal card'),
	writeln('e. return to main menu'),
	read(X),
 	(X = a -> retractKnownCard, nl, subOption5;
 		X = b -> retract(doesNotHave(_,_)), nl, subOption5;
		X = c -> retract(couldHave(_,_,_,_)), nl, subOption5;
		X = d -> retract(goalCard(_)), nl, subOption5;
		X = e -> gameOption).
	
retractKnownCard :-
	writeln('which card do you want to delete'), 
	read(X), retract(knowncard(X,_)).

makeSuggestion :-
	get_most_could_have_p(Person),
	get_most_could_have_w(Weapon),
	get_most_could_have_r(Room),
	
	writeln('This is what your loyal assistant recommends:'), 
	write(' person: '),writeln(Person),
	write(' weapon: '),writeln(Weapon),
	write(' room: '),writeln(Room),nl.
	
/*
 * When we have 3 goalCards, then tell user that program has already solved the game
 */ 
makeAccusation :-
	findall(X,goalCard(X),L),
	length(L,N),
	N =:= 3,nl,
	writeln('HEY I SOLVED IT ALREADY, THESE ARE THE CARDS IN THE ENVELOPE: '),
	writeln(L),nl;
	true.

countKnownCards(N) :-
	findall(X, knowncard(X,_),L),
	length(L,N).

/* 
 **************************  Setters ***************************
 */

/*
 * Sets doesNotHave clause 
 * set_does_not_have(I,F,P,W,R) will update the doesNotHave for all the players between I and F
 * for example, set_does_not_have(1,3,P,W,R) will assert doesNotHave(2,P), doesNotHave(2,W), doesNotHave(2,R)
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
	writeln('which number am I?'),
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
 	validcard(X), player(_,Y) , not(knowncard(X,_))-> checkassert(knowncard(X,Y)),setKnownCards;
 	writeln('invalid card or player or known already'), setKnownCards).
 
setMyCards :- 
 	writeln('input my cards: '),
 	read(X),
 	(validcard(X) ,not(knowncard(X,_))-> mainPlayer(Y), checkassert(knowncard(X,Y)),setMyCards;
 	X = done -> setMainDNH,gameOption;
 	writeln('invalid or known'),setMyCards).

setAccusation :-
	writeln('enter the player number of the player who is making the suggestion'),
	read(X),
	writeln('which suspect?'),
	read(P),
	writeln('which weapon?'),
	read(W),
	writeln('which room?'),
	read(R),
	player(_,X), validperson(P),validweapon(W),validroom(R) -> checkassert(accusation(X,P,W,R));
	writeln('invalid player or card').

% set suggestion and couldhave, simple version
setSuggestion :-
	writeln('enter the player number of the player who is making the suggestion'),
	read(X),
	writeln('which suspect?'),
	read(P),
	writeln('which weapon?'),
	read(W),
	writeln('which room?'),
	read(R),
	(mainPlayer(X),validperson(P),validweapon(W),validroom(R) -> checkassert(suggestion(X,P,W,R)),showCardToMe(X,P,W,R);
	player(_,X),validperson(P),validweapon(W),validroom(R) -> checkassert(suggestion(X,P,W,R)),showCard(X,P,W,R);
	writeln('something is invalid'),setSuggestion).

% the case when the disproving card is shown to someone else	
showCard(X,P,W,R):-
	writeln('enter the player who showed a card (enter "noone" if no one showed a card)'),
	read(A),
	(player(_,A) -> checkassert(couldHave(A,P,W,R)), set_does_not_have(X,A,P,W,R),
					nl,gameOption;
	A = noone -> set_does_not_have(X,X,P,W,R),nl,gameOption;
	writeln('something is invalid'), setSuggestion).
	
% when the card is shown to me	
showCardToMe(X,P,W,R):-
	writeln('enter the player who showed me a card? (enter "noone" if no one showed a card, and enter anything for the followup question)'),
	read(A),
	writeln('which card did he show me?'),
	read(C),
	(player(_,A) -> checkassert(knowncard(C,A)), set_does_not_have(X,A,P,W,R),
					nl,gameOption;
	A = noone -> set_does_not_have(X,X,P,W,R),nl,gameOption;
	writeln('something is invalid'), setSuggestion).

	
setCurrentLocation:-
	write('enter current room: '),
	read(X),
	retractall(currentLocation(_)),
	checkassert(currentLocation(X)).
