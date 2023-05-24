import * as readline from 'readline';

class Node<T> {
    next : Node<T> | null = null;
    prev : Node<T> | null = null;
    val : T | null = null;

    constructor();
    constructor(val: T);
    constructor(val? : T) {
        if (val) {
            this.val = val;
        }
    }

}

class DoublyLinkedList<T> {
    private head: Node<T>;
    private tail: Node<T>;

    constructor()  {
        // use empty nodes to avoid messy if statements caring about
        // creating a head/tail if the list is empty (or when head and tail
        // point to the same node when there's only one element, etc.)
        this.head = new Node<T>();
        this.tail = new Node<T>();
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }

    public push(val : T) : void {
        this.tail.val = val;
        this.tail.next = new Node();
        this.tail.next.prev = this.tail;
        this.tail = this.tail.next;
    }

    public pop() : T | null {
        if (this.tail.prev != this.head) {
            const toReturn = this.tail.prev;

            toReturn.prev.next = this.tail;
            this.tail.prev = toReturn.prev;

            return toReturn.val;
        }

        return null;
    }

    public shift (val: T) : void {
        this.head.val = val;
        this.head.prev = new Node();
        this.head.prev.next = this.head;
        this.head = this.head.prev;
    }

    public unshift() : T | null {
        if (this.head.next != this.tail) {
            const toReturn = this.head.next;

            toReturn.next.prev = this.head;
            this.head.next = toReturn.next;

            return toReturn.val;
        }

        return null;
    }
}

// interactive prompt testing with number
function promptOperation() {

    const rl = readline.createInterface({
        input: process.stdin,
        output: process.stdout
    });

    console.log("Select an operation: ")
    console.log("[0]: Push");
    console.log("[1]: Pop");
    console.log("[2]: Shift");
    console.log("[3]: Unshift");
    console.log("[4]: Stop");

    rl.question("Select an option: ", (userInput) => {
        switch(parseInt(userInput)) {
            case 0 :
                rl.question("Input a value: ", (userInput) => {
                    numList.push(parseInt(userInput));
                    promptOperation();
                });
                break;
            case 1 :
                console.log("Popped " + numList.pop());
                promptOperation();
                break;
            case 2 :
                rl.question("Input a value: ", (userInput) => {
                    numList.shift(parseInt(userInput));
                    promptOperation();
                });
                break;
            case 3:
                console.log("Unshifted " + numList.unshift());
                promptOperation();
                break;
            case 4:
                rl.close();
                break;
            default:
                console.log("Invalid option");
                promptOperation();
                break;
        }
    });

}

// test with numbers
const numList : DoublyLinkedList<number> = new DoublyLinkedList<number>();
console.log("Testing with numbers");
promptOperation();
