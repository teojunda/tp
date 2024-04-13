---
layout: default.md
title: "Ivan's Project Portfolio Page"
---

# Project: TaToolkit

TaToolkit is a desktop application used for teaching assistants to manage their students' personal details and other information related to their classroom. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 28 kLoC.

Given below are my contributions to the project.

## Dynamic Side Panel Integration
The introduction of the side panel within the main window is a cornerstone of our user interface, designed to provide detailed insights contextually based on user actions. It dynamically toggles between displaying individual student details and a comprehensive attendance view, offering tailored information that enhances decision-making and interaction efficiency.

### View Person Details
When the `view` command is executed, the side panel transitions to showcase detailed information about a selected student, including their name, class group, contact number, Telegram handle and Github username. This feature ensures that users have immediate access to comprehensive student profiles at their fingertips.

Utilizing JavaFX's ObservableList and property bindings, I ensured that any changes to a student's details are immediately propagated to the UI. This is crucial for maintaining data accuracy, especially in scenarios where student information might be modified through other parts of the application such as the `uc` update person command and the `dn` delete note command.

### Attendance View
In response to the `ls` or `ls CLASS_GROUP` list command, the side panel morphs to present an attendance summary, highlighting the weekly attendance record of all the students in that list. This transformation allows TAs to quickly view the attendance of a particular class or an entire cohort, all within the same intuitive interface.

The backbone of this feature is the innovative use of `ObservableList` within the `ModelManager` class, coupled with event listeners in `SidePanel.java`. This setup ensures that the attendance list in the side panel is updated in real time in response to any attendance marking actions performed elsewhere in the application. This real-time update mechanism safeguards against stale or inaccurate data being displayed to the user.

## Intelligent Command Recognition

By leveraging the `lastSidePanelCommand`, I've introduced a mechanism that retains context on the last executed side panel related command (be it `None`, `ViewCommand`, or `ListCommand`). This innovative approach enables the side panel to intelligently display relevant information (individual details or attendance summary) based on the user's last interaction, promoting a fluid, contextual user experience.

## Adherence to MVC Architecture

The project's design strictly follows the Model-View-Controller (MVC) architecture, ensuring a clear separation of concerns. This architectural choice enhances maintainability, facilitates easier updates, and allows for more straightforward debugging. By compartmentalizing the application logic, UI, and data model, I've ensured that each component can evolve independently, promoting scalability and flexibility.

## Other Links
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-02-23&tabOpen=true&tabType=authorship&tabAuthor=hiivan&tabRepo=AY2324S2-CS2103T-F14-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Enhancements to existing features**:
  * Updated the NordTheme.css to include more UI customizations (Pull requests [\#112](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/112))
  * Wrote additional tests for MarkCommand (Pull requests [\#114](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/114))

* **Documentation**:
  * User Guide:
    * Updated steps on how to reproduce side panel
  * Developer Guide:
    * Updated UML diagrams for UIClassDiagram and View Command to reflect the new implementation of a side panel [\#97](https://github.com/AY2324S2-CS2103T-F14-3/tp/pull/97)

* **Community**:
  * 29 PRs reviewed: ([link](https://github.com/AY2324S2-CS2103T-F14-3/tp/pulls?q=is%3Apr+reviewed-by%3Ahiivan+-author%3Ahiivan+))
