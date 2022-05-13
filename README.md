# RoboRace

_**The Game**_

The factory hall is a 2-dimensional grid of spaces in which every space can only be occupied by one robot at a time. The game is played in rounds until the first robot reaches the dedicated goal. Each player (1-4) controls a robot that has a position on the board and a direction it is facing. At the beginning of the game each robot is in its dedicated starting position. Each round of the game consists of four individual actions:
Deal program cards
Plan program and select cards
Execute all programs
Revitalize all dead robots

_**Deal Program cards**_

Each player receives 7 randomly generated program cards. There are 84 program cards in total. The cards and their effect when executed are listed in the table below.


![image](https://user-images.githubusercontent.com/80646420/168396346-60a07e60-424e-42d1-ada1-1e69e331272a.png)

_**Plan program and select cards**_

Each player creates a program for her/his robot by selecting 5 out of the 7 program cards in order. These 5 cards will be executed in the order they have been selected and maneuver the robot around the factory hall.

_**Execute all programs**_

The execution of the programs is done in 5 phases. In the first phase the first card of each player in order of their priority are executed. Then the space in which each robot currently is will take an effect on the robot. These effects are executed in random order. For example, the robot of Player 1 has landed on a gear and the robot of Player 2 has landed on a conveyor belt adjacent to that gear after executing the first card. Now, the effect of the factory hall on the robots is executed in random order. Player 2 is picked first. Her/his robot is moved by the belt onto the gear pushing the robot Player 1 on another conveyor belt. Now, the effect for Player 1 is executed. The robot is now on a conveyor belt and no longer on the gear. Therefore, the conveyor belt moves the robot in an adjacent space. In the second phase the second card of each player is executed, and so on. With other words in phase i the following happens:
The i-th card of each player is executed in order of their priority.
The space each robot currently occupies takes an effect on the robot.
During the whole game only one robot can be in a given space. A robot that tries moving into a space occupied by another robot cannot move through or jump over that robot. Instead it will push that robot forward ignoring the orientation of the pushed robot.

If a robot tries to cross a side of square with a wall, it will be blocked and bump into that wall. The robot will stay in the space without completing that move instruction. This applies to both forwards and backwards movement. Notice, that a robot that is pushed and blocked by a wall will also block the pushing robot.

If a robot falls into a pit or moves outside the board it will be destroyed. This effect takes places immediately. Robots cannot, in no circumstances, cross pits.

Effects of spaces on robots do not accumulate. A robot will only be influenced by the space it is currently on. For example, if a robot executes a move card and lands on a conveyor belt, then the robot is moved by the conveyor belt into an adjacent space with a gear. This gear now will not have an effect on the robot. On the other hand, if the robot landed on the gear as a result of a card, the gear would turn the robot.

A space in the factory hall can have up to 2 different elements. Each space will have a tile (Conveyor Belt, Floor, Gear, Goal, Pit) and some spaces have an additional decoration (Pusher, Crusher). The effect of the decoration is always (if present) executed first. However, decorations do not act in every phase. The effect of the tile is only executed if there is no decoration or the decoration had no effect on the robot. For example, a robot lands after executing the third program card (Phase 3) in a space with a conveyor belt and a crusher. In order to compute the effect of this space on the robot, the crusher is considered first. This crusher is set up to act in the phases 2 and 4. Since the current phase is 3, the crusher will not effect the robot. However, the conveyor belt will move the robot in an adjacent space. If it would have been Phase 2, then the crusher would have destroyed the robot and the conveyor belt would not have acted. The effects of the elements are as follows:
Pushers push robots into an adjacent space. Pusher act in 3 out of the 5 phases.
Crushers destroy robots. Crushers act in 2 out of the 5 phases.
Conveyor belts move robots 1 space in the indicated direction.
Floors do not have any effect.
Gears rotate robots 90° in the indicated direction.
Goals end the game. The robot on that spot is the winner of the game.
Pits destroy robots.
When a robot is moved by a factory element it might be blocked by walls or it may push other robots as described above.

_**Revitalize all dead robots**_

All dead robots become alive again and are placed into their dedicated starting position. If this position is occupied by another robot, the robot will be placed into the first free starting position on the board.
