class SudokuSolver {
    private board: string[][];

    constructor(board: string[][]) {
        this.board = board;
    }

    public solve(): boolean {
        const emptyCell = this.findEmptyCell();
        if (!emptyCell) {
            // All cells are filled, solution found
            return true;
        }

        const [row, col] = emptyCell;
        for (let num = 1; num <= 9; num++) {
            if (this.isValidMove(row, col, num)) {
                this.board[row][col] = num.toString();

                if (this.solve()) {
                    // Solution found, return true
                    return true;
                }
                // Backtrack
                this.board[row][col] = ".";
            }
        }

        // No valid number for the current cell, trigger backtracking
        return false;
    }

    public isValidMove(row: number, col: number, num: number): boolean {
        // Check if the number already exists in the same row
        for (let i = 0; i < 9; i++) {
            if (this.board[row][i] === num.toString()) {
                return false;
            }
        }


        // Check if the number already exists in the same column
        for (let i = 0; i < 9; i++) {
            if (this.board[i][col] === num.toString()) {
                return false;
            }
        }

        // Check if the number already exists in the same 3x3 grid
        const gridRow = Math.floor(row / 3) * 3;
        const gridCol = Math.floor(col / 3) * 3;
        for (let i = 0; i < 3; i++) {
            for (let j = 0; j < 3; j++) {
                if (this.board[gridRow + i][gridCol + j] === num.toString()) {
                    return false;
                }
            }
        }

        // Number is valid for the current cell
        return true;
    }

    public findEmptyCell(): [number, number] | null {
        for (let row = 0; row < 9; row++) {
            for (let col = 0; col < 9; col++) {
                if (this.board[row][col] === ".") {
                    return [row, col];
                }
            }
        }

        // No empty cells, puzzle solved
        return null;
    }

    public printBoard(): void {
        for (let row = 0; row < 9; row++) {
            if (row % 3 === 0 && row !== 0) {
                console.log("- - - - - - - - - - -");
            }
            for (let col = 0; col < 9; col++) {
                if (col % 3 === 0 && col !== 0) {
                    process.stdout.write("| ");
                }
                process.stdout.write(this.board[row][col] + " ");
            }
            console.log();
        }
    }
}

// test
const board: string[][] = [
    ["5","3",".",".","7",".",".",".","."],
    ["6",".",".","1","9","5",".",".","."],
    [".","9","8",".",".",".",".","6","."],
    ["8",".",".",".","6",".",".",".","3"],
    ["4",".",".","8",".","3",".",".","1"],
    ["7",".",".",".","2",".",".",".","6"],
    [".","6",".",".",".",".","2","8","."],
    [".",".",".","4","1","9",".",".","5"],
    [".",".",".",".","8",".",".","7","9"]
];

const solver = new SudokuSolver(board);
solver.solve();
solver.printBoard();