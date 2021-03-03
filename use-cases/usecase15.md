# USE CASE: 15 Produce a Report showing The top 'N' populated cities in a country where 'N' is provided by the user.

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *Population  Researcher* I want *to produce a report showing The top 'N' populated cities in a country where 'N' is provided by the user.* so that *I can support population reporting of the organisation.*

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

1. Population Researcher requests report with the top 'N' populated cities in a country where 'N' is provided by the user.
2. Program finds all cities stored in 'world' database that are in specified country.
3. Program goes through each city's population and sorts from largest to smallest. Provides the top 'N' cities.
4. Program provides report to Population Researcher.

## EXTENSIONS

3. **'N' is out of bounds**:
    1. Program informs researcher that 'N' is invaild.

## SUB-VARIATIONS

None.

## SCHEDULE

**30/04/2021**: Release 1.0