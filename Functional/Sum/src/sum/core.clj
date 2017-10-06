(ns sum.core
  (:require [clojure.core.reducers :as r]))

; 재귀적 합
(defn recursive-sum [numbers]
  (if (empty? numbers)
    0
    (+ (first numbers) (recursive-sum (rest numbers)))))

; reduce는 시퀀스의 모든 항목에 지정한 함수를 적용한다 (reduce 함수 시퀀스)
; fn 키워드로 두 개의 인수를 받아 합을 리턴하는 함수 정의
(defn reduce-sum [numbers]
  (reduce (fn [x y] (+ x y)) 0 numbers))

; 위 예제와 달리 익명 함수 조차 만들지 않고 아예 +를 인수로 전달할 수 있다.
(defn sum [numbers]
  (reduce + numbers))

; 병렬적인 sum 함수
(defn parallel-sum [numbers]
  (r/fold + numbers))
