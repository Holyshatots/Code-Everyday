import sys


def main():
    name = get_next_stdin()
    final_name = ""
    skip = 0
    for index in range(len(name)):
        # Skip over letters that are repeated
        if skip > 0:
            skip -= 1
            continue
        letter = name[index]
        final_name += letter
        next_letter_index = index + 1
        if next_letter_index > len(name) - 1:
            continue
        next_letter = name[next_letter_index]
        while letter == next_letter:
            skip += 1
            next_letter_index += 1
            if next_letter_index > len(name) - 1:
                break
            next_letter = name[next_letter_index]
    print(final_name)


def get_next_stdin():
    return sys.stdin.readline()[:-1]


if __name__ == '__main__':
    main()
