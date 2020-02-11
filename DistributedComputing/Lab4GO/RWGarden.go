package main

import (
	"bufio"
	"fmt"
	"math/rand"
	"os"
	"sync"
	"time"
)

var seed = rand.NewSource(time.Now().UnixNano())
var random = rand.New(seed)

type Garden struct {
	gardenMatrix     [][]int
	NatureActions    int
	GardenersActions int
	sync.RWMutex
}

func NewGarden(n, m int) *Garden {
	return &Garden{
		gardenMatrix:     fillingMatrix(n, m),
		NatureActions:    0,
		GardenersActions: 0,
	}
}

func fillingMatrix(n, m int) [][]int {
	matrix := make([][]int, n, m)
	for i := range matrix {
		matrix[i] = make([]int, m)
		for j := range matrix[i] {
			matrix[i][j] = 1
		}
	}

	return matrix
}

func printGarden(g *Garden) {
	g.RLock()
	for i := range g.gardenMatrix {
		fmt.Println(g.gardenMatrix[i])
	}
	fmt.Println("Nature: ", g.NatureActions)
	fmt.Println("Gardeners: ", g.GardenersActions)

	g.RUnlock()
}

func Nature(g *Garden, N, M int) {
	for {
		g.Lock()
		var action = random.Intn(10)
		var n = random.Intn(N)
		var m = random.Intn(M)
		if action > 2 { // nature kills the plant
			g.gardenMatrix[n][m] = 0
		} else {
			g.gardenMatrix[n][m] = 1
		}
		fmt.Println("N: ", n, m)
		g.NatureActions++
		g.Unlock()

		time.Sleep(200)
	}
}

func Gardener(g *Garden, N, M, startN, startM, ID int) {
	var i, j = startN, startM
	for {
		if i == N-1 && j == M-1 {
			i, j = 0, 0
		}
		g.Lock()
		g.gardenMatrix[i][j] = 1
		if j == M-1 {
			j = -1
			i++
			i %= N
		}
		j++
		fmt.Println("G", ID, ": ", i, j)
		g.GardenersActions++
		g.Unlock()

		time.Sleep(200)
	}
}

func printToFileGarden(g *Garden, file *os.File) {
	for {
		g.RLock()
		w := bufio.NewWriter(file)
		for i := range g.gardenMatrix {
			for j := range g.gardenMatrix[i] {
				fmt.Fprint(w, g.gardenMatrix[i][j], " ")
			}
			fmt.Fprint(w, "\n")
		}
		fmt.Fprint(w, "\n")
		w.Flush()
		g.RUnlock()
		time.Sleep(800)
	}
}

func main() {
	const (
		N = 20
		M = 20
	)
	gMatrix := NewGarden(N, M)

	var path = "garden.txt"
	file, err := os.Create(path)
	if err != nil {
		panic(err)
	}
	defer file.Close()

	go Nature(gMatrix, N, M)
	go Gardener(gMatrix, N, M, 0, 0, 0)
	go Gardener(gMatrix, N, M, N/2, 0, 1)
	go printToFileGarden(gMatrix, file)

	time.Sleep(time.Second)
	printGarden(gMatrix)
}
