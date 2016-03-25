(ns koeeoadi.code)

(def code
  {"javascript" {:code/name "javascript"
                 :code-chunks/list [{:code-chunk/face
                                     [:faces/by-name "comment-delimiter"]
                                     ,:code-chunk/string "// ",:code-chunk/line-chunk 1001}
                                    {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                                     "Sieve of Eratosthenes"
                                     ,:code-chunk/line-chunk 1002}
                                    {:code-chunk/face [:faces/by-name "comment-delimiter"],:code-chunk/string
                                     "// "
                                     ,:code-chunk/line-chunk 2001}
                                    {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                                     "https://rosettacode.org/wiki/Sieve_of_Eratosthenes#JavaScrip"
                                     ,:code-chunk/line-chunk 2002}
                                    {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                                     "t"
                                     ,:code-chunk/line-chunk 2003}
                                    {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                                     ""
                                     ,:code-chunk/line-chunk 2004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ""
                                     ,:code-chunk/line-chunk 3001}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "function"
                                     ,:code-chunk/line-chunk 4001}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 4002}
                                    {:code-chunk/face [:faces/by-name "function-name"],:code-chunk/string
                                     "eratosthenes"
                                     ,:code-chunk/line-chunk 4003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 4004}
                                    {:code-chunk/face [:faces/by-name "variable-name"],:code-chunk/string
                                     "limit"
                                     ,:code-chunk/line-chunk 4005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 4006}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 4007}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "{"
                                     ,:code-chunk/line-chunk 4008}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ""
                                     ,:code-chunk/line-chunk 4009}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "    "
                                     ,:code-chunk/line-chunk 5001}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "var"
                                     ,:code-chunk/line-chunk 5002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 5003}
                                    {:code-chunk/face [:faces/by-name "variable-name"],:code-chunk/string
                                     "primes"
                                     ,:code-chunk/line-chunk 5004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " = "
                                     ,:code-chunk/line-chunk 5005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "["
                                     ,:code-chunk/line-chunk 5006}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "]"
                                     ,:code-chunk/line-chunk 5007}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ";"
                                     ,:code-chunk/line-chunk 5008}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "    "
                                     ,:code-chunk/line-chunk 6001}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "if"
                                     ,:code-chunk/line-chunk 6002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 6003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 6004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "limit >= 2"
                                     ,:code-chunk/line-chunk 6005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 6006}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 6007}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "{"
                                     ,:code-chunk/line-chunk 6008}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "        "
                                     ,:code-chunk/line-chunk 7001}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "var"
                                     ,:code-chunk/line-chunk 7002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 7003}
                                    {:code-chunk/face [:faces/by-name "variable-name"],:code-chunk/string
                                     "sqrtlmt"
                                     ,:code-chunk/line-chunk 7004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " = Math.sqrt"
                                     ,:code-chunk/line-chunk 7005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 7006}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "limit"
                                     ,:code-chunk/line-chunk 7007}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 7008}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " - 2;\n        "
                                     ,:code-chunk/line-chunk 8001}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "var"
                                     ,:code-chunk/line-chunk 8002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 8003}
                                    {:code-chunk/face [:faces/by-name "variable-name"],:code-chunk/string
                                     "nums"
                                     ,:code-chunk/line-chunk 8004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " = "
                                     ,:code-chunk/line-chunk 8005}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "new"
                                     ,:code-chunk/line-chunk 8006}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 8007}
                                    {:code-chunk/face [:faces/by-name "type"],:code-chunk/string
                                     "Array"
                                     ,:code-chunk/line-chunk 8008}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 8009}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 8010}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "; "
                                     ,:code-chunk/line-chunk 8011}
                                    {:code-chunk/face [:faces/by-name "comment-delimiter"],:code-chunk/string
                                     "// "
                                     ,:code-chunk/line-chunk 8012}
                                    {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                                     "start with an empty Array..."
                                     ,:code-chunk/line-chunk 8013}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "        "
                                     ,:code-chunk/line-chunk 9001}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "for"
                                     ,:code-chunk/line-chunk 9002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 9003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 9004}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "var"
                                     ,:code-chunk/line-chunk 9005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 9006}
                                    {:code-chunk/face [:faces/by-name "variable-name"],:code-chunk/string
                                     "i"
                                     ,:code-chunk/line-chunk 9007}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " = 2; i <= limit; i++"
                                     ,:code-chunk/line-chunk 9008}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 9009}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 9010}
                                    {:code-chunk/face [:faces/by-name "comment-delimiter"],:code-chunk/string
                                     "// "
                                     ,:code-chunk/line-chunk 9011}
                                    {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                                     "and"
                                     ,:code-chunk/line-chunk 9012}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "            nums.push"
                                     ,:code-chunk/line-chunk 10001}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 10002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "i"
                                     ,:code-chunk/line-chunk 10003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 10004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "; "
                                     ,:code-chunk/line-chunk 10005}
                                    {:code-chunk/face [:faces/by-name "comment-delimiter"],:code-chunk/string
                                     "// "
                                     ,:code-chunk/line-chunk 10006}
                                    {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                                     "only initialize the Array once..."
                                     ,:code-chunk/line-chunk 10007}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "        "
                                     ,:code-chunk/line-chunk 11001}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "for"
                                     ,:code-chunk/line-chunk 11002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 11003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 11004}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "var"
                                     ,:code-chunk/line-chunk 11005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 11006}
                                    {:code-chunk/face [:faces/by-name "variable-name"],:code-chunk/string
                                     "i"
                                     ,:code-chunk/line-chunk 11007}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " = 0; i <= sqrtlmt; i++"
                                     ,:code-chunk/line-chunk 11008}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 11009}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 11010}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "{"
                                     ,:code-chunk/line-chunk 11011}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "            "
                                     ,:code-chunk/line-chunk 12001}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "var"
                                     ,:code-chunk/line-chunk 12002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 12003}
                                    {:code-chunk/face [:faces/by-name "variable-name"],:code-chunk/string
                                     "p"
                                     ,:code-chunk/line-chunk 12004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " = nums"
                                     ,:code-chunk/line-chunk 12005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "["
                                     ,:code-chunk/line-chunk 12006}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "i"
                                     ,:code-chunk/line-chunk 12007}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "]"
                                     ,:code-chunk/line-chunk 12008}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "            "
                                     ,:code-chunk/line-chunk 13001}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "if"
                                     ,:code-chunk/line-chunk 13002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 13003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 13004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "p"
                                     ,:code-chunk/line-chunk 13005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 13006}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "                "
                                     ,:code-chunk/line-chunk 14001}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "for"
                                     ,:code-chunk/line-chunk 14002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 14003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 14004}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "var"
                                     ,:code-chunk/line-chunk 14005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 14006}
                                    {:code-chunk/face [:faces/by-name "variable-name"],:code-chunk/string
                                     "j"
                                     ,:code-chunk/line-chunk 14007}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " = p * p - 2; j < nums.length; j += p"
                                     ,:code-chunk/line-chunk 14008}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 14009}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ""
                                     ,:code-chunk/line-chunk 14010}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "                    nums"
                                     ,:code-chunk/line-chunk 15001}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "["
                                     ,:code-chunk/line-chunk 15002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "j"
                                     ,:code-chunk/line-chunk 15003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "]"
                                     ,:code-chunk/line-chunk 15004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " = 0;\n        "
                                     ,:code-chunk/line-chunk 16001}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "}"
                                     ,:code-chunk/line-chunk 16002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "        "
                                     ,:code-chunk/line-chunk 17001}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "for"
                                     ,:code-chunk/line-chunk 17002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 17003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 17004}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "var"
                                     ,:code-chunk/line-chunk 17005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 17006}
                                    {:code-chunk/face [:faces/by-name "variable-name"],:code-chunk/string
                                     "i"
                                     ,:code-chunk/line-chunk 17007}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " = 0; i < nums.length; i++"
                                     ,:code-chunk/line-chunk 17008}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 17009}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 17010}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "{"
                                     ,:code-chunk/line-chunk 17011}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ""
                                     ,:code-chunk/line-chunk 17012}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "            "
                                     ,:code-chunk/line-chunk 18001}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "var"
                                     ,:code-chunk/line-chunk 18002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 18003}
                                    {:code-chunk/face [:faces/by-name "variable-name"],:code-chunk/string
                                     "p"
                                     ,:code-chunk/line-chunk 18004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " = nums"
                                     ,:code-chunk/line-chunk 18005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "["
                                     ,:code-chunk/line-chunk 18006}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "i"
                                     ,:code-chunk/line-chunk 18007}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "]"
                                     ,:code-chunk/line-chunk 18008}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ";"
                                     ,:code-chunk/line-chunk 18009}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "            "
                                     ,:code-chunk/line-chunk 19001}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "if"
                                     ,:code-chunk/line-chunk 19002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 19003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 19004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "p"
                                     ,:code-chunk/line-chunk 19005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 19006}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "                primes.push"
                                     ,:code-chunk/line-chunk 20001}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 20002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "p"
                                     ,:code-chunk/line-chunk 20003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 20004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ";\n        "
                                     ,:code-chunk/line-chunk 21001}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "}"
                                     ,:code-chunk/line-chunk 21002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "    "
                                     ,:code-chunk/line-chunk 22001}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "}"
                                     ,:code-chunk/line-chunk 22002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "    "
                                     ,:code-chunk/line-chunk 23001}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "return"
                                     ,:code-chunk/line-chunk 23002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " primes;"
                                     ,:code-chunk/line-chunk 23003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "}"
                                     ,:code-chunk/line-chunk 24001}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 25001}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "var"
                                     ,:code-chunk/line-chunk 26001}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 26002}
                                    {:code-chunk/face [:faces/by-name "variable-name"],:code-chunk/string
                                     "primes"
                                     ,:code-chunk/line-chunk 26003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " = eratosthenes"
                                     ,:code-chunk/line-chunk 26004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 26005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "100"
                                     ,:code-chunk/line-chunk 26006}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 26007}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ";\n "
                                     ,:code-chunk/line-chunk 27001}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "if"
                                     ,:code-chunk/line-chunk 28001}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 28002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 28003}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "typeof"
                                     ,:code-chunk/line-chunk 28004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " print == "
                                     ,:code-chunk/line-chunk 28005}
                                    {:code-chunk/face [:faces/by-name "string"],:code-chunk/string
                                     "\"undefined\""
                                     ,:code-chunk/line-chunk 28006}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 28007}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "    print = "
                                     ,:code-chunk/line-chunk 29001}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 29002}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "typeof"
                                     ,:code-chunk/line-chunk 29003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " WScript != "
                                     ,:code-chunk/line-chunk 29004}
                                    {:code-chunk/face [:faces/by-name "string"],:code-chunk/string
                                     "\"undefined\""
                                     ,:code-chunk/line-chunk 29005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 29006}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " ? WScript.Echo : alert;"
                                     ,:code-chunk/line-chunk 29007}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "print"
                                     ,:code-chunk/line-chunk 30001}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 30002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "primes"
                                     ,:code-chunk/line-chunk 30003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 30004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ";"
                                     ,:code-chunk/line-chunk 30005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ""
                                     ,:code-chunk/line-chunk 30006}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ""
                                     ,:code-chunk/line-chunk 31001}
                                    ]}

   "emacs-lisp" {:code/name "emacs-lisp"
                 :code-chunks/list [{:code-chunk/face
                                     [:faces/by-name "comment-delimiter"]
                                     ,:code-chunk/string ";",:code-chunk/line-chunk 1001}
                                    {:code-chunk/face [:faces/by-name "comment-delimiter"],:code-chunk/string
                                     "; "
                                     ,:code-chunk/line-chunk 1002}
                                    {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                                     "Sieve of Eratosthenes"
                                     ,:code-chunk/line-chunk 1003}
                                    {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                                     ""
                                     ,:code-chunk/line-chunk 1004}
                                    {:code-chunk/face [:faces/by-name "comment-delimiter"],:code-chunk/string
                                     ";"
                                     ,:code-chunk/line-chunk 2001}
                                    {:code-chunk/face [:faces/by-name "comment-delimiter"],:code-chunk/string
                                     "; "
                                     ,:code-chunk/line-chunk 2002}
                                    {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                                     "https://rosettacode.org/wiki/Sieve_of_Eratosthenes#Emacs_Lis"
                                     ,:code-chunk/line-chunk 2003}
                                    {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                                     "p"
                                     ,:code-chunk/line-chunk 2004}
                                    {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                                     ""
                                     ,:code-chunk/line-chunk 2005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ""
                                     ,:code-chunk/line-chunk 3001}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 4001}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "defun"
                                     ,:code-chunk/line-chunk 4002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 4003}
                                    {:code-chunk/face [:faces/by-name "function-name"],:code-chunk/string
                                     "sieve"
                                     ,:code-chunk/line-chunk 4004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 4005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 4006}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "limit"
                                     ,:code-chunk/line-chunk 4007}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 4008}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ""
                                     ,:code-chunk/line-chunk 4009}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "  "
                                     ,:code-chunk/line-chunk 5001}
                                    {:code-chunk/face [:faces/by-name "doc"],:code-chunk/string
                                     "\"Finds the primes between 2 and the given LIMIT."
                                     ,:code-chunk/line-chunk 5002}
                                    {:code-chunk/face [:faces/by-name "doc"],:code-chunk/string
                                     "\""
                                     ,:code-chunk/line-chunk 5003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ""
                                     ,:code-chunk/line-chunk 5004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "  "
                                     ,:code-chunk/line-chunk 6001}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 6002}
                                    {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                                     "let"
                                     ,:code-chunk/line-chunk 6003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 6004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 6005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 6006}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "xs "
                                     ,:code-chunk/line-chunk 6007}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 6008}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "vconcat "
                                     ,:code-chunk/line-chunk 6009}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "["
                                     ,:code-chunk/line-chunk 6010}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "0 0"
                                     ,:code-chunk/line-chunk 6011}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "]"
                                     ,:code-chunk/line-chunk 6012}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " "
                                     ,:code-chunk/line-chunk 6013}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 6014}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "number-sequence 2 limit"
                                     ,:code-chunk/line-chunk 6015}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 6016}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 6017}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 6018}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 6019}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ""
                                     ,:code-chunk/line-chunk 6020}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "    "
                                     ,:code-chunk/line-chunk 7001}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 7002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "loop for i from 2 to "
                                     ,:code-chunk/line-chunk 7003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 7004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "sqrt limit"
                                     ,:code-chunk/line-chunk 7005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 7006}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "          when "
                                     ,:code-chunk/line-chunk 8001}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 8002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "aref xs i"
                                     ,:code-chunk/line-chunk 8003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 8004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "          do "
                                     ,:code-chunk/line-chunk 9001}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 9002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "loop for m from "
                                     ,:code-chunk/line-chunk 9003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 9004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "* i i"
                                     ,:code-chunk/line-chunk 9005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 9006}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     " to limit by i\n                   do "
                                     ,:code-chunk/line-chunk 10001}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 10002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "aset xs m 0"
                                     ,:code-chunk/line-chunk 10003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 10004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 10005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 10006}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ""
                                     ,:code-chunk/line-chunk 10007}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "    "
                                     ,:code-chunk/line-chunk 11001}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "("
                                     ,:code-chunk/line-chunk 11002}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     "remove 0 xs"
                                     ,:code-chunk/line-chunk 11003}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 11004}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 11005}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ")"
                                     ,:code-chunk/line-chunk 11006}
                                    {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                                     ""
                                     ,:code-chunk/line-chunk 11007}
                                    ]}

   "c" {:code/name "c"
        :code-chunks/list [{:code-chunk/face
                            [:faces/by-name "preprocessor"]
                            ,:code-chunk/string "#",:code-chunk/line-chunk 1001}
                           {:code-chunk/face [:faces/by-name "preprocessor"],:code-chunk/string
                            "include"
                            ,:code-chunk/line-chunk 1002}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " "
                            ,:code-chunk/line-chunk 1003}
                           {:code-chunk/face [:faces/by-name "string"],:code-chunk/string
                            "<stdio.h>"
                            ,:code-chunk/line-chunk 1004}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ""
                            ,:code-chunk/line-chunk 1005}
                           {:code-chunk/face [:faces/by-name "preprocessor"],:code-chunk/string
                            "#"
                            ,:code-chunk/line-chunk 2001}
                           {:code-chunk/face [:faces/by-name "preprocessor"],:code-chunk/string
                            "include"
                            ,:code-chunk/line-chunk 2002}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " "
                            ,:code-chunk/line-chunk 2003}
                           {:code-chunk/face [:faces/by-name "string"],:code-chunk/string
                            "<malloc.h>"
                            ,:code-chunk/line-chunk 2004}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ""
                            ,:code-chunk/line-chunk 2005}
                           {:code-chunk/face [:faces/by-name "type"],:code-chunk/string
                            "v"
                            ,:code-chunk/line-chunk 3001}
                           {:code-chunk/face [:faces/by-name "type"],:code-chunk/string
                            "oi"
                            ,:code-chunk/line-chunk 3002}
                           {:code-chunk/face [:faces/by-name "type"],:code-chunk/string
                            "d"
                            ,:code-chunk/line-chunk 3003}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " "
                            ,:code-chunk/line-chunk 3004}
                           {:code-chunk/face [:faces/by-name "function-name"],:code-chunk/string
                            "sieve"
                            ,:code-chunk/line-chunk 3005}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 3006}
                           {:code-chunk/face [:faces/by-name "type"],:code-chunk/string
                            "int"
                            ,:code-chunk/line-chunk 3007}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " *"
                            ,:code-chunk/line-chunk 3008}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ","
                            ,:code-chunk/line-chunk 3009}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " "
                            ,:code-chunk/line-chunk 3010}
                           {:code-chunk/face [:faces/by-name "type"],:code-chunk/string
                            "int"
                            ,:code-chunk/line-chunk 3011}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ");"
                            ,:code-chunk/line-chunk 3012}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ""
                            ,:code-chunk/line-chunk 4001}
                           {:code-chunk/face [:faces/by-name "type"],:code-chunk/string
                            "in"
                            ,:code-chunk/line-chunk 5001}
                           {:code-chunk/face [:faces/by-name "type"],:code-chunk/string
                            "t"
                            ,:code-chunk/line-chunk 5002}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " "
                            ,:code-chunk/line-chunk 5003}
                           {:code-chunk/face [:faces/by-name "function-name"],:code-chunk/string
                            "main"
                            ,:code-chunk/line-chunk 5004}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 5005}
                           {:code-chunk/face [:faces/by-name "type"],:code-chunk/string
                            "int"
                            ,:code-chunk/line-chunk 5006}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " "
                            ,:code-chunk/line-chunk 5007}
                           {:code-chunk/face [:faces/by-name "variable-name"],:code-chunk/string
                            "argc"
                            ,:code-chunk/line-chunk 5008}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ","
                            ,:code-chunk/line-chunk 5009}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " "
                            ,:code-chunk/line-chunk 5010}
                           {:code-chunk/face [:faces/by-name "type"],:code-chunk/string
                            "char"
                            ,:code-chunk/line-chunk 5011}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " *"
                            ,:code-chunk/line-chunk 5012}
                           {:code-chunk/face [:faces/by-name "variable-name"],:code-chunk/string
                            "argv"
                            ,:code-chunk/line-chunk 5013}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ")"
                            ,:code-chunk/line-chunk 5014}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ""
                            ,:code-chunk/line-chunk 5015}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "{"
                            ,:code-chunk/line-chunk 6001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "    "
                            ,:code-chunk/line-chunk 7001}
                           {:code-chunk/face [:faces/by-name "type"],:code-chunk/string
                            "in"
                            ,:code-chunk/line-chunk 7002}
                           {:code-chunk/face [:faces/by-name "type"],:code-chunk/string
                            "t"
                            ,:code-chunk/line-chunk 7003}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " *"
                            ,:code-chunk/line-chunk 7004}
                           {:code-chunk/face [:faces/by-name "variable-name"],:code-chunk/string
                            "array"
                            ,:code-chunk/line-chunk 7005}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ","
                            ,:code-chunk/line-chunk 7006}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " "
                            ,:code-chunk/line-chunk 7007}
                           {:code-chunk/face [:faces/by-name "variable-name"],:code-chunk/string
                            "n"
                            ,:code-chunk/line-chunk 7008}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "=10;\n"
                            ,:code-chunk/line-chunk 7009}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "    array ="
                            ,:code-chunk/line-chunk 8001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 8002}
                           {:code-chunk/face [:faces/by-name "type"],:code-chunk/string
                            "int"
                            ,:code-chunk/line-chunk 8003}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " *"
                            ,:code-chunk/line-chunk 8004}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ")"
                            ,:code-chunk/line-chunk 8005}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "malloc"
                            ,:code-chunk/line-chunk 8006}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 8007}
                           {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                            "sizeof"
                            ,:code-chunk/line-chunk 8008}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 8009}
                           {:code-chunk/face [:faces/by-name "type"],:code-chunk/string
                            "int"
                            ,:code-chunk/line-chunk 8010}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ")"
                            ,:code-chunk/line-chunk 8011}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ");\n"
                            ,:code-chunk/line-chunk 8012}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "    sieve"
                            ,:code-chunk/line-chunk 9001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 9002}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "array,n"
                            ,:code-chunk/line-chunk 9003}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ");"
                            ,:code-chunk/line-chunk 9004}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "    "
                            ,:code-chunk/line-chunk 10001}
                           {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                            "return"
                            ,:code-chunk/line-chunk 10002}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " 0;"
                            ,:code-chunk/line-chunk 10003}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "}"
                            ,:code-chunk/line-chunk 11001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " "
                            ,:code-chunk/line-chunk 12001}
                           {:code-chunk/face [:faces/by-name "type"],:code-chunk/string
                            "voi"
                            ,:code-chunk/line-chunk 13001}
                           {:code-chunk/face [:faces/by-name "type"],:code-chunk/string
                            "d"
                            ,:code-chunk/line-chunk 13002}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " "
                            ,:code-chunk/line-chunk 13003}
                           {:code-chunk/face [:faces/by-name "function-name"],:code-chunk/string
                            "sieve"
                            ,:code-chunk/line-chunk 13004}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 13005}
                           {:code-chunk/face [:faces/by-name "type"],:code-chunk/string
                            "int"
                            ,:code-chunk/line-chunk 13006}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " *"
                            ,:code-chunk/line-chunk 13007}
                           {:code-chunk/face [:faces/by-name "variable-name"],:code-chunk/string
                            "a"
                            ,:code-chunk/line-chunk 13008}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ","
                            ,:code-chunk/line-chunk 13009}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " "
                            ,:code-chunk/line-chunk 13010}
                           {:code-chunk/face [:faces/by-name "type"],:code-chunk/string
                            "int"
                            ,:code-chunk/line-chunk 13011}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " "
                            ,:code-chunk/line-chunk 13012}
                           {:code-chunk/face [:faces/by-name "variable-name"],:code-chunk/string
                            "n"
                            ,:code-chunk/line-chunk 13013}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ")"
                            ,:code-chunk/line-chunk 13014}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ""
                            ,:code-chunk/line-chunk 13015}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "{"
                            ,:code-chunk/line-chunk 14001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "    "
                            ,:code-chunk/line-chunk 15001}
                           {:code-chunk/face [:faces/by-name "type"],:code-chunk/string
                            "in"
                            ,:code-chunk/line-chunk 15002}
                           {:code-chunk/face [:faces/by-name "type"],:code-chunk/string
                            "t"
                            ,:code-chunk/line-chunk 15003}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " "
                            ,:code-chunk/line-chunk 15004}
                           {:code-chunk/face [:faces/by-name "variable-name"],:code-chunk/string
                            "i"
                            ,:code-chunk/line-chunk 15005}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "=0"
                            ,:code-chunk/line-chunk 15006}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ","
                            ,:code-chunk/line-chunk 15007}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " "
                            ,:code-chunk/line-chunk 15008}
                           {:code-chunk/face [:faces/by-name "variable-name"],:code-chunk/string
                            "j"
                            ,:code-chunk/line-chunk 15009}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "=0;"
                            ,:code-chunk/line-chunk 15010}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "\n"
                            ,:code-chunk/line-chunk 16001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "\n"
                            ,:code-chunk/line-chunk 17001}
                           {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                            "    for"
                            ,:code-chunk/line-chunk 17002}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 17003}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "i=2; i<=n; i++"
                            ,:code-chunk/line-chunk 17004}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ")"
                            ,:code-chunk/line-chunk 17005}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " "
                            ,:code-chunk/line-chunk 17006}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "{"
                            ,:code-chunk/line-chunk 17007}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "        a"
                            ,:code-chunk/line-chunk 18001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "["
                            ,:code-chunk/line-chunk 18002}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "i"
                            ,:code-chunk/line-chunk 18003}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "] = 1;"
                            ,:code-chunk/line-chunk 18004}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ""
                            ,:code-chunk/line-chunk 19001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ""
                            ,:code-chunk/line-chunk 20001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "    }"
                            ,:code-chunk/line-chunk 19002}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "  "
                            ,:code-chunk/line-chunk 21001}
                           {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                            "  for"
                            ,:code-chunk/line-chunk 21002}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 21003}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "i=2; i<=n; i++"
                            ,:code-chunk/line-chunk 21004}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ")"
                            ,:code-chunk/line-chunk 21005}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " "
                            ,:code-chunk/line-chunk 21006}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "{"
                            ,:code-chunk/line-chunk 21007}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "        printf"
                            ,:code-chunk/line-chunk 22001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 22002}
                           {:code-chunk/face [:faces/by-name "string"],:code-chunk/string
                            "\"\\ni:%d\""
                            ,:code-chunk/line-chunk 22003}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ", i"
                            ,:code-chunk/line-chunk 22004}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ");"
                            ,:code-chunk/line-chunk 22005}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ""
                            ,:code-chunk/line-chunk 23001}
                           {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                            "        if"
                            ,:code-chunk/line-chunk 23002}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 23003}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "a"
                            ,:code-chunk/line-chunk 23004}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "["
                            ,:code-chunk/line-chunk 23005}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "i"
                            ,:code-chunk/line-chunk 23006}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "]"
                            ,:code-chunk/line-chunk 23007}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " == 1"
                            ,:code-chunk/line-chunk 23008}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ")"
                            ,:code-chunk/line-chunk 23009}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " "
                            ,:code-chunk/line-chunk 23010}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "{"
                            ,:code-chunk/line-chunk 23011}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "            "
                            ,:code-chunk/line-chunk 24001}
                           {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                            "for"
                            ,:code-chunk/line-chunk 24002}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 24003}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "j=i; "
                            ,:code-chunk/line-chunk 24004}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 24005}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "i*j"
                            ,:code-chunk/line-chunk 24006}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ")"
                            ,:code-chunk/line-chunk 24007}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "<=n; j++"
                            ,:code-chunk/line-chunk 24008}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ")"
                            ,:code-chunk/line-chunk 24009}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " "
                            ,:code-chunk/line-chunk 24010}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "{"
                            ,:code-chunk/line-chunk 24011}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "                printf "
                            ,:code-chunk/line-chunk 25001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 25002}
                           {:code-chunk/face [:faces/by-name "string"],:code-chunk/string
                            "\"\\nj:%d\""
                            ,:code-chunk/line-chunk 25003}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ", j"
                            ,:code-chunk/line-chunk 25004}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ");"
                            ,:code-chunk/line-chunk 25005}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "                printf"
                            ,:code-chunk/line-chunk 26001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 26002}
                           {:code-chunk/face [:faces/by-name "string"],:code-chunk/string
                            "\"\\nBefore a[%d*%d]: %d\""
                            ,:code-chunk/line-chunk 26003}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ", i, j, a"
                            ,:code-chunk/line-chunk 26004}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "["
                            ,:code-chunk/line-chunk 26005}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "i*j"
                            ,:code-chunk/line-chunk 26006}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "]"
                            ,:code-chunk/line-chunk 26007}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ")"
                            ,:code-chunk/line-chunk 26008}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ";"
                            ,:code-chunk/line-chunk 26009}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "                a"
                            ,:code-chunk/line-chunk 27001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "["
                            ,:code-chunk/line-chunk 27002}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 27003}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "i*j"
                            ,:code-chunk/line-chunk 27004}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ")"
                            ,:code-chunk/line-chunk 27005}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "] = 0;"
                            ,:code-chunk/line-chunk 27006}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "                printf"
                            ,:code-chunk/line-chunk 28001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 28002}
                           {:code-chunk/face [:faces/by-name "string"],:code-chunk/string
                            "\"\\nAfter a[%d*%d]: %d\""
                            ,:code-chunk/line-chunk 28003}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ", i, j, a"
                            ,:code-chunk/line-chunk 28004}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "["
                            ,:code-chunk/line-chunk 28005}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "i*j"
                            ,:code-chunk/line-chunk 28006}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "]"
                            ,:code-chunk/line-chunk 28007}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ");"
                            ,:code-chunk/line-chunk 28008}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "            "
                            ,:code-chunk/line-chunk 29001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "}"
                            ,:code-chunk/line-chunk 29002}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "        "
                            ,:code-chunk/line-chunk 30001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "}"
                            ,:code-chunk/line-chunk 30002}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "    "
                            ,:code-chunk/line-chunk 31001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "}"
                            ,:code-chunk/line-chunk 31002}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " \n    printf"
                            ,:code-chunk/line-chunk 33001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 33002}
                           {:code-chunk/face [:faces/by-name "string"],:code-chunk/string
                            "\"\\nPrimes numbers from 1 to %d are : \""
                            ,:code-chunk/line-chunk 33003}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ", n"
                            ,:code-chunk/line-chunk 33004}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ");"
                            ,:code-chunk/line-chunk 33005}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "    "
                            ,:code-chunk/line-chunk 34001}
                           {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                            "for"
                            ,:code-chunk/line-chunk 34002}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 34003}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "i=2; i<=n; i++"
                            ,:code-chunk/line-chunk 34004}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ")"
                            ,:code-chunk/line-chunk 34005}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " "
                            ,:code-chunk/line-chunk 34006}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "{"
                            ,:code-chunk/line-chunk 34007}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "        "
                            ,:code-chunk/line-chunk 35001}
                           {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                            "if"
                            ,:code-chunk/line-chunk 35002}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 35003}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "a"
                            ,:code-chunk/line-chunk 35004}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "["
                            ,:code-chunk/line-chunk 35005}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "i"
                            ,:code-chunk/line-chunk 35006}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "]"
                            ,:code-chunk/line-chunk 35007}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            " == 1"
                            ,:code-chunk/line-chunk 35008}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ")"
                            ,:code-chunk/line-chunk 35009}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "            printf"
                            ,:code-chunk/line-chunk 36001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 36002}
                           {:code-chunk/face [:faces/by-name "string"],:code-chunk/string
                            "\"%d, \""
                            ,:code-chunk/line-chunk 36003}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ", i"
                            ,:code-chunk/line-chunk 36004}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ");"
                            ,:code-chunk/line-chunk 36005}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "    "
                            ,:code-chunk/line-chunk 37001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "}"
                            ,:code-chunk/line-chunk 37002}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "    printf"
                            ,:code-chunk/line-chunk 38001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "("
                            ,:code-chunk/line-chunk 38002}
                           {:code-chunk/face [:faces/by-name "string"],:code-chunk/string
                            "\"\\n\\n\""
                            ,:code-chunk/line-chunk 38003}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ")"
                            ,:code-chunk/line-chunk 38004}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ";"
                            ,:code-chunk/line-chunk 38005}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            "}"
                            ,:code-chunk/line-chunk 39001}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ""
                            ,:code-chunk/line-chunk 39002}
                           {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                            ""
                            ,:code-chunk/line-chunk 40001}
                           ]}


   "ruby" {:code/name "ruby"
           :code-chunks/list [{:code-chunk/face
                               [:faces/by-name "comment-delimiter"]
                               ,:code-chunk/string "# ",:code-chunk/line-chunk 1001}
                              {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                               "Sieve of Erastosthenes "
                               ,:code-chunk/line-chunk 1002}
                              {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                               ""
                               ,:code-chunk/line-chunk 1003}
                              {:code-chunk/face [:faces/by-name "comment-delimiter"],:code-chunk/string
                               "#"
                               ,:code-chunk/line-chunk 2001}
                              {:code-chunk/face [:faces/by-name "comment-delimiter"],:code-chunk/string
                               " "
                               ,:code-chunk/line-chunk 2002}
                              {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                               "https"
                               ,:code-chunk/line-chunk 2003}
                              {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                               ":/"
                               ,:code-chunk/line-chunk 2004}
                              {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                               "/rosettacode.org/wiki/"
                               ,:code-chunk/line-chunk 2005}
                              {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                               "Sieve_of_Eratosthenes"
                               ,:code-chunk/line-chunk 2006}
                              {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                               "#"
                               ,:code-chunk/line-chunk 2007}
                              {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                               "Rub"
                               ,:code-chunk/line-chunk 2008}
                              {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                               "y"
                               ,:code-chunk/line-chunk 2009}
                              {:code-chunk/face [:faces/by-name "comment"],:code-chunk/string
                               ""
                               ,:code-chunk/line-chunk 2010}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               ""
                               ,:code-chunk/line-chunk 3001}
                              {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                               "def"
                               ,:code-chunk/line-chunk 4001}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               " "
                               ,:code-chunk/line-chunk 4002}
                              {:code-chunk/face [:faces/by-name "function-name"],:code-chunk/string
                               "eratosthenes"
                               ,:code-chunk/line-chunk 4003}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "("
                               ,:code-chunk/line-chunk 4004}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "n"
                               ,:code-chunk/line-chunk 4005}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               ")"
                               ,:code-chunk/line-chunk 4006}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               ""
                               ,:code-chunk/line-chunk 4007}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "  nums = "
                               ,:code-chunk/line-chunk 5001}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "["
                               ,:code-chunk/line-chunk 5002}
                              {:code-chunk/face [:faces/by-name "constant"],:code-chunk/string
                               "nil"
                               ,:code-chunk/line-chunk 5003}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               ", "
                               ,:code-chunk/line-chunk 5004}
                              {:code-chunk/face [:faces/by-name "constant"],:code-chunk/string
                               "nil"
                               ,:code-chunk/line-chunk 5005}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               ", *2..n"
                               ,:code-chunk/line-chunk 5006}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "]"
                               ,:code-chunk/line-chunk 5007}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "  "
                               ,:code-chunk/line-chunk 6001}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "("
                               ,:code-chunk/line-chunk 6002}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "2.."
                               ,:code-chunk/line-chunk 6003}
                              {:code-chunk/face [:faces/by-name "type"],:code-chunk/string
                               "Math"
                               ,:code-chunk/line-chunk 6004}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               ".sqrt"
                               ,:code-chunk/line-chunk 6005}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "("
                               ,:code-chunk/line-chunk 6006}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "n"
                               ,:code-chunk/line-chunk 6007}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               ")"
                               ,:code-chunk/line-chunk 6008}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               ")"
                               ,:code-chunk/line-chunk 6009}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               ".each "
                               ,:code-chunk/line-chunk 6010}
                              {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                               "do"
                               ,:code-chunk/line-chunk 6011}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               " |i|"
                               ,:code-chunk/line-chunk 6012}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "  \n"
                               ,:code-chunk/line-chunk 7001}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "("
                               ,:code-chunk/line-chunk 7002}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "i**2..n"
                               ,:code-chunk/line-chunk 7003}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               ")"
                               ,:code-chunk/line-chunk 7004}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               ".step"
                               ,:code-chunk/line-chunk 7005}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "("
                               ,:code-chunk/line-chunk 7006}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "i"
                               ,:code-chunk/line-chunk 7007}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               ")"
                               ,:code-chunk/line-chunk 7008}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "{"
                               ,:code-chunk/line-chunk 7009}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "|m| nums"
                               ,:code-chunk/line-chunk 7010}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "["
                               ,:code-chunk/line-chunk 7011}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "m"
                               ,:code-chunk/line-chunk 7012}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "]"
                               ,:code-chunk/line-chunk 7013}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               " = "
                               ,:code-chunk/line-chunk 7014}
                              {:code-chunk/face [:faces/by-name "constant"],:code-chunk/string
                               "nil"
                               ,:code-chunk/line-chunk 7015}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "}"
                               ,:code-chunk/line-chunk 7016}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "  "
                               ,:code-chunk/line-chunk 7017}
                              {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                               "if"
                               ,:code-chunk/line-chunk 7018}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               " nums"
                               ,:code-chunk/line-chunk 7019}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "["
                               ,:code-chunk/line-chunk 7020}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "i"
                               ,:code-chunk/line-chunk 7021}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "]"
                               ,:code-chunk/line-chunk 7022}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "  "
                               ,:code-chunk/line-chunk 8001}
                              {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                               "end"
                               ,:code-chunk/line-chunk 8002}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "  nums.compact"
                               ,:code-chunk/line-chunk 9001}
                              {:code-chunk/face [:faces/by-name "keyword"],:code-chunk/string
                               "end"
                               ,:code-chunk/line-chunk 10001}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               " "
                               ,:code-chunk/line-chunk 11001}
                              {:code-chunk/face [:faces/by-name "builtin"],:code-chunk/string
                               "p"
                               ,:code-chunk/line-chunk 12001}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               " eratosthenes"
                               ,:code-chunk/line-chunk 12002}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "("
                               ,:code-chunk/line-chunk 12003}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               "100"
                               ,:code-chunk/line-chunk 12004}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               ")"
                               ,:code-chunk/line-chunk 12005}
                              {:code-chunk/face [:faces/by-name "default"],:code-chunk/string
                               ""
                               ,:code-chunk/line-chunk 12006}
                              ]}})
