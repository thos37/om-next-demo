
# Om TodoMVC Example

> Om is a ClojureScript UI component library over React.

> _[Om - github.com/swannodette/om](http://github.com/swannodette/om)_

This is a version of David Nolen's om-next-demo that uses Boot to run the server, auto-reload CLJ and CLJS files, and run a browser (B)REPL.  It uses [Danielsz/System](https://github.com/danielsz/system) to enable server-side [Component](https://github.com/stuartsierra/component) reloading and [boot-reload](https://github.com/adzerk-oss/boot-reload) for CLJS reloading.

To run it:
```bash
boot dev
```
Open your browser to [http://localhost:8081](http://localhost:8081)

To see server reloading in action, change 'todomvc.server/index to the following and reload your browser:
```clojure
(defn index [req]
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body "Hello auto-reloading!"}
      )
```
To connect to the BREPL, open a new terminal and:
```bash
boot repl -c
```
then
```clojure
(start-repl)
(js/alert "Hello BREPL!")
```
