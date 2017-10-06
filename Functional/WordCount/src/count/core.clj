(ns count.core
  (:require [clojure.core.reducers :as r]))

(defn word-frequencies [words]
  (reduce
      (fn [counts word] (assoc counts word (inc (get counts word 0))))
      {} words))

(defn get-words [text] (re-seq #"\w+" text))

(defn count-words-sequential [pages]
  (frequencies (mapcat get-words pages)))
