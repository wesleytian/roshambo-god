### RoShamBo

While game theorists showed that everyone playing the game rationally should play as randomly as possible (select paper, rock, and scissors each with 1/3 probability), people are terrible random number generators. If a person makes a sequence of random numbers, then conditioning a number in the sequence based on a few of the past numbers often reveals a pattern that is not random! That is, the computer can predict the next move of an opponent after watching them play for several rounds, keeping track of subsequences of choices.

https://en.wikipedia.org/wiki/Rock–paper–scissors

#### Myth: Rock-Paper-Scissors is a trivial game.
Sure, the game has a simple optimal strategy (choose a move uniformly at random), but that has little bearing on the problem at hand. First, not all the players are optimal. This changes everything. To win a tournament where some players are known to be sub-optimal, it is absolutely essential to try to detect patterns and tendencies in the play of the opponent, and then employ an appropriate counter-strategy. A match consists of several turns, and this changes the nature of the game, as was seen in the famous Iterated Prisoner's Dilemma problem.

RoShamBo (and its even simpler cousin, the Penny-Matching game) is an example of a pure prediction game. The difficulty lies in everything else that is associated with opponent modeling, or trying to outwit an adversary.

There is a lot of theory that can be brought to bear on the problem, including but not limited to advanced game theory (the "best-response dynamic in fictitious play"), prediction models, information theory, statistics, encryption, and even philosophical meta-theory.

So what is to be gained by playing this silly little kid's game? Many other problems deal with some form of context analysis or meta-reasoning (thinking about thinking about ...). To quote Jason Hutchens, author of MegaHAL, which was probably the best pattern detector in the tournament:

*A good predictive algorithm will be able to play RoShamBo very well, without being explicitly programmed to solve that task. A few applications of such algorithms are data compression, grammatical inference, speech recognition, data mining, natural language understanding, syntactic pattern recognition, speech segmentation, machine translation, text generation, spelling correction, author identification, email classification, image recognition, stock market analysis, finding structure in data, analysis of DNA sequences, analysis of music, input methods for disabled users, and playing RoShamBo!*

#### Myth: Random (Optimal) can't be beat.

The optimal strategy won't lose a match by a statistically significant margin, but it also won't win a match, regardless of how predictable the opponent is. Try winning a chess tournament by drawing every game!

Moreover, the statement isn't even true in a more fundamental sense. Opportunistic strategies can be theoretically better, having positive expectation under more realistic assumptions. People interested in advanced game theory may enjoy the recent book "The Theory of Learning in Games" by Fudenberg and Levine.

http://webdocs.cs.ualberta.ca/~darse/rsb-results1.html

### Input
```
javac roshambo.java
```
An integer argument will be given when executing via command line that specifies the value of *k*. 
```
java roshambo 1
```
```
java roshambo 3
```
The opponent’s actual choices will be given using a while loop with standard input (system.in, scanf, etc.). The opponent will provide an integer for the choice as follows:

* 0 = Rock
* 1 = Paper
* 2 = Scissors

### Player History

When there is not enough known about the opponent, playing randomly is the best strategy. However, if the opponent has a pattern, then it is a good idea to choose the item that will win against the opponent’s predicted next item. This prediction becomes a probabilistic query where the opponent’s previous choices are evidence:

<p align="center">
  <img src="https://github.com/wesleytian/roshambo-god/blob/master/images/pic1.png">
</p>

where each *Ii* can be assigned paper, rock, or scissors, n is the current turn, and k is the number of turns remembered in history. For example, if *k* = 1, then we only consider the previous choice; if *k* = 2, then we consider the past two choices in order; etc.. Using Bayes Rule, we can actually approximate this probability and update them after each round:

<p align="center">
  <img src="https://github.com/wesleytian/roshambo-god/blob/master/images/pic2.png">
</p>

these can all be counted if we store the opponent’s current choice and the opponent’s past *k* choices; add
a pseudocount of 0.1 for all counts to avoid issues with 0. So you will need a table of 3k+1 entries for P (*In−1, In−2, . . . , In−k |In* ) and a table of 3 entries for P (*In*). Clearly this probability can be made more complicated since research studies found that people have strategies based on whether they won/lost the previous round, and we do not consider the computer’s choice having any affect on the opponent’s decision. For the sake of simplicity, we will just use the opponent’s history alone.

### Output
In response to each input of an opponent’s choice, the computer will select an item for standard output (print, println, printf, etc.). The item selected is determined by the the one that will yield the greatest utility, calculated by:
<p align="center">
  <img src="https://github.com/wesleytian/roshambo-god/blob/master/images/pic3.png">
</p>
