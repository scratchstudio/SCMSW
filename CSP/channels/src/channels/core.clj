(ns channels.core
  (:require [clojure.core.async :as async :refer :all
              :exclude [map into reduce merge partition partition-by take]]))

