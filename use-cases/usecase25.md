# USE CASE: 25 Produce a Report showing The population of people, people living in cities, and people not living in cities in each country.

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *Population  Researcher* I want *to produce a report showing The population of people, people living in cities, and people not living in cities in each country.* so that *I can support population reporting of the organisation.*

### Scope

Population Report

### Level

Primary task.

### Preconditions

Connect to 'world' database.

### Success End Condition

A report is available for the Population Researcher.

### Failed End Condition

No report is produced.

### Primary Actor

Population Researcher.

### Trigger

A request for a Population Report is given.

## MAIN SUCCESS SCENARIO

1. Population Researcher requests report with the population of people, people living in cities, and people not living in cities in each country.
2. Program finds all countries stored in 'world' database.
3. Program goes through each country's population compares how many are living in cities versus how many are not.
4. Program provides report to Population Researcher.

## EXTENSIONS

None.

## SUB-VARIATIONS

None.

## SCHEDULE

**30/04/2021**: Release 1.0