(ns koeeoadi.themes)

(def themes {"solarized-dark" {:theme/name "solarized-dark" 
                               :colors/list
                                [[:colors/by-id 0]
                                 [:colors/by-id 1]
                                 [:colors/by-id 2]
                                 [:colors/by-id 3]
                                 [:colors/by-id 4]
                                 [:colors/by-id 5]
                                 [:colors/by-id 6]
                                 [:colors/by-id 7]
                                 [:colors/by-id 8]
                                 [:colors/by-id 9]],
                                :faces/list
                                [[:faces/by-name "default"]
                                 [:faces/by-name "background"]
                                 [:faces/by-name "builtin"]
                                 [:faces/by-name "comment-delimiter"]
                                 [:faces/by-name "comment"]
                                 [:faces/by-name "constant"]
                                 [:faces/by-name "doc"]
                                 [:faces/by-name "function-name"]
                                 [:faces/by-name "keyword"]
                                 [:faces/by-name "preprocessor"]
                                 [:faces/by-name "string"]
                                 [:faces/by-name "type"]
                                 [:faces/by-name "variable-name"]],
                                :colors/by-id
                                {0 {:color/id 0, :color/rgb "#6c71c4"},
                                 1 {:color/id 1, :color/rgb "#586e75"},
                                 2 {:color/id 2, :color/rgb "#268bd2"},
                                 3 {:color/id 3, :color/rgb "#d33682"},
                                 4 {:color/id 4, :color/rgb "#b58900"},
                                 5 {:color/id 5, :color/rgb "#859900"},
                                 6 {:color/id 6, :color/rgb "#2aa198"},
                                 7 {:color/id 7, :color/rgb "#dc322f"},
                                 8 {:color/id 8, :color/rgb "#073642"},
                                 9 {:color/id 9, :color/rgb "#93a1a1"}},
                                :faces/by-name
                                {"function-name"
                                 {:face/id 8,
                                  :face/name "function-name",
                                  :face/color-fg [:colors/by-id 4]},
                                 "string"
                                 {:face/id 14, :face/name "string", :face/color-fg [:colors/by-id 6]},
                                 "comment-delimiter"
                                 {:face/id 3,
                                  :face/name "comment-delimiter",
                                  :face/color-fg [:colors/by-id 1]},
                                 "constant"
                                 {:face/id 5, :face/name "constant", :face/color-fg [:colors/by-id 2]},
                                 "preprocessor"
                                 {:face/id 11,
                                  :face/name "preprocessor",
                                  :face/color-fg [:colors/by-id 3]},
                                 "keyword"
                                 {:face/id 9, :face/name "keyword", :face/color-fg [:colors/by-id 5]},
                                 "variable-name"
                                 {:face/id 16,
                                  :face/name "variable-name",
                                  :face/color-fg [:colors/by-id 0]},
                                 "type"
                                 {:face/id 15, :face/name "type", :face/color-fg [:colors/by-id 2]},
                                 "comment"
                                 {:face/id 4, :face/name "comment", :face/color-fg [:colors/by-id 1]},
                                 "builtin"
                                 {:face/id 2, :face/name "builtin", :face/color-fg [:colors/by-id 0]},
                                 "background"
                                 {:face/id 1, :face/name "background", :face/color-fg [:colors/by-id 8]},
                                 "default"
                                 {:face/id 0, :face/name "default", :face/color-fg [:colors/by-id 9]},
                                 "doc" {:face/id 6, :face/name "doc", :face/color-fg [:colors/by-id 3]}}}

             "basic-bw" {:theme/name "basic-bw" 
                         :colors/list [[:colors/by-id 0] [:colors/by-id 1]],
                          :faces/list
                          [[:faces/by-name "default"]
                           [:faces/by-name "background"]
                           [:faces/by-name "builtin"]
                           [:faces/by-name "comment-delimiter"]
                           [:faces/by-name "comment"]
                           [:faces/by-name "constant"]
                           [:faces/by-name "doc"]
                           [:faces/by-name "function-name"]
                           [:faces/by-name "keyword"]
                           [:faces/by-name "preprocessor"]
                           [:faces/by-name "string"]
                           [:faces/by-name "type"]
                           [:faces/by-name "variable-name"]],
                          :colors/by-id
                          {0 {:color/id 0, :color/rgb "#000000"},
                           1 {:color/id 1, :color/rgb "#FFFFFF"}},
                          :faces/by-name
                          {"function-name"
                           {:face/id 8,
                            :face/name "function-name",
                            :face/color-fg [:colors/by-id 0]},
                           "string"
                           {:face/id 14, :face/name "string", :face/color-fg [:colors/by-id 0]},
                           "comment-delimiter"
                           {:face/id 3,
                            :face/name "comment-delimiter",
                            :face/color-fg [:colors/by-id 0]},
                           "constant"
                           {:face/id 5, :face/name "constant", :face/color-fg [:colors/by-id 0]},
                           "preprocessor"
                           {:face/id 11,
                            :face/name "preprocessor",
                            :face/color-fg [:colors/by-id 0]},
                           "keyword"
                           {:face/id 9, :face/name "keyword", :face/color-fg [:colors/by-id 0]},
                           "variable-name"
                           {:face/id 16,
                            :face/name "variable-name",
                            :face/color-fg [:colors/by-id 0]},
                           "type"
                           {:face/id 15, :face/name "type", :face/color-fg [:colors/by-id 0]},
                           "comment"
                           {:face/id 4, :face/name "comment", :face/color-fg [:colors/by-id 0]},
                           "builtin"
                           {:face/id 2, :face/name "builtin", :face/color-fg [:colors/by-id 0]},
                           "background"
                           {:face/id 1, :face/name "background", :face/color-fg [:colors/by-id 1]},
                           "default"
                           {:face/id 0, :face/name "default", :face/color-fg [:colors/by-id 0]},
                           "doc" {:face/id 6, :face/name "doc", :face/color-fg [:colors/by-id 0]}}}

             "basic-light" {:theme/name "basic-light" 
                            :colors/list
                             [[:colors/by-id 0]
                              [:colors/by-id 1]
                              [:colors/by-id 2]
                              [:colors/by-id 3]
                              [:colors/by-id 4]
                              [:colors/by-id 5]
                              [:colors/by-id 6]],
                             :faces/list
                             [[:faces/by-name "default"]
                              [:faces/by-name "background"]
                              [:faces/by-name "builtin"]
                              [:faces/by-name "comment-delimiter"]
                              [:faces/by-name "comment"]
                              [:faces/by-name "constant"]
                              [:faces/by-name "doc"]
                              [:faces/by-name "function-name"]
                              [:faces/by-name "keyword"]
                              [:faces/by-name "preprocessor"]
                              [:faces/by-name "string"]
                              [:faces/by-name "type"]
                              [:faces/by-name "variable-name"]],
                             :colors/by-id
                             {0 {:color/id 0, :color/rgb "#000000"},
                              1 {:color/id 1, :color/rgb "#e6e6e6"},
                              2 {:color/id 2, :color/rgb "#656565"},
                              3 {:color/id 3, :color/rgb "#ff0000"},
                              4 {:color/id 4, :color/rgb "#0c5f27"},
                              5 {:color/id 5, :color/rgb "#7a0888"},
                              6 {:color/id 6, :color/rgb "#d8cd28"}},
                             :faces/by-name
                             {"function-name"
                              {:face/id 8,
                               :face/name "function-name",
                               :face/color-fg [:colors/by-id 5]},
                              "string"
                              {:face/id 14, :face/name "string", :face/color-fg [:colors/by-id 5]},
                              "comment-delimiter"
                              {:face/id 3,
                               :face/name "comment-delimiter",
                               :face/color-fg [:colors/by-id 2]},
                              "constant"
                              {:face/id 5, :face/name "constant", :face/color-fg [:colors/by-id 0]},
                              "preprocessor"
                              {:face/id 11,
                               :face/name "preprocessor",
                               :face/color-fg [:colors/by-id 6]},
                              "keyword"
                              {:face/id 9, :face/name "keyword", :face/color-fg [:colors/by-id 3]},
                              "variable-name"
                              {:face/id 16,
                               :face/name "variable-name",
                               :face/color-fg [:colors/by-id 5]},
                              "type"
                              {:face/id 15, :face/name "type", :face/color-fg [:colors/by-id 3]},
                              "comment"
                              {:face/id 4, :face/name "comment", :face/color-fg [:colors/by-id 2]},
                              "builtin"
                              {:face/id 2, :face/name "builtin", :face/color-fg [:colors/by-id 3]},
                              "background"
                              {:face/id 1, :face/name "background", :face/color-fg [:colors/by-id 1]},
                              "default"
                              {:face/id 0, :face/name "default", :face/color-fg [:colors/by-id 0]},
                              "doc" {:face/id 6, :face/name "doc", :face/color-fg [:colors/by-id 4]}}}

             "mouse" {:theme/name "mouse" 
                      :colors/list
                       [[:colors/by-id 0]
                        [:colors/by-id 1]
                        [:colors/by-id 2]
                        [:colors/by-id 3]
                        [:colors/by-id 4]
                        [:colors/by-id 5]],
                       :faces/list
                       [[:faces/by-name "default"]
                        [:faces/by-name "background"]
                        [:faces/by-name "builtin"]
                        [:faces/by-name "comment-delimiter"]
                        [:faces/by-name "comment"]
                        [:faces/by-name "constant"]
                        [:faces/by-name "doc"]
                        [:faces/by-name "function-name"]
                        [:faces/by-name "keyword"]
                        [:faces/by-name "preprocessor"]
                        [:faces/by-name "string"]
                        [:faces/by-name "type"]
                        [:faces/by-name "variable-name"]],
                       :colors/by-id
                       {0 {:color/id 0, :color/rgb "#737272"},
                        1 {:color/id 1, :color/rgb "#2e2e2e"},
                        2 {:color/id 2, :color/rgb "#ff2fd3"},
                        3 {:color/id 3, :color/rgb "#bfbfbf"},
                        4 {:color/id 4, :color/rgb "#dfde31"},
                        5 {:color/id 5, :color/rgb "#ffffff"},
                        6 {:color/id 6, :color/rgb "#2aa198"},
                        7 {:color/id 7, :color/rgb "#dc322f"},
                        8 {:color/id 8, :color/rgb "#073642"},
                        9 {:color/id 9, :color/rgb "#93a1a1"}},
                       :faces/by-name
                       {"function-name"
                        {:face/id 8,
                         :face/name "function-name",
                         :face/color-fg [:colors/by-id 4]},
                        "string"
                        {:face/id 14, :face/name "string", :face/color-fg [:colors/by-id 5]},
                        "comment-delimiter"
                        {:face/id 3,
                         :face/name "comment-delimiter",
                         :face/color-fg [:colors/by-id 0]},
                        "constant"
                        {:face/id 5, :face/name "constant", :face/color-fg [:colors/by-id 4]},
                        "preprocessor"
                        {:face/id 11,
                         :face/name "preprocessor",
                         :face/color-fg [:colors/by-id 0]},
                        "keyword"
                        {:face/id 9, :face/name "keyword", :face/color-fg [:colors/by-id 2]},
                        "variable-name"
                        {:face/id 16,
                         :face/name "variable-name",
                         :face/color-fg [:colors/by-id 4]},
                        "type"
                        {:face/id 15, :face/name "type", :face/color-fg [:colors/by-id 0]},
                        "comment"
                        {:face/id 4, :face/name "comment", :face/color-fg [:colors/by-id 0]},
                        "builtin"
                        {:face/id 2, :face/name "builtin", :face/color-fg [:colors/by-id 4]},
                        "background"
                        {:face/id 1, :face/name "background", :face/color-fg [:colors/by-id 1]},
                        "default"
                        {:face/id 0, :face/name "default", :face/color-fg [:colors/by-id 3]},
                        "doc" {:face/id 6, :face/name "doc", :face/color-fg [:colors/by-id 0]}}}

             "electric-toothbrush" {:theme/name "electric-toothbrush" 
                                    :colors/list
                                    [[:colors/by-id 0]
                                     [:colors/by-id 1]
                                     [:colors/by-id 2]
                                     [:colors/by-id 3]
                                     [:colors/by-id 4]
                                     [:colors/by-id 5]
                                     [:colors/by-id 6]
                                     [:colors/by-id 7]
                                     [:colors/by-id 8]
                                     [:colors/by-id 9]],
                                    :faces/list
                                    [[:faces/by-name "default"]
                                     [:faces/by-name "background"]
                                     [:faces/by-name "builtin"]
                                     [:faces/by-name "comment-delimiter"]
                                     [:faces/by-name "comment"]
                                     [:faces/by-name "constant"]
                                     [:faces/by-name "doc"]
                                     [:faces/by-name "function-name"]
                                     [:faces/by-name "keyword"]
                                     [:faces/by-name "preprocessor"]
                                     [:faces/by-name "string"]
                                     [:faces/by-name "type"]
                                     [:faces/by-name "variable-name"]],
                                    :colors/by-id
                                    {0 {:color/id 0, :color/rgb "#151a73"},
                                     1 {:color/id 1, :color/rgb "#4c5557"},
                                     2 {:color/id 2, :color/rgb "#0096ff"},
                                     3 {:color/id 3, :color/rgb "#ffa3d0"},
                                     4 {:color/id 4, :color/rgb "#ffc200"},
                                     5 {:color/id 5, :color/rgb "#d9fa00"},
                                     6 {:color/id 6, :color/rgb "#7ffff6"},
                                     7 {:color/id 7, :color/rgb "#dc322f"},
                                     8 {:color/id 8, :color/rgb "#8d8d8d"},
                                     9 {:color/id 9, :color/rgb "#8e0081"}},
                                    :faces/by-name
                                    {"function-name"
                                     {:face/id 8,
                                      :face/name "function-name",
                                      :face/color-fg [:colors/by-id 4]},
                                     "string"
                                     {:face/id 14, :face/name "string", :face/color-fg [:colors/by-id 6]},
                                     "comment-delimiter"
                                     {:face/id 3,
                                      :face/name "comment-delimiter",
                                      :face/color-fg [:colors/by-id 1]},
                                     "constant"
                                     {:face/id 5, :face/name "constant", :face/color-fg [:colors/by-id 2]},
                                     "preprocessor"
                                     {:face/id 11,
                                      :face/name "preprocessor",
                                      :face/color-fg [:colors/by-id 3]},
                                     "keyword"
                                     {:face/id 9, :face/name "keyword", :face/color-fg [:colors/by-id 5]},
                                     "variable-name"
                                     {:face/id 16,
                                      :face/name "variable-name",
                                      :face/color-fg [:colors/by-id 0]},
                                     "type"
                                     {:face/id 15, :face/name "type", :face/color-fg [:colors/by-id 2]},
                                     "comment"
                                     {:face/id 4, :face/name "comment", :face/color-fg [:colors/by-id 1]},
                                     "builtin"
                                     {:face/id 2, :face/name "builtin", :face/color-fg [:colors/by-id 0]},
                                     "background"
                                     {:face/id 1, :face/name "background", :face/color-fg [:colors/by-id 8]},
                                     "default"
                                     {:face/id 0, :face/name "default", :face/color-fg [:colors/by-id 9]},
                                     "doc" {:face/id 6, :face/name "doc", :face/color-fg [:colors/by-id 3]}}}})
