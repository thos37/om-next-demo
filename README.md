
# Om TodoMVC Example

> Om is a ClojureScript UI component library over React.

> _[Om - github.com/swannodette/om](http://github.com/swannodette/om)_

This is a fork of David Nolen's om-next-demo that uses Boot to run the server, auto-reload CLJ and CLJS files, and runs a browser (B)REPL.  

To run it:
```bash
boot dev
```
And open your browser to [http://localhost:8081](http://localhost:8081)

To connect to the BREPL, open a new terminal and:
```bash
boot repl -c
```
then
```clojure
(start-repl)
```
