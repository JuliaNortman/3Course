package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

/*
с) Создать приложение с тремя потоками. Каждый поток работает со своим массивом,
потоки проверяют сумму элементов своего массива с суммами элементов других потоков и останавливаются,
когда все три суммы равны между собой. Если суммы не равны,
каждый поток прибавляет единицу к одному элементу массива или отнимает единицу от одного элемента массива,
затем снова проверяет условие равенства сумм. На момент останов- ки всех трех потоков,
суммы элементов массивов должны быть одинаковы.
*/

var seed = rand.NewSource(time.Now().UnixNano())
var random = rand.New(seed)

type Arrays struct {
	arrayList [][]int
	sync.WaitGroup
}

func NewArrays(n, arraySize int) *Arrays {
	return &Arrays{
		arrayList: initializeArray(n, arraySize),
	}
}

func generateArray(arraySize int) []int {
	array := make([]int, arraySize)
	for i := 0; i < arraySize; i++ {
		array[i] = random.Intn(3)
	}
	return array
}

func initializeArray(n, arraySize int) [][]int {
	arrayList := make([][]int, n, arraySize)
	for i := 0; i < n; i++ {
		arrayList[i] = generateArray(arraySize)
	}
	return arrayList
}

func printArrays(list *Arrays) {
	for _, i := range list.arrayList {
		fmt.Println(i)
	}
	fmt.Println("/--------------------------------------------")
}

func changeElement(list *Arrays, group *sync.WaitGroup, index, arraySize int) {
	elemToChange := random.Intn(arraySize)

	if list.arrayList[index][elemToChange] < 5 {
		list.arrayList[index][elemToChange]++
	} else {
		list.arrayList[index][elemToChange]--
	}
	group.Done()
}

func checkArrayRule(list *Arrays, n int) bool {
	sum := make([]int, n)
	for i := 0; i < n; i++ {
		counter := 0
		for _, j := range list.arrayList[i] {
			counter += j
		}
		sum[i] = counter
	}

	if checkAllEqual(sum) {
		return true
	} else {
		return false
	}
}

func checkAllEqual(array []int) bool {
	fmt.Println("Sum: ", array)
	for i := range array {
		if array[0] != array[i] {
			return false
		}
	}

	return true
}

func ArraySimulator(list *Arrays, group *sync.WaitGroup, n, arraySize int) {
	stopFlag := false
	for !stopFlag {
		group.Add(n)
		for i := 0; i < n; i++ {
			go changeElement(list, group, i, arraySize)
		}

		if checkArrayRule(list, n) {
			stopFlag = true
			fmt.Println("Arrays matched the rule")
		}

		printArrays(list)
		time.Sleep(1 * time.Millisecond)
		group.Wait()
	}
}

func main() {
	const (
		N        = 3
		ARR_SIZE = 5
	)

	arr := NewArrays(N, ARR_SIZE)
	group := new(sync.WaitGroup)
	ArraySimulator(arr, group, N, ARR_SIZE)
}
