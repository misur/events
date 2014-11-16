(ns event-spec.views.layout
  (:require [hiccup.page :refer [html5 include-css]]))

(defn common [& body]
  (html5
    [:head
   [:meta {:charset "utf-8"}]
   [:meta
    {:content "width=device-width, initial-scale=1.0",
     :name "viewport"}]
   [:meta
    {:content "Bootbusiness | Short description about company",
     :name "description"}]
   [:meta {:content "Your name", :name "author"}]
   [:title "Bootbusiness | Give unique title of the page here"]
   "<!-- Bootstrap -->"
   [:link {:rel "stylesheet", :href "css/bootstrap.min.css"}]
   "<!-- Bootstrap responsive -->"
   [:link
    {:rel "stylesheet", :href "css/bootstrap-responsive.min.css"}]
   "<!-- Font awesome - iconic font with IE7 support -->"
   " \n    "
   [:link {:rel "stylesheet", :href "css/font-awesome.css"}]
   [:link {:rel "stylesheet", :href "css/font-awesome-ie7.css"}]
   "<!-- Bootbusiness theme -->"
   [:link {:rel "stylesheet", :href "css/boot-business.css"}]]


    [:body
   "<!-- Start: HEADER -->"
   [:header
    "<!-- Start: Navigation wrapper -->"
    [:div.navbar.navbar-fixed-top
     [:div.navbar-inner
      [:div.container
       [:a.brand.brand-bootbus {:href "index.html"} "Bootbusiness"]
       "<!-- Below button used for responsive navigation -->"
       [:button.btn.btn-navbar
        {:data-target ".nav-collapse",
         :data-toggle "collapse",
         :type "button"}
        [:span.icon-bar]
        [:span.icon-bar]
        [:span.icon-bar]]
       "<!-- Start: Primary navigation -->"
       [:div.nav-collapse.collapse
        "        \n              "
        [:ul.nav.pull-right
         [:li.dropdown
          [:a.dropdown-toggle
           {:data-toggle "dropdown", :href "#"}
           "Products and Services"
           [:b.caret]]
          [:ul.dropdown-menu
           [:li.nav-header "PRODUCTS"]
           [:li [:a {:href "product.html"} "Product1"]]
           [:li [:a {:href "product.html"} "Product2"]]
           [:li [:a {:href "product.html"} "Product3"]]
           [:li [:a {:href "all_products.html"} "All products"]]
           "             \n                    "
           [:li.divider]
           [:li.nav-header "SERVICES"]
           [:li [:a {:href "service.html"} "Service1"]]
           [:li [:a {:href "service.html"} "Service2"]]
           [:li [:a {:href "service.html"} "Service3"]]
           [:li [:a {:href "all_services.html"} "All services"]]]
          "                  \n                "]
         [:li.dropdown
          [:a.dropdown-toggle
           {:data-toggle "dropdown", :href "#"}
           "About"
           [:b.caret]]
          [:ul.dropdown-menu
           [:li [:a {:href "our_works.html"} "Our works"]]
           [:li [:a {:href "patnerships.html"} "Parnerships"]]
           [:li [:a {:href "leadership.html"} "Leadership"]]
           [:li [:a {:href "news.html"} "News"]]
           [:li [:a {:href "events.html"} "Events"]]
           [:li [:a {:href "blog.html"} "Blog"]]]]
         [:li [:a {:href "faq.html"} "FAQ"]]
         [:li [:a {:href "contact_us.html"} "Contact us"]]
         [:li [:a.active-link {:href "signup.html"} "Sign up"]]
         [:li [:a {:href "signin.html"} "Sign in"]]]]]]]
    "<!-- End: Navigation wrapper -->"
    "   \n    "]
   "<!-- End: HEADER -->"
   "<!-- Start: MAIN CONTENT -->"
   [:div.content
    [:div.container
     ]]
   "<!-- End: MAIN CONTENT -->"
   "<!-- Start: FOOTER -->"
   [:footer
    [:div.container
     [:div.row
      [:div.span2
       [:h4 [:i.icon-star.icon-white] " Products"]
       [:nav
        [:ul.quick-links
         [:li [:a {:href "product.html"} "Product1"]]
         [:li [:a {:href "product.html"} "Product2"]]
         [:li [:a {:href "product.html"} "Product3"]]
         [:li [:a {:href "all_products.html"} "All products"]]]]
       [:h4 [:i.icon-cogs.icon-white] " Services"]
       [:nav
        [:ul.quick-links
         [:li [:a {:href "service.html"} "Service1"]]
         [:li [:a {:href "service.html"} "Service2"]]
         [:li [:a {:href "service.html"} "Service3"]]
         [:li [:a {:href "all_services.html"} "All services"]]
         "              \n              "]]]
      [:div.span2
       [:h4 [:i.icon-beaker.icon-white] " About"]
       [:nav
        [:ul.quick-links
         [:li [:a {:href "our_works.html"} "Our works"]]
         [:li [:a {:href "patnerships.html"} "Patnerships"]]
         [:li [:a {:href "leadership.html"} "Leadership"]]
         [:li [:a {:href "news.html"} "News"]]
         [:li [:a {:href "events.html"} "Events"]]
         [:li [:a {:href "blog.html"} "Blog"]]]
        [:ul]]
       "          \n          "]
      [:div.span2
       [:h4 [:i.icon-thumbs-up.icon-white] " Support"]
       [:nav
        [:ul.quick-links
         [:li [:a {:href "faq.html"} "FAQ"]]
         [:li [:a {:href "contact_us.html"} "Contact us"]]
         "            \n              "]]
       [:h4 [:i.icon-legal.icon-white] " Legal"]
       [:nav
        [:ul.quick-links
         [:li [:a {:href "#"} "License"]]
         [:li [:a {:href "#"} "Terms of Use"]]
         [:li [:a {:href "#"} "Privacy Policy"]]
         [:li [:a {:href "#"} "Security"]]
         "      \n              "]]
       "            \n          "]
      [:div.span3
       [:h4 "Get in touch"]
       [:div.social-icons-row
        [:a {:href "#"} [:i.icon-twitter]]
        [:a {:href "#"} [:i.icon-facebook]]
        [:a {:href "#"} [:i.icon-linkedin]]
        "                                         \n            "]
       [:div.social-icons-row
        [:a {:href "#"} [:i.icon-google-plus]]
        "              \n              "
        [:a {:href "#"} [:i.icon-github]]
        [:a
         {:href "mailto:soundar.rathinasamy@gmail.com"}
         [:i.icon-envelope]]
        "        \n            "]
       [:div.social-icons-row
        [:i.icon-phone.icon-large.phone-number]
        " +919750227877\n            "]]
      "      \n          "
      [:div.span3
       [:h4 "Get updated by email"]
       [:form
        [:input
         {:placeholder "Email address", :name "email", :type "text"}]
        [:input.btn.btn-primary
         {:value "Subscribe", :type "submit"}]]]]]
    [:hr.footer-divider]
    [:div.container
     [:p
      "\n          © 2012-3000 Bootbusiness, Inc. All Rights Reserved.\n        "]]]
   "<!-- End: FOOTER -->"
   [:script {:src "js/jquery.min.js", :type "text/javascript"}]
   [:script {:src "js/bootstrap.min.js", :type "text/javascript"}]
   [:script {:src "js/boot-business.js", :type "text/javascript"}]]))
