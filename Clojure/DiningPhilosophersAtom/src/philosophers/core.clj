(ns philosophers.core)

(def philosophers (atom (into [] (repeat 5 :thinking))))

(defn release-chopsticks! [philosopher]
  (swap! philosophers assoc philosopher :thinking))

(defn claim-chopsticks! [philosopher left right]
  (swap! philosophers
    (fn [ps]
      (if (and (= (ps left) :thinking) (= (ps right) :thinking))
        (assoc ps philosopher :eating)
        ps)))
  (= (@philosophers philosopher) :eating))

(defn think []
  (Thread/sleep (rand 1000)))

(defn eat []
  (Thread/sleep (rand 1000)))

(defn philosopher-thread [philosopher]
  (Thread.
      #(let [left (mod (- philosopher 1) 5)
             right (mod (+ philosopher 1) 5)]
        (while true
            (println philosopher "philosopher thinking...")
            (think)
            (when (claim-chopsticks! philosopher left right)
              (println philosopher "philosopher eating...")
              (eat)
              (release-chopsticks! philosopher))))))

(defn -main [& args]
  (let [threads (map philosopher-thread (range 5))]
    (doseq [thread threads] (.start thread))
    (doseq [thread threads] (.join thread))))
