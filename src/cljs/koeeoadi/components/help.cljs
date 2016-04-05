(ns koeeoadi.components.help
  (:require [om.next :as om]
            [om.dom :as dom]
            [koeeoadi.util :as util]))

(defn help [comp]
  (let [show-help (:show-help (om/get-state comp))]
    (dom/div #js {:id "help-modal"
                  :className "modal"
                  :style (util/display show-help)}
      (dom/div #js {:className "modal-content-container"}
        (dom/div #js {:className "modal-content"}
          (dom/h5 #js {:className "modal-title"} "Quick Start:")
          (dom/ol nil
            (dom/li nil "Open a new theme or choose a preset in the 'THEME' section.")
            (dom/li nil "Pick your language in the 'LANGUAGE' section.")
            (dom/li nil "Use the 'PALETTE' section to edit your theme's colors.")
            (dom/li nil "Click directly on the code to edit that syntax's color.")
            (dom/li nil "You can also edit colors and additional styles in the 'FACES' section.")
            (dom/li nil "Click on the 'EDIT USER FACES' button to add editor-specific faces.")
            (dom/li nil "Use the Export buttons when you're done to download a theme for your favorite editor.")
            (dom/li nil "You can also save the current theme or load an existing one using the 'THEME' controls."))
          (dom/h5 #js {:className "modal-title"} "User Faces:")
          (dom/p nil
            "Koeeoadi expects you to style the basic syntax faces
                listed in the 'FACE EDITOR'.  However, Emacs and Vim
                support hundreds of other faces and highlighting
                groups respectively.  You can add styles for these by
                clicking the 'EDIT USER FACES' button. This will bring
                up an interface for editing the user defined faces.")
          (dom/p nil
            "You can find a list of the most commonly used faces
                and highlighting groups below.")
          (dom/div nil
            (dom/a #js {:href "https://github.com/seanirby/koeeoadi/blob/master/common-emacs-faces"}
              "Common Emacs Faces"))
          (dom/div nil
            (dom/a #js {:href "https://github.com/seanirby/koeeoadi/blob/master/common-vim-highlight-groups"}
              "Common Vim Highlighting Groups"))
          (dom/p nil
            "These may not be available in your editor depending on your configuration so add accordingly.")
          (dom/p nil
            "Thanks for trying out Koeeoadi and have a nice day.")
          (dom/p nil
            "- sean"))
        (dom/button #js {:className "modal-close-button"
                         :onClick #(om/update-state! comp assoc :show-help false)} "CLOSE")))))
