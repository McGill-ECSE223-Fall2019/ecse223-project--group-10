Feature: Identify if game won
  As a player, I want the Quoridor app to identify when a game is won by one of the players 
  (e.g. I reached target area or opponent�s time is up), and the game should be stopped immediately.

  Background: 
    Given The game is running

  Scenario Outline: Player is on the middle of the board
    Given Player "<player>" has just completed his move
    And The new position of "<player>" is <row>:<col>
    And The clock of "<player>" is more than zero
    When Checking of game result is initated
    Then Game result shall be "<result>"

    Examples: 
      | player | row | col | result  |
      | white  |   9 |   5 | Running |
      | white  |   8 |   5 | Running |
      | black  |   2 |   4 | Running |
      | black  |   1 |   4 | Running |

  Scenario Outline: Player reaches target area
    Given Player "<player>" has just completed his move
    And The new position of "<player>" is <row>:<col>
    And The clock of "<player>" is more than zero
    When Checking of game result is initated
    Then Game result shall be "<result>"
    And The game shall no longer be running

    Examples: 
      | player | row | col | result   |
      | white  |   1 |   4 | WhiteWon |
      | black  |   9 |   3 | BlackWon |

  Scenario Outline: Player's time is exceeded
    Given The player to move is "<player>"
    When The clock of "<player>" counts down to zero
    Then Game result shall be "<result>"
    And The game shall no longer be running

    Examples: 
      | player | result   |
      | white  | BlackWon |
      | black  | WhiteWon |
