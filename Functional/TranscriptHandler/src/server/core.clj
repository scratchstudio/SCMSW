; how to run
; 해당 프로젝트의 루트 디렉토리에서 lein repl 후 (-main) 호출
; 새 터미널창을 띄운 후, curl 호출
; curl -X put -d "Twas brillig, and the slithy toves" -H "Content-type: text/plain" localhost:3000/snippet/0 
;
; curl -X put -d “All mimsy were the borogoves,” -H "Content-type: text/plain" localhost:3000/snippet/2
; curl -X put -d “Did gyre and gimble in the wabe:” -H "Content-type: text/plain" localhost:3000/snippet/1

(ns server.core
  (:require [compojure.core     :refer :all]
            [compojure.handler  :refer [site]]
            [ring.util.response :refer [response]]
            [ring.adapter.jetty :refer [run-jetty]]))

; 무한히 반복되는 프라미스로 이루어진 게으른 열
(def snippets (repeatedly promise))

; 이 함수가 호출될 때 실제 값을 생성
(defn accept-snippet [n text]
  (deliver (nth snippets n) text))

; 각 프라미스를 차례로 역참조하는 스레드 생성
(future 
  (doseq [snippet (map deref snippets)]
    (println snippet)))

(defroutes app-routes
  (PUT "/snippet/:n" [n :as {:keys [body]}]
    (accept-snippet (Integer. n) (slurp body))
    (response "OK")))

(defn -main [& args]
  (run-jetty (site app-routes) {:port 3000}))
