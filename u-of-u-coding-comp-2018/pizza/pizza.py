import sys


def main():
    test_cases = int(get_next_stdin())
    for case in range(test_cases):
        size = get_next_stdin().split(" ")
        x_size = int(size[0])
        y_size = int(size[1])
        grid = get_delivery_grid(x_size, y_size)
        print(str(get_optimal_delivery_cost(x_size, y_size, grid)) + " blocks")


def get_optimal_delivery_cost(x_size, y_size, grid):
    # Bruteforce calculate total delivery cost for every cell on grid
    lowest_cost = None
    for row in range(y_size):
        for col in range(x_size):
            cost = get_delivery_cost(grid, row, col, x_size, y_size)
            if not lowest_cost or cost < lowest_cost:
                lowest_cost = cost
    return lowest_cost


def get_delivery_cost(grid, x, y, x_size, y_size):
    cost = 0
    for row in range(y_size):
        for col in range(x_size):
            cost += grid[row][col] * man_distance(x, y, row, col)
    return cost


def man_distance(x_one, y_one, x_two, y_two):
    return abs(x_one - x_two) + abs(y_one - y_two)


def get_delivery_grid(x_size, y_size):
    grid = []
    for x in range(x_size):
        row_str = get_next_stdin().split(" ")
        row = []
        for pos in row_str:
            row.append(int(pos))
        grid.append(row)
    return grid


def get_next_stdin():
    return sys.stdin.readline()[:-1]


if __name__ == '__main__':
    main()