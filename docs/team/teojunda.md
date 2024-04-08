---
layout: default.md
title: "Axel's Project Portfolio Page"
---

### Project: TaToolkit Level 3

TaToolkit is a desktop application used for teaching assistants to manage their students' personal details and other information related to their classroom. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 28 kLoC.

Given below are my contributions to the project.

* **Modified Feature**: Changed the `editCommand` to suit the purposes of TA Toolkit better.
    * What it does: allows the user to modify the fields in a student contact (Class group, Email, Phone number, Telegram, Github).
    * Justification: This feature improves the product significantly because a user can update student contact information to be correct.
    * Highlights: This enhancement affects existing commands. It also had to fit into the wider design architecture to ensure it worked with other commands like the `viewCommand`.
    * Credits: The code was part of an existing feature from the AB3 brownfield project.

* **New Feature**: Added a `deleteNoteCommand` to delete notes from a student contact.
  * What it does: allows the user to delete notes from a student contact.
  * Justification: This feature improves the product significantly because a user can update delete irrelevant notes to keep only information that is important.
  * Highlights: This enhancement affects existing commands like the `addNoteCommand`. It required a good design approach to ensure it worked well with the various other commands without any bugs.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s2.github.io/tp-dashboard/?search=f14&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-02-23&tabOpen=true&tabType=authorship&tabAuthor=teojunda&tabRepo=AY2324S2-CS2103T-F14-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Project management**:
    * Integrated CodeCov into project repository
    * Set up MarkBind project website

* **Other enhancements**:
    * Implemented sorted order of contacts in TA Toolkit (Pull request [\#43](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/43))
    * Ensured side panel display would correct update after every relevant command (Pull request [\#65](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/65/files))

* **Documentation**:
    * User Guide:
        * Added documentation for the feature `MarkCommand` (Pull request [\#118](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/118))
        * Added introduction, interface walkthrough, parameter guidelines, example images, and glossary (Pull request [\#115](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/115))
    * Developer Guide:
        * Added use cases and user stories (Pull requests [\#30](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/30), [\#37](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/37))
        * Included implementation details of Edit Command (Pull request [\#57](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/57))

* **Community**:
    * 36 PRs reviewed (examples: [\#38](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/38), [\#55](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/55), [\#112](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/112))
    * Reported bugs and suggestions for other teams in the class (examples: [1](https://github.com/AY2324S2-CS2103T-T16-1/tp/issues/157), [2](https://github.com/teojunda/ped/issues/11), [3](https://github.com/teojunda/ped/issues/4))

