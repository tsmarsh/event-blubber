(ns event-blubber.core-test
  (:require [clojure.test :refer :all]
            [event-blubber.core :refer :all]))

(deftest should-call-next-in-batches-test
  (testing "Given a list of stuff partition and forward"
    (let [batch (take 1000 (repeatedly #(rand-int 10)))
          result (atom [])
          n (fn [batch] (swap! result conj batch))]
      (jasonify batch n)
      (is (some #{(take 100 batch)} @result)))))

(deftest should-calmly-react-to-bad-numbers
  (testing "Given a batch with a number greater than 10 it handles it gracefully"
    (let [batch (vec (take 999 (repeatedly #(rand-int 10))))
          bad-batch (assoc batch 234 66)
          result (atom [])
          n (fn [batch] (let [[good bad] ((juxt filter remove) #(< % 10) batch)]
                          (swap! result conj good)
                          bad))
          bad (jasonify bad-batch n)]
      (is (some #{66} bad)))))