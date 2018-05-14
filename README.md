# The Elevator Excercise

Resolution for Elevator Excercise

## Getting Started

### Prerequisites

1. java 8
2. maven 3

### Introduction

Implementation of elevator excercice proposed for an interview.
This solution was designed considering OOP concepts and some common patterns like strategy and observer. 

## Scope

In order to simplify the excercise and deliver an excercise that adds value in first version, I have done some simplifications that can be completed in next iterations

1. IoC container is not included in this version
2. KeyCard Access system was implemented using a simple and dummy mechanism
   - Allowed keys are 902fbdd2b1df0c4f70b4a5d23525e932, c4fd1ef4041f00039def6df0331841de, d8c072a439c55274f145eae9f6583751 
3. KeyCard Reader was implemented using a simple and dummy mechanism
   - Requires manual user input
4. Logs are being done using primitive java console

## Running the tests
```
mvn clean test
```

### Demo

There is a class called ElevatorSimulatorDemo.java, on that class you will be able to find scenarios and a simple non-interactive demo for validating most common scenarios
