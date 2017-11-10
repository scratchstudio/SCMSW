(ns channels.core
  (:require [clojure.core.async :as async :refer :all
              :exclude [map into reduce merge partition partition-by take]]))

(defn go-add [x y]
  (<!! (nth (iterate #(go (inc (<! %))) (go x)) y)))
