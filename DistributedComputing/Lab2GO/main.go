package main

import "fmt"

//
type Task struct {
	from, to int
}

var forest [100]int
var bearWasFound bool
var tasks []Task = make([]Task, 5, 10)

func initProgramme() {
	forest[50] = 1
}

func findBear(where Task) bool {

	for i := where.from; i <= where.to; i++ {
		if forest[i] == 1 {
			return true
		}
	}
	return false
}

func getAndRemove(tasks []Task) (*Task, []Task) {
	if len(tasks) > 0 {
		var t Task
		t = tasks[len(tasks)-1]
		tasks := tasks[:len(tasks)-1]
		return &t, tasks
	}
	return nil, tasks
}

func main() {
	initProgramme()

	//
	t := new(Task)
	for t != nil {
		t, tasks = getAndRemove(tasks)
		fmt.Println(len(tasks), t)
	}
	/*fmt.Println(len(tasks))
	t := Task{0, 60}
	bearWasFound = findBear(t)
	fmt.Println(bearWasFound)
	tasks := tasks[:len(tasks)-1]
	fmt.Println(len(tasks))*/
}
