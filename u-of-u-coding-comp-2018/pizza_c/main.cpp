#include <iostream>
#include <vector>


int manDistance(int xOne, int yOne, int xTwo, int yTwo) {
    return abs(xOne - xTwo) + abs(yOne - yTwo);
}


int getDeliveryCost(int x, int y, std::vector<std::vector<int>> grid, int xSize, int ySize, int lowestCost) {
    int cost = 0;
    for (int row = 0; row < ySize; row++) {
        for (int col = 0; col < xSize; col++) {
            cost += grid[row][col] * manDistance(x, y, row, col);
            if (lowestCost != -1 && cost >= lowestCost) {
                return -1;
            }
        }
    }
    return cost;
}


int getOptimalCost(int xSize, int ySize, std::vector<std::vector<int>> grid) {
    int lowestCost = -1;
    for (int row = 0; row < ySize; row++) {
        for (int col = 0; col < xSize; col++) {
            int cost = getDeliveryCost(row, col, grid, xSize, ySize, lowestCost);
            if (cost != -1 && (lowestCost == -1 || cost < lowestCost)) {
                lowestCost = cost;
            }
        }
    }
    return lowestCost;
}


int main() {
    int testCases;
    std::cin >> testCases;
    for (int testCase = 0; testCase < testCases; testCase++) {
        int xSize;
        std::cin >> xSize;
        int ySize;
        std::cin >> ySize;
        std::vector<std::vector<int>> grid;
        grid.resize(ySize, std::vector<int>(xSize, 0));
        for (int row = 0; row < ySize; row++) {
            for (int col = 0; col < xSize; col++) {
                std::cin >> grid[row][col];
            }
        }

        int optimalCost = getOptimalCost(xSize, ySize, grid);
        std::cout << std::to_string(optimalCost) + " blocks\n";
    }
    return 0;
}
