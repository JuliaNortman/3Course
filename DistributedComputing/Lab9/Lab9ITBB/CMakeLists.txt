cmake_minimum_required(VERSION 3.12)
project(Lab9ITBB)

set(CMAKE_CXX_STANDARD 14)

add_executable(Lab9ITBB main.cpp)

include_directories(D:/CLion/tbb/include)
target_link_libraries(Lab9ITBB D:/CLion/tbb/lib/intel64/vc14/tbb.lib D:/CLion/tbb/lib/intel64/vc14/tbb_debug.lib)