---
layout: default.md
title: "Kailash Gautham's Project Portfolio Page"
---

### Project: TaToolkit

TaToolkit is a desktop application used for teaching assistants to manage their students' personal details and other information related to their classroom. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 28 kLoC.

Given below are my contributions to the project.

* **Modified Feature**: Changed the `add` command to suit the purposes of TA Toolkit better.
  * What it does: allows the user to add a student to the list of students.
  * Justification: The original `add` command was designed for a different purpose. I modified it to suit the needs of TA Toolkit, including new student details such as a Telegram ID, GitHub ID, and ClassGroup Name.
  * Highlights: This enhancement affects existing commands and commands to be added in future. It required an in-depth analysis of design alternatives. The implementation too was challenging as it required changes to existing commands.
  * Credits: The code that I modified was part of an existing feature from the AB3 brownfield project that was forked to create the TA Toolkit.

* **Modified Feature**: Improved the `mark` command to accept attendees as well as absentees in a tutorial class.
* What it does: allows the user to mark students as present or absent in a tutorial class.
* Justification: The original `mark` command was designed to mark students as absent only. I modified it to allow marking students as present as well.
* Highlights: This enhancement required changes to the existing `mark` command and the addition of new prefixes `pre/` and `abs/`. It also required changes to the storage of student data.
* Credits: The code that I modified was part of an existing feature created by Tejas, my teammate.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-02-23&tabOpen=true&tabType=authorship&tabAuthor=kailashgautham&tabRepo=AY2324S2-CS2103T-F14-3/tp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Project management**:
  * Managed release `v1.2` and `v1.3` (2 releases) on GitHub

* **Other enhancements**:
  * Added persistence for attendance data (Pull request [\#100](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/100)
  * Updated the GUI styling for class/groups (Pull request [\#101](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/101)
  * Removed all references to the previous application's commands and terminologies (Pull request [\#106](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/106)

* **Documentation**:
  * User Guide:
    * Added documentation for the features `add`[\#39](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/39)
    * Did cosmetic tweaks to existing documentation of feature `list`: [\#74](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/159)
    * Added changes to the Java installation instructions, yellow label information, and the e-mail address field format instructions: [\#74](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/159)
  * Developer Guide:
    * Modified the links that led to the previous repository, and modified the command keywords to reflect any changes that were made in our project.

* **Community**:
  * Reviewed 46 PRs from my team members.
