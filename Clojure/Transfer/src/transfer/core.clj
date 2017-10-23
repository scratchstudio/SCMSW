(ns transfer.core
  (:require [clojure.core.reducers :as r]))

; user=> (def checking (ref 1000))
; user=> (def savings (ref 2000))
; user=> (transfer savings checking 100)
; user=> @checking
; user=> @savings
(defn transfer [from to amount]
  (dosync
      (alter from - amount)
      (alter to + amount)))
