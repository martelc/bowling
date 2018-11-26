

Following is a program, which, given a valid sequence of rolls for one line of American Ten-Pin Bowling, produces the total score for the game.
The following video is a good overview if you are not familiar with American Ten-Pin Bowling: [YouTube](https://www.youtube.com/watch?v=E2d8PizMe-8).

# American Ten-Pin Bowling Scoring Rules

- The game consists of 10 frames.
- In each frame the player has two opportunities to knock down 10 pins. The score for the frame is the total number of pins knocked down, plus bonuses for strikes and spares.
- A spare is when the player knocks down all 10 pins in two tries. The bonus for that frame is the number of pins knocked down by the next roll. So in frame 3 above, the score is 10 (the total number knocked down) plus a bonus of 5 (the number of pins knocked down on the next roll.)
- A strike is when the player knocks down all 10 pins on his first try. The bonus for that frame is the value of the next two balls rolled.
- In the tenth frame a player who rolls a spare or strike is allowed to roll the extra balls to complete the frame. However no more than three balls can be rolled in tenth frame.

More info on the rules at: [TopEndSports](www.topendsports.com/sport/tenpin/scoring.htm)

# Out Of Scope

Here are some things that the program does not do:

- We will not check for valid rolls
- We will not check for correct number of rolls and frames
- We will not provide scores for intermediate frames

# Assumptions

The following assumptions have been made.  Any behaviour outside of these assumptions may result in unintended consequences.

- Assumes that the sequence of rolls that you input are valid for the values of the rolls
- Assumes that the values of the rolls are within the maximum number of pins
- Assumes that the number of frames is valid
- Assumes you have Java 8 installed (tested on Oracle JDK 1.8.0_66)
- Assumes you have Maven 3 installed (tested on Apache Maven 3.3.9)

# Running The Application

* To compile the application:

        mvn compile

* To test the application:

        mvn test

* To run the application:

        mvn exec:java
