# Linked Number Base Conversion

This project implements a flexible number representation system using a **doubly linked list**, allowing positive whole numbers to be stored, manipulated, and converted across different number systems ranging from **base 2 to base 16**.

Each digit of a number is stored in its own node, enabling precise control over digit-level operations such as insertion, removal, validation, and base conversion.

## 📌 Overview

The system supports:
- Representing numbers in arbitrary bases (binary, decimal, hexadecimal, etc.)
- Validating digits based on the active number system
- Converting numbers between different bases
- Digit-level insertion and removal using positional logic
- Accurate value computation regardless of base

The implementation emphasizes correctness, robustness, and strict adherence to defined behavior through custom exception handling.

## Core Components

### `LinkedNumber`
Represents a positive whole number stored as a doubly linked list.
- Stores digits in forward and reverse order
- Supports base validation and equality comparison
- Converts numbers between bases (2–16)
- Allows positional digit insertion and removal
- Computes numeric values independent of representation

### `LinkedNumberException`
A custom runtime exception used to signal invalid operations or malformed numbers.

## Concepts & Techniques
- Doubly linked data structures
- Abstract digit representation
- Base-N number systems (binary to hexadecimal)
- Exception-driven error handling
- Positional value computation
- Deterministic data structure traversal

## 📂 Project Structure
├── LinkedNumber.java
└── LinkedNumberException.java


## 🔄 Supported Operations
- Validate number format for a given base
- Convert between decimal and non-decimal systems
- Convert between two non-decimal systems
- Insert digits at arbitrary positions
- Remove digits while preserving numeric correctness

## Goal

The goal of this project is to demonstrate how linked data structures can be used to model numeric systems and perform reliable base conversions while maintaining full control over digit-level behavior.
