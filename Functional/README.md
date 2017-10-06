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
