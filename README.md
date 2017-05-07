# Bayesian Probability: Paper, Rock, Scissors Psychic
Paper, Rock, Scissors is an ancient game that exists in many countries under many different names. While game theorists showed that everyone playing the game rationally should play as randomly as possible (select paper, rock, and scissors each with 1/3 probability), people are terrible random number generators. If a person makes a sequence of random numbers, then conditioning a number in the sequence based on a few of the past numbers often reveals a pattern that is not random! That is, the computer can predict the next move of an opponent after watching them play for several rounds, keeping track of subsequences of choices.

# How Paper, Rock, Scissors Works
Two players select from either a paper, a rock, or a pair of scissors at the same time. They both reveal their decisions at the same time (usually through hand gestures) and a winner is decided by the chosen items:
+ Both players choosing the same item is a draw;
+ If one player chooses paper and the other rock, then paper wins;
+ If one player chooses rock and the other scissors, then rock wins;
+ If one player chooses scissors and the other paper, then scissors wins

# How Player History Works
When there is not enough known about the opponent, playing randomly is the best strategy. However, if the opponent has a pattern, then it is a good idea to choose the item that will win against the opponent’s predicted next item. This prediction becomes a probabilistic query where the opponent’s previous choices are evidence:
![alt text](https://github.com/wesleytian/roshambo-god/blob/master/images/pic1.png "pic 1")
where each Ii can be assigned paper, rock, or scissors, n is the current turn, and k is the number of turns remembered in history. For example, if k = 1, then we only consider the previous choice; if k = 2, then we consider the past two choices in order; etc.. Using Bayes’s Rule, we can actually approximate this probability and update them after each round:

![alt text](https://github.com/wesleytian/roshambo-god/blob/master/images/pic2.png "pic 2")
these can all be counted if we store the opponent’s current choice and the opponent’s past k choices; add
a pseudocount of 0.1 for all counts to avoid issues with 0. So you will need a table of 3k+1 entries for 3

![alt text](https://github.com/wesleytian/roshambo-god/blob/master/images/pic3.png "pic 3") 
and a table of 3 entries for P (In). Clearly this probability can be made more complicated since research studies found that people have strategies based on whether they won/lost the previous round, and we do not consider the computer’s choice having any affect on the opponent’s decision. For the sake of simplicity, we will just use the opponent’s history alone.

# Input
An integer argument will be given when executing via command line that specifies the value of k. The opponent’s actual choices will be given using a while loop with standard input (system.in, scanf, etc.). The opponent will provide an integer for the choice as follows:
+ 0 = Rock,
+ 1 = Paper, 
+ 2 = Scissors
You may assume that the opponent will type a valid number each time, and do not prompt the user for a number (this will mess up the autograder). The opponent can cut off standard input or kill the program to terminate the loop.

# Output
In response to each input of an opponent’s choice, the computer will select an item for standard output (print, println, printf, etc.). The item selected is determined by the the one that will yield the greatest utility, calculated by:


. When there are ties in the greatest utility, you may randomly choose one of the items. Using the same numbering as above, print the choice to standard output on its own line.

# Example
We will run the program below with k = 1. The lines will alternate between input and output, and the lack of enough observations for k at the beginning has some random answers (marked with rand). To observe the adaptation, the opponent will alternate between rock, paper, and scissors in order.
Displayed in Terminal (rand would be a number)
0
rand(0,1,2)
1
rand(0,1) 0 20 22 01 rand(0,1,2) 2 10 2
2
0

*Copied with permission from CMPSCI 383 Programming Assignment 3: The Mini-Project Smorgasbord/Buffet by Rick Freedman and Kristina Fedorenko*

