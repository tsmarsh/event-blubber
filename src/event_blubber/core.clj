(ns event-blubber.core)

(defn jasonify [batch n]
  (pmap n (partition 100 batch)))
