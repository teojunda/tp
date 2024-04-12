---
layout: default.md
title: "Yu Soon's Project Portfolio Page"
---

### Project: TaToolkit Level 3

The TaToolkit - Level 3 is a sophisticated desktop application designed to aid in the instruction of Software Engineering principles. It offers a command-line interface (CLI) for efficient task handling and a graphical user interface (GUI) using JavaFX for an enhanced user experience. Comprising approximately 10,000 lines of Java code, it serves as a comprehensive tool for educational facilitators, especially teaching assistants (TAs), to manage student interactions more effectively.

### Technical Skills
 * Java Programming: Proficiency in Java to implement complex functionalities.
 * JavaFX: Utilization of JavaFX for GUI development.
 * Software Engineering Principles: Applied principles like OOP, MVC, and CI/CD during development.

Given below are my contributions to the project.

* **New Feature**: Added the ability to add/delete notes for each student in TaToolkit.
  * What it does: Allows the user to add customized notes to each student of their choice.
  * Justification: This feature significantly improves the product, as it helps ease the mental burden of TAs in CS2100. It allows them to add unique notes to each student, reminding themselves of what they wish to take note of regarding the student.
  * Highlights: This enhancement affects existing commands. It also had to fit into the wider design architecture to ensure it worked with other commands like the `viewCommand`.

* **New Feature**: Added the ability to view students in TaToolkit.
  * What it does: Allows the user to view a student of their choice stored within TaToolkit.
  * Justification: This feature significantly improves the product as it allows TAs to view and retrieve their students' details, such as Telegram, email, or GitHub IDs, should they need to contact them quickly.
  * Highlights: This enhancement affects existing commands. It also had to fit into the wider design architecture to ensure it worked with other commands like the `editCommand`.

### Challenges and Problem-Solving
 * Integration with Existing Systems: Ensured new features were compatible with existing commands and conformed to the broader system architecture.
 * User-Centric Design: Focused on ease of use for the TAs, aligning with the mental model of users to minimize cognitive load.

### Results and Impact
 * Improved the mental workload of TAs by streamlining the note-taking process.
 * Enhanced the accessibility of student information, leading to quicker and more efficient TA-student communication.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s2.github.io/tp-dashboard/?search=YuSoonZ&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-02-23)

* **Documentation**:
  * User Guide:
    * Added documentation for the features `view`, `addnote` and `deletenote` [\#55](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/55), [\#111](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/111)
    * Did tweaks to existing documentation of features `list`: [\#55](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/55)
  * Developer Guide:
    * Added implementation details of the `view` feature [\#60](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/60)

* **Community**:
  * Contributed to forum discussion (example: [\#153](https://github.com/nus-cs2103-AY2324S2/forum/issues/153))
