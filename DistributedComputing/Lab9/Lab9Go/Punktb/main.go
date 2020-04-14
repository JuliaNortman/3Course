package main

import (
	"fmt"
	"math/rand"
	"time"
)

func matrixMultiplication(A[][] float64, B[][] float64, C[][] float64, line int, forkJoin chan [][]float64){
	//
	if line > 0 {
		C = <-forkJoin
	}
	n := len(A)
	if line!=n {
		res := make([]float64, n)
		var sum float64
		for j := 0; j < n; j++ {
			sum = 0
			for k := 0; k < len(A); k++ {
				sum += A[line][k] * B[k][j]
			}
			res[j] = sum
		}
		C[line] = res

		go matrixMultiplication(A, B, C, line+1, forkJoin)

		forkJoin <- C
	}

	forkJoin <- C
}

func makeRandomMatrix(n int) [][]float64{
	rand.Seed(time.Now().UnixNano())
	matrix := make([][]float64, n)
	for i := range matrix {
		matrix[i] = make([]float64, n)
	}
	for i:=0;i<n;i++  {
		for j:=0;j<n;j++  {
			matrix[i][j] = float64(rand.Intn(100))
		}
	}
	return matrix
}

func printMatrix(matrix [][]float64){
	for i:=0;i< len(matrix);i++  {
		for j:=0;j< len(matrix);j++  {
			fmt.Print("[", matrix[i][j], "]")
		}
		fmt.Println()
	}
}

func main() {
	n := 1000
	A := makeRandomMatrix(n)
	//A[0][0] = -1
	//A[0][1] = 2
	//A[0][2] = -5
	//A[1][0] = 3
	//A[1][1] = 4
	//A[1][2] = 1
	//A[2][0] = 0
	//A[2][1] = 1
	//A[2][2] = 2
	B := makeRandomMatrix(n)
	C := make([][]float64, n)
	for i := range C {
		C[i] = make([]float64, n)
	}
	forkJoin := make(chan [][]float64)

	start := time.Now()

	go matrixMultiplication(A, B, C, 0, forkJoin)

	C = <-forkJoin

	elapsed := time.Since(start)

	fmt.Println("Time of execution:", elapsed.Milliseconds(), "ms")

	//printMatrix(C)
}


