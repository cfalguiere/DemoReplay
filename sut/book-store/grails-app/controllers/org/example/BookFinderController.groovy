package org.example

class BookFinderController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [bookFinderInstanceList: BookFinder.list(params), booksFoundTotal: 0]
    }

    def search = {
        def bookFinderInstance = new BookFinder(params)
        def booksFound = Book.find("from Book as b where b.author=:author",
                [author: bookFinderInstance.searchPhrase],
                [cache: true])

        render(view: "list", model: [booksFound: booksFound, booksFoundTotal: booksFound.count()])
    }

}
