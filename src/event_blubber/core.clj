(ns event-blubber.core)

(defn jasonify [batch n]
  (flatten (pmap n (partition 100 batch))))
