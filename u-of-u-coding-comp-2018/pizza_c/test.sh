#!/bin/bash

g++ -g -O2 -static -std=gnu++14 main.cpp
cat sample.in | ./a.out