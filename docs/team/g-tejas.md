---
  layout: default.md
  title: "Tejas Garrepally's Project Portfolio Page"
---

### Project: TaToolkit

TaToolkit is a desktop application used for teaching assistants to manage their students' personal details and other information related to their classroom. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 28 kLoC.
Given below are my contributions to the project.

* **New Feature**: Added the ability to mark the attendance of students in the TaToolkit
  * What it does: Allows the user to mark the attendance of students in the class. Attendance is marked negatively, but students can be marked as present with the `pre/` prefix
  * Justification: This feature is crucial for the product as it allows TAs to keep track of students' attendance in the class, with a simple command. 
  * Highlights: This feature is the hallmark of the TaToolkit, and allows TAs to get insights into the students' attendance patterns from the side panel, either by individual students or by class groups.
  
* **Modified Feature**: Added the ability to list specific classes in the TaToolkit with class groups
  * What it does: Allows the user to list students by one or more class groups, which is handled with prefix matching. 
  * Justification: This feature is crucial as it allows TAs to quickly find class groups if they manage multiple classes.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s2.github.io/tp-dashboard/?search=g-tejas&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-02-23&tabOpen=true&tabType=authorship&tabAuthor=g-tejas&tabRepo=AY2324S2-CS2103T-F14-3/tp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Enhancements to existing features**:
  * Updated the GUI color scheme (Pull requests [\#108](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/108))
  * Wrote additional tests for new attendance tracking feature and new list classes feature (Pull requests [\#51](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/51))

* **Documentation**:
  * User Guide:
    * Updated the UG to remove traces of AB3
    * Updated the UG with new command words
  * Developer Guide:
    * Updated UML diagrams to reflect the latest changes in the codebase [\#53](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/53)

* **Community**:
  * 40 PRs reviewed (with non-trivial review comments): [\#55](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/55), [\#43](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/43)
  * Contributed to forum discussions (examples: [408](https://github.com/nus-cs2103-AY2324S2/forum/issues/408))

* **Tools**:
  * Integrated a third party workflow (Telegram bot) to the project ([\#34](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/34))

