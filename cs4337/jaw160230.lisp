(defun divisible-by-5 (x)
  (= (mod x 5) 0)
)

(defun function-7 (f)
  (apply f '(7))
)

(defun fib (x)
  (if (or (= x 0) (= x 1)) 1
  (+ (fib (- x 1)) (fib (- x 2)))
  )
)

(defun my-map (f L)
  (if (= (length L) 1)
  (funcall f (car L))
  (cons (funcall f (car L)) (my-map f (cdr L)))
  )
)