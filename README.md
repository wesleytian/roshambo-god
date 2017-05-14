```
javac roshambo.java
```
```
java roshambo 1
```
```
java roshambo 3
```

# Input
An integer argument will be given when executing via command line that specifies the value of *k*. The opponent’s actual choices will be given using a while loop with standard input (system.in, scanf, etc.). The opponent will provide an integer for the choice as follows:
* 0 = Rock
* 1 = Paper
* 2 = Scissors

# Output
In response to each input of an opponent’s choice, the computer will select an item for standard output (print, println, printf, etc.). The item selected is determined by the the one that will yield the greatest utility, calculated by:
<p align="center">
  <img src="https://github.com/wesleytian/roshambo-god/blob/master/images/pic3.png">
</p>

# Player History

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


