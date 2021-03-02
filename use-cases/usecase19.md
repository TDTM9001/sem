# USE CASE: 19 Produce a Report showing All the capital cities in a region organised by largest population to smallest.

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *Population  Researcher* I want *to produce a report showing All the capital cities in a region organised by largest population to smallest.* so that *I can support population reporting of the organisation.*

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

Population Resarcher.

### Trigger

A request for a Population Report is given.

## MAIN SUCCESS SCENARIO

1. Population Researcher requests report with all the capital cities in a region organised by largest population to smallest.
2. Program finds all cities stored in 'world' database that are capitals and are in specified region.
3. Program goes through each city's population and sorts from largest to smallest.
4. Program provides report to Population Researcher.

## EXTENSIONS

None.

## SUB-VARIATIONS

None.

## SCHEDULE

**30/04/2021**: Release 1.0