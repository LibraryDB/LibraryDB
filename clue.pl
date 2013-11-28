
% prolog performs calculations to
% 1. removes redundant data
% 2. updates data based on current knowledge
simplify:-

	% if we know player P has C1 , C2, or C3, then retract couldHave(P,C1,C2,C3)
	(couldHave(P,C1,C2,C3),knowncard(C1,P) -> retract(couldHave(P,C1,C2,C3)),simplify;
	couldHave(P,C1,C2,C3),knowncard(C2,P) -> retract(couldHave(P,C1,C2,C3)),simplify;
	couldHave(P,C1,C2,C3),knowncard(C3,P) -> retract(couldHave(P,C1,C2,C3)),simplify;
	couldHave(P,C1,C2),knowncard(C1,P) -> retract(couldHave(P,C1,C2)),simplify;
	couldHave(P,C1,C2),knowncard(C2,P) -> retract(couldHave(P,C1,C2)),simplify;

	% if we know player 2 has a card that was one of the cards player 1 has, then we can reduce the cards player 1 could have.
	couldHave(P,C1,C2,C3),knowncard(C1,P2) -> retract(couldHave(P,C1,C2,C3)),checkassert(couldHave(P,C2,C3)),simplify;
	couldHave(P,C1,C2,C3),knowncard(C2,P2) -> retract(couldHave(P,C1,C2,C3)),checkassert(couldHave(P,C1,C3)),simplify;
	couldHave(P,C1,C2,C3),knowncard(C3,P2) -> retract(couldHave(P,C1,C2,C3)),checkassert(couldHave(P,C1,C2)),simplify;
	couldHave(P,C1,C2),knowncard(C1,P2) -> retract(couldHave(P,C1,C2)),checkassert(knowncard(C2,P)),simplify;
	couldHave(P,C1,C2),knowncard(C2,P2) -> retract(couldHave(P,C1,C2)),checkassert(knowncard(C1,P)),simplify;
	
	% if we know player 1 does not have a card, then we can reduce the couldHave cards.
	couldHave(P,C1,C2,C3),doesNotHave(C1,P) -> retract(couldHave(P,C1,C2,C3)),checkassert(couldHave(P,C2,C3)),simplify;
	couldHave(P,C1,C2,C3),doesNotHave(C2,P) -> retract(couldHave(P,C1,C2,C3)),checkassert(couldHave(P,C1,C3)),simplify;
	couldHave(P,C1,C2,C3),doesNotHave(C3,P) -> retract(couldHave(P,C1,C2,C3)),checkassert(couldhave(P,C1,C2)),simplify;
	couldHave(P,C1,C2),doesNotHave(C1,P) -> retract(couldHave(P,C1,C2)),checkassert(knowncard(C2,P)),simplify;
	couldHave(P,C1,C2),doesNotHave(C2,P) -> retract(couldHave(P,C1,C2)),checkassert(knowncard(C1,P)),simplify;
	
	true).	
	
countCouldHave(C,N):-
	findall(P,couldHave(P,C,_,_),L1),length(L1,N1),
	findall(P,couldHave(P,_,C,_),L2),length(L2,N2),
	findall(P,couldHave(P,_,_,C),L3),length(L3,N3),
	findall(P,couldHave(P,C,_),L4),length(L4,N4),
	findall(P,couldHave(P,_,C),L5),length(L5,N5),
	N is N1 + N2 + N3 + N4 + N5.
	
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

% *********************** Valid Cards ***************************
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

