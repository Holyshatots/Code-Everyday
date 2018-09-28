import sys
import copy


def main():
    # Do a BFS to find the closest white square on each problem
    for i in range(int(get_next_stdin())):
        print(find_min_moves(get_square()))


def find_min_moves(square):
    target_square = ['...', '...', '...']
    if square == target_square:
        return 0
    # Nodes to be checked
    queue = [square]

    levels = {}
    levels[str(square)] = 0

    visited = [square]

    while queue:
        node = queue.pop(0)
        for row in range(3):
            for col in range(3):
                neighbour = flip_cell(row, col, node)
                if neighbour not in visited:
                    if neighbour == target_square:
                        return levels[str(node)] + 1
                    queue.append(neighbour)
                    visited.append(neighbour)

                    levels[str(neighbour)] = levels[str(node)] + 1
    raise Exception


def flip_cell(row, column, square):
    square_copy = copy.copy(square)
    flip(row, column, square_copy)
    # Up
    if row - 1 >= 0:
        flip(row - 1, column, square_copy)
    # Down
    if row + 1 < 3:
        flip(row + 1, column, square_copy)
    # Left
    if column - 1 >= 0:
        flip(row, column - 1, square_copy)
    # Right
    if column + 1 < 3:
        flip(row, column + 1, square_copy)
    return square_copy


def flip(row, col, square):
    if square[row][col] == ".":
        square[row] = square[row][:col] + "*" + square[row][col + 1:]
    else:
        square[row] = square[row][:col] + "." + square[row][col + 1:]
    return square


def get_squares(num):
    squares = []
    for i in range(num):
        squares.append(get_square())
    return squares


def get_square():
    square = []
    for i in range(3):
        square.append(get_next_stdin())
    return square


def get_next_stdin():
    return sys.stdin.readline()[:-1]


if __name__ == '__main__':
    main()