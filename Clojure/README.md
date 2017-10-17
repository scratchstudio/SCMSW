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
