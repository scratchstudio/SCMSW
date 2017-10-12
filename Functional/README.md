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
