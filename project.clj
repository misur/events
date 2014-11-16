(defproject event-spec "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.5"]
                 [ring-server "0.3.1"]
                 [liberator "0.11.0"]
                 [hiccups "0.3.0"]
                 [lib-noir "0.8.3"]
                 [org.clojure/clojurescript "0.0-2227"]
                 [reagent "0.4.2"]
                 [com.datomic/datomic-free "0.9.4766"]
                 [com.cemerick/friend "0.2.0" ]
                 [midje "1.5.1"]
                 [domina "1.0.2"]
                 [cljs-ajax "0.2.3"]
                 [formative "0.8.8"]
                 [prismatic/dommy "0.1.1"]]
  :plugins [[lein-ring "0.8.10"]
            [lein-cljsbuild "1.0.3"]
            [lein-idefiles "0.2.0"]]
  :source-paths ["src/clj"]
  :ring {:handler event-spec.handler/app
         :init event-spec.handler/init
         :destroy event-spec.handler/destroy}
  :hooks [leiningen.cljsbuild]
  :aot :all
  :profiles
  {:production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.2.1"]]}
    :prod {:cljsbuild
                    {:builds
                     {:client {:compiler
                               {:optimizations :advanced
                                :preamble ^:replace ["reagent/react.min.js"]
                                :pretty-print false}}}}}
             :srcmap {:cljsbuild
                      {:builds
                       {:client {:compiler
                                 {:source-map "target/client.js.map"
                                  :source-map-path "client"}}}}}}
  :cljsbuild {:builds [{
                         :source-paths ["src/cljs"],
											   :compiler{
					                          :pretty-print true,
																    :output-to "resources/public/js/events.js",
																    :preamble ["reagent/react.js"],
																    :optimizations :whitespace}}]})
