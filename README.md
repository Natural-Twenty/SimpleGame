# Project

## Aims

* Appreciate issues in user interface design

* Learn practical aspects of graphical user interface programming

* Learn more about the Java class libraries

* Learn the application of design patterns.

## Set time limitation: 1 month (4 weeks)

---

## Overview

You have received a request from a client for an application for the playing of dungeon-style puzzles. You will follow an agile development process to design and implement a desktop Java application that satisfies the requirements of the client (see below). The final piece of software you deliver is expected to be of professional quality, user-friendly, and demonstrate the knowledge and skills you have acquired in this course.

## Project setup

**NOTE**: For the first milestone, it is not necessary to set up the project in VSCode.

Because this project uses JavaFX, to use it on a non-CSE computer, additional setup will be needed.

The repository provided has already been setup to run the starter-game, and tested on a CSE machine (using VLAB). To play the starter game, clone the repository onto your CSE machine on VLAB, open the root directory of the repository in VSCode, and click the *"Run"* link above the *main* method of **DungeonApplication.java**

Note that this works because *lib/symlink_javafx* is a symbolic link to a copy of JavaFX in the cs2511 class account. To make this work on a non-CSE computer, you will need to delete the *symlink_javafx* symbolic link, then download and unzip the latest version of the JavaFX JDK for Java 11 for your Operating System (taking into account if you have a 64 or 32 bit machine), and transfer the contents of the *lib* folder inside the JDK download into the *lib* folder on your cloned repository. You will also need to change the *launch.json* file to refer to **"./lib"** instead of **./lib/symlink_javafx** in the *"vmArgs"* configuration (note these modifications were tested on Windows 10).

You may also need to copy the contents of the *bin* folder in the unzipped JavaFX JDK download into a *bin* folder under the root directory of your cloned repository (e.g. for Windows).

The following version of the JavaFX JDK is recommended if you choose to run it on your computer, since it is the same version as on the CSE machine:

https://gluonhq.com/products/javafx/

Note that if you deviate from this precise directory structure, you may need to modify the VSCode configuration in *.vscode/launch.json* to be able to run the game in VSCode.

If the steps in the above instructions worked, you should be able to run the starter code.

**IMPORTANT**: Please do not push the contents of the *lib* or *bin* folders to your Gitlab repository. This is very likely to push you over the memory limits for the milestone 2 and 3 submissions.

## Preliminary client requirements

The client desires an application that lets the user move a player around a dungeon and try to overcome various challenges in order to "complete" the dungeon by reaching some goal. The simplest form of such a puzzle is a maze, where the player must find their way from the starting point to the exit.

![Maze][maze]

More advanced puzzles may contain things like boulders that need to be pushed onto floor switches,

![Boulders][boulders]

enemies that need to be fought with weapons, or collectables like potions and treasure.

![Advanced dungeon][advanced]

### Dungeon layout

To be specific, the layout of each dungeon is defined by a grid of squares, each of which may contain one or more entities. The different types of entities are as follows:

| Entity               | Example | Description                             |
| ------               | ------- | --------------------------------------- |
| Player               | ![Player][player] | Can be moved up, down, left, and right into adjacent squares, provided another entity doesn't stop them (e.g. a wall). |
| Wall                 | ![Wall][wall] | Blocks the movement of the player, enemies and boulders. |
| Exit                 | ![Exit][exit] | If the player goes through it the puzzle is complete.  |
| Treasure             | ![Treasure][treasure] | Can be collected by the player. |
| Door                 | ![Door][door_open] ![Door][door_closed] | Exists in conjunction with a single key that can open it. If the player holds the key, they can open the door by moving through it. Once open it remains so. The client will be satisfied if dungeons can be made with up to 3 doors. |
| Key                  | ![Key][key] | Can be picked up by the player when they move into the square containing it. The player can carry only one key at a time, and only one door has a lock that fits the key. It disappears once it is used to open its corresponding door. |
| Boulder              | ![Boulder][boulder] | Acts like a wall in most cases. The only difference being that it can be pushed by the player into adjacent squares. The player is only strong enough to push **one** boulder at a time. |
| Floor switch         | ![Floor switch][switch] | Switches behave like empty squares, so other entities can appear on top of them. When a boulder is pushed onto a floor switch, it is triggered. Pushing a boulder off the floor switch untriggers it. |
| Portal               | ![Portal][portal] | Teleports entities to a corresponding portal. |
| Enemy                | ![Enemy][enemy] | Constantly moves toward the player, stopping if it cannot move any closer. The player dies upon collision with an enemy. |
| Sword                | ![Sword][sword] | This can be picked up the player and used to kill enemies. Only one sword can be carried at once. Each sword is only capable of 5 hits and disappears after that. One hit of the sword is sufficient to destroy any enemy. |
| Invincibility potion | ![Invincibility][invincibility] | If the player picks this up they become invincible to enemies. Colliding with an enemy should result in their immediate destruction. Because of this, all enemies will run away from the player when they are invincible. The effect of the potion only lasts a limited time. |

### Goals

In addition to its layout, each dungeon also has a goal that defines what must be achieved by the player for the dungeon to be considered complete. Basic goals are:

* Getting to an exit.
* Destroying all enemies.
* Having a boulder on all floor switches.
* Collecting all treasure.

More complex goals can be built by logically composing goals. For example,

* Destroying all enemies AND getting to an exit
* Collecting all treasure OR having a boulder on all floor switches
* Getting to an exit AND (destroying all enemies OR collecting all treasure)

If getting to an exit is one of a conjunction of conditions, it must be done last. For example, if the condition is to destroy all enemies AND get to an exit, the player must destroy the enemies *then* get to the exit.

### Input

Your application will read from a JSON file containing a complete specification of the dungeon (the initial position of entities, goal, etc.). Example dungeons are included in the `dungeons` directory and the starter code contains an incomplete dungeon loader.

The dungeon files have the following format:

> { "width": *width in squares*, "height": *height in squares*, "entities": *list of entities*, "goal-condition": *goal condition* }

Each entity in the list of entities is structured as:

> { "type": *type*, "x": *x-position*, "y": *y-position* }

where *type* is one of

> ["player", "wall", "exit", "treasure", "door", "key", "boulder", "switch", "portal", "enemy", "sword", "invincibility"]

The `door`, `key`, and `portal` entities include an additional field `id` containing a number. Keys open the door with the same `id` (e.g. the key with `id` 0 opens the door with `id` 0). Portals will teleport entities to the **one** other portal with the same ID.

The goal condition is a JSON object representing the logical statement that defines the goal. Basic goals are:

> { "goal": *goal* }

where *goal* is one of

> ["exit", "enemies", "boulders", "treasure"]

In the case of a more complex goal, *goal* is the logical operator and the additional *subgoals* field is a JSON array containing subgoals, which themselves are goal conditions. For example,

```JSON
{ "goal": "AND", "subgoals":
  [ { "goal": "exit" },
    { "goal": "OR", "subgoals":
      [ {"goal": "enemies" },
        {"goal": "treasure" }
      ]
    }
  ]
}
```

Note that the same basic goal *can* appear more than once in a statement.

You can extend this format to include additional information if you wish, but your application should still work with files in the original format.

### User interface

The UI component of this project will be implemented in JavaFX. The starter code contains a very basic UI showing how a player can be moved around with the arrow keys, but it is missing many features (the player can walk through walls for one).

The client has given you free reign over the visual design of the program. Included in the starter code are some example assets, but you are free to use different ones. You can find them elsewhere or even create your own. The examples above came from [here](http://opengameart.org).

## Requirement analysis (Milestone 1)

For this initial milestone, you are to model the requirements of the client as user stories on the issue board in GitLab. You will demonstrate these user stories to your tutor in the Week 7 lab, where they will ask you questions and assign marks based on your answers to these questions (and also through offline marking when appropriate).

It's important that you and your partner meet and collaborate early in order to develop a shared understanding of the project and how you intend to approach it. In developing the stories you will need to consider the requirements as given by the client in this document, but also make your own judgements about the expectations of potential users. Epic stories should be broken down into user stories and each story should have its own card.

The default columns that GitLab provides are sufficient for this project.

You are expected to produce:

1. High-level epic stories from the problem statement. Each epic should have its own card/issue and a corresponding tag used to mark user stories that fall under it.
2. User stories, each containing:
   * a short description of the feature based on the Role-Goal-Benefit (or Role-Feature-Reason) template (Refer to the RGB model from COMP1531 if unsure)
   * an estimate for the implementation of the user story in user story points (e.g. 4 points).
   * a tag indicating the priority
   * acceptance criteria for each user story as a checklist in the issue (Refer to material from COMP1531 if unsure)

As you progress through the rest of the project, you will keep your board and issues up to date: checking off acceptance criteria that have been satisfied and moving stories from **To Do** into **Doing** and finally into **Closed**.

**IMPORTANT**: You must add your user stories to the Gitlab issue board for them to be considered. Work in a different format (such as a PDF or word document) will not be assessed during marking.

## Domain modelling and backend implementation (Milestone 2)

Based on your requirements analysis, and all feedback you have received, you will produce a domain model for the backend component of your project in the form of a conceptual UML class diagram, implement it in Java and write JUnit tests to test its functionality.

In deciding on your design and writing your implementation, you should follow the practices and design principles covered in the course. You are expected to apply at least 3 of the design patterns covered in the course. It is up to you where they are applied, but you will be expected to justify how and why you used them to your tutor during demonstration.

Your class diagram only needs to be conceptual (showing the general structure of the classes and their relationship), but it needs to be consistent with the code and clearly indicate where you're using design patterns (use labels if necessary).

Your JUnit tests should be rigorous to ensure your backend functions as expected. In addition to basic unit tests, you need to have tests based on your acceptance criteria.

These JUnit tests should be placed into the "src/test" directory, using package "test". The dryrun will check your tests pass. It is important you follow this structure, since we will run automated coverage checking on your program.

In the week 8 lab, your tutor will ask you questions, and assign marks based on your answers to these questions (and also through offline marking when appropriate).

### Running coverage checking

To run coverage checking, on a CSE machine in the root directory of your repository:

```bash
$ gradle test -b test.gradle
```

The coverage checking report will be in: *build/reports/jacoco/test/html/index.html*

The test report will be in: *build/reports/tests/test/index.html*

Your tutor will receive a report generated using this command, generated from the master branch, from your latest submission as of the deadline.

Please do not push the *build* directory to your Gitlab repository. The gitignore has been configured to ensure this in the most recent version of the *final_project* repository.

## UI design and extensions (Milestone 3)

For this milestone you are to design and implement the user interface component of the application. A very basic UI can be built with minimal changes to the starter code, so that is where you should start. Fancier UI features can be added once you have something that is at least usable. You should apply the ideas from user-centric design and consider the usability heuristics covered in the lectures.

Additionally, for this milestone, you also have the chance to extend the project with your own ideas. Note that, to get high marks for these extensions, you will need to consider how they impact the user. **Extensions that are technically complex, but do not provide the user with any real benefit are not considered good extensions**. You can, and should, create additional user stories to model the requirements of these extensions. Possible extensions include but are not limited to, multiplayer, different sorts of enemies, new weapons, and animated movement.

This final milestone will be a culmination of all the work done in the previous milestones. You have the opportunity to improve on your design based on feedback from your tutor. Marking of the design will be harsher for the final milestone as you have already had the opportunity to receive feedback.

In the week 10 lab, your tutor will ask you questions, and assign marks based on your answers to these questions (and also through offline marking when appropriate).


## Hints

* The first two milestones do not require a working UI. First determine how you are going to model a dungeon and its entities before considering the UI. A well designed back-end will require minimal change to connect to the UI.
* Up until the JavaFX has been covered, you may find some of the starter code to be hard to understand. For the backend, you can either ignore the starter code completely, or just look at the `Dungeon`, `Entity`, `Player` and `Wall` classes.
* The starter code uses the observer pattern to ensure the frontend and backend are in a consistent state and that they are not tightly coupled. It would be advisable to do the same for the changes you will make.
* The majority of marks available (see below) are for having a well designed application that meets the requirements. Avoid adding extra complexity and extensions till you have something that meets the most basic requirements.
* You will need to engage in research to learn parts of JavaFX yourself. This is expected, and an important learning process mirroring real-world development. You may also have to research how to configure VSCode to work with JavaFX to make some functionality work - this is similarly an important learning process. You should not expect to be taught all of the necessary JavaFX functionality.
* For JUnit in VSCode - it can be useful to run CTRL-SHIFT-P and run "clean the Java language server workspace" if you are having issues with packages/tests recognition.
* For JUnit tests - for this to work in VSCode, it is important to make the test classes public and named after the file they are inside.
* Useful JUnit5 documentation/tutorial:
https://junit.org/junit5/docs/current/api/
https://junit.org/junit5/docs/current/user-guide/

## Important Technical Notes

### Submission sizes

Maximum submission sizes apply (for milestones 2 and 3, since a submit command is used). For both milestones 2 and 3, a maximum repository size of 4MB applies. Exceeding this limit may result in you being unable to submit. Please email your tutor if you are unable to submit due to the maximum submission size being exceeded.

A likely reason for you exceeding the memory limit is uploading Java class files, or JavaFX binaries in the *lib* or *bin* folders. You should not push these to your Gitlab repository - particularly since it is configured to your Operating System, which might not match your partner's configuration or the CSE machine. The **.gitignore** file has been configured to prevent pushing the contents of your *lib* or *bin* folders - please do not adjust this. Also remember that you should be making the submission work on the CSE machine, so there is no reason to push your JavaFX binaries.

### Directory Structures

Please adhere to the provided layout of the directories, and filenames and filepaths of the starter files. Modifying this may break tests we run on your submission and may result in loss of marks.

You may add additional Java files with new Java classes in the *src/unsw/dungeon* folder.

### Milestone 1

You should have all your user stories entered into the issue board on your GitLab repository. You may continue to use the board between the deadline and your tutor's assessment, but they will be looking at the dates issues were modified to make sure you did the work that was required of you prior to the deadline.

### Milestone 2

Your UML class diagram should be a PDF file at the root of your repository named `design.pdf`.

[player]:        images/human_new.png
[wall]:          images/brick_brown_0.png
[exit]:          images/exit.png
[door_open]:     images/open_door.png
[door_closed]:   images/closed_door.png
[key]:           images/key.png
[boulder]:       images/boulder.png
[switch]:        images/pressure_plate.png
[portal]:        images/portal.png
[enemy]:         images/deep_elf_master_archer.png
[sword]:         images/greatsword_1_new.png
[invincibility]: images/brilliant_blue_new.png
[treasure]:      images/gold_pile.png

[maze]:          examples/maze.png
[boulders]:      examples/boulders.png
[advanced]:      examples/advanced.png

