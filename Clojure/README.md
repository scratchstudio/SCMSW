# 클로저 방식
클로저는 순수 함수형 언어가 아니기 때문에 전통적인 함수형 언어와는 조금 다른 방식으로 동작한다.

## 1일차: 원자와 영속적인 자료 구조
- 명령형언어: 변수는 기본적으로 가변이고 코드는 그런 변수를 수시로 변경한다.
- 함수형언어: 변수는 기본적으로 불변이고, 코드는 일반적으로 그들이 꼭 필요한 경우에 한해 변경한다.

### 원자
클로저의 원자는 원자적인 변수로 `java.util.concurrent.atomic`을 기반으로 구현되어 있다.

```clojure
user=> (def my-atom (atom 42))
user=> @my-atom
42
user=> (swap! my-atom +2)
44
user=> (reset! my-atom)
0
```

### 영속적인 자료구조
여기서 말하는 '영속적'의 의미는 데이터를 디스크나 DB에 저장하는 것과 상관 없이 값이 수정되었을 때 이전 버전을 영속적으로 보관하는 자료구조를 의미.
- 수정이 일어날 때마다 마치 자료구조 전체가 새로운 값으로 복사되는 것처럼 행동
- 하지만 실제로 그런 식이라면 대단히 비효율적이기 때문에 구조 공유라는 기법을 이용

```clojure
user=> (def listv1 (list 1 2 3))
user=> (def listv2 (cons 4 listv1))
```

`listv2`는 `listv1`의 내용을 전부 공유할 수 있어서 데이터를 복사할 필요 없이 `listv1`의 구조를 공유한다.

## 참고자료
- [Understanding Clojure's PersistentVector implementation](http://blog.higher-order.net/2009/02/01/understanding-clojures-persistentvector-implementation)
- [Understanding Clojure's PersistentHashMap](http://blog.higher-order.net/2009/09/08/understanding-clojures-persistenthashmap-deftwice)


## 2일차: 에이전트와 소프트웨어 트랜잭션 메모리

### 에이전트
원자<sup>atom</sup>과 거의 비슷하다.

```clojure
user => def my-agent (agent 0))
#'user/my-agent
user=> (send my-agent inc)
#object[clojure.lang.Agent 0x783f67d0 {:status :ready, :val 1}]
user=> @my-agent
1
```

`send`가 `swap!`과 다른점은 에이전트의 값 변경 여부와 무관하게 즉시 리턴 된다는 점. `send`에 전달된 함수들은 일렬로 줄을 서고, 한번에 하나씩, 나중에 차례가 되면 실행 된다.


```clojure
user=> (send my-agent #((Thread/slepp 2000) (inc %)))
```

### Ref
Ref는 트랜잭션 메모리<sup>Software Transaction Memory</sup>를 제공하기 때문에 원자나 에이전트에 비해 더 정교함.
한 번에 단인 변수 값을 변경하는 기능만 제공하는 원자나 에이전트와 달리 STM은 일정한 협업을 통해 여러 값을 동시에 변경하는 것이 가능.

```clojure
user=> (def my-ref (ref 0))
user=> (ref-set my-ref 42)
IllegalStateException No transaction running
user=> (alter my-ref inc)
IllegalStateException No transaction running
```
ref의 값은 `ref-set`을 통해 설정하고, `swap!` 또는 `send`에 해당하는 것은 `alter`이다. 하지만 ref의 값을 변경하는 것은 트랜잭션 내부에서만 가능.

```clojure
user=> (dosync (ref-set my-ref 42))
42
user=> (dosync (alter my-ref inc))
43
```
`dosync`의 본문 안에 있는 코드는 모두 하나의 단일 트랜잭션을 구성.

STM의 트랜잭션은 데이터베이스가 지원하는 트랜잭션의 성질 중 ACI 를 지원. 한가지 빠진 것은 Durabillity 이다. STM의 데이터는 전원이 끊기거나 시스템이 다운되면 사라진다.
