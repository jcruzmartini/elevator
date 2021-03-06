# The Elevator Excercise

Resolution for Elevator Excercise

## Getting Started

### Prerequisites

1. java 8
2. maven 3

### Introduction

Implementation of elevator excercice proposed for an interview.
This solution was designed considering OOP concepts and some common patterns like strategy and observer. 

## Main Entities
- Elevator
- ElevatorState
- ElevatorRequest
- Floor
- Cabin
- Building
- KeyCardRequest

## Main Components

- *Managers*: Thread for handling new requests. Having this components will allow us to use different managers, thus we can use different strategies for attending new incoming requests. 
Currentyly we have an implementation that is using a direction based strategy.  But a based time strategy can be implemented later using same base components

- *Handlers*: Component for processing elevator requests. Having this components will allow us to implement different handlers. In this version we have 2 different implementation for handlers:
   * ElevatorSimpleRequestHandler: Handler without floors access restrictions 
   * ElevatorKeyCardRequestHandler: Hanlder for interacting with keycard access system for floors with access restriction

## Scope

In order to simplify the excercise and deliver an excercise that adds value in first version, I have done some simplifications that can be completed in next iterations

1. IoC container is not included in this version, so injections are being done manually

2. Factories for creating different managers, handlers and authentication method need to be implemented.

3. KeyCard Reader was implemented using a simple and dummy mechanism, ideally this should be integrated with a real card reader system provided by the keycard reader provider
   - Requires manual user input  

4. KeyCard Access system was implemented using a simple and dummy mechanism
   - Allowed keys are 902fbdd2b1df0c4f70b4a5d23525e932, c4fd1ef4041f00039def6df0331841de, d8c072a439c55274f145eae9f6583751 

5. Logs are being done using primitive java console

6. Improve Error Handling


## Running the tests
```
mvn clean test
```

### Demo

There is a class called ElevatorSimulatorDemo.java, on that class you will be able to find scenarios and a simple non-interactive demo for validating most common scenarios
