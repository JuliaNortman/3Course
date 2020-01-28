package main

import "fmt"

type Task struct {
	from, to int
}

var forest [100]int
var bearWasFound bool
var tasks []Task = make([]Task, 5, 10)

func initProgramme() {
	forest[50] = 1

	/*diapasone = len(forest)/float32(len(tasks))
	for i := 0; i < len(tasks); i++ {
		tasks[i].
		//fmt.Println(i, value)
	}*/
}

func getTask(tasks Task) (Task) {
	if len(tasks) < 1{
		return Task t
	}
}

func findBear(where Task) bool {

	for i := where.from; i <= where.to; i++ {
		if forest[i] == 1 {
			return true
		}
	}
	return false
}

func main() {
	initProgramme()
	fmt.Println(len(tasks))
	t := Task{0, 60}
	bearWasFound = findBear(t)
	fmt.Println(bearWasFound)
	tasks := tasks[:len(tasks)-1]
	fmt.Println(len(tasks))

}
