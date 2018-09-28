import sys


def main():
    bus_num = int(get_next_stdin())
    buses_str = get_next_stdin().split(" ")
    buses = []
    for bus in buses_str:
        buses.append(int(bus))

    buses.sort()

    final_str = ""
    visited_buses = []
    for bus in buses:
        if bus in visited_buses:
            continue

        highest_bus = bus
        while highest_bus in buses:
            visited_buses.append(highest_bus)
            highest_bus += 1

        if highest_bus == bus + 1:
            # This bus isn't in a series
            final_str += "{} ".format(str(bus))
        elif highest_bus == bus + 2:
            final_str += "{} ".format(str(bus))
            visited_buses.remove(highest_bus - 1)
        else:
            # Get the actual highest bus in the series
            highest_bus -= 1
            final_str += "{}-{} ".format(str(bus), str(highest_bus))

    print(final_str)


def get_next_stdin():
    return sys.stdin.readline()[:-1]


if __name__ == '__main__':
    main()
