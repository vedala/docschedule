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

### Features in the Current Version ###
* The application is currently in early stages of development, so very
  basic functionality is available.
* Upon login, schedule for 6 weeks is displayed (starting from current week).
* Schedule page can be accessed only by authenticated users.

### Planned Features ###
* Ability to scroll schedule forward and backward, so users can view
more than six weeks' worth of schedule.
* Ability to generate schedule. This feature will enable group director
  to create schedule for a period of time (usually a few months) based
  on pre-set rules. The pre-set rules are similar to rules described in
  the Basic Scenario above. This feature will be authorized only for
  the director.
* Ability to modify rules. Provide interface to enable the director
  to modify rules. This feature only authorized for the director.
* Add SSL support.
* Ability to Sign-Up users from website.
* Enhance application to support multiple tenants.
