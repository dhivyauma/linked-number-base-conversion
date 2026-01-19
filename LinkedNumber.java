public class LinkedNumber {
    private int base;  // Variables to store the base of the number and the front/rear nodes of the linked list
    private DLNode<Digit> front;
    private DLNode<Digit> rear;

    public LinkedNumber(String num, int baseNum) {  // Constructor to create a LinkedNumber from a string representation and a specified base
        this.base = baseNum; 

        if (num.isEmpty()) {  
            throw new LinkedNumberException("no digits given");  // If the string is empty, throw an exception indicating that no digits were given
        }

        char[] digitsArray = num.toCharArray();  // Convert the string of digits to an array of characters
        Digit firstDigit = new Digit(digitsArray[0]);  
        front = new DLNode<>(firstDigit); 
        rear = front;  // Set both front and rear to the first node initially

        for (int i = 1; i < digitsArray.length; i++) {  
            char digitChar = digitsArray[i];  // Extract the current character representing a digit
            Digit digit = new Digit(digitChar);  
            DLNode<Digit> newNode = new DLNode<>(digit);  
            rear.setNext(newNode);  // Connect the new node to the current rear node in the linked list
            newNode.setPrev(rear);
            rear = newNode;
        }
    }
    
    public LinkedNumber(int num) {  // Constructor to create a LinkedNumber from an integer, defaulting to base 10
        this(String.valueOf(num), 10);  // Convert the integer to a string and use the main constructor with base 10
    }

    public boolean isValidNumber() {  // Method to check if the linked number is valid (each digit is within the specified base)
        DLNode<Digit> current = front;  

        while (current != null) {  // Iterate through each digit in the linked list
            int digitValue = current.getElement().getValue(); 
            if (digitValue < 0 || digitValue >= base) { 
                return false;  // Return false if an invalid digit is found
            }
            current = current.getNext(); 
        }

        return true;  
    }

    public int getBase() {  
        return base;
    }

    public DLNode<Digit> getFront() {  
        return front;
    }

    public DLNode<Digit> getRear() {  
        return rear;
    }

    public int getNumDigits() {  // Method to get the total number of digits in the linked number
        int count = 0;  
        DLNode<Digit> current = front;  // Start with the front node

        while (current != null) {  // Iterate through each node in the linked list
            count++;  
            current = current.getNext();  // Move to the next node
        }

        return count;  
    }

    public String toString() {  
        String result = "";  
        DLNode<Digit> current = front;  // Start with the front node of the linked list

        while (current != null) { 
            result += current.getElement().toString();  // Add the current digit to the result string
            current = current.getNext();  
        }

        return result;  
    }

    public boolean equals(LinkedNumber other) {  // Method to check if two linked numbers are equal (having the same base and digit values)
        if (this.base != other.base) {  
            return false;  
        }

        DLNode<Digit> currentThis = this.front;  // Initialize iterators for both linked numbers starting from their front nodes
        DLNode<Digit> currentOther = other.front;

        while (currentThis != null && currentOther != null) {  // Iterate through each node in both linked numbers
            if (!currentThis.getElement().equals(currentOther.getElement())) {  
                return false; 
            }
            currentThis = currentThis.getNext();  // Move to the next node in both linked numbers
            currentOther = currentOther.getNext();
        }

        return currentThis == null && currentOther == null;  
    }
    
    public LinkedNumber convert(int newBase) {  // Method to convert the linked number to a new base
        if (!isValidNumber()) {  // Check if the current number is valid before performing conversion
            throw new LinkedNumberException("cannot convert invalid number");
        }

        String convertedNumber = convertToBase(newBase);  // Convert the current number to a string representation in the new base
        return new LinkedNumber(convertedNumber, newBase);  // Create and return a new LinkedNumber with the converted string and the new base
    }

    private String convertToBase(int newBase) {  // Convert the current number to a string representation in the new base
        if (!isValidNumber()) {  // Check if the current number is valid before attempting the conversion
            throw new LinkedNumberException("cannot convert invalid number");
        }

        int base10Value = convertToBase10();  // Convert the current number to its base-10 equivalent
        return convertFromBase10(base10Value, newBase);  // Convert the base-10 value to the desired base and return the result as a string
    }

    private int convertToBase10() {  // Convert the current number to its base-10 equivalent
        int result = 0;
        int positionValue = 1;
        DLNode<Digit> current = rear;

        while (current != null) {  // Iterate through the linked list in reverse order, calculating the base-10 value
            result += current.getElement().getValue() * positionValue;
            positionValue *= base;  // Increase the position value for the next digit
            current = current.getPrev();  // Move to the previous node in the linked list
        }

        return result;
    }

    private String convertFromBase10(int base10Value, int newBase) {  // Convert the base-10 value to the desired base and return the result as a string
        StringBuilder result = new StringBuilder();

        while (base10Value > 0) {  // Convert the base-10 value to the desired base, adding digits to the result
            int remainder = base10Value % newBase;
            Digit digit = new Digit(getCharForDigit(remainder));
            result.insert(0, digit.toString());  // Insert the digit at the beginning of the result
            base10Value /= newBase;  // Update the base-10 value for the next iteration
        }

        return result.toString();
    }

    private char getCharForDigit(int digit) {  // Convert the numeric digit to its character representation in the specified range
        if (digit < 10) {
            return (char) ('0' + digit);  // Digits 0-9 are represented by characters '0' to '9'
        } else {
            return (char) ('A' + digit - 10);  // Digits 10 and above are represented by characters 'A' to 'Z'
        }
    }
    
    public void addDigit(Digit digit, int position) {  // Method to add a digit at a specified position in the linked number
        if (position < 0 || position > getNumDigits()) {  // Check if the position is valid (within the range of existing digits)
            throw new IndexOutOfBoundsException("Invalid position");
        }

        DLNode<Digit> newNode = new DLNode<>(digit);  // Create a new node with the given digit

        // Handle different cases: beginning, end, or middle
        if (position == 0) {  // Check if adding at the beginning
            addDigitAtBeginning(newNode);
        } else if (position == getNumDigits()) {  // Check if adding at the end
            addDigitAtEnd(newNode);
        } else {
            addDigitInMiddle(newNode, position);  // Adding in the middle, find the correct position and adjust pointers
        }
    }

    private void addDigitAtBeginning(DLNode<Digit> newNode) {
        rear.setNext(newNode);
        newNode.setPrev(rear);
        rear = newNode;  // Update rear to the new node
    }

    private void addDigitAtEnd(DLNode<Digit> newNode) {
        newNode.setNext(front);
        front.setPrev(newNode);
        front = newNode;  // Update front to the new node
    }

    private void addDigitInMiddle(DLNode<Digit> newNode, int position) {
        DLNode<Digit> current = front;  // Start from the front
        for (int i = 0; i < (getNumDigits() - position) - 1; i++) {
            current = current.getNext();
        }
        DLNode<Digit> nextNode = current.getNext();
        newNode.setPrev(current);
        newNode.setNext(nextNode);
        current.setNext(newNode);
        nextNode.setPrev(newNode);
    }

    public int removeDigit(int position) {  // Method to remove a digit at a specified position in the linked number
        validatePosition(position);  // Validate if the position is within the range of existing digits

        int value = calculateDigitValue(position);  // Calculate the value of the digit to be removed

        if (position == 0) {  // Remove the digit node at the specified position
            removeFirstDigit();
        } else if (position == getNumDigits() - 1) {
            removeLastDigit();
        } else {
            removeDigitAtPosition(position);
        }

        convertAndReturn(value);  // Convert and return the calculated digit value
        return value;
    }

    private void validatePosition(int position) {  // Validation method for position
        if (position < 0 || position >= getNumDigits()) {
            throw new IndexOutOfBoundsException("Invalid position");
        }
    }

    private int calculateDigitValue(int position) {  // Calculate the decimal value of the digit at a given position
        DLNode<Digit> current = rear;
        int value = 0;
        int count = 0;

        while (current != null) {
            if (count == position) {
                value += (int) (current.getElement().getValue() * Math.pow(getBase(), count));
            }
            count++;
            current = current.getPrev();
        }

        return value;
    }

    private void removeFirstDigit() {  // Remove the first digit in the linked list
        rear = rear.getPrev();
        if (rear != null) {
            rear.setNext(null);
        } else {
            front = null;  // If rear becomes null, set front to null as well
        }
    }

    private void removeLastDigit() {  // Remove the last digit in the linked list
        front = front.getNext();
        if (front != null) {
            front.setPrev(null);
        } else {
            rear = null;  // If front becomes null, set rear to null as well
        }
    }

    private void removeDigitAtPosition(int position) {  // Remove the digit at a specified position in the linked list
        DLNode<Digit> current = rear;

        for (int i = getNumDigits(); i >= position; i--) {
            if (i == position) {
                current.getPrev().setNext(current.getNext());
                current.getNext().setPrev(current.getPrev());
            }
            current = current.getPrev();
        }
    }

    private void convertAndReturn(int value) {  // Convert the value to a LinkedNumber object and convert its base
        LinkedNumber convertedValue = new LinkedNumber(value);
        convertedValue.convert(getBase());
    }
}
