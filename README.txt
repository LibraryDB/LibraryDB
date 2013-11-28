 Jinzhou Zhang 	71620090	s0f7
 Xue Qi Xu		52732104	p8t7
 
Load all three .pl files to use the assistant.
Initialize the clue assistant by typing the predicate "clue"
  
What it handles:
1. Minimum Requirements:
	a. Number of players will be asked in initialization
	b. the order of play will be recorded in the initialization process
	c. included in initialization process
	d. suggestions are being stored in the predicate suggestion(A,B,C,D)
	e. see ui/main menu
	f. This is in the predicate makeSuggestion, which can be triggered from the main menu
	g. tells user when to make an accusation when prolog deduced the three hidden cards
	
2. A level:
	[For a. and b. refer to the predicate setSuggestion (in ui.pl), for c. refer to makeSuggestion (in ui.pl)]
	a. When someone shows the main player ( player this program is assisting) a card,
	   it will assert knowncard(C,P) where card C belongs to player P,
	   then it will update the doesNotHave clauses for other players who were unable to disprove the suggestion,
	   i.e. if player 1 suggested a card plum, and player 2 and 3 passed their turn, we will assert doesNotHave(2,plum) and doesNotHave(3,plum).
        If no one shows a card then everyone's (except for person asking) doesNotHave clause will be updated.
		
	b. When someone whos a card to another player:
		We will update the doesNotHave clause like we did above, and assert a couldHave clause for the player who disproved the suggestion.
		
	c. The program will recommend a suggestion to the user based on current knowledge of the game.
	   
	
3. A+ level;

	a. We process the information that is taken in and simplify the clauses and also deduce new information from the given
	   For instance, if we know player 1 has either "plum" or "ax", and player 2 showed me a "ax", 
	   then the program will retract the couldHave(1,plum,ax) clause and assert(knowncard(plum,1)), which means it will
	   deduce that player 1 must have "plum"
	   Other simplifications are seen in the predicate "simplify" in clue.pl
	   
	b. Advanced suggestions:
		In "makeSuggestion", prolog will compute which suspect,weapon, and room appears most often in couldHave clauses
		then it will recommend those cards as suggestions that the user should make.
		The reason is we will then be able to reduce the couldHave clauses further.
		This implementation is done in clue.pl : countCouldHave, max, and get_most_could_have_p(..),get_most_could_have_w(..),get_most_could_have_r(..)
	
	c. Additional functionalities for ui:
		see top of ui.pl
		
	d. Nontrivial process of deducing the hidden cards:
		See updateGoalCard in clue.pl
		