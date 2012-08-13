package org.example

class BookFinder {
    String searchPhrase

    static constraints = {
        searchPhrase(blank: false)
    }
}
