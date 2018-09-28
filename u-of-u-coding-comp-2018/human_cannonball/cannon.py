import sys

RUN_RATE = 5 # m/s
LAUNCH_DISTANCE = 50 # meters
CANNON_TIME = 2 # seconds


def main():
    start = get_2d_float()
    goal = get_2d_float()
    cannon_count = int(get_next_stdin())
    cannons = get_cannons(cannon_count)

    path = []

    # Do a depth first search to find the optimal path
    graph = [cannons]
    queue = [cannons]
    visited = []

    while queue:
        vertex = queue.pop(0)
        if vertex not in visited:
            visited.append(vertex)
            queue.append()


def get_nonvisited_cannons(graph, current_cannon, level):
    nonvisited = []
    graph[level]


def get_distance(location_one, location_two):
    return abs(location_one[0] - location_two[1]), abs(location_one[1] - location_two[1])


def get_cannons(cannon_count):
    cannons = []
    for index in range(cannon_count):
        cannons.append(get_2d_float())
    return cannons


def get_2d_float():
    arr = get_next_stdin().split(" ")
    return [float(arr[0]), float(arr[1])]


def get_next_stdin():
    return sys.stdin.readline()[:-1]


if __name__ == "__main__":
    main()