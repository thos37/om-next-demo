(def project 'om-next-demo)
(def version "0.1.0-SNAPSHOT")
(set-env!
  :project project
  :version version
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths   #{"src/cljs" "src/clj"}
  :resource-paths #{"resources"}

  :dependencies '[

                  [org.clojure/clojure        "1.7.0"]
                  [org.clojure/clojurescript  "1.7.145"]
                  [com.datomic/datomic-free   "0.9.5327" :exclusions [joda-time]]
                  [bidi                       "1.20.3"]
                  [org.omcljs/om              "1.0.0-alpha3"]
                  [ring/ring                  "1.4.0"]
                  [com.cognitect/transit-clj  "0.8.283"]
                  [com.cognitect/transit-cljs "0.8.225"]
                  [cljs-http                  "0.1.30" :exclusions
                                                       [org.clojure/clojure
                                                        org.clojure/clojurescript
                                                        com.cognitect/transit-cljs]]
                  [com.stuartsierra/component "0.3.0"]

                  ; dev ===============================
                  [boot/core                 "2.3.0"     :scope "test"]
                  [adzerk/boot-cljs          "1.7.48-6"  :scope "test"]
                  [adzerk/boot-cljs-repl     "0.2.0"     :scope "test"]
                  [adzerk/boot-reload        "0.4.1"     :scope "test"]
                  [adzerk/boot-test          "1.0.4"     :scope "test"]

                  ; server ===============================
                  [org.danielsz/system       "0.1.9"]
                  [reloaded.repl             "0.2.1"]

                  ])

(boot.core/load-data-readers!)

(require
  '[adzerk.boot-cljs       :refer [cljs]]
  '[adzerk.boot-cljs-repl  :refer [cljs-repl start-repl]]
  '[adzerk.boot-reload     :refer [reload]]
  '[system.boot            :refer [system run]]
  '[reloaded.repl          :refer [go reset start stop]]
  '[adzerk.boot-test       :refer [test]]
  '[todomvc.system]
  )

(task-options! pom {:project project
                    :version (str version "-standalone")
                    :description "FIXME: write description"
                    :license {"License Name" "All Rights Reserved"}}
               aot {:namespace #{'todomvc.main}}
               jar {:main 'todomvc.main}
               cljs {:source-map true}
               )

;(deftask generic
;         "generic boot task"
;         []
;         (fn [next-task]
;           (fn [fileset]
;             (next-task fileset))))

(deftask dev
         "Run a restartable system in the Repl"
         []
         (comp
           (watch :verbose true)
           (system :sys #(todomvc.system/dev-system {:db-uri "datomic:mem://localhost:4334/todos" :web-port 8081})
                   :auto-start true
                   :hot-reload true
                   :files ["server.clj" "parser.clj" "middleware.clj"]) ; server code will be reloaded when these files change
           (speak)
           (cljs-repl)   ;; to access browser repl, open a second terminal, "boot repl -c", then (start-repl)
           (reload)
           (cljs :source-map true :optimizations :none )
           ;; cljs compiler-options :output-to :output-dir
           ;; are set via src/cljs/public/js/app.cljs.edn
           ;; see https://github.com/adzerk-oss/boot-cljs/issues/104
           ;(repl :server true)
         ))

; (deftask run-tests []
;          (test))
;
; (deftask autotest []
;          (comp
;            (watch)
;            (run-tests)))
;)

;(deftask dev-run
;  "Run a dev system from the command line"
;  []
;  (comp
;   (environ :env {:http-port 3000})
;   (cljs)
;   (run :main-namespace "todomvc.core" :arguments [#'dev-sys])
;   (wait)))
;
;(deftask prod-run
;  "Run a prod system from the command line"
;  []
;  (comp
;   (environ :env {:http-port 8008
;                  :repl-port 8009})
;   (cljs :optimizations :advanced)
;   (run :main-namespace "todomvc.core" :arguments [#'prod-sys])
;   (wait)))

;(deftask build
;         "Build my project."
;         []
;         (comp (aot)
;               (pom)
;               (uber)
;               (jar)))