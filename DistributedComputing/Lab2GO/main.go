package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

// Forest
var Matrix [][]int

// Here’s the worker, of which we’ll run several concurrent instances.
// These workers will receive work on the jobs channel and send the corresponding results on results.
// We’ll sleep a second per job to simulate an expensive task.
func worker(wg *sync.WaitGroup, id int, jobs <-chan []int, terminateChannel chan bool, terminated *bool) {
	for j := range jobs {
		if *terminated {
			wg.Done()
			return
		}
		select {
		case <-terminateChannel:
			wg.Done()
			return
		default:
			fmt.Println("bee group", id, "started job", j)
			time.Sleep(time.Second)
			for i := range j {
				if j[i] == 1 {
					terminateChannel <- true
					*terminated = true
					fmt.Println("Bee group", id, "found bear")
				}
			}
			fmt.Println("bee group", id, "finished job", j)
		}
	}
}

func fillingMatrix(n, m int) [][]int {
	matrix := make([][]int, n, m)
	for i := range matrix {
		matrix[i] = make([]int, m)
	}
	rand.Seed(time.Now().UnixNano())
	x := rand.Intn(n - 1)
	y := rand.Intn(m - 1)
	matrix[x][y] = 1

	fmt.Println("Forest created ", matrix)

	return matrix
}

func main() {
	var (
		n  = 10
		m  = 10
		wg sync.WaitGroup
	)

	Matrix = fillingMatrix(n, m)
	terminateChannel := make(chan bool)
	jobs := make(chan []int, 100)
	var terminateFlag bool = false

	//Workers initially blocked because there are no jobs yet.
	for w := 1; w <= 3; w++ {
		go worker(&wg, w, jobs, terminateChannel, &terminateFlag)
		wg.Add(1)
	}

	//Here we send jobs and then close that channel to indicate that’s all the work we have.
	for j := 0; j < n; j++ {
		jobs <- Matrix[j]
	}
	close(jobs)
	wg.Wait()
}
