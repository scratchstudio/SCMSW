# 함수형 프로그래밍

## 클로저 실행 방법
1. REPL 실행
```bash
$ lein repl
```

2. 프로젝트 실행
```bash
# 프로젝트 루트 디렉토리로 이동
$ lein repl
sum.core => (def numbers (into [] (range 0 1000000)))
sum.core => (time (reduce-sum numbers))
```

## 1일차: 가변 상태 없이 프로그래밍하기
함수형 프로그래밍이 가지고 있는 가장 흥미로운 요소는 가변 상태를 원천적으로 피하는 것.

### reduce
시퀀스의 모든 항목에 지정한 함수를 적용
```clojure
; (reduce f val coll)
user=> (reduce (fn [x y] (+ x y)) 0 numbers))
```

### map
시퀀스의 모든 항목에 지정한 함수를 적용한 결과를 담은 시퀀스를 반환
```clojure
; (map f coll)
user=> (map (fn [e] (+ e 1)) [1 2 3 4 5])
(2 3 4 5 6)
```

## 2일차: 함수 병렬화
### clujure.core.reducers의 함수들
map, mapcat, filter와 같은 축소자(reducers)를 이용하는 함수들은 실제적인 결과를 리턴하는 대신 어떤 결과를 만들 수 있는 표현을 담은 조리법을 리턴. 조리법은 reduce 혹은 fold에 전달되기 전까지 실행되지 않는다. 이런 방법의 장점은
1. 중간 단계의 열이 만들어진 필요가 없기 때문에 단순히 게으른 열을 리턴하는 함수의 체인보다 효율적이다.
2. fold가 컬렉션 내부에서 일어나는 모든 동작의 체인을 병렬화 하는 것을 허용한다.

### fold
컬렉션을 순차적으로 축소하는 대신 이진 분할을 이용
```clojure
(defn parallel-frequencies [coll]
    (r/fold
        (partial merge-with +)
        (fn [counts x] (assoc counts x (inc (get counts x 0))))
    coll))
```

## 3일차: 함수 동시성
자바와 같은 명령형 언어에서는 발생하는 동작 순서가 소스 코드에 적힌 명령문 순서와 밀접하게 연관. 그에 비해 함수형 언어는 선언적 느낌이 강함.
1. 연산을 어떻게 수행하라는 방법을 적는 대신, 결과가 무엇이 되어야 하는지를 적음.
2. 결과를 낳기 위해 계산이 어떤 순서로 실행되어야 하는지에 대해 결정하는 것은 유동적.
3. 계산 순서를 바꿀 수 있기 때문에 코드의 병렬화가 쉬움.

### future
코드의 몸체를 받아 그것을 다른 스레드에서 실행. 리턴되는 값은 퓨쳐 객체.
```clojure
(let [a (future (+ 1 2))
      b (future (+ 3 4))]
    (+ @a +@b))
```
퓨처에 대한 역참조(@)는 값이 준비될 때까지 블로킹 됨.

### promise
퓨처와 거의 비슷하지만, 차이는 실행될 코드 없이도 프라미스를 만들 수 있다.
```shell
user=> (def meaning-of-life (promise))
user=> (future (println "The meaning of life is: " @meaning-of-life))
user=> (deliver meaning-of-life 42)
The meaning of life is: 42
```

