;; This file provides some emacs utilities for converting a buffer of code
;; into a clojure data structure that komokio can use to build code data

(setq komokio-faces-name-map
      '(default                                 "default"
         font-lock-builtin-face                  "builtin"
         font-lock-comment-delimiter-face        "comment-delimiter"
         font-lock-comment-face                  "comment"
         font-lock-constant-face                 "constant"
         font-lock-doc-face                      "doc"
         font-lock-doc-string-face               "doc-string"
         font-lock-function-name-face            "function-name"
         font-lock-keyword-face                  "keyword"
         font-lock-negation-char-face            "negation-char"
         font-lock-preprocessor-face             "preprocessor"
                                        ; TODO: don't need to support these just yet
                                        ; font-lock-regexp-grouping-backslash  "regexp-grouping-backslash"
                                        ; font-lock-regexp-grouping-construct  "regexp-grouping-construct"
         font-lock-string-face                   "string"
         font-lock-type-face                     "type"
         font-lock-variable-name-face            "variable-name"
         font-lock-warning-face                  "warning"
         ))

(defun komokio-to-valid-face (face)
  (let ((face (if (consp face) (car face) face))
        (name (plist-get komokio-faces-name-map face)))
    (if name
        name
      "default")))

(defun komokio-buffer-to-list ()
  (read
   (subseq
    (with-current-buffer (current-buffer)
      (prin1-to-string (buffer-string))) 1)))

(defun komokio-update-plist (plist line-no chunk-no data)
  (plist-put plist line-no (plist-put (plist-get plist line-no) chunk-no data)))

(defun komokio-trim (str)
  (let ((chomp-left (replace-regexp-in-string "^\n" "" str)))
    (replace-regexp-in-string "\n$" "" chomp-left)))

(defun komokio-build-code-data (string props)
  (let ((region (subseq props 0 2))
        last-line-no
        last-chunk-no
        acc)
    (while (not (equal  nil (car region))) ;; end test
      (let* ((region-start     (car region))
             (region-end       (cadr region))
             (region-line-no   (line-number-at-pos (goto-char region-end)))
             (line-no          (if (equal last-line-no region-line-no) last-line-no region-line-no))
             (chunk-props      (car (nthcdr 2 props)))
             (chunk-face       (komokio-to-valid-face (plist-get chunk-props 'face)))
             (chunk-no         (if (equal last-line-no line-no) (1+ last-chunk-no) 1))
             (data             (list :code-chunk/face   (vector :faces/by-name chunk-face)
                                     :code-chunk/string (komokio-trim
                                                         (substring string
                                                                    region-start
                                                                    region-end))
                                     :code-chunk/line-chunk (+ chunk-no (* line-no 1000)))))
        (setq props (subseq props 3))
        (setq region (subseq props 0 2))
        (setq last-line-no line-no)
        (setq last-chunk-no chunk-no)
        (setq acc (cons data acc))))
    acc))

(defun komokio-buffer-to-data ()
  (interactive)
  (let* ((string-list (komokio-buffer-to-list))
         (string (nth 0 string-list))
         (props  (cdr string-list)))
    (save-excursion
      (with-current-buffer (current-buffer)
        (komokio-build-code-data string props)))))

(defun komokio-insert-code-def ()
  (interactive)
  (let ((code-name (read-string "Enter code name: ")))
    (setq cider-buffer-ns "komokio.emacs-helpers")
    (setq cider-repl-type "cljs")
    (let ((result  (cider-nrepl-sync-request:eval
                    (format
                     (concat
                      ;;"(in-ns 'komokio.emacs-helpers)"
                      ;;"(clojure.core/use 'komokio.emacs-helpers)"
                      "(plist-to-map '%s)") (prin1-to-string (komokio-buffer-to-data))))))
      (insert (format "(def code-%s %s)" code-name (nrepl-dict-get result "value"))))))
