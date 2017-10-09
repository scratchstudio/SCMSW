(ns count.core
  (:require [clojure.core.reducers :as r]))

;
(defn word-frequencies [words]
  (reduce
      (fn [counts word] (assoc counts word (inc (get counts word 0))))
      {} words))

;정규식 패턴 매칭
(defn get-words [text] (re-seq #"\w+" text))

;mapcat을 통해 페이지를 단어의 열로 변환
;단어의 열은 frequencies에 전달
(defn count-words-sequential [pages]
  (frequencies (mapcat get-words pages)))

;병렬적인 단어세기
;하지만 페이지별로 단어의 수를 센 다음 전체적인 병합을 시도하기 때문에
;병합이 일어나는 횟수가 엄청나게 많다.
(defn count-words-parallel [pages]
  (reduce (partial merge-with +)
      (pmap #(frequencies (get-words %)) pages)))

;개선된 벙렬적인 단어세기
;페이지를 100개씩 묶어 일괄처리 한다.
(defn count-words-parallel-better [pages]
  (reduce (partial merge-with +)
      (pmap count-words-sequential (partition-all 100 pages))))
