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
