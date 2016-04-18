# DocSchedule #

DocSchedule application generates schedule for hospitalist
physician groups. A hospitalist physician group usually
provides 24x7x365 care to admitted patients at a hospital.

### A Basic Scenario ###

Following scenario will help understand the application better:

* The hospitalist group consists of eight physicians.
* The group is divided into two teams of four physicians
  each (Let's call the teams Team-A and Team-B).
* Each team works 7 days straight starting on a Tuesday. During
  the 7 days that Team-A is working, Team-B does not work.
* For example, Team-A works from 6-July-2015 to 13-July-2015. Then,
  Team-B works from 14-July-2015 to 20-July-2015. And so on.
* On any given day, four physicians work at the hospital,
  three on Day shift and one on Night shift.
* Day shift is 7am to 7pm. Night shift is 7pm to 7am.

