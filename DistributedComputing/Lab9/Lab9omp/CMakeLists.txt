cmake_minimum_required(VERSION 3.12)
project(Lab9omp)

set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -fopenmp")

add_executable(Lab9omp main.cpp)